/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.controllers;

import com.ct.views.EventWindow;
import com.ct.views.MainWindow;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author Hiram K.
 */
public class MainWindowController {

    private final MainWindow mainWindow;

    public MainWindowController() {
        this.mainWindow = new MainWindow();
        
        JMenuItem eventMenuItem = mainWindow.getEventMenuItem();
        JPanel primaryPanel = mainWindow.getPrimaryPanel();
        
        eventMenuItem.addActionListener(actionEvent -> {
            primaryPanel.removeAll();
            
            primaryPanel.add(new EventWindow(), BorderLayout.CENTER);
            
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
