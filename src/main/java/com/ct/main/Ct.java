/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ct.main;

import com.ct.controllers.LoginController;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 *
 * @author admin
 */
public class Ct {

    private static final Logger LOG = Logger.getLogger(Ct.class.getName());

    /**
     * Application's entry point.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        //Set the look and feel of the application
        try {
            String osName = System.getProperty("os.name");

            if (osName.equals("Linux")) {
                // Borders look weird (invisible) on Linux...
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } else {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (ClassNotFoundException
                 | InstantiationException
                 | IllegalAccessException
                 | javax.swing.UnsupportedLookAndFeelException ex) {
            LOG.log(java.util.logging.Level.SEVERE, null, ex);
        }

        //Start the login controller
        var loginController = new LoginController();
        //Ask the controller to initiate login
        loginController.displayLogin();
    }
}
