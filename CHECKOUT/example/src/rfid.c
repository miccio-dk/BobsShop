/**
\file rfid.c
\brief Functions for RFID. For AVR microcontroller.
\code
Commands for the MicroRWD reader/writer:

Byte									Command		Arguments following the command													Response
---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
Get series number on the card (UID):	0x55 (?U?)	0																				Acknowledge 0x86+ 7 bytes of UID, LSB first
Card status								0x53 (?S?)	0																				Acknowledge 0x86
Write data to a block					0x57 (?W?)	Byte block start address+Key type+16 databyte									Acknowledge 0x86
Read card Block							0x52 (?R?)	Byte block start address+Key type												Acknowledge 0x86 + 16 bytes data
Increment value on Value data structure 0x49 (?I?)	Byte block start address+Key type+destination address+4 databytes (LSB first)	Acknowledge 0x86
Decrement value on Value data structure	0x44 (?D?)	Byte block start address+Key type+destination address+4 databytes (LSB first)	Acknowledge 0x86
Transfer value							0x54 (?T?)	Byte block start address+Key type+destination block address						Acknowledge 0x86
Type identification						0x78 (?x?)	0																				Acknowledge 0x86 + 3 bytes data
Message									0x7A (?z?)	0																				A string of max. 99 bytes

Control codes
----------------------------------------------------------------------------
Sent when								Byte		Parameter in RFIDCodes.h
dummy data sent when receiving data		0xF5		CODE_DATA_REQUEST
Sent from MicroRWD when command OK		0x86		CODE_ACKNOWLEDGE

Error codes
----------------------------------------------------------------------------
Sent when error							Byte		Parameter in RFIDCodes.h
Command contains wrong data	address		0xFA		CODE_WRONG_DATA_ADDRESS
Communication data buffer overflow		0xFB		CODE_BUFFER_FULL
Unknown command received				0xFC		CODE_UNKNOWN_COMMAND
New command received before	processing-
previous command finished				0xFD		CODE_DOUBLE_COMMAND
Timeout or incomplete message			0xFE		CODE_TIMEOUT_ERROR
\endcode
\author Riccardo Miccini
\author Documented by Rudolf Anton Fortes
\version 1.2
\date Dec-2013
*/
#include <util/delay.h>
#include <stdio.h>
#include "interrupts.h"
#include "defines.h"
#include "rfid.h"

extern volatile bool CardPresent;	/**<boolean is card present set when interrupt triggerd or data processed*/
extern volatile bool SPI_dataready;	/**<boolean is SPI dataready set when interrupt triggerd or data processed */
extern volatile int milliseconds;	/**<milliseconds value of milliseconds set in the main*/

unsigned char SPI_do(char cData)
{
	PORTB &= ~(0x04);				/* Start transmission */
	SPDR = cData;
	while(!(SPSR & (1<<SPIF))) {};	/* Wait for transmission complete */
	PORTB &= ~(0x04);
	/*_delay_ms(1);*/
	return SPDR;
}

unsigned char RFIDGetData(char *buffData)
{
	int i=0;
	unsigned char CardData[20] = {0};

	SPI_do(GET_BLOCK);		// COMMAND
	_delay_ms(1);
	SPI_do(0x00);			// PARAMETER 1 (NULL)
	_delay_ms(1);			
	SPI_do(0x00);			// PARAMETER 2 (NULL)
	_delay_ms(1);

	milliseconds=0;
	while (!SPI_dataready || (milliseconds<1000));

	for(i=0; i<19; i++) {
		/*PORTB &= ~(0x04);*/
		CardData[i] = SPI_do(DUMMY_DATA);		// CODE_DATA_REQUEST, ACK is first byte in.
		/*PORTB &= ~(0x04);*/
		_delay_ms(1);
	}
	sprintf(buffData, "%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X%02X", CardData[1], CardData[2], CardData[3], CardData[4], CardData[5], CardData[6], CardData[7], CardData[8], CardData[9], CardData[10], CardData[11], CardData[12], CardData[13], CardData[14], CardData[15], CardData[16]);

	CardPresent = false;
	SPI_dataready = false;
	return CardData[0];
}
