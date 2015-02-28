private final int SOURCEINDEX = 0;
private final int SOURCESIZE = 2; //#of bytes
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
	SOURCESIZE +
	DESTINATIONSIZE+
	 COMMANDSTATUSSIZE +
	 DATALENGTHSIZE +
	 CHECKSUMSIZE +
	 ENDOFPACKETSIZE;
private byte[] bytePacket;


//Cart => PC
    //08 is not first priority
    REQUEST_CUST("01"), //
    VERIFY_CUST("02"), //
    BLOCK_CUST("03"), //
    ADD_PROD("04"), //
    REM_PROD("05"), //
    UPDATE_QTY("06"), //
    FINALIZE("07"), //
    CART_ACKN("08"), //

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
