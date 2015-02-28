#include "packet.h"
#include "serialio.h"

char *source;
char *destination;
CommandStatus commandStatus;
int dataLength;
char *data;
int checksum;
char *endofPacket;
char *bytePacket;

void initializeArray() {
	for (int i = 0; i < sizeof(bytePacket); i++) {
		bytePacket[i] = '0';
	}
}

void putSource() {
	int j=0;
	for(int i=SOURCEINDEX; i<SOURCEINDEX+SOURCESIZE; i++)
	{
		bytePacket[i]=source[j];
		//SerialPutChar(bytePacket[i]);
		j++;
	}
}

void putDestination() {
	int j=0;
	for(int i=DESTINATIONINDEX; i<DESTINATIONINDEX+DESTINATIONSIZE; i++)
	{
		bytePacket[i]=destination[j];
		//SerialPutChar(bytePacket[i]);
		j++;
	}
}

void putCommandStatus() {
	int j=0;
	
	//ugly but working
	char commandStatusString[COMMANDSTATUSSIZE];
	commandStatusString[0] = ('0'+commandStatus/10);
	commandStatusString[1] = ('0'+commandStatus%10);
	for(int i=COMMANDSTATUSINDEX; i<COMMANDSTATUSINDEX+COMMANDSTATUSSIZE; i++)
	{
		bytePacket[i]=commandStatusString[j];
		//SerialPutChar(bytePacket[i]);
		j++;
	}
}

void putDataLength() {
	int j;
	char buff[DATALENGTHSIZE+1];
	char dataLengthString[DATALENGTHSIZE];
	itoa(strlen(data), buff, 10);
	
	j=strlen(buff)-1;
	for(int i=0; i<DATALENGTHSIZE; i++)
	{
		if(j>=0)
			dataLengthString[DATALENGTHSIZE-1-i]=buff[j];
		else
			dataLengthString[DATALENGTHSIZE-1-i]='0';
		j--;
	}
	
	j=0;
	for(int i=DATALENGTHINDEX; i<DATALENGTHINDEX+DATALENGTHSIZE; i++)
	{
		bytePacket[i]=dataLengthString[j];
		//SerialPutChar(bytePacket[i]);
		j++;
	}
}

void putData() {
	int j=0;
	for(int i=DATAINDEX; i<DATAINDEX+getDataLength(); i++)
	{
		bytePacket[i]=data[j];
		//SerialPutChar(bytePacket[i]);
		j++;
	}
}

void putChecksum() {
	SerialPutString("z");
	int j=0;	
	char buff[CHECKSUMSIZE+1];
	char checksumString[CHECKSUMSIZE];
	itoa(checksum, buff, 10);
	
	j=strlen(buff)-1;
	for(int i=0; i<CHECKSUMSIZE; i++)
	{
		if(j>=0)
			checksumString[CHECKSUMSIZE-1-i]=buff[j];
		else
			checksumString[CHECKSUMSIZE-1-i]='0';
		j--;
	}
	SerialPutString("x");
	
	j=0;
	for(int i=DATAINDEX+dataLength; i<DATAINDEX+dataLength+CHECKSUMSIZE; i++)
	{
		bytePacket[i]=checksumString[j];
		//SerialPutChar(bytePacket[i]);
		j++;
	}
	SerialPutString("y");
	for(int i=0;i<strlen(bytePacket);i++)
	{
		//res[i] = bytePacket[i];
		SerialPutChar(bytePacket[i]);
		
	}
}

void putEndofPacket() {
	int j=0;
	for(int i=DATAINDEX+getDataLength()+CHECKSUMSIZE; i<DATAINDEX+getDataLength()+CHECKSUMSIZE+ENDOFPACKETSIZE; i++)
	{
		bytePacket[i]=endofPacket[j];
		//SerialPutChar(bytePacket[i]);
		j++;
	}
}

void NewPacket(char *aSource, char *aDestination, CommandStatus aCommandStatus, char *aData) {
	source = aSource;
	destination = aDestination;
	commandStatus = aCommandStatus;
	dataLength = strlen(aData);
	data = aData;
	//generateChecksum();
}

void NewProjectPacket(char *source, char *destination, CommandStatus commandStatus, char *data) {
	NewPacket(source, destination, commandStatus, data);
	generateChecksum();
	generateEndofPacket();
	int aPacketLength = HEADERLENGTH+getDataLength();
	char a[aPacketLength];
	bytePacket = a;
	initializeArray();
}

