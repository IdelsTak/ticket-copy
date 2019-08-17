/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ct.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
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

    public void setUsername(String username) {
        this.username = username;
    }

    public char[] getPassword() {
        return password;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void loadProperties() {
        var props = new Properties();

        LOG.log(Level.INFO, "Loading properties from: [{0}]", "./Props");

        try (InputStream in = new FileInputStream("./Props")) {
            props.load(in);

            username = props.getProperty("username");
            password = props.getProperty("password").toCharArray();
            url = props.getProperty("url");
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    public void storePoperties() {
        var props = new Properties();
        props.setProperty("username", username);
        props.setProperty("password", String.valueOf(password));
        props.setProperty("url", url);
        File file = new File("./Props");

        LOG.log(Level.INFO, "Storing properties to: [{0}]", file);

        try (OutputStream out = new FileOutputStream("./Props")) {
            //Store with some comments 
            props.store(out, "Database properties");
        } catch (FileNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
