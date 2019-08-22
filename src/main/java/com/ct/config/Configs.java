package com.ct.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Contains the properties that can be used to make a connection to a database.
 * These include:
 * <ul>
 * <li> <strong>username</strong>
 * <li> <strong>password</strong>
 * <li> <strong>url</strong>
 * </ul>
 * <p>
 * This class's clients can set these properties manually using the methods:
 * <ul>
 * <li> <code>{@link #setUsername(java.lang.String username)}</code>
 * <li> <code>{@link #setPassword(char[] password)} </code>
 * <li> <code>{@link #setUrl(java.lang.String url)}</code>
 * </ul>
 * <p>
 * Accessing those properties is as simple as calling:
 * <ul>
 * <li> <code>{@link #getUsername()}</code>
 * <li> <code>{@link #getPassword()} </code>
 * <li> <code>{@link #getUrl()}</code>
 * </ul>
 * <p>
 * Other notable capabilities include the capability to store the properties in
 * a properties file. Storing is as simple as calling
 * <code>{@link #storePoperties()}</code>, while retrieval is by calling
 * <code>{@link #loadProperties()}</code>
 *
 * @author admin
 */
public class Configs {

    /**
     * The classes logger.
     */
    private static final Logger LOG = Logger.getLogger(Configs.class.getName());
    /**
     * The username property that you may use to connect to database.
     */
    private String username;
    /**
     * The password property that you may use to connect to database.
     */
    private char[] password;
    /**
     * The url property that you may use to connect to database.
     */
    private String url;

    /**
     * Accesses the username that may be used to connect to database.
     *
     * @return the database's username property.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Sets the username that may be used to connect to the database.
     *
     * @param username the username property that will be used for database
     *                 connection.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Accesses the password property that will be used to connect to database.
     * The method returns it as an array of <code>char</code>, because these are
     * harder to interpret when they are intercepted. The method also makes a
     * defensive copy of the password and returns that instead of the original.
     * These ensures that client classes do not mutate this class's password
     * property.
     *
     * @return the password property.
     */
    public char[] getPassword() {
        //Best practice: Make a defensive copy of the password
        //and return instead of the original
        return Arrays.copyOf(password, password.length);
    }

    /**
     * Sets the password property that may be used to connect to database. As in
     * the case of <code>{@link #getPassword()}</code>, this method sets this
     * classes password property by making a defensive copy of the
     * <code>char</code> array it receives.
     *
     * @param password the password property to use to connect to database.
     */
    public void setPassword(char[] password) {
        //Best practice: Retrieve a defensive of the password that's
        //been given instead of using it as it is.
        this.password = Arrays.copyOf(password, password.length);
    }

    /**
     * Accesses the database's <code>URL</code>. You should note, however, that
     * the <code>{@link DriverManager}</code> that you should use to make a
     * connection to database requires that the URL be provided as
     * <code>{@link String}</code> &mdash; and not as a
     * <code>{@link URL}</code>.
     *
     * @return the URL <code>{@link String}</code> that will be used to connect
     *         to database.
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the URL <code>{@link String}</code> that will be used to connect to
     * database. An example of URL that connects to a MySQL database is:
     * <ul>
     * <li><code><strong>jdbc:mysql://localhost:3306/somedatabase</strong></code>
     * </ul>
     *
     * @param url the URL <code>{@link String}</code> that will be used to
     *            connect to database.
     */
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * Retrieves the database connection properties from an external file. In
     * the absence of an interface that enables the user to set these
     * parameters, the external file offers a means to change the database
     * parameters. It's useful in cases where one needs to connect to another
     * database instance, for instance.
     * Remember, do not call this method if you've not set the
     * <code>username</code>, <code>password</code>, and <code>url</code>
     * properties already as this may result in an error.
     *
     * @see #setUsername(java.lang.String username)
     * @see #setPassword(char[] password)
     * @see #setUrl(java.lang.String url)
     */
    public void loadProperties() {

        //Load the database connection properties from file
        try (var inputStream = new FileInputStream("./Props")) {
            var props = new Properties();
            props.load(inputStream);
            //Read the connection properties file
            username = props.getProperty("username");
            password = props.getProperty("password").toCharArray();
            url = props.getProperty("url");
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Stores this class's database connection properties to an external file.
     * Remember, do not access the database connection properties if you have
     * not called first this method during the lifetime cycle of this class.
     *
     * @see #getUsername()
     * @see #getPassword()
     * @see #getUrl()
     */
    public void storePoperties() {
        //Add the database connection properties
        //to an external file to persist them
        var props = new Properties();
        props.setProperty("username", username);
        props.setProperty("password", String.valueOf(password));
        props.setProperty("url", url);

        try (var outputStream = new FileOutputStream("./Props")) {
            //Store with some comments 
            props.store(outputStream, "Database properties");
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