void NewProjectPacketFromPacket(char *bytePacket) {
	int j=0;
	char source[SOURCESIZE];
	for(int i=SOURCEINDEX; i<SOURCEINDEX+SOURCESIZE; i++) {
		source[j] = bytePacket[i];
		j++;
	}
	setSource(source);
	
	j=0;
	char destination[DESTINATIONSIZE];
	for(int i=DESTINATIONINDEX; i<DESTINATIONINDEX+DESTINATIONSIZE; i++) {
		destination[j] = bytePacket[i];
		j++;
	}
	setDestination(destination);
	
	j=0;
	char commandStatus[COMMANDSTATUSSIZE];
	for(int i=COMMANDSTATUSINDEX; i<COMMANDSTATUSINDEX+COMMANDSTATUSSIZE; i++) {
		commandStatus[j] = bytePacket[i];
		j++;
	}
	setCommandStatus((CommandStatus)atoi(commandStatus));
	
	j=0;
	char dataLengthString[DATALENGTHSIZE];
	for(int i=DATALENGTHINDEX; i<DATALENGTHINDEX+DATALENGTHSIZE; i++) {
		dataLengthString[j] = bytePacket[i];
		j++;
	}
	setDataLength(atoi(dataLengthString));
	
	j=0;
	char data[dataLength];
	for(int i=DATAINDEX; i<DATAINDEX+dataLength; i++) {
		data[j] = bytePacket[i];
		j++;
	}
	setData(data);
	
	j=0;
	int checksumIndex = DATAINDEX + dataLength;
	char checksumString[CHECKSUMSIZE];
	for(int i=checksumIndex; i<checksumIndex+CHECKSUMSIZE; i++) {
		checksumString[j] = bytePacket[i];
		j++;
	}
	setChecksum(atoi(checksumString));
	
	//j=0;
	//int endofPacketIndex = checksumIndex + CHECKSUMSIZE;
	//char endofPacketString[ENDOFPACKETSIZE];
	//for(int i=endofPacketIndex; i<endofPacketIndex+ENDOFPACKETSIZE; i++) {
		//endofPacketString[j] = bytePacket[i];
		//j++;
	//}
}

void getBytes(char *res) {
	putSource();
	SerialPutString("g");
	putDestination();
	SerialPutString("f");
	putCommandStatus();
	SerialPutString("e");
	putDataLength();
	SerialPutString("d");
	putData();
	SerialPutString("c");
	putChecksum();
	SerialPutString("b");
	putEndofPacket();
	
	SerialPutString("aa");
	
	for(int i=0;i<strlen(bytePacket);i++)
	{
		//res[i] = bytePacket[i];
		//SerialPutChar(bytePacket[i]);
		
	}
	
	char buff[4];
	itoa(strlen(bytePacket), buff, 10);
	SerialPutString(buff);
}

char *getSource() {
    return source;
}

void setSource(char *aSource) {
    source = aSource;
}

char *getDestination() {
    return destination;
}

void setDestination(char *aDestination) {
    destination = aDestination;
}

CommandStatus getCommandStatus() {
	return commandStatus;
}

void setCommandStatus(CommandStatus aCommandStatus) {
	commandStatus = aCommandStatus;
}

int getDataLength() {
	return dataLength;
}

void setDataLength(int aDataLength) {
	dataLength = aDataLength;
}

char *getData() {
    return data;
}

void setData(char *aData) {
    data = aData;
}

void generateChecksum() {
	setChecksum(123); //TO DO
}

int getChecksum() {
    return checksum;
}

void setChecksum(int aCheckSum) {
    checksum = aCheckSum;
}

void generateEndofPacket() {
	setEndofPacket(ENDOFPACKET);
}

char *getEndofPacket() {
	return endofPacket;
}

void setEndofPacket(char *aEndofPacket) {
	endofPacket = aEndofPacket;
}

bool isPacketComplete(char *byteFrame) {
	if (sizeof(byteFrame) >= DATALENGTHINDEX+DATALENGTHSIZE)
	{
		char dataLengthString[DATALENGTHSIZE];
		int j=DATALENGTHINDEX;
		for(int i=0; i<DATALENGTHSIZE; i++)
		{
			dataLengthString[i]=byteFrame[j];
			j++;
		}
		int dataLength = atoi(dataLengthString);
		if (sizeof(byteFrame) >= dataLength+HEADERLENGTH)
		{
			return true;
		}
	}
	return false;
}

int getPacketSize(char *byteFrame) {

	char dataLengthString[DATALENGTHSIZE];
	int j=DATALENGTHINDEX;
	for(int i=0; i<DATALENGTHSIZE; i++)
	{
		dataLengthString[i]=byteFrame[j];
		j++;
	}
	int packetSize = atoi(dataLengthString)+HEADERLENGTH;
	return packetSize;
}