/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.views;

import java.awt.Frame;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

/**
 *
 * @author admin
 */
public class MainWindow extends javax.swing.JFrame {

    private static final long serialVersionUID = -3077898430347351322L;

    /** Creates new form MainWindow */
    public MainWindow() {
        super();
        //Open the window in its maximized state
        super.setExtendedState(Frame.MAXIMIZED_BOTH);
        
        initComponents();
    }

    public JPanel getPrimaryPanel() {
        return primaryPanel;
    }

    public JMenuItem getCustomersMenuItem() {
        return customersMenuItem;
    }

    public JMenuItem getEventsMenuItem() {
        return eventsMenuItem;
    }

    public JMenuItem getNoOfTicketsSoldMenuItem() {
        return noOfTicketsSoldMenuItem;
    }

    public JMenuItem getSalesReportLast30DaysMenuItem() {
        return salesReportLast30DaysMenuItem;
    }

    public JMenuItem getSignoutMenuItem() {
        return signoutMenuItem;
    }

    public JMenuItem getTotalSalesMenuItem() {
        return totalSalesMenuItem;
    }

    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        primaryPanel = new javax.swing.JPanel();
        menuBar = new javax.swing.JMenuBar();
        menu = new javax.swing.JMenu();
        eventsMenuItem = new javax.swing.JMenuItem();
        customersMenuItem = new javax.swing.JMenuItem();
        reportsMenu = new javax.swing.JMenu();
        salesReportLast30DaysMenuItem = new javax.swing.JMenuItem();
        noOfTicketsSoldMenuItem = new javax.swing.JMenuItem();
        totalSalesMenuItem = new javax.swing.JMenuItem();
        signoutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Welcome to Colombo Theater");

        primaryPanel.setLayout(new java.awt.BorderLayout());
        getContentPane().add(primaryPanel, java.awt.BorderLayout.CENTER);

        menu.setText("Menu");

        eventsMenuItem.setText("Events");
        menu.add(eventsMenuItem);

        customersMenuItem.setText("Customers");
        menu.add(customersMenuItem);

        reportsMenu.setText("Reports");

        salesReportLast30DaysMenuItem.setText("Sales Last 30 Days");
        reportsMenu.add(salesReportLast30DaysMenuItem);

        noOfTicketsSoldMenuItem.setText("No of Tickets Sold - Count");
        reportsMenu.add(noOfTicketsSoldMenuItem);

        totalSalesMenuItem.setText("Total Sales Value");
        reportsMenu.add(totalSalesMenuItem);

        menu.add(reportsMenu);

        signoutMenuItem.setText("Sign out");
        menu.add(signoutMenuItem);

        menuBar.add(menu);

        setJMenuBar(menuBar);

        setSize(new java.awt.Dimension(400, 332));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem customersMenuItem;
    private javax.swing.JMenuItem eventsMenuItem;
    private javax.swing.JMenu menu;
    private javax.swing.JMenuBar menuBar;
    private javax.swing.JMenuItem noOfTicketsSoldMenuItem;
    private javax.swing.JPanel primaryPanel;
    private javax.swing.JMenu reportsMenu;
    private javax.swing.JMenuItem salesReportLast30DaysMenuItem;
    private javax.swing.JMenuItem signoutMenuItem;
    private javax.swing.JMenuItem totalSalesMenuItem;
    // End of variables declaration//GEN-END:variables
}
