package com.ct.database;

import com.ct.config.Configs;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a utility class that enables its calling clients to get an instance
 * of a database connection. It functions by first loading the properties from
 * an external file (using the <code>loadProperties</code> method of class
 * <code>{@link Configs}</code>). After that, it can then create a database
 * connection using the <code>{@link DriverManager}</code>.
 * <p>
 * This class keeps a static instance of the created connection throughout the
 * application's life cycle. This is useful because it prevents multiple
 * instantiation of the database connection.
 *
 * @author admin
 */
public class Connection {

    /**
     * This class's logger.
     */
    private static final Logger LOG = Logger.getLogger(Connection.class.getName());
    /**
     * The static, database <code>{@link Connection}</code> created by
     * <code>{@link DriverManager}</code> using the properties loaded from an
     * external file by <code>{@link Configs}</code>.
     */
    private static java.sql.Connection connection;

    /**
     * Attempts to connect to database. If a connection had been made earlier,
     * then that connection is returned.
     *
     * @return the connection to database.
     */
    public static java.sql.Connection getConnection() {
        //Create a connection if one hasn't been created yet
        //This check will skip if a connection has been
        //made already at some point in the apps lifecyle
        //which most probably is during the login
        if (connection == null) {
            //Retrieve the database connection
            //properties from the external file
            var configs = new Configs();
            configs.loadProperties();
            //The url of the database
            var url = configs.getUrl();
            //The user name
            var user = configs.getUsername();
            //The database password
            var password = String.valueOf(configs.getPassword());

            //Attempt to create a database connection
            //Will fail and throw an exception:
            //(1) if the credentials are incorrect or
            //(2) if the database doesn't exist
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        //The connection to database
        return connection;
    }

}
