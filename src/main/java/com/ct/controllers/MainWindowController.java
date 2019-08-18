/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.controllers;

import com.ct.views.MainWindow;
import com.ct.views.ReportsPopup;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

/**
 *
 * @author Hiram K.
 */
public class MainWindowController {

    private final MainWindow mainWindow;

    public MainWindowController() {
        this.mainWindow = new MainWindow();

        var eventsMenuItem = mainWindow.getEventsMenuItem();
        var primaryPanel = mainWindow.getPrimaryPanel();

        eventsMenuItem.addActionListener(actionEvent -> {
            primaryPanel.removeAll();
            var eventsWindowController = new EventsWindowController();
            primaryPanel.add(
                    eventsWindowController.getEventsWindow(),
                    BorderLayout.CENTER);
            primaryPanel.revalidate();
        });

        var customersMenuItem = mainWindow.getCustomersMenuItem();

        customersMenuItem.addActionListener(actionEvent -> {
            primaryPanel.removeAll();
            var customersWindowController = new CustomersWindowController();
            primaryPanel.add(
                    customersWindowController.getCustomersWindow(),
                    BorderLayout.CENTER);
            primaryPanel.revalidate();
        });

        var signoutMenuItem = mainWindow.getSignoutMenuItem();

        signoutMenuItem.addActionListener(actionEvent -> {
            int result = JOptionPane.showConfirmDialog(
                    mainWindow,//parent component
                    "Are you sure you want to sign out?",//message
                    "Confirm Signout",//title
                    JOptionPane.YES_NO_CANCEL_OPTION);//option type

            if (result != JOptionPane.YES_OPTION) {
                return;
            }

            mainWindow.dispose();

            var loginController = new LoginController();
            loginController.displayLogin();
        });
        //Create an action that dispalys a popup
        //window for reports
        ActionListener showReportsPopup = (actionEvent) -> {
            var reportsPopup = new ReportsPopup(mainWindow, true);
            var closeButton = reportsPopup.getCloseButton();

            closeButton.addActionListener(e -> {
                reportsPopup.dispose();
            });

            reportsPopup.setVisible(true);
        };

        var salesReportLast30DaysMenuItem = mainWindow.getSalesReportLast30DaysMenuItem();
        var noOfTicketsSoldMenuItem = mainWindow.getNoOfTicketsSoldMenuItem();
        var totalSalesMenuItem = mainWindow.getTotalSalesMenuItem();

        salesReportLast30DaysMenuItem.addActionListener(showReportsPopup);
        noOfTicketsSoldMenuItem.addActionListener(showReportsPopup);
        totalSalesMenuItem.addActionListener(showReportsPopup);
    }

    public void displayMainWindow() {
        //Show the main window on the EDT
        EventQueue.invokeLater(() -> {
            mainWindow.setVisible(true);
        });
    }
}
