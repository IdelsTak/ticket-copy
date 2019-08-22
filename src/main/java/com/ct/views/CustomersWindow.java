package com.ct.views;

import com.ct.models.CustomerModel;
import com.ct.models.EventModel;
import com.github.lgooddatepicker.components.DatePicker;
import java.io.Serializable;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 * A <code>{@link JPanel}</code> containing the UI elements for showing and
 * editing the properties of <code>{@link CustomerModel}</code>.
 *
 * @see JPanel
 *
 * @author admin
 */
public class CustomersWindow extends JPanel {

    /**
     * The serialVersionUID used for serialization.
     *
     * @see Serializable
     */
    private static final long serialVersionUID = 2311466034675098786L;

    /** Creates new form CustomersWindow */
    public CustomersWindow() {
        initComponents();
    }

    /**
     * Retrieves the <code>{@link JButton}</code> for adding customers to
     * database.
     *
     * @return the <code>{@link JButton}</code> for adding customers to
     *         database.
     */
    public JButton getAddButton() {
        return addButton;
    }

    /**
     * Retrieves the <code>{@link DatePicker}</code> that sets the booking date.
     *
     * @return the <code>{@link DatePicker}</code> that sets the booking date.
     */
    public DatePicker getBookingDatePicker() {
        return bookingDatePicker;
    }

    /**
     * Retrieves the <code>{@link JTable}</code> containing the customers'
     * details.
     *
     * @return the <code>{@link JTable}</code> containing the customers'
     *         details.
     */
    public JTable getCustomersTable() {
        return customersTable;
    }

    /**
     * Retrieves the <code>{@link JButton}</code> for deleting customers from
     * database.
     *
     * @return the <code>{@link JButton}</code> for deleting customers from
     *         database.
     */
    public JButton getDeleteButton() {
        return deleteButton;
    }

    /**
     * Retrieves the <code>{@link JComboBox}</code> that chooses the events the
     * customer seeks to attend.
     *
     * @return the <code>{@link JComboBox}</code> that chooses the events the
     *         customer seeks to attend.
     */
    public JComboBox<EventModel> getEventsComboBox() {
        return eventsComboBox;
    }

    /**
     * Retrieves the <code>{@link JTextField}</code> displaying the customer's
     * id property. Note, it is not editable.
     *
     * @return the <code>{@link JTextField}</code> displaying the customer's
     *         id property.
     */
    public JTextField getIdTextField() {
        return idTextField;
    }

    /**
     * Retrieves the <code>{@link JCheckBox}</code> showing whether the customer
     * has been issued with a ticket.
     *
     * @return the <code>{@link JCheckBox}</code> showing whether the customer
     *         has been issued with a ticket.
     */
    public JCheckBox getIssuedCheckBox() {
        return issuedCheckBox;
    }

    /**
     * Retrieves the <code>{@link JTextField}</code> displaying the customer's
     * name property.
     *
     * @return the <code>{@link JTextField}</code> displaying the customer's
     *         name property.
     */
    public JTextField getNameTextField() {
        return nameTextField;
    }

    /**
     * Retrieves the <code>{@link JTextField}</code> displaying the number of
     * tickets the customer has bought.
     *
     * @return the <code>{@link JTextField}</code> displaying the number of
     *         tickets the customer has bought.
     */
    public JTextField getNoOfTicketsTextField() {
        return noOfTicketsTextField;
    }

    /**
     * Retrieves the <code>{@link JCheckBox}</code> showing whether the customer
     * has paid for tickets.
     *
     * @return the <code>{@link JCheckBox}</code> showing whether the customer
     *         has paid for tickets.
     */
    public JCheckBox getPaidCheckBox() {
        return paidCheckBox;
    }

    /**
     * Retrieves the <code>{@link JTextField}</code> displaying the customer's
     * phone number property.
     *
     * @return the <code>{@link JTextField}</code> displaying the customer's
     *         phone number property.
     */
    public JTextField getPhoneTextField() {
        return phoneTextField;
    }

    /**
     * Retrieves the <code>{@link JTextField}</code> displaying the total cost
     * of tickets that the customer has bought.
     *
     * @return the <code>{@link JTextField}</code> displaying the total cost
     *         of tickets that the customer has bought.
     */
    public JTextField getTicketsTotalTextField() {
        return ticketsTotalTextField;
    }

