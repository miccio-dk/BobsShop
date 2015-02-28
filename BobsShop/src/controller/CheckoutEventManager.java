/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import data.*;
import elements.*;
import java.util.Date;
import java.util.TooManyListenersException;
import javax.swing.JOptionPane;
import protocol.*;
import serial.FrameEvent;
import serial.FrameEventListener;
import serial.SerialTransceiver;

/**
 * The <code>CartEventManager</code> class is the central controller in
 * the application. It receives requests from the serial port, processes them
 * and transmits the response. The <code>CartEventManager</code> is
 * implemented using the State design pattern.
 *
 * @version 16/02/10
 * @author ibr
 */
public class CheckoutEventManager implements FrameEventListener {

    private SerialTransceiver transmitter;
	private Device device;
    //private String portNumber = "COM3"; // Windows
    //private String portNumber = "/dev/cu.PL2303-0040121A"; // Mac OS X
    private String source = "34";
    //private String destination = "12";
    private Packet packet;
    private final DataConnection conn;
	Cart virtualCart;
	Cart actualCart;

    /**
     *
     * @param device
     * @param source
     * @param conn
     * @param virtualCart
     */
    public CheckoutEventManager(Device device, String source, DataConnection conn, Cart virtualCart) {
		//this.portNumber = portNumber;
		this.device = device;
		this.source = source;
		//this.destination = destination;
        this.conn = conn;
		this.virtualCart = virtualCart;
		this.actualCart = null;
    }

    /**
     * Get the value of source
     *
     * @return the value of source
     */
    public synchronized String getSource() {
        return source;
    }

    /**
     * Set the value of source
     *
     * @param source new value of source
     */
    public synchronized void setSource(String source) {
        this.source = source;
    }

    /**
     * Get the value of packet
     *
     * @return the value of packet
     */
    public synchronized Packet getPacket() {
        return packet;
    }

    /**
     * Get the value of destination
     *
     * @return the value of destination
     */
    public synchronized String getDestination() {
        return device.getId();
    }

    /**
     * Set the value of device
     *
     * @param device new value of device
     */
    public synchronized void setDevice(Device device) {
        this.device = device;
    }

    /**
     * Set the value of transmitter
     *
     * @param transmitter new value of transmitter
     */
    public synchronized void setTransmitter(SerialTransceiver transmitter) {
        this.transmitter = transmitter;
    }

    /**
     * Open the transmitter serial port.
     *
     * @throws TooManyListenersException
     */
    public synchronized void openPort() throws TooManyListenersException {
        if (transmitter != null) {
            transmitter.openPort(device.getPortNumber());
        }
    }

    /**
     * Close the transmitter serial port.
     */
    public synchronized void closePort() {
        if (transmitter != null) {
            transmitter.closePort();
        }
    }

    /**
     * Send the <code>RFIDResponse</code> as an <code>ProjectPacket</code> using
     * the serial transmitter.
     *
     * @param status The status response to send
     * @param data The specific data for the status response
     */
    public synchronized void sendResponse(CommandStatus status, String data) {
        ProjectPacket responsePacket = new ProjectPacket(source, device.getId(), status, data);
        transmitter.transmit(responsePacket.getBytes());
    }

    /**
     * The method called by the <code>SerialFrame</code> when a complete data
     * packet is received.
     *
     * @param frameEvent the frame event
     */
    @Override
    public synchronized void frameReady(FrameEvent frameEvent) {
        byte[] received = frameEvent.getData();
		System.out.print("\nReceived at Server: [");
		System.out.println(new String(received) + "]");
		packet = new ProjectPacket(received);

		System.out.println("source:\t\t[" + packet.getSource() + "]");
		System.out.println("destination:\t[" + packet.getDestination() + "]");
		System.out.println("command:\t[" + packet.getCommandStatus() + "] (" + packet.getCommandStatus().name() + ")");
		System.out.println("data:\t\t[" + packet.getData() + "]");
		
		//TO DO Process request and send response
		int oldCheckSum = packet.getChecksum();
		packet.generateChecksum();
		int newCheckSum = packet.getChecksum();
		System.out.println("pkt. checksum:\t[" + oldCheckSum + "]");
		System.out.println("cal. checksum:\t[" + newCheckSum + "]");
		
		if(oldCheckSum==newCheckSum) {
			processRequest(packet);
		}
		else {
			sendResponse(CommandStatus.SEND_COMMAND_AGAIN, "");
		}
    }

