/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.controllers;

import com.ct.models.CustomerTableModel;
import com.ct.views.CustomersWindow;
import javax.swing.JTable;

/**
 *
 * @author Hiram K.
 */
public class CustomersWindowController {

    private final CustomersWindow customersWindow;
    private final CustomerTableModel customerTableModel;
    private final JTable customersTable;

    public CustomersWindowController() {
        this.customersWindow = new CustomersWindow();
        this.customerTableModel = new CustomerTableModel();
        
        this.customersTable = customersWindow.getCustomersTable();
        
        customersTable.setModel(customerTableModel);
    }

    public CustomersWindow getCustomersWindow() {
        return customersWindow;
    }

}
