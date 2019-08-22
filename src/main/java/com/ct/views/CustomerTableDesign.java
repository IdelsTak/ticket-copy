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
        switch (columnIndex) {
            case 0:
                return Number.class;
            case 1:
                return super.getColumnClass(columnIndex);
            case 2:
                return Number.class;
            case 3:
                return super.getColumnClass(columnIndex);
            case 4://Allow case 4 to fall through
            case 5:
                return Number.class;
            case 6:
                return super.getColumnClass(columnIndex);
            //Make the paid and issued
            //columns show checkboxes
            //instead of the strings "false" or "true"
            case 7://Allow case 7 to fall through
            case 8:
                return Boolean.class;
            default:
                return super.getColumnClass(columnIndex);
        }
    }

}
