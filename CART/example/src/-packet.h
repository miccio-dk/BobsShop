#ifndef PACKET_H_
#define PACKET_H_

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include "defines.h"

#define byte uint8_t
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
#define ENDOFPACKET "\r\n"
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
	UDATED = 16,
	FINALIZED = 17,
	
	//PC => Checkout
	//'-ed' are not first priority
	CHECKOUT_CUSTOMER_FOUND = 41,
	CHECKOUT_CUSTOMER_NOTFOUND = 51,
	CHECKOUT_PIN_MATCHES = 42,
	CHECKOUT_PIN_DOESNTMATCH = 52,
	} CommandStatus;

void NewProjectPacket(char *source, char *destination, CommandStatus commandStatus, char *data);
void NewProjectPacketFromPacket(char *bytePacket);
void getBytes(char *res);

char *getSource();
void setSource(char *aSource);

char *getDestination();
void setDestination(char *aDestination);

CommandStatus getCommandStatus();
void setCommandStatus(CommandStatus aCommandStatus);

int getDataLength();
void setDataLength(int aDataLength);

char *getData();
void setData(char *aData);

void generateChecksum();
int getChecksum();
void setChecksum(int aCheckSum);

void generateEndofPacket();
char *getEndofPacket();
void setEndofPacket(char *aEndofPacket);

bool isPacketComplete(char *byteFrame);
int getPacketSize(char *byteFrame);

#endif /* PACKET_H_ */