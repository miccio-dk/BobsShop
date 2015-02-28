/**
\file packet.c
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
#include <stdio.h>
#include "packet.h"
#include "serialio.h"

//packet fields
char *source = NULL;			/**<Source of the packet */
char *destination = NULL;		/**<destination of the packet */
CommandStatus commandStatus;	/**<type of command */
int dataLength = 0;				/**<length of data */
char *data = NULL;				/**<data */
int checksum = 0;				/**<checksum */
char *endofPacket = NULL;		/**<end of packet */

//packet string
char *bytePacket = NULL;		/**<String version of packet */

/**
 * \brief  frees all the variables and pointers
 \code
 frees all the variables and pointers
 local functions not intended to be accessed from outside the library
 \endcode
 * 
 * \return void
 */
void ErasePacket(void) {
	source = NULL;
	destination = NULL;
	dataLength = 0;
	data = NULL;
	checksum = 0;
	endofPacket = NULL;
}

/**
 * \brief puts the source field into the packet 
 * 
 * 
 * \return void
 */
void putSource() {
	int j = 0;
	int i;
	for (i = SOURCEINDEX; i < SOURCEINDEX + SOURCESIZE; i++) {
		bytePacket[i] = source[j];
		j++;
	}
}

/**
 * \brief puts the destination field into the packet
 * 
 * 
 * \return void
 */
void putDestination() {
	int j = 0;
	int i;
	for (i = DESTINATIONINDEX; i < DESTINATIONINDEX + DESTINATIONSIZE; i++) {
		bytePacket[i] = destination[j];
		//SerialPutChar(bytePacket[i]);
		j++;
	}
}

/**
 * \brief puts the command/status field into the packet
 * 
 * 
 * \return void
 */
void putCommandStatus() {
	int j = 0;

	//ugly but working
	char commandStatusString[COMMANDSTATUSSIZE];
	commandStatusString[0] = ('0' + commandStatus / 10);
	commandStatusString[1] = ('0' + commandStatus % 10);
	int i;
	for (i = COMMANDSTATUSINDEX; i < COMMANDSTATUSINDEX + COMMANDSTATUSSIZE; i++) {
		bytePacket[i] = commandStatusString[j];
		//SerialPutChar(bytePacket[i]);
		j++;
	}
}

/**
 * \brief puts the data length field into the packet
 * 
 * 
 * \return void
 */
void putDataLength() {
	int j;
	char buff[DATALENGTHSIZE + 1];
	char dataLengthString[DATALENGTHSIZE];
	
	itoa(dataLength, buff, 10);

	j = strlen(buff) - 1;
	int i;
	for (i = 0; i < DATALENGTHSIZE; i++) {
		if (j >= 0)
			dataLengthString[DATALENGTHSIZE - 1 - i] = buff[j];
		else
			dataLengthString[DATALENGTHSIZE - 1 - i] = '0';
		j--;
	}

	j = 0;
	for (i = DATALENGTHINDEX; i < DATALENGTHINDEX + DATALENGTHSIZE; i++) {
		bytePacket[i] = dataLengthString[j];
		//SerialPutChar(bytePacket[i]);
		j++;
	}
}

/**
 * \brief puts the data field into the packet
 * 
 * 
 * \return void
 */
void putData() {
	int j = 0;
	int i;
	for (i = DATAINDEX; i < DATAINDEX + dataLength; i++) {
		bytePacket[i] = data[j];
		//SerialPutChar(bytePacket[i]);
		j++;
	}
}

/**
 * \brief puts the checksum field into the packet
 * 
 * 
 * \return void
 */
void putChecksum() {
	int j = 0;
	char buff[CHECKSUMSIZE + 1];
	char checksumString[CHECKSUMSIZE];
	itoa(generateChecksum(source, destination, commandStatus, dataLength, data), buff, 10);

	j = strlen(buff) - 1;
	int i;
	for (i = 0; i < CHECKSUMSIZE; i++) {
		if (j >= 0)
			checksumString[CHECKSUMSIZE - 1 - i] = buff[j];
		else
			checksumString[CHECKSUMSIZE - 1 - i] = '0';
		j--;
	}

	j = 0;
	for (i = DATAINDEX + dataLength; i < DATAINDEX + dataLength + CHECKSUMSIZE; i++) {
		bytePacket[i] = checksumString[j];
		j++;
	}
}

//puts the proper terminating sequence into the packet
void putEndofPacket() {
	int j = 0;
	int i;
	for (i = DATAINDEX + dataLength + CHECKSUMSIZE; i < DATAINDEX + dataLength + CHECKSUMSIZE + ENDOFPACKETSIZE; i++) {
		bytePacket[i] = endofPacket[j];
		//SerialPutChar(bytePacket[i]);
		j++;
	}
}

