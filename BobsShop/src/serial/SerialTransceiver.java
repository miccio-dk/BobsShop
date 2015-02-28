/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package serial;

import java.util.TooManyListenersException;
import protocol.Packet;

/**
 * The <code>SerialTransceiver</code> class represents a serial transmitter
 * and receiver. When a complete data packet is received the
 * <code>FrameEventListener</code> is notified.
 * @version 16/02/10
 * @author ibr
 */
public class SerialTransceiver {

    private final Packet packet;
    private SerialFrame serialFrame;
    private final FrameEventListener frameEventListener;

    /**
     * Constructs <code>SerialTransceiver</code> object specifying
     * the packet protocol and frameEventListener.
     * @param packet the packet protocol
     * @param frameEventListener the listener
     */
    public SerialTransceiver(Packet packet, FrameEventListener frameEventListener) {
        this.packet = packet;
        this.frameEventListener = frameEventListener;
    }

    /**
     * Opens the specified serial port.
     * If the corresponding <code>SerialFrame</code> already has a listener
     * registered a <code>TooManyListenersException</code> is thrown.
     * @param port the serial port
     * @throws TooManyListenersException
     */
    public void openPort(String port) throws TooManyListenersException {
        if (serialFrame == null) {
            serialFrame = new SerialFrame(port, packet);
            serialFrame.addFrameEventListener(frameEventListener);
        }
    }

    /**
     * Transmits the specified byte array through the opened serial port.
     * @param bytePacket the byte array to transmit
     */
    public synchronized void transmit(byte[] bytePacket) {
        serialFrame.transmit(bytePacket);
    }

    /**
     * Closes the serial port.
     */
    public void closePort() {
        serialFrame.closePort();
        serialFrame = null;
    }
}
