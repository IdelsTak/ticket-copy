/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ct.models;

import com.ct.database.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class LoginModel {

    private static final Logger LOG = Logger.getLogger(LoginModel.class.getName());
    private final String username;
    private final char[] password;

    /**
     * Default constructor for the login model.
     *
     * @param username the username to login with.
     * @param password the user's password.
     */
    public LoginModel(String username, char[] password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public char[] getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "LoginModel{"
                + "username="
                + username
                + ", password="
                + Arrays.toString(password)
                + '}';
    }

}
