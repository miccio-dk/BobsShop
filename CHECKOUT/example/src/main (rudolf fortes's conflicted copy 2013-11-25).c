#include <avr/interrupt.h>	// define interrupt service
#include <stdio.h>			// define function sprintf
#include <stdlib.h>
#include "defines.h"		// define various macros etc.
#include "lcd.h"			// define LCD functions
#include "keypad.h"			// define Keypad functions
#include "display.h"		// define Display handling functions
#include "serialio.h"		// define Serial functions
#include "rfid.h"			// define RFID functions
#include "packet.h"			// define the protocol packet

#define TIMER0_PRESCALER  0
#define TIMER1_PRESCALER  2
#define TIMER2_PRESCALER  0

#define TIMER0_DIVISOR     1  // allowed range: 1 - 255
#define TIMER1_DIVISOR  1250  // allowed range: 1 - 65535
#define TIMER2_DIVISOR     1  // allowed range: 1 - 255

#define TIMER0_TOGGLE     0  // (port B bit 3)
#define TIMER1_TOGGLE     1  // (port D bit 5)
#define TIMER2_TOGGLE     0  // (port D bit 7)


void Initialize ();


#if TIMER0_PRESCALER
ISR(TIMER0_COMP_vect) {}
#endif

#if TIMER1_PRESCALER
ISR(TIMER1_COMPA_vect) {}
#endif
 
ISR(INT0_vect) {}

ISR(INT1_vect) {}

int main(void)
{
	Initialize ();
	SerialInitialize();
	LCDInit(20);
	
	/* first case: create and send a packet */
	char buff[100]={0};
	NewProjectPacket("CA", "PC", REQUEST_CUST, "aaa", buff);
	SerialPutString(buff);
	//edit some field and send again
	setCommandStatus(VERIFY_CUST);
	setData("aa1234aa");
	SerialPutString(buff);
	
	//erase the buffer
	memset(buff, 0, 100);
	
	/* second case: receive (only simulated here) and parse a packet */
	//test packet (supposed to be received from the serial port)
	char testPacket[]="1234120004ciao630\n\r";
	//storage variables
	char source[SOURCESIZE+1]={0};
	char destination[DESTINATIONSIZE+1]={0};
	CommandStatus commandStatus;
	int dataLength;
	char data[50]={0};
	int checksum;
	//packet parsing
	NewProjectPacketFromPacket(source, destination, &commandStatus, &dataLength, data, &checksum, testPacket);
	//generate a validity check string (using C syntax and pointers)
	char v[]="valid.";
	char nv[]="not valid.";
	char *isValid = (generateChecksum(source, destination, commandStatus, dataLength, data)==checksum)?v:nv;
	//print the packet infos
	sprintf(buff, "source:\t%s\ndestination:\t%s\ncommand-status:\t%d\ndata:\t%s(%d bytes)\n%s", source, destination, (int)commandStatus, data, strlen(data), isValid);
	SerialPutString(buff);
	
	//erase the buffer
	memset(buff, 0, 100);
	
	/* extra: to verify whether the parsed data is valid, an attempt is made to rebuild the same packet */
	SerialPutString("re-generated packet:");
	NewProjectPacket(source, destination, commandStatus, data, buff);
	SerialPutString(buff);
	

	//char pin[]="1234";
	//char qty[]="99";
	//char code[]="i\'m a packet :)";
	//
	SetMode(IDLE);
	SetCustomer("Riccardo");
	SetBalance(45999);
	SetProduct("Ciao",23.5*100);
	
	while(true)
	{
		
		//KeypadGetQty(qty, 3);
		//SetQuantity(atoi(qty));
		//PrintScreen(IDLE);
		//SerialPutString(code);
		//KeypadGetPIN(pin, 4);
		//SerialPutString(pin);
		//KeypadGetQty(qty, 9);
		//SerialPutString(qty);
	}
	return 0;

}


void Initialize ()
{
	DDRA  = 0b11111100;
	PORTA = 0b00000000;
	DDRB  = 0b00010011;
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

	MCUCR = (1<<ISC11) | (1<<ISC10) | (1<<ISC01) | (1<<ISC00);
	GICR = (1<<INT0) | (1<<INT1);
	MCUCSR = 0;
	GIFR   = 0;

	ACSR=0x80;
	SFIOR=0x00;

	UCSRA = 0x02;
	UCSRB = 0x18;
	UCSRC = 0x86;
	UBRRL = 0x40;
	UBRRH = 0x00;

	SetInterrupt();
}

