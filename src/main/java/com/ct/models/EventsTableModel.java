/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.models;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hiram K.
 */
public class EventsTableModel extends DefaultTableModel {

    private static final long serialVersionUID = -6515825795700587750L;
    private static final String[] COLUMN_NAMES = {
        "Id",
        "Type",
        "Name",
        "Venue",
        "Date & Time",
        "Ticket Price",
        "Remarks"};

    public EventsTableModel() {
        for (String columnName : COLUMN_NAMES) {
            super.addColumn(columnName);
        }
    }
}
