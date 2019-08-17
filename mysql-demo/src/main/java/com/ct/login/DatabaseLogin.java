/*
 * Copyright (c) 2019, Hiram K.
 * All rights reserved.
 *
 */
package com.ct.login;

/**
 *
 * @author Hiram K.
 */
public class DatabaseLogin {

    private final String url;
    private final String username;
    private final char[] password;

    public DatabaseLogin(String url, String username, char[] password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }
    
}
