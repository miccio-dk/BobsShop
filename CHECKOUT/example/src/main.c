/**
 * \file main.c
 *
 * \brief This File contains the main Logic for the program a simple series of tasks that will allow the user to navigate through the usability of the software
 * \par Content
 * -# The lcd.h and lcd.c files were imported teacher code.
 * -# The serialio.h and serialio.c files were imported teacher code.
 * -# This Checkout Terminal started off as an Integration Test for the Shopping Cart but was Ported to minimized Checkout terminal.
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
 * -# This Checkout Terminal started off as an Integration Test for the Shopping Cart but was Ported to minimized Checkout terminal.
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
#include "interrupts.h"

#define THIS_DEVICE "CE"	/**< define this device CA[RT] */
#define MAIN_DEVICE "PC"	/**< define main device PC(Server) */

// interrupts vars
volatile bool CardPresent = false;		/**< interrupts Variable: CardPresent */
volatile bool SPI_dataready = false;	/**< interrupts Variable: SPI_dataready */
volatile bool keypadPressed = false;	/**< interrupts Variable: keypadPressed */
volatile bool doneChecking = false;		/**< interrupts Variable: doneChecking */
volatile int milliseconds = 0;			/**< interrupts Variable: milliseconds */

// keypad vars
char pin[]="1234";
char qty[]="99";
char code[]="ABCDEFGHIJKLMNO";
	
// rfid vars
char buffData[33]={0};
unsigned char status='\0';
	
// packet vars
char	mainSource[SOURCESIZE+1]={0};
char	mainDestination[DESTINATIONSIZE+1]={0};
CommandStatus mainCommandStatus;
int		mainDataLength;
char	mainData[50]={0};
int		mainChecksum;
	
// serial vars
char buffPacketSend[60]={0};
char buffPacketGet[60]={0};

// main flow vars
volatile bool	loggedIn = false;
volatile bool	userDetected = false;
volatile bool	prodDetected = false;
volatile bool	billingAllowed = false;
volatile int	logAttempts = 0;

// user infos
char	userRFID[33] = {0};

// prod info
char	lastProdRFID[33] = {0};
int		lastProdID = 0;
char	lastProdName[QTY_COL+1] = {0};
int		lastProdQty = 0;
long int total = 0;

void Initialize();
void detectUser();
void login();
void scanProduct();
void waitDone();
void finalizeCheckout();
void verifyCart();


int main(void)
{
	// setup
	Initialize();
	SerialInitialize();
	LCDInit(20);
	
	
	// main flow
	while(true) {
		// wait for user to scan his card
		SetMode(IDLE);
		detectUser();
	
		if(userDetected) {
			// scan products (altogether)
			SetMode(CHECKING);		
			while(!doneChecking) {
				scanProduct();
				waitDone();
			}
			
			verifyCart();
			
			// authenticate the user 
			if(billingAllowed) {
				SetMode(BILLING);
				login();
				if(loggedIn) {
					SetMessage(DEFAULT_GOODBYE_MSG);
					_delay_ms(7000);
				}
			}
		}
	}
	return 0;
}

void verifyCart() {
	while(!billingAllowed) {
		//send a cart checking request
		memset(buffPacketSend, 0, 60);
		NewProjectPacket(THIS_DEVICE, MAIN_DEVICE, CHECKOUT_DONE, "", buffPacketSend);
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
			if(mainCommandStatus==CHECKOUT_CARTS_MATCH) {
				billingAllowed = true;
			}
			else if(mainCommandStatus==CHECKOUT_CARTS_DONTMATCH) {
				SetMessage(DEFAULT_CART_ERROR);
				_delay_ms(1000);
				SetMessage(DEFAULT_CART_MSG);
				if(KeypadGetResponce()) {
					billingAllowed = true;
				}
				return;
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
}

void waitDone() {
	int col, row;
	while(true)
	{
		//if enter button pressed, exits
		if(CardPresent || doneChecking)
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
					doneChecking = true;
				}
			}
		}
	}
}

void finalizeCheckout() {
	//SetMessage(DEFAULT_DONE_MSG);
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
	doneChecking = false;
	milliseconds = 0;
}

void scanProduct() {
	prodDetected = false;
	while(!prodDetected && !doneChecking) {
		if(CardPresent) {			
			status = RFIDGetData(lastProdRFID);
			if(status==ACK) {
				//request product and add one
				memset(buffPacketSend, 0, 60);
				NewProjectPacket(THIS_DEVICE, MAIN_DEVICE, CHECKOUT_PROD, lastProdRFID, buffPacketSend);
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
					if(mainCommandStatus==CHECKOUT_PRODUCT_ADDED) {
						//parses data and update screen
						//CHECKOUT_PRODUCT_ADDED sends the id, name, quantity, and running total
						sscanf(mainData, "%d\t%s\t%d\t%ld", &lastProdID, lastProdName, &lastProdQty, &total);
						SetProduct(lastProdName, lastProdQty, total);
						SetMessage(DEFAULT_SCAN_MSG);
						prodDetected = true;
					}
					else if(mainCommandStatus==CHECKOUT_PRODUCT_NOTFOUND) {
						SetMessage(DEFAULT_PROD_ERROR);
						_delay_ms(1000);
						SetMessage(DEFAULT_SCAN_MSG);
					}
					else {
						SetMessage(DEFAULT_INTERNAL_ERROR);
						_delay_ms(2000);
						SetMessage(DEFAULT_SCAN_MSG);
					}
				}
				else {
					SetMessage(DEFAULT_INTERNAL_ERROR);
					_delay_ms(2000);
					SetMessage(DEFAULT_SCAN_MSG);
				}
			}
		}
	}
}

void login()
{
	while(!(loggedIn || logAttempts>=3)) {
		//acquires pin and send a request
		SetMessage(DEFAULT_PIN_MSG);
		KeypadGetPIN(pin, 4);
		sprintf(mainData, "%s%s", pin, userRFID);
		memset(buffPacketSend, 0, 60);
		NewProjectPacket(THIS_DEVICE, MAIN_DEVICE, CHECKOUT_PINCHECK, mainData, buffPacketSend);
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
			if(mainCommandStatus==CHECKOUT_PIN_MATCHES) {
				loggedIn = true;
			}
			else if(mainCommandStatus==CHECKOUT_PIN_DOESNTMATCH) {
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
}

void detectUser()
{
	while(!userDetected) {
		if(CardPresent) {
			status = RFIDGetData(userRFID);
			if(status==ACK) {
				//request user name
				memset(buffPacketSend, 0, 60);
				NewProjectPacket(THIS_DEVICE, MAIN_DEVICE, CHECKOUT_CUST, userRFID, buffPacketSend);
				SerialPutString(buffPacketSend);
				
				//fetch and parse response (TODO: timeout as in rfid.c)
				while(SerialInputReady());
				memset(buffPacketGet, 0, 60);
				SerialGetString(buffPacketGet, 60);
				NewProjectPacketFromPacket(mainSource, mainDestination, &mainCommandStatus, &mainDataLength, mainData, &mainChecksum, buffPacketGet);
				
				
				//validate the received packet
				if(generateChecksum(mainSource, mainDestination, mainCommandStatus, mainDataLength, mainData)==mainChecksum)
				{
					if(mainCommandStatus==CHECKOUT_CUSTOMER_FOUND) {
						SetCustomer(mainData);
						userDetected = true;
					}
					else if(mainCommandStatus==CHECKOUT_CUSTOMER_NOTFOUND) {
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

