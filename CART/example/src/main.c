/**
 * \file main.c
 *
 * \brief This File contains the main Logic for the program a simple series of tasks that will allow the user to navigate through the usability of the software
 * \par Content
 * -# The lcd.h and lcd.c files were imported teacher code.
 * -# The serialio.h and serialio.c files were imported teacher code.
 * -# This Shopping Cart started off as an Integration Test but developed into the Full working Solution
 * \author Riccardo Doxygen By Rudolf Anton Fortes
 * \version 1.2
 * \date Dec-2013
 */

/**
 * \mainpage Group2 Doxygen commented Code.
 *
 * \par Empty user application template
 *
 * This project is was done for a a 2nd Semester Project at DTU Ballerup for the combined courses of EPRODE2-2 and EPROPE2-2 which started on Sept 2013.
 * 
 * \par Content
 *
 * -# The lcd.h and lcd.c files were imported teacher code.
 * -# The serialio.h and serialio.c files were imported teacher code.
 * -# This Shopping Cart started off as an Integration Test but developed into the Full working Solution
 * \author Riccardo Doxygen By Rudolf Anton Fortes
 */

#ifndef F_CPU
#define F_CPU			10000000UL	/**< F_CPU set to use delay with the microcontrollers speed*/
#endif
#include <avr/interrupt.h>	// define interrupt service */
#include <stdio.h>			// define function sprintf */
#include <stdlib.h>
#include "defines.h"		// define various macros etc. */
#include "keypad.h"			// define Keypad functions */
#include "display.h"		// define Display handling functions */
#include "serialio.h"		// define Serial functions */
#include "rfid.h"			// define RFID functions */
#include "packet.h"			// define the protocol packet */
#include "interrupts.h"		// define the interrupts */

#define THIS_DEVICE "CA"	/**< define this device CA[RT] */
#define MAIN_DEVICE "PC"	/**< define main device PC(Server) */

// interrupts vars
volatile bool CardPresent = false;		/**< interrupts Variable: CardPresent */
volatile bool SPI_dataready = false;	/**< interrupts Variable: SPI_dataready */
volatile bool keypadPressed = false;	/**< interrupts Variable: keypadPressed */
volatile bool doneShopping = false;		/**< interrupts Variable: doneShopping */
volatile int milliseconds = 0;			/**< interrupts Variable: milliseconds */

// keypad vars
char pin[]="1234";								/**< keypad Variable: pin Initially set size of the pin to 4 digits */
char qty[]="99";								/**< keypad Variable: qty Initially set the maximum quantity to 99 2 digits */
char code[]="ABCDEFGHIJKLMNO";					/**< keypad Variable: code Initially set the size of 16 the maximum packet data length */
	
// rfid vars
char buffData[33]={0};							/**< RFID Variable: buffData Initial set zeros */
unsigned char status='\0';						/**< RFID Variable: status Initial set \0 */
	
// packet vars
char	mainSource[SOURCESIZE+1]={0};			/**< Packet Variable: mainSource */
char	mainDestination[DESTINATIONSIZE+1]={0};	/**< Packet Variable: mainDestination */
CommandStatus mainCommandStatus;				/**< Packet Variable: mainCommandStatus */
int		mainDataLength;							/**< Packet Variable: mainDataLength */
char	mainData[50]={0};						/**< Packet Variable: mainData Initially set zeros */
int		mainChecksum;							/**< Packet Variable: mainChecksum */
	
// serial vars
char buffPacketSend[60]={0};					/**< serial Variable: Buffer Packet Send - buffPacketSend Initial set zeros */
char buffPacketGet[60]={0};						/**< serial Variable: Buffer Packet Get - buffPacketGet Initial set zeros*/

// main flow vars
volatile bool	loggedIn = false;				/**< main flow Variable: loggedIn */
volatile bool	userDetected = false;			/**< main flow Variable: userDetected */
volatile bool	prodDetected = false;			/**< main flow Variable: prodDetected */
volatile int	logAttempts = 0;				/**< main flow Variable: logAttempts */

