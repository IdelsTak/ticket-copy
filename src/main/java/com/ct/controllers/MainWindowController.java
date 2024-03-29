package com.ct.controllers;

import com.ct.views.MainWindow;
import com.ct.views.Last30DaysSalesReportPopup;
import com.ct.views.TicketsSoldReportPopup;
import com.ct.views.TotalSalesReportPopup;
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 * A controller class that manages the actions of the view object,
 * <code>{@link MainWindow}</code>.
 *
 * @author admin
 */
public class MainWindowController {

    /**
     * The view object.
     */
    private final MainWindow mainWindow;

    /**
     * Default constructor for the <code>{@link MainWindowController}</code>.
     */
    public MainWindowController() {
        this.mainWindow = new MainWindow();

        var eventsMenuItem = mainWindow.getEventsMenuItem();
        var primaryPanel = mainWindow.getPrimaryPanel();
        //Create an action for showing the events window
        //then add it to the events menu item 
        eventsMenuItem.addActionListener(e -> {
            primaryPanel.removeAll();
            var eventsWindowController = new EventsWindowController();
            primaryPanel.add(
                    eventsWindowController.getEventsWindow(),
                    BorderLayout.CENTER);
            primaryPanel.revalidate();
        });

        var customersMenuItem = mainWindow.getCustomersMenuItem();

        //Create an action for showing the customers window
        //then add it to the customers menu item 
        customersMenuItem.addActionListener(e -> {
            primaryPanel.removeAll();
            var customersWindowController = new CustomersWindowController();
            primaryPanel.add(
                    customersWindowController.getCustomersWindow(),
                    BorderLayout.CENTER);
            primaryPanel.revalidate();
        });

        //Show customers window on startup
        SwingUtilities.invokeLater(() -> {
            primaryPanel.removeAll();
            var customersWindowController = new CustomersWindowController();
            primaryPanel.add(
                    customersWindowController.getCustomersWindow(),
                    BorderLayout.CENTER);
            primaryPanel.revalidate();
        });

        var signoutMenuItem = mainWindow.getSignoutMenuItem();
        //Create an action for showing the login window
        //then add it to the sign out menu item 
        signoutMenuItem.addActionListener(e -> {
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
        var salesReportLast30DaysMenuItem = mainWindow.getSalesReportLast30DaysMenuItem();
        var noOfTicketsSoldMenuItem = mainWindow.getNoOfTicketsSoldMenuItem();
        var totalSalesMenuItem = mainWindow.getTotalSalesMenuItem();

        //Create an action that displays a popup
        //window for reports
        ActionListener showReportsPopup = actionEvent -> {
            if (actionEvent.getSource() == salesReportLast30DaysMenuItem) {
                var popup = new Last30DaysSalesReportPopup(mainWindow, true);
                var closeButton = popup.getCloseButton();

                closeButton.addActionListener(e -> popup.dispose());
                popup.setVisible(true);
            } else if (actionEvent.getSource() == noOfTicketsSoldMenuItem) {
                var popup = new TicketsSoldReportPopup(mainWindow, true);
                var closeButton = popup.getCloseButton();

                closeButton.addActionListener(e -> popup.dispose());
                popup.setVisible(true);
            } else if (actionEvent.getSource() == totalSalesMenuItem) {
                var popup = new TotalSalesReportPopup(mainWindow, true);
                var closeButton = popup.getCloseButton();

                closeButton.addActionListener(e -> popup.dispose());
                popup.setVisible(true);
            }
        };

        salesReportLast30DaysMenuItem.addActionListener(showReportsPopup);
        noOfTicketsSoldMenuItem.addActionListener(showReportsPopup);
        totalSalesMenuItem.addActionListener(showReportsPopup);
    }

    /**
     * Shows the main window.
     */
    public void displayMainWindow() {
        //Show the main window on the EDT
        EventQueue.invokeLater(() -> {
            mainWindow.setVisible(true);
        });
    }

}
