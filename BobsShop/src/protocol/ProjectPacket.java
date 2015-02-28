/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol;

import java.util.Arrays;

/**
 * The
 * <code>ProjectPacket</code> class represents a data packet. The packet is made
 * up of source, destination, command or status, datalength, data fields,
 * checksum, and
 *
 * @version 2010/10/11
 * @author ibr, hbe
 */
public final class ProjectPacket extends Packet
{

	private final int SOURCEINDEX = 0;
	private final int SOURCESIZE = 2; //number of bytes
	private final int DESTINATIONINDEX = 2;
	private final int DESTINATIONSIZE = 2;
	private final int COMMANDSTATUSINDEX = 4;
	private final int COMMANDSTATUSSIZE = 2;
	private final int DATALENGTHINDEX = 6;
	private final int DATALENGTHSIZE = 4;
	private final int DATAINDEX = 10;
	private final int CHECKSUMSIZE = 3;
	private final String ENDOFPACKET = "\r\n";
	private final int ENDOFPACKETSIZE = ENDOFPACKET.length();
	private final int HEADERLENGTH =
			SOURCESIZE + DESTINATIONSIZE + COMMANDSTATUSSIZE + DATALENGTHSIZE + CHECKSUMSIZE + ENDOFPACKETSIZE;
	private byte[] bytePacket;

	public ProjectPacket()
	{
	}

	/**
	 * Constructs a new
	 * <code>ProjectPacket</code> based upon the source, destination, command or
	 * status and data supplied. The datalength and checksum fields are defined
	 * automatically from the parameters supplied.
	 *
	 * @param source the packet source.
	 * @param destination the packet destination
	 * @param commandStatus the command or status value
	 * @param data the packet data.
	 */
	public ProjectPacket(String source, String destination, CommandStatus commandStatus, String data)
	{
		super(source, destination, commandStatus, data);
		generateChecksum();
		generateEndofPacket();
		int packetLength = HEADERLENGTH + getDataLength();
		bytePacket = new byte[packetLength];
		initializeArray();
	}

	/**
	 * Constructs a new
	 * <code>ProjectPacket</code> from the byte array supplied. The byte array
	 * should contain data corresponding to all the
	 * <code>ProjectPacket</code> fields. The byte array is parsed and the
	 * <code>ProjectPacket</code> constructed.
	 *
	 * @param bytePacket the byte array containing the data for all *
	 * the <code>ProjectPacket</code> fields.
	 */
	public ProjectPacket(byte[] bytePacket)
	{
		String source = new String(Arrays.copyOfRange(bytePacket, SOURCEINDEX, SOURCEINDEX + SOURCESIZE));
		setSource(source);

		String destination = new String(Arrays.copyOfRange(bytePacket, DESTINATIONINDEX, DESTINATIONINDEX + DESTINATIONSIZE));
		setDestination(destination);

		String commandStatus = new String(Arrays.copyOfRange(bytePacket, COMMANDSTATUSINDEX, COMMANDSTATUSINDEX + COMMANDSTATUSSIZE));
		setCommandStatus(CommandStatus.getCommandStatus(commandStatus));

		String dataLengthString = new String(Arrays.copyOfRange(bytePacket, DATALENGTHINDEX, DATALENGTHINDEX + DATALENGTHSIZE));
		int dataLength = Integer.parseInt(dataLengthString);
		setDataLength(dataLength);

		String data = new String(Arrays.copyOfRange(bytePacket, DATAINDEX, DATAINDEX + dataLength));
		setData(data);

		int checksumIndex = DATAINDEX + dataLength;
		String checksumString = new String(Arrays.copyOfRange(bytePacket, checksumIndex, checksumIndex + CHECKSUMSIZE));
		int checksum = Integer.parseInt(checksumString);
		setChecksum(checksum);

		int endofPacketIndex = checksumIndex + CHECKSUMSIZE;
		String endofPacketString = new String(Arrays.copyOfRange(bytePacket, endofPacketIndex, endofPacketIndex + ENDOFPACKETSIZE));
	}

	/**
	 * Returns the
	 * <code>ProjectPacket</code> contents as a byte array.
	 *
	 * @return the <code>ProjectPacket</code> contents as a byte array.
	 */
	@Override
	public byte[] getBytes()
	{
		putSource();
		putDestination();
		putCommandStatus();
		putDataLength();
		putData();
		putChecksum();
		putEndofPacket();
		return bytePacket;
	}

	/**
	 * Fills byte array with ascii value for 0.
	 */
	private void initializeArray()
	{
		for (int i = 0; i < bytePacket.length; i++) {
			byte zeroAscii = "0".getBytes()[0];
			bytePacket[i] = zeroAscii;
		}
	}

	/**
	 * Populate the byte array representing the
	 * <code>ProjectPacket</code> contents with the source value.
	 */
	private void putSource()
	{
		if (getSource().length() >= SOURCESIZE) {
			byte[] sourceBytes = getSource().getBytes();
			System.arraycopy(sourceBytes, 0, bytePacket, SOURCEINDEX, SOURCESIZE);
		}
	}

	/**
	 * Populate the byte array representing the
	 * <code>ProjectPacket</code> contents with the destination value.
	 */
	private void putDestination()
	{
		if (getDestination().length() >= DESTINATIONSIZE) {
			byte[] destinationBytes = getDestination().getBytes();
			System.arraycopy(destinationBytes, 0, bytePacket, DESTINATIONINDEX, DESTINATIONSIZE);
		}
	}