    private void processRequest(Packet packet) {
        CommandStatus command = packet.getCommandStatus();

        switch (command) {
            case CHECKOUT_CUST: {
				System.out.println("aa");
				//CHECKOUT_CUST command contains the customer id
                Customer customer = conn.getCustomer(packet.getData());
				System.out.println();
				if(customer!=null) {
					actualCart = new Cart(conn.getCustomer(packet.getData()));
					System.out.println(actualCart.getCustomer().getUserName());
					sendResponse(CommandStatus.CHECKOUT_CUSTOMER_FOUND, "");
				}
				else {
					sendResponse(CommandStatus.CHECKOUT_CUSTOMER_NOTFOUND, "");
				}
                break;
            }
			case CHECKOUT_PROD: {
				//CHECKOUT_PROD contains the rfid
                Product prod = conn.getProduct(packet.getData());
				//if the product exists.. 
				if (prod != null) {
					//..check if it has already been scanned once
					Purchase oldPurchase = actualCart.getPurchase(prod);
					Purchase newPurchase;
					if(oldPurchase!=null) {
						//if yes, update qty
						newPurchase = Purchase.create(oldPurchase);
						newPurchase.setPurcQty(oldPurchase.getPurcQty()+1);
						actualCart.editPurchase(oldPurchase, newPurchase);
						//CHECKOUT_PRODUCT_ADDED sends the id, name, quantity, and running total
						String packetData = newPurchase.getProd().getProdID() + "\t" + 
											newPurchase.getProd().getProdName() + "\t" + 
											newPurchase.getPurcQty() + "\t" + 
											(int) (actualCart.getTotalCost()*100);
						sendResponse(CommandStatus.CHECKOUT_PRODUCT_ADDED, packetData);
					}
					else {
						//otherwise, add one
						newPurchase = Purchase.create(prod, 1, Purchase.FlowDirection.SELL);
						actualCart.addPurchase(newPurchase);
						//CHECKOUT_PRODUCT_ADDED sends the id, name, quantity, and running total
						String packetData = newPurchase.getProd().getProdID() + "\t" + 
											newPurchase.getProd().getProdName() + "\t" + 
											newPurchase.getPurcQty() + "\t" + 
											(int) (actualCart.getTotalCost()*100);
						sendResponse(CommandStatus.CHECKOUT_PRODUCT_ADDED, packetData);
					}		
                }
				else {
                    sendResponse(CommandStatus.CHECKOUT_PRODUCT_NOTFOUND, "");
                }
                break;
			}	
			case CHECKOUT_DONE: {
				JOptionPane.showMessageDialog(null, actualCart);
				JOptionPane.showMessageDialog(null, virtualCart);
				if(actualCart.equals(virtualCart)) {
					sendResponse(CommandStatus.CHECKOUT_CARTS_MATCH, "");
				}
				else {
					sendResponse(CommandStatus.CHECKOUT_CARTS_DONTMATCH, "");
				}
				
				break;
			}
			case CHECKOUT_PINCHECK: {
				Customer cust = conn.getCustomer(packet.getData().substring(4));
                //and here it extracts and checks the pin
                if (cust.checkUserPin(packet.getData().substring(0, 4))) {
					conn.addReceipt(cust, actualCart.getPurchasesList(), new Date());
                    sendResponse(CommandStatus.CHECKOUT_PIN_MATCHES, "");
                } else {
                    sendResponse(CommandStatus.CHECKOUT_PIN_DOESNTMATCH, "");
                }
                break;
			}
            default:
                throw new AssertionError(command.name()+" not supported.");
        }
    }
}
