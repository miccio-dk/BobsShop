/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol;

/**
 * The abstract <code>Packet</code> class represents a data packet. A packet is
 * composed of a header and data. The header consists of the source,
 * destination, command or status, data length, and checksum. The size and
 * order of the header components are defined by the concrete subclasses.
 * 
 * @author ibr, hbe
 * @version 2012/10/11
 */
public abstract class Packet {

    private String source;
    private String destination;
    private CommandStatus commandStatus;
    private int dataLength;
    private String data;
    private int checksum;
    private String endofPacket;
    
    /**
     *Constructor
     */
    public Packet() {
    }

    /**
     * Initializes the <code>Packet</code> fields.
     * @param source
     * @param destination
     * @param commandStatus
     * @param data
     */
    public Packet(String source, String destination, CommandStatus commandStatus, String data) {
        this.source = source;
        this.destination = destination;
        this.commandStatus = commandStatus;
        this.dataLength = data.length();
        this.data = data;
        //generateChecksum();
    }

    /**
     * Get the value of bytePacket
     *
     * @return the value of bytePacket
     */
    public abstract byte[] getBytes();

    /**
     * Get the value of source
     * @return the value of source
     */
    public String getSource() {
        return source;
    }

    /**
     * Set the value of source
     * @param source new value of source
     */
    protected void setSource(String source) {
        this.source = source;
    }

    /**
     * Get the value of destination
     * @return the value of destination
     */
    public String getDestination() {
        return destination;
    }

    /**
     * Set the value of destination
     * @param destination new value of destination
     */
    protected void setDestination(String destination) {
        this.destination = destination;
    }

    /**
     * Get the value of commandStatus
     * @return the value of commandStatus
     */
    public CommandStatus getCommandStatus() {
        return commandStatus;
    }

    /**
     * Set the value of command or status
     * @param commandStatus new value of command or status
     */
    protected void setCommandStatus(CommandStatus commandStatus) {
        this.commandStatus = commandStatus;
    }

    /**
     * Get the value of dataLength
     * @return the value of dataLength
     */
    public int getDataLength() {
        return dataLength;
    }

    /**
     * Set the value of dataLength
     * @param dataLength new value of dataLength
     */
    protected void setDataLength(int dataLength) {
        this.dataLength = dataLength;
    }

    /**
     * Get the value of data
     * @return the value of data
     */
    public String getData() {
        return data;
    }

    /**
     *
     * @param data
     */
    protected void setData(String data) {
        this.data = data;
    }

    /**
     * Generate the packet checksum
     */
    public abstract void generateChecksum();

    /**
     * Get the value of checksum
     * @return the value of checksum
     */
    public int getChecksum() {
        return checksum;
    }

    /**
     * Set the value of checkSum
     * @param checkSum new value of checkSum
     */
    protected void setChecksum(int checkSum) {
        this.checksum = checkSum;
    }

    /**
     * Generate the endofPacket string
     */
    public abstract void generateEndofPacket();

    /**
     * Get the value of endofPacket
     * @return the value of endofPacket
     */
    public String getEndofPacket() {
        return endofPacket;
    }

    /**
     * Set the value of endofPacket
     * @param endofPacket new value of endofPacket
     */
    protected void setEndofPacket(String endofPacket) {
        this.endofPacket = endofPacket;
    }

    /**
     * Determines whether the byte array represents a complete
     * <code>Packet</code> data frame by comparing the number of bytes in the
     * byte array with the value specified in the datalength field
     * plus the header length.
     * The <code> abstract isPacketComplete</code> method is the "algorithm interface"
     * for a concrete strategy class in the Strategy design pattern.
     * The <code>Packet</code> class is the abstract Strategy class and the
     * <code>SerialFrame</code> class is the Context class.
     * @param byteFrame the byte array to check.
     * @return <code>true</code> if the byte array represents a complete
     * <code>Packet</code> data frame, <code>false</code> otherwise.
     */
    public abstract boolean isPacketComplete(byte[] byteFrame);

    /**
     * Determines the size of the first packet in <code>byteFrame</code>.
     *
     * Precondition: A complete packet is available in the <code>byteFrame</code> and
     * the packet starts at <code>byteFrame[0]</code>
     * @param byteFrame the byte array with a complete packet beginning at index 0
     * @return Number of bytes in the packet
     */
    public abstract int getPacketSize(byte[] byteFrame);
}
