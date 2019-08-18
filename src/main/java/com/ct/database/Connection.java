/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ct.database;

import com.ct.config.Configs;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class Connection {

    private static final Logger LOG = Logger.getLogger(Connection.class.getName());
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
            
            configs.getUsername();
            configs.getUsername();
            configs.getPassword();
            //The url of the database
            String url = configs.getUrl()/*"jdbc:mysql://localhost:3306/mydb"*/;
            //The user name
            String user = configs.getUsername()/*"root"*/;
            //The database password
            String password = String.valueOf(configs.getPassword())/*"corner-dicing"*/;
            
            //Attempt to retrieve a connection
            //Will fail and throw an exception
            //if the credentials are incorrect or
            //if the database doesn't exist
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
        
        return connection;
    }

}