    /**
     * Retrieves the <code>{@link JButton}</code> used for updating customers'
     * details in database.
     *
     * @return the <code>{@link JButton}</code> used for updating customers'
     *         details in database.
     */
    public JButton getUpdateButton() {
        return updateButton;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerLabel = new javax.swing.JLabel();
        scrollPane = new javax.swing.JScrollPane();
        customersTable = new javax.swing.JTable();
        idLabel = new javax.swing.JLabel();
        idTextField = new javax.swing.JTextField();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        phoneLabel = new javax.swing.JLabel();
        phoneTextField = new javax.swing.JTextField();
        eventsLabel = new javax.swing.JLabel();
        eventsComboBox = new javax.swing.JComboBox<>();
        noOfTicketsLabel = new javax.swing.JLabel();
        noOfTicketsTextField = new javax.swing.JTextField();
        ticketsTotalLabel = new javax.swing.JLabel();
        ticketsTotalTextField = new javax.swing.JTextField();
        bookingDateLabel = new javax.swing.JLabel();
        bookingDatePicker = new com.github.lgooddatepicker.components.DatePicker();
        ticketsPaidOrIssuedLabel = new javax.swing.JLabel();
        paidCheckBox = new javax.swing.JCheckBox();
        issuedCheckBox = new javax.swing.JCheckBox();
        addButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

        headerLabel.setFont(headerLabel.getFont().deriveFont(headerLabel.getFont().getSize()+3f));
        headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerLabel.setText("Customers");

        scrollPane.setViewportView(customersTable);

        idLabel.setText("Id:");

        idTextField.setEditable(false);

        nameLabel.setText("Name:");

        phoneLabel.setText("Phone:");

        eventsLabel.setText("Event:");

        noOfTicketsLabel.setText("No. of Tickets");

        ticketsTotalLabel.setText("Total Tickets Cost:");

        ticketsTotalTextField.setEditable(false);

        bookingDateLabel.setText("Booking Date:");

        ticketsPaidOrIssuedLabel.setText("Ticket has been:");

        paidCheckBox.setText("Paid");

        issuedCheckBox.setText("Issued");

        addButton.setText("Add");

        updateButton.setText("Update");

        deleteButton.setText("Delete");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(eventsLabel)
                            .addComponent(idLabel)
                            .addComponent(nameLabel)
                            .addComponent(phoneLabel)
                            .addComponent(noOfTicketsLabel)
                            .addComponent(ticketsTotalLabel)
                            .addComponent(bookingDateLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(noOfTicketsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ticketsTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(ticketsPaidOrIssuedLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(paidCheckBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(issuedCheckBox))
                            .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(addButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(updateButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(deleteButton))
                            .addComponent(nameTextField)
                            .addComponent(phoneTextField)
                            .addComponent(eventsComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(bookingDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 50, Short.MAX_VALUE))
                    .addComponent(headerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {idTextField, noOfTicketsTextField, ticketsTotalTextField});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idLabel)
                    .addComponent(idTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneLabel)
                    .addComponent(phoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(eventsLabel)
                    .addComponent(eventsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(noOfTicketsLabel)
                    .addComponent(noOfTicketsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ticketsTotalLabel)
                    .addComponent(ticketsTotalTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bookingDateLabel)
                    .addComponent(bookingDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(paidCheckBox)
                    .addComponent(issuedCheckBox)
                    .addComponent(ticketsPaidOrIssuedLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(updateButton)
                    .addComponent(deleteButton))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel bookingDateLabel;
    private com.github.lgooddatepicker.components.DatePicker bookingDatePicker;
    private javax.swing.JTable customersTable;
    private javax.swing.JButton deleteButton;
    private javax.swing.JComboBox<EventModel> eventsComboBox;
    private javax.swing.JLabel eventsLabel;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JLabel idLabel;
    private javax.swing.JTextField idTextField;
    private javax.swing.JCheckBox issuedCheckBox;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel noOfTicketsLabel;
    private javax.swing.JTextField noOfTicketsTextField;
    private javax.swing.JCheckBox paidCheckBox;
    private javax.swing.JLabel phoneLabel;
    private javax.swing.JTextField phoneTextField;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JLabel ticketsPaidOrIssuedLabel;
    private javax.swing.JLabel ticketsTotalLabel;
    private javax.swing.JTextField ticketsTotalTextField;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
