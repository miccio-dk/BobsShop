/**
\file packet.h
\brief Functions for packet. For AVR microcontroller. ported from the java code to match design
\code
RECOMMENDED WORKFLOW:

to send a packet:
-use NewProjectPacket with your variables as parameters (the last one should be some sort of buffer)
-change any of the fields with the setters, if needed
-send the last parameter through the serial port

to read a packet:
-store the incoming packet into a buffer variable
-create pointers/variables for each field
-use NewProjectPacketFromPacket with your variables as parameters (the last one should be your buffer, which contains the received packet)
-check if the packet is valid with generateChecksum
-access the data right from the previously created pointers/variables
\endcode
\author Riccardo Miccini
\author Documented by Rudolf Anton Fortes
\version 1.2
\date Dec-2013
*/
#ifndef PACKET_H_
#define PACKET_H_

#include <string.h>
#include <stdlib.h>
#include "defines.h"

#define SOURCEINDEX 0
#define SOURCESIZE 2
#define DESTINATIONINDEX 2
#define DESTINATIONSIZE 2
#define COMMANDSTATUSINDEX 4
#define COMMANDSTATUSSIZE 2
#define DATALENGTHINDEX 6
#define DATALENGTHSIZE 4
#define DATAINDEX 10
#define CHECKSUMSIZE 3
#define ENDOFPACKET "\0"
#define ENDOFPACKETSIZE strlen(ENDOFPACKET)
#define HEADERLENGTH (SOURCESIZE + DESTINATIONSIZE + COMMANDSTATUSSIZE + DATALENGTHSIZE + CHECKSUMSIZE + ENDOFPACKETSIZE)

typedef enum {
	//Cart => PC
	//08 is not first priority
	REQUEST_CUST = 1,
	VERIFY_CUST = 2,
	BLOCK_CUST = 3,
	ADD_PROD = 4,
	REM_PROD = 5,
	UPDATE_QTY = 6,
	FINALIZE = 7,
	CART_ACKN = 8,
	
	//Checkout => PC
	//38 is not first priority
	CHECKOUT_CUST = 31,
	CHECKOUT_PROD = 34,
	CHECKOUT_DONE = 37,
	CHECKOUT_ACKN = 38,
	
	//PC => Cart
	//'-ed' are not first priority
	CART_CUSTOMER_FOUND = 11,
	CART_CUSTOMER_NOTFOUND = 21,
	CART_PIN_MATCHES = 12,
	CART_PIN_DOESNTMATCH = 22,
	BLOCKED = 13,
	PRODUCT_FOUND = 14,
	PRODUCT_NOTFOUND = 24,
	REMOVED = 15,
	UPDATED = 16,
	BALANCE_EXCEEDED = 17,
	FINALIZED = 18,
	
	//PC => Checkout
	//'-ed' are not first priority
	CHECKOUT_CUSTOMER_FOUND = 41,
	CHECKOUT_CUSTOMER_NOTFOUND = 51,
	CHECKOUT_PIN_MATCHES = 42,
	CHECKOUT_PIN_DOESNTMATCH = 52,
	CHECKOUT_PRODUCT_ADDED = 44,
	CHECKOUT_PRODUCT_NOTFOUND = 54,
	CHECKOUT_CARTS_MATCH = 47,
	CHECKOUT_CARTS_DONTMATCH = 57,
	CHECKOUT_SUCCEEDED = 99,
	
	//checksum issues
	SEND_COMMAND_AGAIN = 10,
	SEND_STATUS_AGAIN = 9
	} CommandStatus;

/**
 * \brief creates a packet and stores it into aBytePacket
 * 
 * \param source string
 * \param destination string
 * \param commandStatus enumerated see packet.h
 * \param data string data
 * \param aBytePacket referenced string to update.
 * 
 * \return void
 */
void NewProjectPacket(char *source, char *destination, CommandStatus commandStatus, char *data, char *aBytePacket);

/**
 * \brief this function reads a packet stored in aBytePacket and parses the result
 * 
 * \param source string
 * \param destination string
 * \param commandStatus enumerated see packet.h
 * \param dataLenght int
 * \param data string
 * \param checksum int
 * \param aBytePacket referenced string to update
 * 
 * \return void
 */
void NewProjectPacketFromPacket(char *source, char *destination, CommandStatus *commandStatus, int *dataLenght, char *data, int *checksum, char *aBytePacket);

/**
 * \brief changes the source of the packet
 * 
 * \param aSource string
 * 
 * \return void
 */
void setSource(char *aSource);

/**
 * \brief changes the destination of the packet
 * 
 * \param aDestination string
 * 
 * \return void
 */
void setDestination(char *aDestination);

/**
 * \brief changes the command/status of the packet
 * 
 * \param aCommandStatus enumerated see packet.h
 * 
 * \return void
 */
void setCommandStatus(CommandStatus aCommandStatus);

/**
 * \brief changes the data of the packet
 * 
 * \param aData string
 * 
 * \return void
 */
void setData(char *aData);

/**
 * \brief calculates the checksum number
 * 
 * \param source string
 * \param destination string
 * \param commandStatus enumerated see packet.h
 * \param dataLength int
 * \param data string
 * 
 * \return int
 */
int generateChecksum(char *source, char *destination, CommandStatus commandStatus, int dataLength, char *data);


#endif /* PACKET_H_ */