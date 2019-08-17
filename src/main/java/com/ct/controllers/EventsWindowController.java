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
import java.math.BigDecimal;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTable;
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

    public EventsWindowController() {
        this.eventsWindow = new EventsWindow();

        this.eventsTable = eventsWindow.getEventsTable();
        this.eventsTableModel = new EventsTableModel();

        ListSelectionModel selectionModel = eventsTable.getSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        selectionModel.addListSelectionListener(event -> {
            Object selectedData = null;
            int[] selectedRows = eventsTable.getSelectedRows();
//            int[] selectedColumns = eventsTable.getSelectedColumns();

            for (int i = 0; i < selectedRows.length; i++) {
                selectedRow = selectedRows[i];
//                for (int j = 0; j < selectedColumns.length; j++) {
//                    selectedRow = selectedRows[i];
//                    selectedData = eventsTable.getValueAt(
//                            selectedRows[i],
//                            selectedColumns[j]);
//                }
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

                eventsWindow.getIdTextField().setText(id);
                eventsWindow.getTypeComboBox().setSelectedItem(type);
                eventsWindow.getNameTextField().setText(name);
                eventsWindow.getVenueTextField().setText(venue);
                eventsWindow.getDateTimePicker().setDateTimeStrict(dateTime);
                eventsWindow.getPriceTextField().setText(price.toString());
                eventsWindow.getRemarksTextArea().setText(remarks);
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

                    eventsTableModel.setEvents(new HashSet<>(eventsCache.values()));
                    eventsTable.setModel(eventsTableModel);
                } catch (InterruptedException
                         | ExecutionException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        });

        worker.execute();
    }

    public EventsWindow getEventsWindow() {
        return eventsWindow;
    }

    private static class SwingWorkerImpl extends SwingWorker<Map<String, EventModel>, Void> {

        @Override
        protected Map<String, EventModel> doInBackground() throws Exception {
            Map<String, EventModel> cache = new TreeMap<>();

            var connection = Connection.getConnection();

            if (connection != null) {
                String sql = "select * from event";

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
