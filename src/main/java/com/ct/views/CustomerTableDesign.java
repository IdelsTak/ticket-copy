/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.views;

import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Hiram K.
 */
public class CustomerTableDesign extends DefaultTableModel {

    private static final long serialVersionUID = 5855967701661693450L;
    private static final String[] COLUMN_NAMES = {
        "Id",
        "Name",
        "Phone",
        "Event",
        "No. of Tickets",
        "Total Tickets Cost",
        "Booking Date",
        "Paid",
        "Issued"
    };

    public CustomerTableDesign() {
        for (String columnName : COLUMN_NAMES) {
            super.addColumn(columnName);
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        //Make the paid and issued
        //columns show checkboxes
        //instead of the strings "false" or "true"
        return columnIndex == 7 || columnIndex == 8
               ? Boolean.class
               : super.getColumnClass(columnIndex);
    }

}
