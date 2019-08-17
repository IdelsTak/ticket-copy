/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.login;

import java.awt.Frame;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Hiram K.
 */
public class LoginDialog extends JDialog {

    private static final Logger LOG = Logger.getLogger(LoginDialog.class.getName());
    private static final long serialVersionUID = 5256561819423404597L;
    private DatabaseLogin databaseLogin;
    private JTextField urlTextField;
    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    private boolean isValid;

    public LoginDialog(Frame owner) {
        super(owner);

        this.databaseLogin = new DatabaseLogin("", "", new char[]{});

        initComponents();
    }

    public DatabaseLogin display() {
        setVisible(true);

        return databaseLogin;
    }

    private void initComponents() {
        var settingsPanel = new DatabaseSettingsPanel();

        urlTextField = settingsPanel.getUrlTextField();
        usernameTextField = settingsPanel.getUsernameTextField();
        passwordField = settingsPanel.getPasswordField();
        statusLabel = settingsPanel.getStatusLabel();
        
        statusLabel.setText(null);

        var okButton = settingsPanel.getOkButton();
        var cancelButton = settingsPanel.getCancelButton();

        ActionListener actionListener = event -> {
            if (event.getSource() == okButton) {

                checkValid();

                if (isValid) {
                    databaseLogin = new DatabaseLogin(
                            urlTextField.getText().trim(),
                            usernameTextField.getText().trim(),
                            passwordField.getPassword());
                }
                
            } else if (event.getSource() == cancelButton) {
                dispose();
            }

        };

        okButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);

        super.getContentPane().add(settingsPanel);

        super.pack();
    }

    @Override
    public void addNotify() {
        super.addNotify();

        LOG.log(Level.INFO, "Dialog shown");
    }

    private void checkValid() {
        String url = urlTextField.getText();
        String username = usernameTextField.getText();
        char[] password = passwordField.getPassword();

        if (url == null || url.isBlank()) {
            statusLabel.setText("Please enter the URL");
            isValid = false;
        } else if (username == null || username.isBlank()) {
            statusLabel.setText("Please enter a username");
            isValid = false;
        } else if (password == null || password.length == 0) {
            statusLabel.setText("You are about to use a blank password");
            isValid = true;
        }else{
            isValid = true;
        }
    }

}