/**
 * \brief regenerates the packet string
 * 
 * 
 * \return void
 */
void updateBytePacket() {
	int i;
	for (i = 0; i < HEADERLENGTH+dataLength; i++) {
		bytePacket[i] = '0';
	}
	
	putSource();
	putDestination();
	putCommandStatus();
	putDataLength();
	putData();
	putChecksum();
	putEndofPacket();
}

/**
 * \brief initializes all the packet fields
 * 
 * \param aSource string
 * \param aDestination string
 * \param aCommandStatus enumeraged see packet.h
 * \param aData string
 * 
 * \return void
 */
void NewPacket(char *aSource, char *aDestination, CommandStatus aCommandStatus, char *aData) {
	source = aSource;
	destination = aDestination;
	commandStatus = aCommandStatus;
	dataLength = strlen(aData);
	data = aData;
	checksum = generateChecksum(source, destination, commandStatus, dataLength, data);
	endofPacket = ENDOFPACKET;
}


/* public functions intended to be accessed and used*/
void NewProjectPacket(char *source, char *destination, CommandStatus commandStatus, char *data, char *aBytePacket) {
	ErasePacket();
	NewPacket(source, destination, commandStatus, data);
	bytePacket=aBytePacket;
	updateBytePacket();
}

void NewProjectPacketFromPacket(char *source, char *destination, CommandStatus *commandStatus, int *dataLength, char *data, int *checksum, char *aBytePacket) {
	int i, j;
	ErasePacket();
	bytePacket = aBytePacket;
	
	//gets the source
	j = 0;
	for (i = SOURCEINDEX; i < SOURCEINDEX + SOURCESIZE; i++) {
		source[j] = bytePacket[i];
		j++;
	}
	
	//gets the destination
	j = 0;
	for (i = DESTINATIONINDEX; i < DESTINATIONINDEX + DESTINATIONSIZE; i++) {
		destination[j] = bytePacket[i];
		j++;
	}

	//gets the command/status
	j = 0;
	char commandStatusString[COMMANDSTATUSSIZE+1];
	for (i = COMMANDSTATUSINDEX; i < COMMANDSTATUSINDEX + COMMANDSTATUSSIZE; i++) {
		commandStatusString[j] = bytePacket[i];
		j++;
	}
	commandStatusString[j]=0;
	*commandStatus = (CommandStatus)atoi(commandStatusString);

	//gets the data length
	j = 0;
	char dataLengthString[DATALENGTHSIZE+1];
	for (i = DATALENGTHINDEX; i < DATALENGTHINDEX + DATALENGTHSIZE; i++) {
		
		dataLengthString[j] = bytePacket[i];
		j++;
	}
	dataLengthString[j]=0;
	*dataLength = atoi(dataLengthString);
	
	//gets the data
	j = 0;
	for (i = DATAINDEX; i < DATAINDEX + *dataLength; i++) {
		data[j] = bytePacket[i];
		j++;
	}
	
	//gets the checksum
	j = 0;
	int checksumIndex = DATAINDEX + *dataLength;
	char checksumString[CHECKSUMSIZE+1];
	for (i = checksumIndex; i < checksumIndex + CHECKSUMSIZE; i++) {
		checksumString[j] = bytePacket[i];
		j++;
	}
	checksumString[j]=0;
	*checksum = atoi(checksumString);
	
	//NewPacket(source, destination, *commandStatus, data);
	//bytePacket = NULL;
	//j=0;
	//int endofPacketIndex = checksumIndex + CHECKSUMSIZE;
	//char endofPacketString[ENDOFPACKETSIZE];
	//for(int i=endofPacketIndex; i<endofPacketIndex+ENDOFPACKETSIZE; i++) {
	//endofPacketString[j] = bytePacket[i];
	//j++;
	//}
}

void setSource(char *aSource) {
	source = aSource;
	updateBytePacket();
}

void setDestination(char *aDestination) {
	destination = aDestination;
	updateBytePacket();
}

void setCommandStatus(CommandStatus aCommandStatus) {
	commandStatus = aCommandStatus;
	updateBytePacket();
}

void setData(char *aData) {
	data = aData;
	dataLength = strlen(data);
	updateBytePacket();
}

int generateChecksum(char *source, char *destination, CommandStatus commandStatus, int dataLength, char *data) {
	int sum = 0;
	int i;
	for(i=0; i<SOURCESIZE; i++)
	sum+=source[i];
	for(i=0; i<DESTINATIONSIZE; i++)
	sum+=destination[i];
	
	sum+=commandStatus;
	sum+=dataLength;
	
	for(i=0; i<dataLength; i++)
	sum+=data[i];
	
	return (sum%1000);
}
