/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bobsshop;

import controller.*;
import data.*;
import elements.*;
import forms.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.TooManyListenersException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import protocol.*;
import prototype.*;
import serial.SerialTransceiver;

/**
 * <pre>
 * The BobsShop class
 *
 * Factory Method Design Pattern
 * with data validity check.
 * </pre>
 *
 * @version 1.0
 * @author Miccio,reviewed and Documented by Yin
 * @see bobsshop
 * @see controller
 * @see data
 * @see elements
 * @see forms
 * @see protocol
 * @see prototype
 * @todo add to report
 */

public class BobsShop {

    /**
     *
     */
    public static final String DB_URL = "jdbc:mysql://sql4.freesqldatabase.com:3306/sql419771";
    public static final String DB_USERNAME = "sql419771";
    public static final String DB_PASSWD = "gH7*nI5%";
    public static final String LOGIN_PASSWD = "0000";
    public static final String COMM_SOURCE = "PC";
    public static Device COMM_CART1 = new Device("COM5", "CA");
    public static Device COMM_CHECKOUT = new Device("COM6", "CE");

    public static void main(String[] args) throws UnknownHostException {

        if ("FortesHome".equals(InetAddress.getLocalHost().getHostName())) {
            COMM_CART1 = new Device("COM8", "CA");
            COMM_CHECKOUT = new Device("COM9", "CO");
        }

        JFrame frame = new JFrame();
        DataConnection conn;
        CartEventManager managerCart1;
        CheckoutEventManager managerCheckOut1;
        Cart virtualCart1;

        JDialog dialog = new JDialog(frame, "Start", true);
        Start start = new Start(dialog);
        dialog.add(start);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        dialog.pack();
        dialog.setVisible(true);

        if (start.getPassword().equals(LOGIN_PASSWD) || true) {
            //JOptionPane.showMessageDialog(null, "Success! Hello & Welcome", "Access Granted", JOptionPane.INFORMATION_MESSAGE);

            conn = DataConnectionDB.create(DB_URL, DB_USERNAME, DB_PASSWD);

            
			//new cart begin			
			virtualCart1 = new Cart();
            managerCart1 = new CartEventManager(COMM_CART1, COMM_SOURCE, conn, virtualCart1);
            SerialTransceiver transceiverCart = new SerialTransceiver(new ProjectPacket(), managerCart1);
            managerCart1.setTransmitter(transceiverCart);
			//new cart end

			managerCheckOut1 = new CheckoutEventManager(COMM_CHECKOUT, COMM_SOURCE, conn, virtualCart1);
			SerialTransceiver transceiverCheckout = new SerialTransceiver(new ProjectPacket(), managerCheckOut1);
			managerCheckOut1.setTransmitter(transceiverCheckout);
			
            try {
                managerCart1.openPort();
                managerCheckOut1.openPort();
            } catch (TooManyListenersException ex) {
                JOptionPane.showMessageDialog(null, "Couln't open the port. Sorry :(", "Error Message", JOptionPane.ERROR_MESSAGE);
                Logger.getLogger(BobsShop.class.getName()).log(Level.SEVERE, null, ex);
            }

            frame.setTitle("Bob's Awesome Hardware Store");
            frame.add(new BobsInterface(new ProductsInterface(frame, conn), new CustomersInterface(frame, conn)));
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setVisible(true);
        } else if (start.getPassword().length() == 0) {
            JOptionPane.showMessageDialog(null, "Please fill these Fields", "Error Message", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } else {
            JOptionPane.showMessageDialog(null, "Invalid Pin Number", "Error Message", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }

    }
}
