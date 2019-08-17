/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.models;

import java.util.HashSet;
import java.util.Set;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Hiram K.
 */
public class EventsTableModel extends AbstractTableModel {

    private static final long serialVersionUID = -6515825795700587750L;
    private Set<EventModel> events = new HashSet<>();
    private final String[] columnNames = {
        "Id",
        "Type",
        "Name",
        "Venue",
        "Date & Time",
        "Ticket Price",
        "Remarks"};

    public void setEvents(Set<EventModel> events) {
        this.events = events;
    }

    @Override
    public int getRowCount() {
        return events.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        EventModel event = events.toArray(new EventModel[events.size()])[rowIndex];

        switch (columnIndex) {
            case 0:
                return event.getEventId();
            case 1:
                return event.getEventType();
            case 2:
                return event.getEventName();
            case 3:
                return event.getVenue();
            case 4:
                return event.getDateTime();
            case 5:
                return event.getTicketPrice();
            case 6:
                return event.getRemark();
            default:
                throw new IllegalArgumentException("Column not known");

        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

}
