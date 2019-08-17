/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ct.main;

import com.ct.controllers.LoginController;
import com.ct.views.LoginWindow;
import java.awt.EventQueue;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 *
 * @author admin
 */
public class CT {

    private static final Logger LOG = Logger.getLogger(CT.class.getName());

    /**
     * Application's entry point.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        //Set the look and feel to the system's default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException
                 | InstantiationException
                 | IllegalAccessException
                 | javax.swing.UnsupportedLookAndFeelException ex) {
            LOG.log(java.util.logging.Level.SEVERE, null, ex);
        }

//        /* Create and display the login window */
//        EventQueue.invokeLater(() -> {
//            new LoginWindow().setVisible(true);
//        });
        var loginController = new LoginController();
        
        loginController.displayLogin();
    }
}
