package com.ct.views;

import com.ct.models.CustomerModel;
import javax.swing.table.DefaultTableModel;

/**
 * This is the <code>{@link DefaultTableModel}</code> of a table containing
 * <code>{@link CustomerModel}</code> properties.
 * <p>
 * It defines the columns and also adds support for showing
 * <code>{@link Boolean}</code> values as check boxes.
 *
 * @see #getColumnClass(int columnIndex)
 *
 * @author admin
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

    /**
     * Default constructor for the <code>{@link CustomerTableDesign}</code>,
     * whose main function is to define the column names.
     */
    public CustomerTableDesign() {
        for (String columnName : COLUMN_NAMES) {
            super.addColumn(columnName);
        }
    }

    /**
     * Describes the class types for the customers table. It overrides the
     * parent class's method to avoid returning only <code>{@link Object}</code>
     * for all columns.
     * <p>
     * The approach allows the return of <code>{@link Boolean}</code> class in
     * columns 7 and 8, which in turn force the table to paint check boxes.
     *
     * @param columnIndex the column index to determine the class type for.
     *
     * @return the class type for a given column index.
     */
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
