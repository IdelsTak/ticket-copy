/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.controllers;

import com.ct.database.Connection;
import com.ct.models.CustomerModel;
import com.ct.models.CustomerTableModel;
import com.ct.models.EventModel;
import com.ct.views.CustomersWindow;
import com.github.lgooddatepicker.components.DatePicker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
import javax.swing.SwingWorker;
import static javax.swing.SwingWorker.StateValue.DONE;

/**
 *
 * @author Hiram K.
 */
public class CustomersWindowController {

    private static final Logger LOG = Logger.getLogger(CustomersWindowController.class.getName());
    private final CustomersWindow customersWindow;
    private final CustomerTableModel customerTableModel;
    private final JTable customersTable;
    private final Map<Integer, CustomerModel> customersCache = new TreeMap<>();
    private final JButton addButton;
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
        this.customersWindow = new CustomersWindow();
        this.customerTableModel = new CustomerTableModel();

        this.customersTable = customersWindow.getCustomersTable();
        this.addButton = customersWindow.getAddButton();
        this.idTextField = customersWindow.getIdTextField();
        this.nameTextField = customersWindow.getNameTextField();
        this.phoneTextField = customersWindow.getPhoneTextField();
        this.eventsComboBox = customersWindow.getEventsComboBox();
        this.noOfTicketsTextField = customersWindow.getNoOfTicketsTextField();
        this.ticketsTotalTextField = customersWindow.getTicketsTotalTextField();
        this.bookingDatePicker = customersWindow.getBookingDatePicker();
        this.paidCheckBox = customersWindow.getPaidCheckBox();
        this.issuedCheckBox = customersWindow.getIssuedCheckBox();

        customersTable.setModel(customerTableModel);

        SwingWorker<Map<Integer, CustomerModel>, Void> customersLoader = new CustomersLoader();

        customersLoader.addPropertyChangeListener(event -> {
            if (event.getPropertyName().equals("state")
                    && event.getNewValue().equals(DONE)) {
                try {
                    Map<Integer, CustomerModel> result = customersLoader.get();
                    result.forEach(customersCache::putIfAbsent);

                    customersCache.values().forEach(customer -> {
                        customerTableModel.addRow(new Object[]{
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

        customersLoader.execute();

        var eventModels = new EventsWindowController().getEventModels();

        LOG.log(Level.INFO, "Events to add to combobox = [{0}]", eventModels.size());

        eventModels.forEach((eventModel) -> {
            eventsComboBox.addItem(eventModel);
        });

        eventsComboBox.setSelectedIndex(-1);

        addButton.addActionListener(new AddCustomerToDatabase());
    }

    public CustomersWindow getCustomersWindow() {
        return customersWindow;
    }

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

    private static class CustomersLoader extends SwingWorker<Map<Integer, CustomerModel>, Void> {

        @Override
        protected Map<Integer, CustomerModel> doInBackground() throws Exception {
            Map<Integer, CustomerModel> cache = new TreeMap<>();

            var connection = Connection.getConnection();

            if (connection != null) {
                String sql = "SELECT * FROM customer";

                try (PreparedStatement ps = connection.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {

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

                        var eventModels = new EventsWindowController().getEventModels();
                        EventModel eventModel = null;

                        for (var event : eventModels) {
                            if (event_id != null
                                    && event.getEventId().equals(event_id)) {
                                eventModel = event;
                                break;
                            }
                        }

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

                        cache.put(id, customerModel);
                    }

                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }

            LOG.log(Level.INFO, "No. of customers in database = [{0}]", cache.size());

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
                              : new BigDecimal(totalCost.trim());
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
                                        customerTableModel.addRow(new Object[]{
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

}
