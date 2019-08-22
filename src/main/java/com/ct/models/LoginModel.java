package com.ct.models;

import com.ct.controllers.LoginController;
import java.beans.Beans;
import java.util.Arrays;

/**
 * This is essentially a Java <code>{@link Beans}</code> class that represents
 * the login model. It has two main properties, namely:
 * <ul>
 * <li>username
 * <li>password
 * </ul>
 * The <code>{@link LoginController}</code> calls these properties to help it to
 * sign in to the application.
 *
 * @author admin
 */
public class LoginModel {

    /**
     * The username property that will be used to sign in to the application.
     */
    private final String username;
    /**
     * The password property that will be used to sign in to the application.
     */
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

    /**
     * Retrieves the username property to sign in with.
     *
     * @return the username property.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Retrieves the password property to sign in with. Out of best practice,
     * the method provides a defensive copy of the password <code>char</code>s
     * to avoid mutating the internals of this class.
     *
     * @return the password property.
     */
    public char[] getPassword() {
        return Arrays.copyOf(password, password.length);
    }

    /**
     *
     * {@inheritDoc}
     */
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