// user infos
char	userRFID[33] = {0};						/**< user infos Variable: userRFID - Initial set zeros*/
long	balance = 0;							/**< user infos Variable: balance */

// prod info
char	lastProdRFID[33] = {0};					/**< prod infos Variable: lastProdRFID */
int		lastProdID = 0;							/**< prod infos Variable: lastProdID */
int		prevProdID = 0;							/**< prod infos Variable: prevProdID */
char	lastProdName[QTY_COL+1] = {0};			/**< prod infos Variable: lastProdName[QTY_COL+1] */
int		lastProdPrice = 0;						/**< prod infos Variable: lastProdPrice */
int		lastProdQty = 0;						/**< prod infos Variable: lastProdQty */

/**
 * \brief Initialize
 * This function initializes all the IO on the AVR Board.
 * 
 * \return void
 */
void Initialize ();
/**
 * \brief Detect user.
 * This Function Retrieves a user
 \return void
 */
void detectUser();
/**
 * \brief this is the logic and communication protocol for requesting a login.
 * 
 * \return long balance details
 */
long login();

/**
 * \brief this is the communication protocol for blocking a customer.
 * 
 * 
 * \return void
 */
void blockUser();

/**
 * \brief wait for scanning a product if scanned then establish communication to retrieve RFID's information from the database
 * 
 * \return int Product ID
 */
int scanProduct();

/**
 * \brief update the quantity of the last selected product
 * 
 * 
 * \return void
 */
void updateQuantity();
/**
 * \brief Infinite loop waits for input from either keypress F "exit key" or card scans to exit
 * 
 * \return void
 */
void waitCheckout();

/**
 * \brief reset all values to default and lock the cart. only physical reset by bob will re-enable the cart
 * 
 * 
 * \return void
 */
void finalizeCart();


/**
 * \brief This is the Main it contains the general Logic of the code
 * 
 * \return int
 */
int main(void)
{
	// setup
	Initialize();
	SerialInitialize();
	LCDInit(20);
	
	
	// main flow
	while(true) {
		// idle mode;
		SetMode(IDLE);
		detectUser();
	
		// login mode
		SetMode(LOGIN);
		SetCustomer(mainData);
		balance = login();
	
		//block user or continue to shopping
		if(!loggedIn) {
			blockUser();
		}
		else {
			// shopping mode
			SetMode(SHOPPING);
			SetBalance(balance);
			while(!doneShopping) {
				lastProdID = scanProduct();
				updateQuantity();
				waitCheckout();
			}
			finalizeCart();
			_delay_ms(7000);
		}
		
	}
	return 0;
}

void waitCheckout() {
	int col, row;
	while(true)
	{
		//if enter button pressed, exits
		if(CardPresent || doneShopping)
		{
			return;
		}
		
		//columns switching
		PORTB = ++PORTB%4;
		_delay_us(5);

		//key detection and debouncing
		if(PINB&KEYPRESS_PIN)
		{
			_delay_ms(30);
			
			if(PINB&KEYPRESS_PIN)
			{
				col = 4-PORTB;
				row = (PINA&0b00000011)+1;
				
				//value of the last pressed key
				if(CoordToChar(row,col)==EXIT_KEY) {
					doneShopping = true;
				}
			}
		}
	}
}

void finalizeCart() {
	SetMessage(DEFAULT_DONE_MSG);
	memset(buffPacketSend, 0, 60);
	NewProjectPacket(THIS_DEVICE, MAIN_DEVICE, FINALIZE, "", buffPacketSend);
	SerialPutString(buffPacketSend);
	
	loggedIn = false;
	userDetected = false;
	prodDetected = false;
	logAttempts = 0;
	CardPresent = false;
	SPI_dataready = false;
	keypadPressed = false;
	doneShopping = false;
	milliseconds = 0;
}

