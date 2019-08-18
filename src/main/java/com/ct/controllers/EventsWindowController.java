/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.controllers;

import com.ct.database.Connection;
import com.ct.models.EventModel;
import com.ct.models.EventsTableModel;
import com.ct.views.EventsWindow;
import com.github.lgooddatepicker.components.DateTimePicker;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.text.MessageFormat;
import java.time.LocalDateTime;
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
 *
 * @author Hiram K.
 */
public class EventsWindowController {

    private static final Logger LOG = Logger.getLogger(EventsWindowController.class.getName());
    private final EventsWindow eventsWindow;
    private final JTable eventsTable;
    private final EventsTableModel eventsTableModel;
    private int selectedRow = -1;
    private final Map<String, EventModel> eventsCache = new TreeMap<>();
    private final JTextField idTextField;
    private final JTextArea remarksTextArea;
    private final JTextField priceTextField;
    private final DateTimePicker dateTimePicker;
    private final JTextField venueTextField;
    private final JTextField nameTextField;
    private final JComboBox<String> typeComboBox;
    private final JButton addButton;

    public EventsWindowController() {
        this.eventsWindow = new EventsWindow();

        this.eventsTable = eventsWindow.getEventsTable();
        this.eventsTableModel = new EventsTableModel();

        this.idTextField = eventsWindow.getIdTextField();
        this.remarksTextArea = eventsWindow.getRemarksTextArea();
        this.priceTextField = eventsWindow.getPriceTextField();
        this.dateTimePicker = eventsWindow.getDateTimePicker();
        this.venueTextField = eventsWindow.getVenueTextField();
        this.nameTextField = eventsWindow.getNameTextField();
        this.typeComboBox = eventsWindow.getTypeComboBox();
        this.addButton = eventsWindow.getAddButton();

        ListSelectionModel selectionModel = eventsTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        selectionModel.addListSelectionListener(event -> {
            int[] selectedRows = eventsTable.getSelectedRows();

            for (int i = 0; i < selectedRows.length; i++) {
                selectedRow = selectedRows[i];
            }

            LOG.log(Level.INFO, "Selected row = [{0}]", selectedRow);

            if (selectedRow >= 0) {
                String id = (String) eventsTableModel.getValueAt(selectedRow, 0);
                String type = (String) eventsTableModel.getValueAt(selectedRow, 1);
                String name = (String) eventsTableModel.getValueAt(selectedRow, 2);
                String venue = (String) eventsTableModel.getValueAt(selectedRow, 3);
                LocalDateTime dateTime = (LocalDateTime) eventsTableModel.getValueAt(selectedRow, 4);
                BigDecimal price = (BigDecimal) eventsTableModel.getValueAt(selectedRow, 5);
                String remarks = (String) eventsTableModel.getValueAt(selectedRow, 6);

                idTextField.setText(id);
                typeComboBox.setSelectedItem(type);
                nameTextField.setText(name);
                venueTextField.setText(venue);
                dateTimePicker.setDateTimeStrict(dateTime);
                priceTextField.setText(price.toString());
                remarksTextArea.setText(remarks);
            }

        });

        SwingWorker<Map<String, EventModel>, Void> worker = new SwingWorkerImpl();

        worker.addPropertyChangeListener(changeEvent -> {
            if (changeEvent.getPropertyName().equals("state")
                    && changeEvent.getNewValue().equals(DONE)) {
                try {
                    Map<String, EventModel> result = worker.get();
                    result.forEach(eventsCache::putIfAbsent);

                    LOG.log(Level.INFO, "Events cache: [{0}]", eventsCache.values());

                    eventsCache.values().forEach(eventModel -> {
                        eventsTableModel.addRow(new Object[]{
                            eventModel.getEventId(),
                            eventModel.getEventType(),
                            eventModel.getEventName(),
                            eventModel.getVenue(),
                            eventModel.getDateTime(),
                            eventModel.getTicketPrice(),
                            eventModel.getRemark()
                        });
                    });

                    eventsTable.setModel(eventsTableModel);
                } catch (InterruptedException
                         | ExecutionException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        });
        //Start the background thread
        worker.execute();

        //Create an action for adding events to the database
        //and attach it to the addButton
        addButton.addActionListener(actionEvent -> {
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
            var name = nameTextField.getText();
            var venue = venueTextField.getText();
            var dateTime = dateTimePicker.getDateTimePermissive();

            if (dateTime == null) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        "Please provide a valid date/time",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            var priceText = priceTextField.getText();

            BigDecimal price;

            try {
                price = priceText == null || priceText.isBlank()
                        ? BigDecimal.ZERO
                        : new BigDecimal(priceText.trim());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(
                        eventsWindow,//Parent component
                        "Please provide a valid price",//Message
                        "Error",//title
                        JOptionPane.ERROR_MESSAGE);//Message type
                return;
            }

            var remarks = remarksTextArea.getText();

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

                    int updatedRows = ps.executeUpdate();

                    LOG.log(Level.INFO, "Updated rows = [{0}]", updatedRows);

                    resetTableSelection();

                    SwingWorker<Map<String, EventModel>, Void> swingWorker = new SwingWorkerImpl();

                    swingWorker.addPropertyChangeListener(changeEvent -> {
                        if (changeEvent.getPropertyName().equals("state")
                                && changeEvent.getNewValue().equals(DONE)) {
                            try {
                                Map<String, EventModel> result = swingWorker.get();
                                result.forEach(eventsCache::putIfAbsent);

                                LOG.log(Level.INFO, "Events cache: [{0}]", eventsCache.values());

                                eventsTableModel.addRow(new Object[]{
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
                    swingWorker.execute();

                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }

        });
    }

    public EventsWindow getEventsWindow() {
        return eventsWindow;
    }

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

    private static class SwingWorkerImpl extends SwingWorker<Map<String, EventModel>, Void> {

        @Override
        protected Map<String, EventModel> doInBackground() throws Exception {
            Map<String, EventModel> cache = new TreeMap<>();

            var connection = Connection.getConnection();

            if (connection != null) {
                String sql = "SELECT * FROM event";

                try (PreparedStatement ps = connection.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {

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

                        LOG.log(Level.INFO, "Loaded event from database: [{0}]", eventModel);

                        cache.put(id, eventModel);
                    }

                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }

            LOG.log(Level.INFO, "Nos. of events in db = [{0}]", cache.size());

            return cache;
        }
    }

}
