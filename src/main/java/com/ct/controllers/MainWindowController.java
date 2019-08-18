/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.controllers;

import com.ct.views.MainWindow;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JMenuItem;

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
    }

    public void displayMainWindow() {
        //Show the main window on the EDT
        EventQueue.invokeLater(() -> {
            mainWindow.setVisible(true);
        });
    }
}
