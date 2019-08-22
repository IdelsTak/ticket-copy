package com.ct.views;

import com.ct.models.EventModel;
import javax.swing.table.DefaultTableModel;

/**
 * This is the <code>{@link DefaultTableModel}</code> of a table containing
 * <code>{@link EventModel}</code> properties.
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

    /**
     * Default constructor for the <code>{@link EventsTableDesign}</code>,
     * whose main function is to define the column names.
     */
    public EventsTableDesign() {
        for (String columnName : COLUMN_NAMES) {
            super.addColumn(columnName);
        }
    }
}
