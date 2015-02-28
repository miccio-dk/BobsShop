/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import data.*;
import elements.*;
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
public class CartEventManager implements FrameEventListener {

    private SerialTransceiver transmitter;
    private Device device;
    //private String portNumber = "COM3"; // Windows
    //private String portNumber = "/dev/cu.PL2303-0040121A"; // Mac OS X
    private String source;
    //private String destination = "12";
    private Packet packet;
    private DataConnection conn;
	Cart virtualCart;

    public CartEventManager(Device device, String source, DataConnection conn, Cart virtualCart) {
		//this.portNumber = portNumber;
		this.device = device;
		this.source = source;
		//this.destination = destination;
		this.conn = conn;
		this.virtualCart = virtualCart;
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
     * Set the value of destination
     *
     * @param device
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
            case REQUEST_CUST: {
                //REQUEST_CUST contains the UID
                Customer cust = conn.getCustomer(packet.getData());
                if (cust != null && cust.isActive()) {
                    sendResponse(CommandStatus.CART_CUSTOMER_FOUND, cust.getUserName());
                } else {
                    sendResponse(CommandStatus.CART_CUSTOMER_NOTFOUND, "");
                }
                break;
            }
            case VERIFY_CUST: {

				System.out.println(packet.getData());
				System.out.println("rfid\t" + packet.getData().substring(4));
				System.out.println("pin\t" + packet.getData().substring(0,4));
				
                //VERIFY_CUST command contains pin+UID, so here it extracts the UID
                Customer cust = conn.getCustomer(packet.getData().substring(4));
				
                //and here it extracts and checks the pin
                if (cust.checkUserPin(packet.getData().substring(0, 4))) {
                    sendResponse(CommandStatus.CART_PIN_MATCHES, String.valueOf((long) conn.getBalance(cust)));
                    //should create a virtual cart
					virtualCart.erasePurchasesList();
					virtualCart.setCustomer(cust);//future implementation possible singlton design pattern
                } else {
                    sendResponse(CommandStatus.CART_PIN_DOESNTMATCH, "");
                }
                break;
            }
            case BLOCK_CUST: {
				//BLOCK_CUST containt the customer's UID
				Customer cust = conn.getCustomer(packet.getData());
				cust.lock();
				conn.editCustomer(cust);
                break;
            }
            case ADD_PROD: {
                //ADD_PROD contains the rfid
                Product prod = conn.getProduct(packet.getData());
				//if the product exists.. 
				if (prod != null) {
					//..check if it has already been scanned once
					Purchase oldPurchase = virtualCart.getPurchase(prod);
					Purchase newPurchase;
					if(oldPurchase!=null) {
						//if yes, update qty
						newPurchase = Purchase.create(oldPurchase);
						newPurchase.setPurcQty(oldPurchase.getPurcQty()+1);
						virtualCart.editPurchase(oldPurchase, newPurchase);
						if(virtualCart.getTotalCost()<conn.getBalance(virtualCart.getCustomer())) {
							String packetData = newPurchase.getProd().getProdID() + "\t" + 
												newPurchase.getProd().getProdName() + "\t" + 
												(int) (newPurchase.getProd().getProdPrice() * 100) + "\t" + 
												newPurchase.getPurcQty() + "\t" + 
												(int) (conn.getBalance(virtualCart.getCustomer())-virtualCart.getTotalCost());
							sendResponse(CommandStatus.UPDATED, packetData);
						}
						else {
							virtualCart.editPurchase(newPurchase, oldPurchase);
							sendResponse(CommandStatus.BALANCE_EXCEEDED, "");
						}
					}
					else {
						//PRODUCT_FOUND sends the id, name, and price
						String packetData = prod.getProdID() + "\t" + prod.getProdName() + "\t" + (int) (prod.getProdPrice() * 100);
						sendResponse(CommandStatus.PRODUCT_FOUND, packetData);
						//should add item to the virtual cart (qty=1)
						virtualCart.addPurchase(Purchase.create(prod, 1, Purchase.FlowDirection.SELL));
					}		
                }
				else {
                    sendResponse(CommandStatus.PRODUCT_NOTFOUND, "");
                }
                break;
            }
            case REM_PROD: {
				//REM_PROD contains the id
                //should remove the item from the virtual cart
				virtualCart.remPurchase(Integer.parseInt(packet.getData()));
                break;
            }
            case UPDATE_QTY: {
                //UPDATE_QTY contains the id and the new quantity
                int prodID = Integer.parseInt(packet.getData().substring(0, packet.getData().indexOf("\t")));
                int prodQty = Integer.parseInt(packet.getData().substring(packet.getData().indexOf("\t") + 1));
				System.out.println(prodID + " " + prodQty);
				if(prodQty==0) {
					virtualCart.remPurchase(prodID);
					String packetData = prodID + "\t" + (int) (conn.getBalance(virtualCart.getCustomer())-virtualCart.getTotalCost());
					sendResponse(CommandStatus.REMOVED, packetData);
				}
				else {
					//should update the quantity for the item
					Purchase oldPurchase = virtualCart.getPurchase(prodID);
					Purchase newPurchase;
					if(oldPurchase!=null) {
						newPurchase = Purchase.create(oldPurchase);
						newPurchase.setPurcQty(prodQty);
						virtualCart.editPurchase(oldPurchase, newPurchase);					
						if(virtualCart.getTotalCost()<conn.getBalance(virtualCart.getCustomer())) {
								String packetData = newPurchase.getProd().getProdID() + "\t" + 
													newPurchase.getProd().getProdName() + "\t" + 
													(int) (newPurchase.getProd().getProdPrice() * 100) + "\t" + 
													newPurchase.getPurcQty() + "\t" + 
													(int) (conn.getBalance(virtualCart.getCustomer())-virtualCart.getTotalCost());
								sendResponse(CommandStatus.UPDATED, packetData);
							}
							else {
								virtualCart.editPurchase(newPurchase, oldPurchase);
								sendResponse(CommandStatus.BALANCE_EXCEEDED, "");
							}
					}
					else {
						sendResponse(CommandStatus.PRODUCT_NOTFOUND, "");
					}
				}
                break;
            }
            case FINALIZE: {
                //should "block" the cart
				JOptionPane.showMessageDialog(null, virtualCart);
                sendResponse(CommandStatus.FINALIZED, "");
                break;
            }
            default:
                throw new AssertionError(command.name()+" not supported.");
        }
    }
}