void updateQuantity() {
	SetMessage(DEFAULT_QTY_MSG);
	//if the user successfully entered a quantity..
	if(KeypadGetQtyTimed(qty, 2, 5000)) {
		//..send an update (or remove) request
		sprintf(mainData, "%d\t%d", lastProdID, atoi(qty));
		memset(buffPacketSend, 0, 60);
		NewProjectPacket(THIS_DEVICE, MAIN_DEVICE, UPDATE_QTY, mainData, buffPacketSend);
		SerialPutString(buffPacketSend);
		
		//fetch and parse response (TODO: timeout as in rfid.c)
		while(SerialInputReady());
		memset(buffPacketGet, 0, 60);
		memset(mainData, 0, 50);
		SerialGetString(buffPacketGet, 60);
		NewProjectPacketFromPacket(mainSource, mainDestination, &mainCommandStatus, &mainDataLength, mainData, &mainChecksum, buffPacketGet);
		
		//validate the received packet
		if(generateChecksum(mainSource, mainDestination, mainCommandStatus, mainDataLength, mainData)==mainChecksum) {
			if(mainCommandStatus==UPDATED) {
				//parses data and update screen
				sscanf(mainData, "%d\t%s\t%d\t%d\t%ld", &lastProdID, lastProdName, &lastProdPrice, &lastProdQty, &balance);
				SetBalance(balance);
				SetProduct(lastProdName, lastProdPrice);
				SetQuantity(lastProdQty);
				SetMessage(DEFAULT_SHOPPING_MSG);
			}
			else if(mainCommandStatus==PRODUCT_NOTFOUND) {
				SetMessage(DEFAULT_PROD_ERROR);
				_delay_ms(2000);
				SetMessage(DEFAULT_SHOPPING_MSG);
			}
			else if(mainCommandStatus==BALANCE_EXCEEDED) {
				SetMessage(DEFAULT_BALANCE_ERROR);
				_delay_ms(1000);
				SetMessage(DEFAULT_SHOPPING_MSG);
			}
			else if(mainCommandStatus==REMOVED) {
				SetMessage(DEFAULT_PROD_REM);
				_delay_ms(1000);
				SetMessage(DEFAULT_SHOPPING_MSG);
			}
			else {
				SetMessage(DEFAULT_INTERNAL_ERROR);
				_delay_ms(2000);
				SetMessage(DEFAULT_SHOPPING_MSG);
			}
		}
		else {
			SetMessage(DEFAULT_INTERNAL_ERROR);
			_delay_ms(2000);
		}
	}
	else {
		SetMessage(DEFAULT_SHOPPING_MSG);
	}
}

int scanProduct() {
	int prodID=0;
	prodDetected = false;
	while(!prodDetected && !doneShopping) {
		if(CardPresent) {
			
			/*
			char a[10];
			sprintf(a, "%d", milliseconds);
			SetMessage(a);
			*/
			
			status = RFIDGetData(lastProdRFID);
			if(status==ACK) {
				//request product and add one
				memset(buffPacketSend, 0, 60);
				NewProjectPacket(THIS_DEVICE, MAIN_DEVICE, ADD_PROD, lastProdRFID, buffPacketSend);
				SerialPutString(buffPacketSend);
				
				//fetch and parse response (TODO: timeout as in rfid.c)
				while(SerialInputReady());
				memset(buffPacketGet, 0, 60);
				memset(mainData, 0, 50);
				SerialGetString(buffPacketGet, 60);
				NewProjectPacketFromPacket(mainSource, mainDestination, &mainCommandStatus, &mainDataLength, mainData, &mainChecksum, buffPacketGet);
				
				
				//validate the received packet
				if(generateChecksum(mainSource, mainDestination, mainCommandStatus, mainDataLength, mainData)==mainChecksum)
				{
					if(mainCommandStatus==PRODUCT_FOUND) {
						//parses data and update screen
						sscanf(mainData, "%d\t%s\t%d", &prodID, lastProdName, &lastProdPrice);
						SetProduct(lastProdName, lastProdPrice);
						balance=(balance*100-lastProdPrice)/100;
						SetBalance(balance);
						lastProdQty=1;
						prodDetected = true;
					}
					else if(mainCommandStatus==PRODUCT_NOTFOUND) {
						SetMessage(DEFAULT_PROD_ERROR);
						_delay_ms(2000);
						SetMessage(DEFAULT_SHOPPING_MSG);
					}
					else if(mainCommandStatus==UPDATED) {
						//parses data and update screen
						sscanf(mainData, "%d\t%s\t%d\t%d\t%ld", &prodID, lastProdName, &lastProdPrice, &lastProdQty, &balance);
						SetBalance(balance);
						SetProduct(lastProdName, lastProdPrice);
						SetQuantity(lastProdQty);
						SetMessage(DEFAULT_SHOPPING_MSG);
						prodDetected = true;
					}
					else if(mainCommandStatus==BALANCE_EXCEEDED) {
						SetMessage(DEFAULT_BALANCE_ERROR);
						_delay_ms(1000);
						SetMessage(DEFAULT_SHOPPING_MSG);
					}
					else {
						SetMessage(DEFAULT_INTERNAL_ERROR);
						_delay_ms(2000);
						SetMessage(DEFAULT_SHOPPING_MSG);
					}
				}
				else {
					SetMessage(DEFAULT_INTERNAL_ERROR);
					_delay_ms(2000);
					SetMessage(DEFAULT_SHOPPING_MSG);
				}
			}
		}
	}
	return prodID;
}

