/**
\file rfid.h
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

#ifndef RFID_H_
#define RFID_H_

#define GET_UID			0x55
#define GET_STATUS		0x53
#define WRT_BLOCK		0x57
#define GET_BLOCK		0x52
#define INC_DATA		0x49
#define DEC_DATA		0x44
#define TRASF_DATA		0x54
#define TYPE_ID			0x78
#define GET_MSG			0x7A

#define DUMMY_DATA		0xF5
#define ACK				0x86

#define  CODE_WRONG_DATA_ADDRESS	0xFA
#define  CODE_BUFFER_FULL			0xFB
#define  CODE_UNKNOWN_COMMAND		0xFC
#define  CODE_DOUBLE_COMMAND		0xFD
#define  CODE_TIMEOUT_ERROR			0xFE

unsigned char RFIDGetData(char *buffData);



#endif /* RFID_H_ */