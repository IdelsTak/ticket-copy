package com.ct.views;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * A <code>{@link JFrame}</code> containing the UI elements for signing in to
 * the application.
 *
 * @see JFrame
 *
 * @author admin
 */
public class LoginWindow extends JFrame {

    /**
     * The serialVersionUID used for serialization.
     *
     * @see Serializable
     */
    private static final long serialVersionUID = 9153252296083194807L;

    /** Creates new form LoginWindow */
    public LoginWindow() {
        initComponents();
    }

    /**
     * Retrieves the <code>{@link JButton}</code> for cancelling the sign in.
     *
     * @return the <code>{@link JButton}</code> for cancelling the sign in.
     */
    public JButton getCancelButton() {
        return cancelButton;
    }

    /**
     * Retrieves the <code>{@link JButton}</code> for signing in.
     *
     * @return the <code>{@link JButton}</code> for signing in.
     */
    public JButton getLoginButton() {
        return loginButton;
    }

    /**
     * Retrieves the <code>{@link JPasswordField}</code> displaying the sign in
     * password.
     *
     * @return the <code>{@link JPasswordField}</code> displaying the sign in
     *         password.
     */
    public JPasswordField getPasswordField() {
        return passwordField;
    }

    /**
     * Retrieves the <code>{@link JLabel}</code> for displaying sign in
     * statuses.
     *
     * @return the <code>{@link JLabel}</code> for displaying sign in
     *         statuses.
     */
    public JLabel getStatusLabel() {
        return statusLabel;
    }

    /**
     * Retrieves the <code>{@link JTextField}</code> displaying the sign in
     * username.
     *
     * @return the <code>{@link JTextField}</code> displaying the sign in
     *         username.
     */
    public JTextField getUsernameTextField() {
        return usernameTextField;
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
        usernameLabel = new javax.swing.JLabel();
        usernameTextField = new javax.swing.JTextField();
        passwordLabel = new javax.swing.JLabel();
        passwordField = new javax.swing.JPasswordField();
        statusLabel = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Login");

        headerLabel.setFont(headerLabel.getFont().deriveFont(headerLabel.getFont().getStyle() | java.awt.Font.BOLD));
        headerLabel.setText("Please enter your login details");

        usernameLabel.setText("Username:");

        passwordLabel.setText("Password:");

        statusLabel.setForeground(java.awt.Color.red);

        loginButton.setText("Login");

        cancelButton.setText("Cancel");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(headerLabel)
                        .addGap(0, 170, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(passwordLabel)
                            .addComponent(usernameLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(usernameTextField)
                            .addComponent(passwordField)
                            .addComponent(statusLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(loginButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cancelButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(usernameLabel)
                    .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(passwordLabel)
                    .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(loginButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(400, 245));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JButton loginButton;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JLabel passwordLabel;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JTextField usernameTextField;
    // End of variables declaration//GEN-END:variables
}
