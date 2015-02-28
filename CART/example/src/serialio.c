/**
\file serialio.c
\brief Functions for serial communication. For AVR microcontroller. functionality was provided to the students in the course.
\code 
 The default communications settings are 19200 baud, 8 data bits, no
 parity, 1 stop bit.

 A driver for USB to serial converter is needed when connecting to a
 PC through a USB cable.

 RXD is connected to port D bit 0.
 TXD is connected to port D bit 1.
\endcode
\author AgF
\author Doxygen by Rudolf Anton Fortes
\date 2011-05-07
*/
//                        SERIALIO.C                       2012-12-06 AgF
//
// Functions for serial communication. For AVR microcontroller.
//
// The default communications settings are 19200 baud, 8 data bits, no
// parity, 1 stop bit.
//
// A driver for USB to serial converter is needed when connecting to a
// PC through a USB cable.
//
// RXD is connected to port D bit 0.
// TXD is connected to port D bit 1.
/////////////////////////////////////////////////////////////////////////

#include <avr/interrupt.h>
#include "serialio.h"

//////////////////////////////////////////////////////////////////////
//
//            definitions
//
//////////////////////////////////////////////////////////////////////
#define UART_UDRE 5
#define UART_RXC  7


//////////////////////////////////////////////////////////////////////
//
//            functions
//
//////////////////////////////////////////////////////////////////////

// Initialize UART
void SerialInitialize() {
   /** USART initialization*/
   /** Communication Parameters: 8 Data bits, 1 Stop bit, No Parity*/
   /** USART Receiver: On*/
   /** USART Transmitter: On*/
   /** USART Mode: Asynchronous*/
   /** USART Baud rate: 19200 (Double Speed Mode)*/
   UCSRA=0x02;
   UCSRB=0x18;
   UCSRC=0x86;
   UBRRH=0x00;
   UBRRL=0x40;
}


// Write one character to serial connection
int SerialPutChar(int c) {
   if (c == '\n') SerialPutChar('\r');      // replace "\n" with "\r\n"
   while ((UCSRA & (1<<UART_UDRE))==0);     // wait until ready
   UDR = c;                                 // write character
   return c;
}


// Read one character from serial connection.
// Will wait if no character ready
char SerialGetChar(void) {
   while ((UCSRA & (1<<UART_RXC))==0);      // wait until ready
   return UDR;                              // read character
}


// Tell if input is ready for reading.
// Returns 1 if there is input, 0 if no input.
char SerialInputReady() {
   return (UCSRA >> UART_RXC) & 1;
}


// Write a zero-terminated ASCII string followed by a newline ("\r\n")
int SerialPutString(const char *str) {
   char c;
   for (; (c = *str) != 0; str++) SerialPutChar(c);
   SerialPutChar('\n');
   return 1;
}


//	Reads a character string terminated by the newline character ('\r' or '\n')
// The newline character will be replaced with 0.
// The maximum length of the string is len. If len characters were read
// without encountering the newline character, then the string is terminated
// with 0 and the function returns.
// str must be a character array of at least len characters.
char *SerialGetString(char *str, unsigned char len) {
   char c = 1;
	char * s;
   for (s = str; c; s++, len--) {
      if (len <= 1) break;             // stopping at len
      c = SerialGetChar();             // read character
      //if (c=='\r') break;              // stop at carriage return
		if (c=='\r') c = SerialGetChar();// ignore '\r' before '\n'
      if (c=='\n') break;              // stop at newline
      *s = c;                          // put character into buffer
   }
   *s = 0;
   return str;
}

// Write formatted output. See any C language textbook for details.
// Warning: Will fail if string longer than 81 characters!
// Floating point not supported.
int SerialPrintf(const char *format, ...) {
   char c, *s, buffer[82];
   va_list argptr;
   int cnt;
   va_start(argptr, format);
   cnt = vsprintf(buffer, format, argptr);            // use vsprintf to format string
   va_end(argptr);
   if (cnt >= sizeof(buffer)) cnt = sizeof(buffer)-1;
   buffer[cnt] = 0;                                   // terminate string
   for (s = buffer; (c = *s) != 0; s++) SerialPutChar(c);// output string
   return(cnt);
}