	/**
	 * Populate the byte array representing the
	 * <code>ProjectPacket</code> contents with the commandStatus value.
	 */
	private void putCommandStatus()
	{
		if (getCommandStatus().getCode().length() >= COMMANDSTATUSSIZE) {
			byte[] commandStatusBytes = getCommandStatus().getCode().getBytes();
			System.arraycopy(commandStatusBytes, 0, bytePacket, COMMANDSTATUSINDEX, COMMANDSTATUSSIZE);
		}
	}

	/**
	 * Populate the byte array representing the
	 * <code>ProjectPacket</code> contents with the datalength value.
	 */
	private void putDataLength()
	{
		String format = "%0" + DATALENGTHSIZE + "d"; // with preceeding zeros
		String dataLengthString = String.format(format, getData().length());
		byte[] dataLengthBytes = dataLengthString.getBytes();
		System.arraycopy(dataLengthBytes, 0, bytePacket, DATALENGTHINDEX, DATALENGTHSIZE);
	}

	/**
	 * Populate the byte array representing the
	 * <code>ProjectPacket</code> contents with the data value.
	 */
	private void putData()
	{
		System.arraycopy(getData().getBytes(), 0, bytePacket, DATAINDEX, getDataLength());
	}

	/**
	 * Populate the byte array representing the
	 * <code>ProjectPacket</code> contents with the checksum value.
	 */
	private void putChecksum()
	{
		String format = "%0" + CHECKSUMSIZE + "d"; // with preceeding zeros
		String checksumString = String.format(format, getChecksum());
		byte[] checksumBytes = checksumString.getBytes();
		System.arraycopy(checksumBytes, 0, bytePacket, DATAINDEX + getDataLength(), CHECKSUMSIZE);
	}

	private void putEndofPacket()
	{
		if (getEndofPacket().length() >= ENDOFPACKETSIZE) {
			byte[] endofPacketBytes = getEndofPacket().getBytes();
			System.arraycopy(endofPacketBytes, 0, bytePacket, DATAINDEX + getDataLength() + CHECKSUMSIZE, ENDOFPACKETSIZE);
		}
	}

	/**
	 * Generate the checksum value.
	 */
	@Override
	public void generateChecksum()
	{
		int sum = 0;
		for(int i=0; i<SOURCESIZE; i++) {
			sum+=getSource().charAt(i);
		}
			
		for(int i=0; i<DESTINATIONSIZE; i++) {
			sum+=getDestination().charAt(i);
		}
		
		sum += Integer.parseInt(getCommandStatus().getCode());
		
		sum += getDataLength();

		for(int i=0; i<getDataLength(); i++) {
			sum+=getData().charAt(i);
		}

		setChecksum(sum%1000);
	}

	@Override
	public void generateEndofPacket()
	{
		setEndofPacket(ENDOFPACKET);
	}

	/**
	 * Determines whether the byte array represents a complete
	 * <code>ProjectPacket</code> data frame by comparing the number of bytes in
	 * the byte array with the value specified in the datalength field plus the
	 * header length. The
	 * <code>isPacketComplete</code> method is the "algorithm interface" for the
	 * concrete strategy class
	 * <code>ProjectPacket</code> in the Strategy design pattern. The
	 * <code>Packet</code> class is the abstract Strategy class and the
	 * <code>SerialFrame</code> class is the Context class.
	 *
	 * @param byteFrame the byte array to check.
	 * @return <code>true</code> if the byte array represents a complete
	 * <code>ProjectPacket</code> data frame, <code>false</code> otherwise.
	 */
	@Override
	public boolean isPacketComplete(byte[] byteFrame)
	{

		if (byteFrame.length >= DATALENGTHINDEX + DATALENGTHSIZE) { //data length field received
			byte[] dataLengthByte = new byte[DATALENGTHSIZE]; //create string from byte array
			System.arraycopy(byteFrame, DATALENGTHINDEX, dataLengthByte, 0, DATALENGTHSIZE);
			String dataLengthString = new String(dataLengthByte);
			int dataLength = Integer.parseInt(dataLengthString);
			if (byteFrame.length >= dataLength + HEADERLENGTH) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Determines the size of the first packet in
	 * <code>byteFrame</code>.
	 *
	 * Precondition: A complete packet is available in the
	 * <code>byteFrame</code> and the packet starts at
	 * <code>byteFrame[0]</code>
	 *
	 * @param byteFrame the byte array with a complete packet beginning at index
	 * 0
	 * @return Number of bytes in the packet
	 */
	@Override
	public int getPacketSize(byte[] byteFrame)
	{

		byte[] dataLengthByte = new byte[DATALENGTHSIZE];
		System.arraycopy(byteFrame, DATALENGTHINDEX, dataLengthByte, 0, DATALENGTHSIZE);
		String dataLengthString = new String(dataLengthByte);
		return Integer.parseInt(dataLengthString) + HEADERLENGTH;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ProjectPacket other = (ProjectPacket) obj;
		if (!Arrays.equals(this.bytePacket, other.bytePacket)) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = 71 * hash + Arrays.hashCode(this.bytePacket);
		return hash;
	}
}