void blockUser() {
	memset(buffPacketSend, 0, 60);
	NewProjectPacket(THIS_DEVICE, MAIN_DEVICE, BLOCK_CUST, userRFID, buffPacketSend);
	SerialPutString(buffPacketSend);
	//maybe should wait for a response, gonna check in the java code
	userDetected = false;
	SetMessage("Card blocked.");
	_delay_ms(4000);
}

long login()
{
	while(!(loggedIn || logAttempts>=3)) {
		//acquires pin and send a request
		SetMessage(DEFAULT_PIN_MSG);
		KeypadGetPIN(pin, 4);
		sprintf(mainData, "%s%s", pin, userRFID);
		memset(buffPacketSend, 0, 60);
		NewProjectPacket(THIS_DEVICE, MAIN_DEVICE, VERIFY_CUST, mainData, buffPacketSend);
		SerialPutString(buffPacketSend);
		
		//fetch and parse response (TODO: timeout as in rfid.c)
		while(SerialInputReady());
		memset(buffPacketGet, 0, 60);
		memset(mainData, 0, 50);
		SerialGetString(buffPacketGet, 60);
		NewProjectPacketFromPacket(mainSource, mainDestination, &mainCommandStatus, &mainDataLength, mainData, &mainChecksum, buffPacketGet);
		
		//validate the received packet
		if(generateChecksum(mainSource, mainDestination, mainCommandStatus, mainDataLength, mainData)==mainChecksum)
		{
			if(mainCommandStatus==CART_PIN_MATCHES) {
				loggedIn = true;
			}
			else if(mainCommandStatus==CART_PIN_DOESNTMATCH) {
				logAttempts++;
				SetMessage(DEFAULT_PIN_ERROR);
				_delay_ms(700);
			}
			else {
				SetMessage(DEFAULT_INTERNAL_ERROR);
				_delay_ms(2000);
			}
		}
		else {
			SetMessage(DEFAULT_INTERNAL_ERROR);
			_delay_ms(2000);
		}
	}
	
	if(loggedIn) {
		return atoi(mainData);
	}
	else {
		return -1;
	}
}

