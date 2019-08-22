package com.ct.controllers;

import com.ct.database.Connection;
import com.ct.models.CustomerModel;
import com.ct.models.EventModel;
import com.ct.views.EventsTableDesign;
import com.ct.views.EventsWindow;
import com.ct.views.RoundedDecimal;
import com.github.lgooddatepicker.components.DateTimePicker;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;
import static javax.swing.SwingWorker.StateValue.DONE;

/**
 * A controller class that manages the interactions between the view,
 * <code>{@link EventsWindow}</code>, and the model,
 * <code>{@link EventModel}</code>.
 *
 * @author admin
 */
public class EventsWindowController {

    /**
     * The class's logger.
     */
    private static final Logger LOG = Logger.getLogger(EventsWindowController.class.getName());
    /**
     * The view object.
     */
    private final EventsWindow eventsWindow;
    /**
     * A <code>{@link JTable}</code> retrieved from the
     * <code>{@link EventsWindow}</code> that displays details of
     * <code>{@link EventModel}</code>s.
     */
    private final JTable eventsTable;
    /**
     * This is the <code>{@link DefaultTableModel}</code> of a table containing
     * <code>{@link EventModel}</code> properties.
     *
     * @see EventsTableDesign
     */
    private final EventsTableDesign eventsTableDesign;
    /**
     * The index of the row selected in the <code>{@link #eventsTable}</code>.
     */
    private int selectedRow = -1;
    /**
     * A local cache of the available event details as provided by the
     * <code>{@link EventModel}</code>s.
     */
    private final Map<String, EventModel> eventsCache = new TreeMap<>();
    /**
     * A <code>{@link JTextField}</code> retrieved from the
     * <code>{@link EventsWindow}</code> that displays the id property of a
     * selected <code>{@link EventModel}</code>.
     */
    private final JTextField idTextField;
    /**
     * A <code>{@link JTextField}</code> retrieved from the
     * <code>{@link EventsWindow}</code> that displays the remark property of a
     * selected <code>{@link EventModel}</code>.
     */
    private final JTextArea remarksTextArea;
    /**
     * A <code>{@link JTextField}</code> retrieved from the
     * <code>{@link EventsWindow}</code> that displays the ticket price property
     * of a selected <code>{@link EventModel}</code>.
     */
    private final JTextField priceTextField;
    /**
     * A <code>{@link DateTimePicker}</code> retrieved from the
     * <code>{@link EventsWindow}</code> that displays the date and time
     * property of a selected <code>{@link EventModel}</code>.
     */
    private final DateTimePicker dateTimePicker;
    /**
     * A <code>{@link JTextField}</code> retrieved from the
     * <code>{@link EventsWindow}</code> that displays the venue property of a
     * selected <code>{@link EventModel}</code>.
     */
    private final JTextField venueTextField;
    /**
     * A <code>{@link JTextField}</code> retrieved from the
     * <code>{@link EventsWindow}</code> that displays the name property of a
     * selected <code>{@link EventModel}</code>.
     */
    private final JTextField nameTextField;
    /**
     * A <code>{@link JComboBox}</code> retrieved from the
     * <code>{@link EventsWindow}</code> that displays the event type property
     * of a selected <code>{@link EventModel}</code>.
     */
    private final JComboBox<String> typeComboBox;
    /**
     * A <code>{@link JButton}</code> retrieved from the
     * <code>{@link EventsWindow}</code> that is used to add a selected
     * <code>{@link EventModel}</code> to database.
     */
    private final JButton addButton;
    /**
     * A <code>{@link JButton}</code> retrieved from the
     * <code>{@link EventsWindow}</code> that is used to add a update the
     * details of a selected <code>{@link EventModel}</code> in database.
     */
    private final JButton updateButton;
    /**
     * A <code>{@link JButton}</code> retrieved from the
     * <code>{@link EventsWindow}</code> that is used to delete a selected
     * <code>{@link EventModel}</code> from database.
     */
    private final JButton deleteButton;

