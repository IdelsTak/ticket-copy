package com.ct.main;

import com.ct.controllers.LoginController;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * This is the application's main class/entry point. It is registered in
 * manifest and used to start the application.
 * <p>
 * It shows a GUI by initiating the login process. It achieves this by
 * instantiating <code>{@link LoginController}</code> and then calling the
 * controller's <code>displayLogin</code> method.
 * <p>
 * This class also sets the look and feel of the entire application. It sets the
 * look and feel to the system's default. But, in case the application is ran in
 * a Linux system, it sets the look and feel to Java's cross-platform look.
 *
 * @author admin
 */
public class Ct {

    /**
     * The class's logger.
     */
    private static final Logger LOG = Logger.getLogger(Ct.class.getName());

    /**
     * Application's entry point.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        //Set the look and feel of the application
        try {
            var osName = System.getProperty("os.name");

            if (osName.equals("Linux")) {
                // Borders look weird (invisible) on Linux...
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } else {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }
        } catch (ClassNotFoundException
                 | InstantiationException
                 | IllegalAccessException
                 | UnsupportedLookAndFeelException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }

        //Start the login controller
        var loginController = new LoginController();
        //Ask the controller to initiate login
        loginController.displayLogin();
    }
}
