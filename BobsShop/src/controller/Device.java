/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 * <pre>
 * 
 * The Device class
 * encapsulates all functionality
 * related to the Device attributes.
 * </pre>
 *
 * @version 1.0
 * @author Miccio, reviewed and documented by Yin.
 * @see controller
 * @todo add to report
 */
public class Device
{
	private String portNumber;
	private String id;

    /**
     *
     * @param portNumber
     * @param id
     */
    public Device(String portNumber, String id)
	{
		this.portNumber = portNumber;
		this.id = id;
	}

	/**
	 * @return the portNumber
	 */
	public String getPortNumber()
	{
		return portNumber;
	}

	/**
	 * @return the source
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * @param portNumber the portNumber to set
	 */
	public void setPortNumber(String portNumber)
	{
		this.portNumber = portNumber;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source)
	{
		this.id = source;
	}
	
	
}
