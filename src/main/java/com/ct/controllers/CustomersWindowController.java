/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.controllers;

import com.ct.database.Connection;
import com.ct.models.CustomerModel;
import com.ct.views.CustomerTableDesign;
import com.ct.models.EventModel;
import com.ct.views.CustomersWindow;
import com.github.lgooddatepicker.components.DatePicker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import static javax.swing.SwingWorker.StateValue.DONE;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Hiram K.
 */
public class CustomersWindowController {

    private static final Logger LOG = Logger.getLogger(CustomersWindowController.class.getName());
    private final CustomersWindow customersWindow;
    private final CustomerTableDesign customersTableDesign;
    private final JTable customersTable;
    private final Map<Integer, CustomerModel> customersCache = new TreeMap<>();
    private final JButton addButton;
    private final JButton updateButton;
    private final JButton deleteButton;
    private final JTextField idTextField;
    private final JTextField nameTextField;
    private final JTextField phoneTextField;
    private final JComboBox<EventModel> eventsComboBox;
    private final JTextField noOfTicketsTextField;
    private final JTextField ticketsTotalTextField;
    private final DatePicker bookingDatePicker;
    private final JCheckBox paidCheckBox;
    private final JCheckBox issuedCheckBox;
    private int selectedRow = -1;

    public CustomersWindowController() {
        //Create the customers window
        this.customersWindow = new CustomersWindow();
        //Create the customers table design
        this.customersTableDesign = new CustomerTableDesign();
        //Access all the fields that will be used
        //to edit a customer's data from the customers window
        this.customersTable = customersWindow.getCustomersTable();
        this.addButton = customersWindow.getAddButton();
        this.updateButton = customersWindow.getUpdateButton();
        this.deleteButton = customersWindow.getDeleteButton();
        this.idTextField = customersWindow.getIdTextField();
        this.nameTextField = customersWindow.getNameTextField();
        this.phoneTextField = customersWindow.getPhoneTextField();
        this.eventsComboBox = customersWindow.getEventsComboBox();
        this.noOfTicketsTextField = customersWindow.getNoOfTicketsTextField();
        this.ticketsTotalTextField = customersWindow.getTicketsTotalTextField();
        this.bookingDatePicker = customersWindow.getBookingDatePicker();
        this.paidCheckBox = customersWindow.getPaidCheckBox();
        this.issuedCheckBox = customersWindow.getIssuedCheckBox();
        //Create the table model for the customers table
        customersTable.setModel(customersTableDesign);
        //Retrieve the customer's table selection model
        //then force it to make only single selections
        //at a time
        ListSelectionModel selectionModel = customersTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Listen to changes in selection
        //Then update the edit fields with the
        //values from the selected customer
        selectionModel.addListSelectionListener(event -> {
            int[] selectedRows = customersTable.getSelectedRows();
            //Retrieve the selected row
            for (int i = 0; i < selectedRows.length; i++) {
                selectedRow = selectedRows[i];
            }
            //If there's a selection update the
            //fields with the values from the 
            //selected customer
            if (selectedRow >= 0) {
                int id = (int) customersTableDesign.getValueAt(selectedRow, 0);
                String name = (String) customersTableDesign.getValueAt(selectedRow, 1);
                int phone = (int) customersTableDesign.getValueAt(selectedRow, 2);
                EventModel eventModel = (EventModel) customersTableDesign.getValueAt(selectedRow, 3);
                int noOfTickets = (int) customersTableDesign.getValueAt(selectedRow, 4);
                BigDecimal totalCost = (BigDecimal) customersTableDesign.getValueAt(selectedRow, 5);
                LocalDate bookingDate = (LocalDate) customersTableDesign.getValueAt(selectedRow, 6);
                boolean paid = (boolean) customersTableDesign.getValueAt(selectedRow, 7);
                boolean issued = (boolean) customersTableDesign.getValueAt(selectedRow, 8);

                idTextField.setText(Integer.toString(id));
                nameTextField.setText(name);
                phoneTextField.setText(Integer.toString(phone));
                eventsComboBox.setSelectedItem(eventModel);
                noOfTicketsTextField.setText(Integer.toString(noOfTickets));
                ticketsTotalTextField.setText(totalCost.toString());
                bookingDatePicker.setDate(bookingDate);
                paidCheckBox.setSelected(paid);
                issuedCheckBox.setSelected(issued);
            }

        });
        var noOfTicketsDoc = noOfTicketsTextField.getDocument();

        noOfTicketsDoc.addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateTotalTicketCost();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateTotalTicketCost();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateTotalTicketCost();
            }

        });

        eventsComboBox.addItemListener(itemEvent -> {
            if (itemEvent.getStateChange() == ItemEvent.SELECTED) {
                calculateTotalTicketCost();
            }
        });

        //Create a swing worker named CustomersLoader
        //to retrieve customer data from database
        var customersLoader = new CustomersLoader();
        //Listen to changes in the worker's progress
        //If the thread finishes loading the data
        //update the customers table with the retrieved values
        customersLoader.addPropertyChangeListener(event -> {
            if (event.getPropertyName().equals("state")
                    && event.getNewValue().equals(DONE)) {
                try {
                    //Attach all the customers' data to a local cache
                    //for easier access
                    Map<Integer, CustomerModel> result = customersLoader.get();
                    result.forEach(customersCache::putIfAbsent);

                    customersCache.values().forEach(customer -> {
                        customersTableDesign.addRow(new Object[]{
                            customer.getId(),
                            customer.getName(),
                            customer.getPhone(),
                            customer.getEvent(),
                            customer.getNumberOfTickets(),
                            customer.getTotalCostOfTickets(),
                            customer.getBookingDate(),
                            customer.isPaid(),
                            customer.isIssued()

                        });
                    });
                } catch (InterruptedException
                         | ExecutionException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        });
        //Start loading the customers' data
        //in a background thread
        customersLoader.execute();
        //Retrieve the events from database
        //so that we can add them to the dropdown
        //which allows a customer to select an event
        var eventModels = new EventsWindowController().getEventModels();
        //Add all the events to the dropdown
        eventModels.forEach((eventModel) -> {
            eventsComboBox.addItem(eventModel);
        });
        //But don't select anything yet
        //Leave that to the user
        eventsComboBox.setSelectedIndex(-1);
        //create an action that will add customers to database
        //the attach it to the add button
        addButton.addActionListener(new AddCustomerToDatabase());
        //create an action that will update customers' details in database
        //then attach it to the update button
        updateButton.addActionListener(new UpdateCustomerInDatabase());
        //create an action that will delete customers from database
        //then attach it to the delete button
        deleteButton.addActionListener(new DeleteCustomerFromDatabase());
    }

    public CustomersWindow getCustomersWindow() {
        return customersWindow;
    }

    /**
     * Expose the customers who have been loaded to other classes too.
     * <p>
     * A class like {@link EventsWindowController} may need to read the list of
     * customers so that it determine whether it should delete an event.
     * <p>
     * Remember, an event that is associated with some customers should not be
     * deleted.
     *
     * @return a collection of customers from database.
     */
    public Collection<CustomerModel> getCustomerModels() {

        var customersLoader = new CustomersLoader();

        customersLoader.execute();

        try {
            return customersLoader.get().values();
        } catch (InterruptedException
                 | ExecutionException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        return Collections.emptyList();
    }

    /**
     * Resets all the values in the customer edit fields that may have been
     * changed due to a selection.
     */
    private void resetTableSelection() {
        selectedRow = -1;
        idTextField.setText(null);
        nameTextField.setText(null);
        phoneTextField.setText(null);
        eventsComboBox.setSelectedIndex(-1);
        noOfTicketsTextField.setText(null);
        ticketsTotalTextField.setText(null);
        bookingDatePicker.clear();
        paidCheckBox.setSelected(false);
        issuedCheckBox.setSelected(false);
    }
    /**
     * Calculates the total ticket cost.
     * 
     * total ticket cost = (ticket cost) x (no. of tickets)
     */
    private void calculateTotalTicketCost() {
        BigDecimal totalTicketCost = BigDecimal.ZERO;

        if (selectedRow >= 0 && eventsComboBox.getSelectedItem() != null) {
            var eventModel = (EventModel) eventsComboBox.getSelectedItem();

            if (eventModel != null) {
                var ticketPrice = eventModel.getTicketPrice();

                String noOfTickets = noOfTicketsTextField.getText();

                Integer numberOfTickets = null;

                try {
                    numberOfTickets = Integer.parseInt(noOfTickets);
                } catch (NumberFormatException numberFormatException) {
                }

                if (numberOfTickets != null) {

                    totalTicketCost = BigDecimal.valueOf(numberOfTickets).multiply(ticketPrice);
                }

            }
        }

        totalTicketCost = totalTicketCost.setScale(2, RoundingMode.HALF_UP);

        ticketsTotalTextField.setText(totalTicketCost.toPlainString());
    }

    /**
     * Loads the customers data from database and add it into a local cache.
     * <p>
     * It uses a background thread to avoid slowing down the GUI thread.
     */
    private static class CustomersLoader extends SwingWorker<Map<Integer, CustomerModel>, Void> {

        /**
         * Loads customers' data in a background thread.
         *
         * @return a cache of customer's data.
         *
         * @throws Exception if an interruption occurs during the execution of
         *                   the background task.
         */
        @Override
        protected Map<Integer, CustomerModel> doInBackground() throws Exception {
            Map<Integer, CustomerModel> cache = new TreeMap<>();

            var connection = Connection.getConnection();
            //Only continue if there's a connection to database
            if (connection != null) {
                String sql = "SELECT * FROM customer";

                try (PreparedStatement ps = connection.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {
                    //retrieve the data from all the rows
                    while (rs.next()) {
                        var id = rs.getInt("customer_id");
                        var event_id = rs.getString("event_id");
                        var name = rs.getString("customer_name");
                        var phone = rs.getInt("phone");
                        var numberofTickets = rs.getInt("number_of_tickets");
                        var totalTicketsCost = rs.getFloat("total_tickets_cost");
                        var paid = rs.getBoolean("paid");
                        var issued = rs.getBoolean("issued");
                        var bookingDate = rs.getDate("booking_date");
                        //Load the event models so that we can probably 
                        //assign one to the customer model we're about to create
                        var eventModels = new EventsWindowController().getEventModels();
                        EventModel eventModel = null;

                        for (var event : eventModels) {
                            if (event_id != null
                                    && event.getEventId().equals(event_id)) {
                                eventModel = event;
                                break;
                            }
                        }
                        //Create a customer model
                        var customerModel = new CustomerModel(
                                id,//id
                                eventModel,//event model
                                name,//name
                                phone,//phone
                                numberofTickets,//number of tickets
                                BigDecimal.valueOf(totalTicketsCost),//total cost of tickets
                                paid,//paid 
                                issued,//issued
                                bookingDate.toLocalDate()); //booking date
                        //Save the customer model in a local cache
                        cache.put(id, customerModel);
                    }

                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }

            return cache;
        }
    }

    private class AddCustomerToDatabase implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String name = nameTextField.getText();
            String phone = phoneTextField.getText();
            EventModel eventModel = (EventModel) eventsComboBox.getSelectedItem();
            String noOfTickets = noOfTicketsTextField.getText();
            String totalCost = ticketsTotalTextField.getText();
            LocalDate bookingDate = bookingDatePicker.getDate();
            boolean paid = paidCheckBox.isSelected();
            boolean issued = issuedCheckBox.isSelected();

            Integer phoneNumber;

            try {
                phoneNumber = Integer.parseInt(phone);
            } catch (NumberFormatException numberFormatException) {
                //Report error
                JOptionPane.showMessageDialog(
                        customersWindow,//Parent component
                        "Please provide a valid phone number",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            Integer numberOfTickets;

            try {
                numberOfTickets = Integer.parseInt(noOfTickets);
            } catch (NumberFormatException numberFormatException) {
                //Report error
                JOptionPane.showMessageDialog(
                        customersWindow,//Parent component
                        "Please provide a valid number of tickets",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            BigDecimal ticketsCost;

            try {
                ticketsCost = totalCost == null || totalCost.isBlank()
                              ? BigDecimal.ZERO
                              : new BigDecimal(totalCost.trim()).setScale(2, RoundingMode.HALF_UP);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        customersWindow,//Parent component
                        "Please provide a cost for the tickets",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            if (ticketsCost.equals(BigDecimal.ZERO)) {
                JOptionPane.showMessageDialog(
                        customersWindow,//Parent component
                        "Please enter the total cost of the tickets",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            if (bookingDate == null) {
                JOptionPane.showMessageDialog(
                        customersWindow,//Parent component
                        "Please enter the booking date",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            var connection = Connection.getConnection();

            if (connection != null) {
                String sql = "INSERT INTO customer "
                        + "(event_id, customer_name, phone, number_of_tickets, total_tickets_cost, paid, issued, booking_date) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, eventModel != null
                                    ? eventModel.getEventId()
                                    : null);
                    ps.setString(2, name);
                    ps.setInt(3, phoneNumber);
                    ps.setInt(4, numberOfTickets);
                    ps.setFloat(5, ticketsCost.floatValue());
                    ps.setBoolean(6, paid);
                    ps.setBoolean(7, issued);
                    ps.setDate(8, Date.valueOf(bookingDate));

                    int addedRows = ps.executeUpdate();

                    LOG.log(Level.INFO, "Added rows = [{0}]", addedRows);

                    resetTableSelection();

                    SwingWorker<Map<Integer, CustomerModel>, Void> loader = new CustomersLoader();

                    loader.addPropertyChangeListener(event -> {
                        if (event.getPropertyName().equals("state")
                                && event.getNewValue().equals(DONE)) {
                            try {
                                Map<Integer, CustomerModel> result = loader.get();
                                result.forEach((Integer key, CustomerModel value)
                                        -> {
                                    CustomerModel returnedModel = customersCache.putIfAbsent(key, value);
                                    //Map will return null if it adds
                                    //the new customer model to the cache
                                    if (returnedModel == null) {
                                        customersTableDesign.addRow(new Object[]{
                                            key,
                                            name,
                                            phoneNumber,
                                            eventModel,
                                            numberOfTickets,
                                            ticketsCost,
                                            bookingDate,
                                            paid,
                                            issued

                                        });
                                    }
                                });

                            } catch (InterruptedException
                                     | ExecutionException ex) {
                                LOG.log(Level.SEVERE, null, ex);
                            }
                        }
                    });

                    loader.execute();

                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }

            }

        }
    }

    private class UpdateCustomerInDatabase implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            String idText = idTextField.getText();
            String name = nameTextField.getText();
            String phone = phoneTextField.getText();
            EventModel eventModel = (EventModel) eventsComboBox.getSelectedItem();
            String noOfTickets = noOfTicketsTextField.getText();
            String totalCost = ticketsTotalTextField.getText();
            LocalDate bookingDate = bookingDatePicker.getDate();
            boolean paid = paidCheckBox.isSelected();
            boolean issued = issuedCheckBox.isSelected();

            Integer id = Integer.parseInt(idText);

            Integer phoneNumber;

            try {
                phoneNumber = Integer.parseInt(phone);
            } catch (NumberFormatException numberFormatException) {
                //Report error
                JOptionPane.showMessageDialog(
                        customersWindow,//Parent component
                        "Please provide a valid phone number",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            Integer numberOfTickets;

            try {
                numberOfTickets = Integer.parseInt(noOfTickets);
            } catch (NumberFormatException numberFormatException) {
                //Report error
                JOptionPane.showMessageDialog(
                        customersWindow,//Parent component
                        "Please provide a valid number of tickets",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            BigDecimal ticketsCost;

            try {
                ticketsCost = totalCost == null || totalCost.isBlank()
                              ? BigDecimal.ZERO
                              : new BigDecimal(totalCost.trim()).setScale(2, RoundingMode.HALF_UP);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        customersWindow,//Parent component
                        "Please provide a cost for the tickets",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            if (ticketsCost.equals(BigDecimal.ZERO)) {
                JOptionPane.showMessageDialog(
                        customersWindow,//Parent component
                        "Please enter the total cost of the tickets",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            if (bookingDate == null) {
                JOptionPane.showMessageDialog(
                        customersWindow,//Parent component
                        "Please enter the booking date",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            var connection = Connection.getConnection();

            if (connection != null) {
                String sql = "UPDATE customer "
                        + "SET event_id = ?, "
                        + "customer_name = ?, "
                        + "phone = ?, "
                        + "number_of_tickets = ?, "
                        + "total_tickets_cost = ?, "
                        + "paid = ?, "
                        + "issued = ?, "
                        + "booking_date = ? "
                        + "WHERE customer_id = ?";

                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, eventModel != null
                                    ? eventModel.getEventId()
                                    : null);
                    ps.setString(2, name);
                    ps.setInt(3, phoneNumber);
                    ps.setInt(4, numberOfTickets);
                    ps.setFloat(5, ticketsCost.floatValue());
                    ps.setBoolean(6, paid);
                    ps.setBoolean(7, issued);
                    ps.setDate(8, Date.valueOf(bookingDate));
                    ps.setInt(9, id);

                    int updatedRows = ps.executeUpdate();

                    LOG.log(Level.INFO, "Updated rows = [{0}]", updatedRows);

                    final int row = selectedRow;

                    resetTableSelection();

                    SwingWorker<Map<Integer, CustomerModel>, Void> eventsLoader = new CustomersLoader();

                    eventsLoader.addPropertyChangeListener(changeEvent -> {
                        if (changeEvent.getPropertyName().equals("state")
                                && changeEvent.getNewValue().equals(DONE)) {
                            try {
                                Map<Integer, CustomerModel> result = eventsLoader.get();
                                result.forEach(customersCache::putIfAbsent);

                                customersTableDesign.removeRow(row);

                                customersTableDesign.insertRow(row, new Object[]{
                                    id,
                                    name,
                                    phoneNumber,
                                    eventModel,
                                    numberOfTickets,
                                    ticketsCost,
                                    bookingDate,
                                    paid,
                                    issued
                                });

                            } catch (InterruptedException
                                     | ExecutionException ex) {
                                LOG.log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    //Start the background thread
                    eventsLoader.execute();

                } catch (SQLException ex) {
                    Logger.getLogger(CustomersWindowController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private class DeleteCustomerFromDatabase implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            var id = idTextField.getText();
            var name = nameTextField.getText();

            int result = JOptionPane.showConfirmDialog(
                    customersWindow,
                    MessageFormat.format(
                            "Are you sure you want to delete {0} - {1}?",
                            new Object[]{
                                id,
                                name}),
                    "Confirm Customer Deletion",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (result != JOptionPane.YES_OPTION) {
                return;
            }

            var connection = Connection.getConnection();

            if (connection != null) {
                String sql = "DELETE FROM customer WHERE customer_id = ?";

                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, id);

                    int deletedRows = ps.executeUpdate();

                    LOG.log(Level.INFO, "Deleted rows = [{0}]", deletedRows);

                    final int row = selectedRow;

                    resetTableSelection();

                    SwingWorker<Map<Integer, CustomerModel>, Void> customersLoader = new CustomersLoader();

                    customersLoader.addPropertyChangeListener(changeEvent -> {
                        if (changeEvent.getPropertyName().equals("state")
                                && changeEvent.getNewValue().equals(DONE)) {
                            try {
                                Map<Integer, CustomerModel> map = customersLoader.get();
                                map.forEach(customersCache::putIfAbsent);

                                customersTableDesign.removeRow(row);

                            } catch (InterruptedException
                                     | ExecutionException ex) {
                                LOG.log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    //Start the background thread
                    customersLoader.execute();
                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
