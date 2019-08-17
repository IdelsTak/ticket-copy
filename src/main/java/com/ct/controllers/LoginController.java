/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ct.controllers;

import com.ct.database.Connection;
import com.ct.models.LoginModel;
import com.ct.views.Login;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingWorker;
import static javax.swing.SwingWorker.StateValue.DONE;

/**
 *
 * @author admin
 */
public class LoginController {

    private static final Logger LOG = Logger.getLogger(LoginController.class.getName());
    private final Login login;
    private final Map<Integer, LoginModel> usersCache = new TreeMap<>();
    private final JTextField usernameTextField;
    private final JPasswordField passwordField;
    private final JLabel statusLabel;
    private boolean valid;

    public LoginController() {
        this.login = new Login();

        SwingWorker<Map<Integer, LoginModel>, Void> worker = new SwingWorkerImpl();

        worker.addPropertyChangeListener(changeEvent -> {
            LOG.log(Level.INFO, "Prop name: [{0}]; oldVal: [{1}]; newVal: [{2}]", new Object[]{
                changeEvent.getPropertyName(),
                changeEvent.getOldValue(),
                changeEvent.getNewValue()});

            if (changeEvent.getPropertyName().equals("state")
                    && changeEvent.getNewValue().equals(DONE)) {
                try {
                    Map<Integer, LoginModel> map = worker.get();
                    
                    map.forEach(usersCache::putIfAbsent);
                    
                } catch (InterruptedException |
                        ExecutionException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        });

        worker.execute();

        usernameTextField = login.getUsernameTextField();
        passwordField = login.getPasswordField();
        statusLabel = login.getStatusLabel();

        var loginButton = login.getLoginButton();
        var cancelButton = login.getCancelButton();

        ActionListener actionListener = actionEvent -> {
            if (actionEvent.getSource() == cancelButton) {
                System.exit(0);
            } else if (actionEvent.getSource() == loginButton) {
                checkValid();

                if (valid) {
                    login.dispose();
                }
            }
        };

        loginButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);

    }

    /**
     * Displays the login window. The display is made on the event thread. So
     * it's safe to call this method from any thread.
     */
    public void displayLogin() {
        EventQueue.invokeLater(() -> {
            login.setVisible(true);
        });
    }

    private void checkValid() {
        String username = usernameTextField.getText();
        char[] password = passwordField.getPassword();

        if (username == null || username.isBlank()) {
            valid = false;
            statusLabel.setText("Please enter the username");
        } else {
            char[] storedPassword = null;

            for (var loginModel : usersCache.values()) {
                if (username.equals(loginModel.getUsername())) {
                    storedPassword = Arrays.copyOf(
                            loginModel.getPassword(),
                            loginModel.getPassword().length);
                    break;
                }
            }

            if (storedPassword != null
                    && Arrays.equals(password, storedPassword)) {
                valid = true;
            } else if (!Arrays.equals(password, storedPassword)) {
                valid = false;
                statusLabel.setText("Incorrect password, please try again");
            }
        }

    }

    private static class SwingWorkerImpl extends SwingWorker<Map<Integer, LoginModel>, Void> {

        @Override
        protected Map<Integer, LoginModel> doInBackground() throws Exception {
            Map<Integer, LoginModel> cache = new TreeMap<>();

            java.sql.Connection connection = Connection.getConnection();

            if (connection != null) {
                String sql = "select * from user";

                try (PreparedStatement ps = connection.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {

                    while (rs.next()) {
                        int id = rs.getInt("user_id");
                        String username = rs.getString("username");
                        String password = rs.getString("password");

                        var loginModel = new LoginModel(username, password.toCharArray());

                        LOG.log(Level.INFO, "Loaded user: [{0}] from database", loginModel);

                        cache.put(id, loginModel);
                    }
                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
            
            LOG.log(Level.INFO, "No of users: [{0}]", cache.keySet().size());

            return cache;
        }
    }

}
