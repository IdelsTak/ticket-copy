/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.models;

import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hiram K.
 */
public class EventsTableModel extends DefaultTableModel {

    private static final Logger LOG = Logger.getLogger(EventsTableModel.class.getName());
    private static final long serialVersionUID = -6515825795700587750L;
    private final String[] columnNames = {
        "Id",
        "Type",
        "Name",
        "Venue",
        "Date & Time",
        "Ticket Price",
        "Remarks"};

    public EventsTableModel() {
        for (String columnName : columnNames) {
            super.addColumn(columnName);
        }
    }
}