    /**
     * Default constructor for the <code>{@link EventsWindowController}</code>
     * class.
     */
    public EventsWindowController() {
        //Create the events window to display
        //events data
        this.eventsWindow = new EventsWindow();
        //Get the table where we'll add the events
        //details as a master view
        this.eventsTable = eventsWindow.getEventsTable();
        this.eventsTableDesign = new EventsTableDesign();
        //Get the fields which we will use to create
        //the events details' view
        this.idTextField = eventsWindow.getIdTextField();
        this.remarksTextArea = eventsWindow.getRemarksTextArea();
        this.priceTextField = eventsWindow.getPriceTextField();
        this.dateTimePicker = eventsWindow.getDateTimePicker();
        this.venueTextField = eventsWindow.getVenueTextField();
        this.nameTextField = eventsWindow.getNameTextField();
        this.typeComboBox = eventsWindow.getTypeComboBox();
        this.addButton = eventsWindow.getAddButton();
        this.updateButton = eventsWindow.getUpdateButton();
        this.deleteButton = eventsWindow.getDeleteButton();
        //make the events table only get one event
        //selected at a time
        ListSelectionModel selectionModel = eventsTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        //Listen to selections and update the details
        //view accordingly
        selectionModel.addListSelectionListener(event -> {
            int[] selectedRows = eventsTable.getSelectedRows();

            for (int i = 0; i < selectedRows.length; i++) {
                selectedRow = selectedRows[i];
            }
            //only continue if we have a selection
            if (selectedRow >= 0) {
                String id = (String) eventsTableDesign.getValueAt(selectedRow, 0);
                String type = (String) eventsTableDesign.getValueAt(selectedRow, 1);
                String name = (String) eventsTableDesign.getValueAt(selectedRow, 2);
                String venue = (String) eventsTableDesign.getValueAt(selectedRow, 3);
                LocalDateTime dateTime = (LocalDateTime) eventsTableDesign.getValueAt(selectedRow, 4);
                BigDecimal price = (BigDecimal) eventsTableDesign.getValueAt(selectedRow, 5);
                String remarks = (String) eventsTableDesign.getValueAt(selectedRow, 6);

                idTextField.setText(id);
                typeComboBox.setSelectedItem(type);
                nameTextField.setText(name);
                venueTextField.setText(venue);
                dateTimePicker.setDateTimeStrict(dateTime);
                priceTextField.setText(price.toString());
                remarksTextArea.setText(remarks);
            }

        });
        //create the swing worker that will load
        //the events in a background thread
        var loader = new EventsLoader();
        //Listen to whether the loader has finished
        //executing its background task
        loader.addPropertyChangeListener(changeEvent -> {
            if (changeEvent.getPropertyName().equals("state")
                    && changeEvent.getNewValue().equals(DONE)) {
                try {
                    Map<String, EventModel> result = loader.get();
                    //Extract and load the events data that has
                    //been got from a background thread into
                    //the local cache
                    result.forEach(eventsCache::putIfAbsent);
                    //Populate the events table with the data
                    //that has been loaded from database
                    eventsCache.values().forEach(eventModel -> {
                        eventsTableDesign.addRow(new Object[]{
                            eventModel.getEventId(),
                            eventModel.getEventType(),
                            eventModel.getEventName(),
                            eventModel.getVenue(),
                            eventModel.getDateTime(),
                            eventModel.getTicketPrice(),
                            eventModel.getRemark()
                        });
                    });

                    eventsTable.setModel(eventsTableDesign);
                } catch (InterruptedException
                         | ExecutionException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        });
        //Start the background thread
        loader.execute();

        //Create an action for adding events to the database
        //and attach it to the addButton
        addButton.addActionListener(new AddEventToDatabase());
        //Create an action for modifying events that are
        //in the database and then attach it to the update button
        updateButton.addActionListener(new UpdateEventInDatabase());
        //Create an action that deletes a selected event from
        //database, then attach the action to the delete button
        deleteButton.addActionListener(new DeleteEventFromDatabase());

    }

    /**
     * Exposes the collection of events that are in database to any clients that
     * may be interested.
     * <p>
     * For instance, the {@link CustomersWindowController} may ask for the list
     * of events so that it can create customer models.
     *
     * @return a list of events loaded from database.
     */
    public Collection<EventModel> getEventModels() {
        var loader = new EventsLoader();
        loader.execute();

        try {
            return loader.get().values();
        } catch (InterruptedException
                 | ExecutionException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        //Just return an empty list if there
        //was a problem retrieving the data
        return Collections.emptyList();
    }

    /**
     * Retrieves the view object that this controller manages.
     *
     * @return the view object that this controller manages.
     */
    public EventsWindow getEventsWindow() {
        return eventsWindow;
    }

    /**
     * Remove all the data in the details view.
     */
    private void resetTableSelection() {
        selectedRow = -1;
        idTextField.setText(null);
        typeComboBox.setSelectedIndex(-1);
        nameTextField.setText(null);
        venueTextField.setText(null);
        dateTimePicker.clear();
        priceTextField.setText(null);
        remarksTextArea.setText(null);
    }

    /**
     * Loads the events data in a background thread.
     */
    private static class EventsLoader extends SwingWorker<Map<String, EventModel>, Void> {

        @Override
        protected Map<String, EventModel> doInBackground() throws Exception {
            Map<String, EventModel> cache = new TreeMap<>();
            var connection = Connection.getConnection();
            //Only continue if a valid database
            //connection is available
            if (connection != null) {
                String sql = "SELECT * FROM event";

                try (PreparedStatement ps = connection.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {
                    //Scan all rows and use their data
                    //to create events models
                    while (rs.next()) {
                        var id = rs.getString("event_id");
                        var type = rs.getString("event_type");
                        var name = rs.getString("event_name");
                        var venue = rs.getString("venue");
                        var date = rs.getDate("event_date");
                        var time = rs.getTime("event_time");
                        var price = rs.getFloat("ticket_price");
                        var remarks = rs.getString("remark");

                        var dateTime = LocalDateTime.of(
                                date.toLocalDate(),
                                time.toLocalTime());

                        var eventModel = new EventModel(
                                id,
                                type,
                                name,
                                venue,
                                dateTime,
                                BigDecimal.valueOf(price),
                                remarks);

                        cache.put(id, eventModel);
                    }

                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }

            return cache;
        }
    }

    /**
     * Adds the details of selected <code>{@link EventModel}</code> as a new
     * database row.
     */
    private class AddEventToDatabase implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            var id = idTextField.getText();
            if (eventsCache.keySet().contains(id)) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        MessageFormat.format("Id: {0} exists already. Please enter a new Id value.", id),//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }
            var type = (String) typeComboBox.getSelectedItem();

            if (type == null) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        "Please select an event type",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            var name = nameTextField.getText();

            if (name == null || name.isBlank()) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        "Please enter the event's name",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            var venue = venueTextField.getText();
            var dateTime = dateTimePicker.getDateTimePermissive();
            var priceText = priceTextField.getText();

            BigDecimal price;

            try {
                price = priceText == null || priceText.isBlank()
                        ? BigDecimal.ZERO
                        : new RoundedDecimal()
                                .toTwoDecimalPlaces(
                                        new BigDecimal(priceText.trim()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        "Please provide a valid price",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            if (price.equals(BigDecimal.ZERO)) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        "Please enter the ticket price",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            var remarks = remarksTextArea.getText();

            if (remarks == null || remarks.isBlank()) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        "Please enter the remarks",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            var connection = Connection.getConnection();

            if (connection != null) {

                String sql = "INSERT INTO event "
                        + "(event_id, event_type, event_name, venue, event_date, event_time, ticket_price, remark) "
                        + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

                try (PreparedStatement ps = connection.prepareStatement(sql)) {

                    ps.setString(1, id);
                    ps.setString(2, type);
                    ps.setString(3, name);
                    ps.setString(4, venue);
                    ps.setDate(5, Date.valueOf(dateTime.toLocalDate()));
                    ps.setTime(6, Time.valueOf(dateTime.toLocalTime()));
                    ps.setFloat(7, price.floatValue());
                    ps.setString(8, remarks);

                    int addedRows = ps.executeUpdate();

                    LOG.log(Level.INFO, "Added rows = [{0}]", addedRows);

                    resetTableSelection();

                    SwingWorker<Map<String, EventModel>, Void> eventsLoader = new EventsLoader();

                    eventsLoader.addPropertyChangeListener(changeEvent -> {
                        if (changeEvent.getPropertyName().equals("state")
                                && changeEvent.getNewValue().equals(DONE)) {
                            try {
                                Map<String, EventModel> result = eventsLoader.get();
                                result.forEach(eventsCache::putIfAbsent);

                                LOG.log(Level.INFO, "Events cache: [{0}]", eventsCache.values());

                                eventsTableDesign.addRow(new Object[]{
                                    id,
                                    type,
                                    name,
                                    venue,
                                    dateTime,
                                    price,
                                    remarks
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
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Updates the details of selected <code>{@link EventModel}</code> in an
     * existing database row.
     */
    private class UpdateEventInDatabase implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            var id = idTextField.getText();

            var type = (String) typeComboBox.getSelectedItem();

            if (type == null) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        "Please select an event type",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            var name = nameTextField.getText();

            if (name == null || name.isBlank()) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        "Please enter the event's name",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            var venue = venueTextField.getText();
            var dateTime = dateTimePicker.getDateTimePermissive();
            var priceText = priceTextField.getText();

            BigDecimal price;

            try {
                price = priceText == null || priceText.isBlank()
                        ? BigDecimal.ZERO
                        : new RoundedDecimal()
                                .toTwoDecimalPlaces(
                                        new BigDecimal(priceText.trim()));
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        "Please provide a valid price",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            if (price.equals(BigDecimal.ZERO)) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        "Please enter the ticket price",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            var remarks = remarksTextArea.getText();

            if (remarks == null || remarks.isBlank()) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        "Please enter the remarks",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            var connection = Connection.getConnection();

            if (connection != null) {
                String sql = "UPDATE event "
                        + "SET event_type = ?, "
                        + "event_name = ?, "
                        + "venue = ?, "
                        + "event_date = ?, "
                        + "event_time = ?, "
                        + "ticket_price = ?, "
                        + "remark = ? "
                        + "WHERE event_id = ?";

                try (PreparedStatement ps = connection.prepareStatement(sql)) {

                    ps.setString(1, type);
                    ps.setString(2, name);
                    ps.setString(3, venue);
                    ps.setDate(4, dateTime != null
                                  ? Date.valueOf(dateTime.toLocalDate())
                                  : null);
                    ps.setTime(5, dateTime != null
                                  ? Time.valueOf(dateTime.toLocalTime())
                                  : null);
                    ps.setFloat(6, price.floatValue());
                    ps.setString(7, remarks);
                    ps.setString(8, id);

                    int updatedRows = ps.executeUpdate();

                    LOG.log(Level.INFO, "Updated rows = [{0}]", updatedRows);

                    final int row = selectedRow;

                    resetTableSelection();

                    SwingWorker<Map<String, EventModel>, Void> eventsLoader = new EventsLoader();

                    eventsLoader.addPropertyChangeListener(changeEvent -> {
                        if (changeEvent.getPropertyName().equals("state")
                                && changeEvent.getNewValue().equals(DONE)) {
                            try {
                                Map<String, EventModel> result = eventsLoader.get();
                                result.forEach(eventsCache::putIfAbsent);

                                LOG.log(Level.INFO, "Events cache: [{0}]", eventsCache.values());

                                //Only remove/insert a row if there
                                //was a selected row to start with
                                if (row >= 0) {
                                    eventsTableDesign.removeRow(row);

                                    eventsTableDesign.insertRow(row, new Object[]{
                                        id,
                                        type,
                                        name,
                                        venue,
                                        dateTime,
                                        price,
                                        remarks
                                    });
                                }

                            } catch (InterruptedException
                                     | ExecutionException ex) {
                                LOG.log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    //Start the background thread
                    eventsLoader.execute();

                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Deletes the details of selected <code>{@link EventModel}</code> from
     * database.
     */
    private class DeleteEventFromDatabase implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            var id = idTextField.getText();
            //Do not delete the event if there's
            //a customer who's booked this event
            int result = JOptionPane.showConfirmDialog(
                    eventsWindow,
                    MessageFormat.format("Are you sure you want to delete event {0}?", id),
                    "Confirm Event Deletion",
                    JOptionPane.YES_NO_CANCEL_OPTION);

            if (result != JOptionPane.YES_OPTION) {
                return;
            }

            var customersWindowController = new CustomersWindowController();
            var customerModels = customersWindowController.getCustomerModels();
            boolean canDelete = true;

            for (CustomerModel customerModel : customerModels) {
                EventModel event = customerModel.getEvent();
                if (event != null && event.getEventId().equals(id)) {
                    canDelete = false;
                    break;
                }
            }

            if (!canDelete) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        "Event should not be deleted. It has been booked already.",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            var connection = Connection.getConnection();

            if (connection != null) {
                String sql = "DELETE FROM event WHERE event_id = ?";

                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, id);

                    int deletedRows = ps.executeUpdate();

                    LOG.log(Level.INFO, "Deleted rows = [{0}]", deletedRows);

                    final int row = selectedRow;

                    resetTableSelection();

                    SwingWorker<Map<String, EventModel>, Void> eventsLoader = new EventsLoader();

                    eventsLoader.addPropertyChangeListener(changeEvent -> {
                        if (changeEvent.getPropertyName().equals("state")
                                && changeEvent.getNewValue().equals(DONE)) {
                            try {
                                Map<String, EventModel> map = eventsLoader.get();
                                map.forEach(eventsCache::putIfAbsent);

                                LOG.log(Level.INFO, "Events cache: [{0}]", eventsCache.values());

                                eventsTableDesign.removeRow(row);

                            } catch (InterruptedException
                                     | ExecutionException ex) {
                                LOG.log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    //Start the background thread
                    eventsLoader.execute();

                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        }
    }

}
