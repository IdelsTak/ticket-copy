/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ct.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author admin
 */
public class Configs {

    private static final Logger LOG = Logger.getLogger(Configs.class.getName());
    private String username;
    private char[] password;
    private String url;

    public Configs() {

    }

    public String getUsername() {
        return username;
    }

    public char[] getPassword() {
        return password;
    }

    public String getUrl() {
        return url;
    }

    public void setUsername(String username) {

    }

    public void setPassword() {

    }

    public void setUrl() {

    }

    public void storePoperties() {
        Properties props = new Properties();
        props.setProperty("username", username);
        props.setProperty("password", String.valueOf(password));
        props.setProperty("url", url);
        File file = new File("./Props");

        try {
            OutputStream out = new FileOutputStream(file);
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
        
        try(OutputStream out = new FileOutputStream(file)){
            //Store with some comments 
            props.store(out, "Database properties");
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