void detectUser()
{
	while(!userDetected) {
		if(CardPresent) {
			status = RFIDGetData(userRFID);
			if(status==ACK) {
				//request user name
				memset(buffPacketSend, 0, 60);
				NewProjectPacket(THIS_DEVICE, MAIN_DEVICE, REQUEST_CUST, userRFID, buffPacketSend);
				SerialPutString(buffPacketSend);
				
				//fetch and parse response (TODO: timeout as in rfid.c)
				while(SerialInputReady());
				memset(buffPacketGet, 0, 60);
				SerialGetString(buffPacketGet, 60);
				NewProjectPacketFromPacket(mainSource, mainDestination, &mainCommandStatus, &mainDataLength, mainData, &mainChecksum, buffPacketGet);
				
				
				//validate the received packet
				if(generateChecksum(mainSource, mainDestination, mainCommandStatus, mainDataLength, mainData)==mainChecksum)
				{
					if(mainCommandStatus==CART_CUSTOMER_FOUND) {
						userDetected = true;
					}
					else if(mainCommandStatus==CART_CUSTOMER_NOTFOUND) {
						SetMessage(DEFAULT_CUST_ERROR);
						_delay_ms(2000);
						SetMessage(DEFAULT_IDLE_MSG);
					}
					else {
						SetMessage(DEFAULT_INTERNAL_ERROR);
						_delay_ms(2000);
						SetMessage(DEFAULT_IDLE_MSG);
					}
				}
				else {
					SetMessage(DEFAULT_INTERNAL_ERROR);
					_delay_ms(2000);
					SetMessage(DEFAULT_IDLE_MSG);
				}
			}
		}
	}
}


/**
 * \brief Initialize the IO settings for the Board
 * 
 * \return void
 */
void Initialize ()
{
	DDRA  = 0b11111100;
	PORTA = 0b00000000;
	DDRB  = 0b10110011;
	PORTB = 0b00000000;
	DDRC  = 0b00000000;
	PORTC = 0b00000000;
	DDRD  = 0b00000000;
	PORTD = 0b00000000;

	#if TIMER0_PRESCALER
		TCCR0 = 8 | (TIMER0_TOGGLE<<4) | TIMER0_PRESCALER;
		TCNT0 = 0;
		OCR0  = TIMER0_DIVISOR;
	#else
		TCCR0 = 0;
		TCNT0 = 0;
		OCR0  = 0;
	#endif

	// Timer/Counter 1 initialization
	#if TIMER1_PRESCALER
		TCCR1A = (TIMER1_TOGGLE<<6);
		TCCR1B = 8 | TIMER1_PRESCALER;
		TCNT1  = 0;
		OCR1A  = TIMER1_DIVISOR;
		OCR1B  = 0;
	#else
		TCCR1A = 0;
		TCCR1B = 0;
		TCNT1  = 0;
		OCR1A  = 0;
		OCR1B  = 0;
	#endif

	// Timer/Counter 2 initialization
	#if TIMER2_PRESCALER
		ASSR  = 0;
		TCCR2 = 8 | (TIMER2_TOGGLE<<4) | TIMER2_PRESCALER;
		TCNT2 = 0;
		OCR2  = TIMER2_DIVISOR;
	#else
		ASSR=0x00;
		TCCR2 = 0;
		TCNT2 = 0;
		OCR2  = 0;
	#endif

	TIMSK = ((TIMER0_PRESCALER!=0)<<1) | ((TIMER1_PRESCALER!=0)<<4) | ((TIMER2_PRESCALER!=0)<<7);

	GICR |= 0b11100000;		// INT 0,1,2 ON
	GIFR |= 0b00000000;		// Flags, when interrupts triggers.
	MCUCR |= 0b00001111;	//INT0 & INT1 triggers on rising edge
	MCUCSR |= 0b01000000;	//INT2 triggers on rising edge

	// Analog Comparator: Off
	ACSR=0x80;
	SFIOR=0x00;

	//Initialize SPI
	SPCR = 0b01010001; //SPIint = 0, SPE = 1, DORD = 0 (MSB first), MSTR = 1, ClkPOL = 0 (low when idle), ClkPHASE = 0 (sample->setup), SPR1, SPR0 = 0,0 (F_osc / 4) = 0,1 (F_osc / 16)
	SPSR = (0<<SPI2X); //ingen dobbelt hastighed = 625khz F_osc(10mhz)/16

	SetInterrupt();
}

