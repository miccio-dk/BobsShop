/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package protocol;

/**
 * <pre>
 * The CommandStatus class.
 * encapsulates all functionality
 * related to the CommandStatus attributes
 *
 * Factory Method Design Pattern
 * with data validity check.
 * </pre>
 *
 * 
 * @version 1.0
 * @author Miccio,reviewed and Documentation by Yin
 * @see protocol
 * @todo add to report
 */


/**
	 * <pre>
	 * enumerated CommandStatus to describe the different situation.
	 * For Example:
         * <code>
	 * REQUEST_CUST: request customer,which shows "01"
	 * CART_CUSTOMER_FOUND: customer found,which shows "11" for cart
	 * CHECKOUT_CUSTOMER_NOTFOUND: customer not found ,which shows "51" for checkout
         * </code>
         * etc
	 * </pre>
	 */
public enum CommandStatus
{
	//Cart => PC
	//08 is not first priority
	REQUEST_CUST("01"),				//
	VERIFY_CUST("02"),				//
	BLOCK_CUST("03"),				//
	ADD_PROD("04"),					//
	REM_PROD("05"),					//
	UPDATE_QTY("06"),				//
	FINALIZE("07"),					//
	CART_ACKN("08"),				//
	
	//Checkout => PC
	//38 is not first priority
	CHECKOUT_CUST("31"),
	CHECKOUT_PROD("34"),
	CHECKOUT_DONE("37"),
	CHECKOUT_PINCHECK("32"),
	CHECKOUT_ACKN("38"),
	
	//PC => Cart
	//'-ed' are not first priority
	CART_CUSTOMER_FOUND("11"),
	CART_CUSTOMER_NOTFOUND("21"),
	CART_PIN_MATCHES("12"),
	CART_PIN_DOESNTMATCH("22"),
	BLOCKED("13"),
	PRODUCT_FOUND("14"),
	PRODUCT_NOTFOUND("24"),
	REMOVED("15"),
	UPDATED("16"),
	BALANCE_EXCEEDED("17"),
	FINALIZED("18"),
	
	//PC => Checkout
	//'-ed' are not first priority
	CHECKOUT_CUSTOMER_FOUND("41"),
	CHECKOUT_CUSTOMER_NOTFOUND("51"),
	CHECKOUT_PIN_MATCHES("42"),
	CHECKOUT_PIN_DOESNTMATCH("52"),
	CHECKOUT_PRODUCT_ADDED("44"),
	CHECKOUT_PRODUCT_NOTFOUND("54"),
	CHECKOUT_CARTS_MATCH("47"),
	CHECKOUT_CARTS_DONTMATCH("57"),
	CHECKOUT_SUCCEEDED("99"),
	
	//checksum issues
	SEND_COMMAND_AGAIN("10"),
	SEND_STATUS_AGAIN("09");
	
	private final String code;
	
       /**        
       * <pre>
       * Constructor.
       *  
       * Factory internal Constructor.
       * </pre>
       * @param command String
       */
	
    private CommandStatus(String command) {
		this.code = command;
	}

     /**
     * <pre>
     * get the Code.
     *
     * return the code
     * </pre>
     *
     * @return code String
     */
    
    public String getCode() {
		return code;
	}

    /**
     * <pre>
     * get CommandStatus.
     * 
     * get the status of command
     * 
     * data validity check
     * -code equals to CommandStatus.values().code
     * <pre>
     * @param code String
     * @return CommandStatus
     */
    
    public static CommandStatus getCommandStatus(String code) {
		if (code != null) {
			for (CommandStatus b : CommandStatus.values()) {
				if (code.equalsIgnoreCase(b.code)) {
					return b;
				}
			}
		}
		throw new IllegalArgumentException("No command with code " + code + " found");
	}
	
      /**
     * <pre>
     * toString.
     * </pre>
     *
     * @return String code
     * @override
     */
    
	@Override
	public String toString(){
		return code;
	}
	
}
