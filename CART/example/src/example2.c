
#include <stdio.h>          // define function sprintf
#include "interrupts.h"
#include "defines.h"        // define various macros etc.
#include "lcd.h"            // define LCD functions
#include "serialio.h"       // define Serial functions
#include "packet.h"


/*********************************************************
*             Global variables
*********************************************************/
volatile bool CardPresent = false;
volatile bool SPI_dataready = false;
volatile int milliseconds = 0;


/*********************************************************
*             Initialize
*********************************************************/
// Initialization of system control registers
void Initialize ()
{
   // Input/Output ports initialization 0bMSB...LSB
   DDRA  = 0b11111100;// Port A data direction. Each bit is 0 for input, 1 for output
   PORTA = 0b00000000;// Port A initial value
   //        LCDLCDxx -- LCDb7,LCDb6,LCDb5,LCDb4,LCDen,LCDrs
   DDRB  = 0b10110001;// Port B data direction. Each bit is 0 for input, 1 for output
   PORTB = 0b00000000;// Port B initial value
   //        SPISxixL -- SCK,MISO,MOSI,SS,X,INT2,X,LEDCPU
   DDRC  = 0b00000000;// Port C data direction. Each bit is 0 for input, 1 for output
   PORTC = 0b00000000;// Port C initial value
   //        xx------ -- X,X
   DDRD  = 0b00000000;// Port D data direction. Each bit is 0 for input, 1 for output
   PORTD = 0b00000000;// Port D initial value
   //        TxTxii-- -- TIMER2,X,TIMER1,X,INT1,INT0

   // Timers initialization

#if TIMER0_PRESCALER				// Timer/Counter 0 initialization
   TCCR0 = 8 | (TIMER0_TOGGLE<<4) | TIMER0_PRESCALER;
   TCNT0 = 0;
   OCR0  = TIMER0_DIVISOR;
#else
   TCCR0 = 0;
   TCNT0 = 0;
   OCR0  = 0;
#endif

#if TIMER1_PRESCALER				// Timer/Counter 1 initialization
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

   TIMSK = ((TIMER0_PRESCALER!=0)<<1) | ((TIMER1_PRESCALER!=0)<<4) | ((TIMER2_PRESCALER!=0)<<7);	// Timer/Counter012 Interrupt enable

   // External interrupts 0, 1, 2 off
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

   // Initialize USART
   UCSRA = 0x02;		// Communication Parameters: 8 Data bits, 1 Stop bit, No Parity
   UCSRB = 0x18;		// USART Receiver: On
   UCSRC = 0x86;		// USART Transmitter: On
   UBRRL = 0x40;		// USART Mode: Asynchronous
   UBRRH = 0x00;		// USART Baud rate: 19200 (Double Speed Mode)

   SetInterrupt();				// Enable interrupts
}



int main(void)
{
	Initialize();									// Initialize system control registers etc.
	LCDInit(20);									// Initialize LCD display and indicate the number of columns

	while (1)										// Infinite 'main' loop
	{
		if (CardPresent) {
			char buffData[40]={0};
			char buffPacket[60]={0};
			char buffStatus[5]={0};
			unsigned char status = RFIDGetData(buffData);
			LCDGotoXY(0,0);
			LCDPutString(buffData);
			
			sprintf(buffStatus, "%02X", status);
			LCDGotoXY(0,3);
			LCDPutString(buffStatus);

			NewProjectPacket("CA", "PC", REQUEST_CUST, buffData, buffPacket);
			SerialPutString(buffPacket);
			//LCDGotoXY(0,0);
			//LCDPutString(buffPacket);
		}
	}
	return 0;
}
