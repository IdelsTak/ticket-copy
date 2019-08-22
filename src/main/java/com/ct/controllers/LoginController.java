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
        //Create the login form
        this.login = new Login();
        //Create a swingworker, which will pull data
        //from the database in a background thread
        var loader = new UserLoaders();
        //Listen to whether the worker has finished
        //loading the data from database
        loader.addPropertyChangeListener(changeEvent -> {
            //If the worker has finished loading
            //the cache with the usernames and passwords
            //insert the values in a local cache
            if (changeEvent.getPropertyName().equals("state")
                    && changeEvent.getNewValue().equals(DONE)) {
                //Attempt to get the values that
                //were loaded by the worker in a
                //background thread
                try {
                    Map<Integer, LoginModel> map = loader.get();
                    //Use Java's member referencing to
                    //put all the values loaded by the
                    //worker in a background thread in the
                    //local cache of usernames and passwords
                    map.forEach(usersCache::putIfAbsent);
                } catch (InterruptedException
                         | ExecutionException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }
        });
        //Start running the worker in a background thread
        loader.execute();

        //Get the username and password fields from
        //the login form
        usernameTextField = login.getUsernameTextField();
        passwordField = login.getPasswordField();
        //Get the status label where we will
        //display any errors, e.g. incorrect password
        statusLabel = login.getStatusLabel();
        //Get the buttons for login and cancel
        var loginButton = login.getLoginButton();
        var cancelButton = login.getCancelButton();
        //Create an action listener that will
        //do something when the login or cancel button
        //is pressed
        ActionListener actionListener = actionEvent -> {
            if (actionEvent.getSource() == cancelButton) {
                //If user doesn't want to login
                //then exit the application
                System.exit(0);
            } else if (actionEvent.getSource() == loginButton) {
                //Ensure that the details entered
                //by the user are correct
                checkValid();
                //If the details are correct
                //then close the login form and show
                //another window
                if (valid) {
                    //Close the login window
                    login.dispose();
                    //open the main window
                    var mainWindowController = new MainWindowController();
                    mainWindowController.displayMainWindow();
                }
            }
        };
        //Attach the action listener to the login
        //and cancel buttons
        loginButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);
    }

    /**
     * Displays the login window. The display is made on the event thread. So
     * it's safe to call this method from any thread.
     */
    public void displayLogin() {
        //Show the login form on the EDT
        EventQueue.invokeLater(() -> login.setVisible(true));
    }
    private char[] storedPassword = null;

    /**
     * Checks whether the username and password provided are correct.
     */
    private void checkValid() {
        //Get the username and password fields
        //so that we can retreive the login details
        //provided by the user
        String username = usernameTextField.getText();
        char[] password = passwordField.getPassword();
        //If no username has been provided
        //then mark the login as invalid
        if (username == null || username.isBlank()) {
            valid = false;
            statusLabel.setText("Please enter the username");
        } else {
            //Start checking whether the password
            //provided by the user is correct
//            char[] storedPassword = null;
            //Look for the password corresponding to
            //the username in the local cache
            usersCache.values()
                    .stream()
                    .filter(val -> username.equals(val.getUsername()))
                    .forEach(val -> storePassword(val.getPassword()));
//            for (var loginModel : usersCache.values()) {
//                if (username.equals(loginModel.getUsername())) {
//                    //If the username is found in cache
//                    //then copy the password that is stored
//                    //for that user in database
//                    storedPassword = Arrays.copyOf(
//                            loginModel.getPassword(),
//                            loginModel.getPassword().length);
//                    //Since the password has been found
//                    //we should exit this loop
//                    break;
//                }
//            }

            //If the stored password is equal to
            //what the user has provided, then mark the
            //login as valid
            if (storedPassword != null
                    && Arrays.equals(password, storedPassword)) {
                valid = true;
                //Mark the login as invalid if the passwords
                //do not match
            } else if (!Arrays.equals(password, storedPassword)) {
                valid = false;
                statusLabel.setText("Incorrect password, please try again");
            }
        }

    }

    private void storePassword(char[] val) {
        storedPassword = Arrays.copyOf(val, val.length);
    }

    /**
     * Works in a background thread to retrieve the usernames and passwords from
     * database.
     */
    private static class UserLoaders extends SwingWorker<Map<Integer, LoginModel>, Void> {

        @Override
        protected Map<Integer, LoginModel> doInBackground() throws Exception {
            //create a local cache of the usernames
            //and passwords
            Map<Integer, LoginModel> cache = new TreeMap<>();
            //Get the connection to database
            java.sql.Connection connection = Connection.getConnection();
            //Only continue if a database connection
            //was established
            if (connection != null) {
                //Retrieve all the users from database
                String sql = "select * from user";

                try (PreparedStatement ps = connection.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {
                    //Scroll through all the rows
                    while (rs.next()) {
                        int id = rs.getInt("user_id");
                        String username = rs.getString("username");
                        String password = rs.getString("password");
                        //Create a LoginModel object using the
                        //values extracted from the row
                        var loginModel = new LoginModel(username, password.toCharArray());

                        //Attach the LoginModel object
                        //to the cache
                        cache.put(id, loginModel);
                    }
                } catch (SQLException ex) {
                    LOG.log(Level.SEVERE, null, ex);
                }
            }

            return cache;
        }
    }

}
