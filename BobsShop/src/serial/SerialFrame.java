/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serial;

import gnu.io.*;
import java.io.*;
import java.util.*;
import protocol.Packet;

/**
 * Wrapper class for Java serial classes.
 * Uses the Strategy pattern to specify the <code>FrameProtocol</code>
 * Baudrate = 19200 bits/sec.
 * @author lmo, oc, ibr, hbe
 * @version 29/10/10
 */
public class SerialFrame implements SerialPortEventListener {

    private FrameEventListener frameEventListener;
    private final Enumeration portList;
    private CommPortIdentifier portId;
    private SerialPort serialPort;
    private InputStream inputStream;
    private OutputStream outputStream;
    private final int BAUDRATE = 19200;
    private final ArrayList<Byte> byteFrameList = new ArrayList<>();
    private final Packet packet;

    /**
     * Constructs a new SerialFrame instance and opens the port given in the
     * argument port.
     * If an initial attempt to attach a listener succeeds,
     * subsequent attempts will throw TooManyListenersException without
     * effecting the first listener. 
     * @param port The port to use for communication (COM1, COM2 etc.)
     * @param packet
     * @throws java.util.TooManyListenersException
     */
    public SerialFrame(String port, Packet packet) throws TooManyListenersException {
        this.packet = packet;
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                if (portId.getName().equals(port)) {
                    try {
                        serialPort = (SerialPort) portId.open("", 500);
                        serialPort.setSerialPortParams(
                                BAUDRATE,
                                SerialPort.DATABITS_8,
                                SerialPort.STOPBITS_1,
                                SerialPort.PARITY_NONE);
                        inputStream = serialPort.getInputStream();
                        serialPort.addEventListener(this);
                        serialPort.notifyOnDataAvailable(true);
                    } catch (PortInUseException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (UnsupportedCommOperationException e) {
                        e.printStackTrace();
                    } catch (TooManyListenersException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Close the port after transmission.
     */
    public void closePort() {
        serialPort.close();
    }

    /**
     * Adds a FrameEventListener to this instance.
     * Only one listener is allowed per port.
     * @param fel the listener
     * @throws TooManyListenersException
     */
    public void addFrameEventListener(FrameEventListener fel) throws
            TooManyListenersException {
        if (fel == null) {
            return;
        }
        if (frameEventListener != null) {
            throw new TooManyListenersException("Only one listener allowed");
        } else {
            frameEventListener = fel;
        }
    }

    /**
     * Implementation of the SerialPortEventListener method.
     * This method receives data from the serial port and sends the received
     * byte Array in a FrameEvent to a FrameEventListener that has registered
     * using addFrameEventListener.
     * The <code>FrameProtocol</code> is specified using the Strategy design pattern.
     * @param event Event object.
     */
    @Override
    public void serialEvent(SerialPortEvent event) {
        switch (event.getEventType()) {
            case SerialPortEvent.DATA_AVAILABLE:
                byte[] readBuffer = new byte[20];
                int numBytes = 0;
                try {
                    while (inputStream.available() > 0) {
                        numBytes = inputStream.read(readBuffer);

                        //extract bytes received from readBuffer and store in arrayList
                        for (int i = 0; i < numBytes; i++) {
                            byteFrameList.add(readBuffer[i]);
                        }
                    }
                    //convert Byte arraylist to byte[]
                    byte[] byteFrame = new byte[byteFrameList.size()];
                    for (int i = 0; i < byteFrameList.size(); i++) {
                        byteFrame[i] = (Byte) byteFrameList.get(i).byteValue();
                    }

                    //String str = new String(readBuffer);
                    // check for 0 when the transmitter is closed.
                    //if (str.charAt(0) == '\u0000') {
                    //    break;
                    //}

                    //Strategy pattern
                    if (this.packet.isPacketComplete(byteFrame)) {
                        //Remove packet data from byteFrameList
                        int packetSize = this.packet.getPacketSize(byteFrame);
                        for (int i = 0; i < packetSize; i++) {
                            byteFrameList.remove(0);
                        }

                        //Send FrameEvent to frameEventListener
                        if (frameEventListener != null) {
                            FrameEvent responseEvent = new FrameEvent(this, byteFrame);
                            frameEventListener.frameReady(responseEvent);
                            break;
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            default:
                System.err.println("EVENT TYPE : " + event.getEventType());
                break;
        }
    }

    /**
     * Sends a byte array frame to the serial port.
     * Does not add a termination char to the byyte array
     * @param byteArray the array to be sent.
     */
    public synchronized void transmit(byte[] byteArray) {
        //Debug output
        System.out.println("Sent back:\t[" + new String(byteArray) + "]");
        try {
            outputStream = serialPort.getOutputStream();
            outputStream.write(byteArray);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
