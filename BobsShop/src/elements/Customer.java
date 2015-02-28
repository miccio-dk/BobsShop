/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package elements;

import java.util.Objects;

/**
 * <pre>
 * The Customer class
 * encapsulates all functionality
 * related to the Customer and attributes.
 *
 * Factory Method Design Pattern
 * with data validity check
 *
 * For example:
 * <code>
 * Customer customer = Customer.create(int userID, String userPin, String userName, boolean userActive, String userAddress);
 * </code>
 * </pre>
 *
 * @version 1.3
 * @since updated userActive original create set to deprecated.
 * @author Miccio, Rudolf reviewed and documented by Rudolf.
 * @see elements
 * @todo add to report
 */
public class Customer {

    private final int userID;
    private String userPin;
    private String userName;
    private String userAddress;
    private boolean userActive;

    /**
     * <pre>
     * Factory method to Create an object of type Customer
     * </pre>
     *
     * @param userID final String
     * @param userPin String [Validity Check "^[0-9]{4}$"]
     * @param userName String
     * @param userAddress String
     * @deprecated Customer create(int userID, String userPin, String userName, String userAddress, boolean userActive)
     * @return Customer | null
     */
    public static Customer create(int userID, String userPin, String userName, String userAddress) {
        if (userID >= 0 && userPin.matches("^[0-9]{4}$") && !userName.trim().isEmpty() && !userAddress.trim().isEmpty()) {
            return new Customer(userID, userPin, userName, userAddress, false);
        }
        return null;
    }

    /**
     * <pre>
     * Factory method to Create an object of type Customer
     * </pre>
     *
     * @param userID final String
     * @param userPin String [Validity Check "^[0-9]{4}$"]
     * @param userName String
     * @param userAddress String
     * @param userActive boolean
     * @return Customer | null
     */
    public static Customer create(int userID, String userPin, String userName, String userAddress, boolean userActive) {
        if (userID >= 0 && userPin.matches("^[0-9]{4}$") && !userName.trim().isEmpty() && !userAddress.trim().isEmpty()) {
            return new Customer(userID, userPin, userName, userAddress, userActive);
        }
        return null;
    }

    /**
     * <pre>
     * Factory internal Constructor. <deprecated to include userActive>.
     * security loophole sets userActive to True for now all code referring to this should be updated.
     * </pre>
     *
     * @param userID final String
     * @param userPin String [Validity Check "^[0-9]{4}$"]
     * @param userName String
     * @param userAddress String
     * @deprecated
     */
    private Customer(int userID, String userPin, String userName, String userAddress) {
        this.userID = userID;
        this.userPin = userPin;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userActive = true;
    }

    /**
     * <pre>
     * Factory internal Constructor.
     * </pre>
     *
     * @param userID final String
     * @param userPin String [Validity Check "^[0-9]{4}$"]
     * @param userName String
     * @param userAddress String
     * @param userActive boolean
     */
    private Customer(int userID, String userPin, String userName, String userAddress, boolean userActive) {
        this.userID = userID;
        this.userPin = userPin;
        this.userName = userName;
        this.userAddress = userAddress;
        this.userActive = userActive;
    }

    /**
     * <pre>
     * get the user's userID.
     *
     * return the user ID
     * </pre>
     *
     * @return userID integer
     */
    public int getUserID() {
        return userID;
    }

    /**
     * <pre>
     * check the user's userPin.
     *
     * validates the User security pin code
     * </pre>
     *
     * @param pin String
     * @return ValidityCheck boolean
     */
    public boolean checkUserPin(String pin) {
        return userPin.toString().equals(pin);
    }

    /**
     * <pre>
     * get the user's userPin. <this has been deprecated for security purposes>
     *
     * return the user Pin
     * </pre>
     *
     * @return userPin String
     * @deprecated Use the Check Function instead
     */
    public String getUserPin() {
        return userPin;
    }

    /**
     * <pre>
     * get the user's userName.
     *
     * return the user Name
     * </pre>
     *
     * @return userName String
     */
    public String getUserName() {
        return userName;
    }

    /**
     * <pre>
     * get the user's addressAddress.
     *
     * return the user address
     * </pre>
     *
     *
     * @return userAddress String
     */
    public String getUserAddress() {
        return userAddress;
    }

    /**
     * <pre>
     * set the user's userPin.
     *
     * data validity
     * -check thePin matches "^[0-9]{4}$"
     * </pre>
     *
     * @param userPin String
     * @return TaskAccomplised boolean
     */
    public boolean setUserPin(String userPin) {
        if (userPin.matches("^[0-9]{4}$")) {
            this.userPin = userPin;
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * set the user's userName.
     *
     * data validity
     * -check trimmed String userName is not empty
     * </pre>
     *
     * @param userName String
     * @return TaskAccomplised boolean
     * @todo improved userName Filter maybe no numeric characters etc
     */
    public boolean setUserName(String userName) {
        if (!userName.trim().isEmpty()) {
            this.userName = userName;
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * set the user's Address.
     *
     * data validity
     * -check trimmed String userAddress is not empty
     * </pre>
     *
     * @param userAddress String
     * @return boolean
     * @todo improved userAddress Filter maybe no numeric characters etc
     */
    public boolean setUserAddress(String userAddress) {
        if (!userAddress.trim().isEmpty()) {
            this.userAddress = userAddress;
            return true;
        }
        return false;
    }

    /**
     * <pre>
     * toString.
     * </pre>
     *
     * @return String userID + "\t" + userPin + "\t" + userName + "\t" +
     * userAddress + "\n"
     * @override
     */
    @Override
    public String toString() {
        return userID + "\t" + userPin + "\t" + userName + "\t" + userAddress + "\n" + userActive + "\n";
    }

    /**
     * <pre>
     * is the Customer's account active.
     * </pre>
     * @return boolean
     */
    public boolean isActive() {
        return this.userActive;
    }

    /**
     * <pre>
     * lock the Customer's account.
     * </pre>
     */
    public void lock() {
        this.userActive = false;
    }

    /**
     * <pre>
     * unlock the Customer's account.
     * </pre>
     */
    public void unlock() {
        this.userActive = true;
    }

    /**
     *
     * @param obj
     * @return boolean
     */
    @Override
	public boolean equals(Object obj)
	{
		if (obj == this) {
			return true;
		}
		if (obj == null || obj.getClass() != this.getClass()) {
			return false;
		}

		Customer customer = (Customer)obj;
		return (getUserID()==customer.getUserID() && 
				getUserName().equals(customer.getUserName()) && 
				getUserAddress().equals(customer.getUserAddress()));
	}

   
}
