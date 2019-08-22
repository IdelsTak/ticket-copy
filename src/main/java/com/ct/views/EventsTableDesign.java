/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.views;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author admin
 */
public class EventsTableDesign extends DefaultTableModel {

    private static final long serialVersionUID = -6515825795700587750L;
    private static final String[] COLUMN_NAMES = {
        "Id",
        "Type",
        "Name",
        "Venue",
        "Date & Time",
        "Ticket Price",
        "Remarks"};

    public EventsTableDesign() {
        for (String columnName : COLUMN_NAMES) {
            super.addColumn(columnName);
        }
    }
}
