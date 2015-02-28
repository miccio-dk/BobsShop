/**
\file display.c
\brief Functions for display. For AVR microcontroller.this display module utilizes the teachers provided lcd module and appends to its functionality to suit the needs of this project
\author Riccardo Miccini
\author Documented by Riccardo Miccini
\version 1.2
\date Dec-2013
*/
#include "display.h"
 
static char customer[DISP_LEN];/**<customer name max length up to where the balance column starts. */
static char product[QTY_COL];  /**<Product name max length up to where Quantity column starts */
static char message[DISP_LEN]; /**<message length total length of the screen */
static long int total;         /**<running cart total */
static int quantity;           /**<quantity */


/**
 * \brief Refresh the screen update using the settings of the current set mode and attributes
 * 
 * \param 
 * 
 * \return void
 */
void RefreshScreen(void) {
	char buff[DISP_LEN];
	char formatBuff[10];
	LCDClear();
	
	LCDGotoXY(0,USER_LINE);
	LCDPutString(customer);
	
	LCDGotoXY(0,PROD_LINE);
	LCDPutString(product);
	LCDGotoXY(QTY_COL,PROD_LINE);
	if(quantity!=-1) {
		sprintf(buff, "%02d", quantity);
	}
	else {
		sprintf(buff, " ");
	}
	LCDPutString(buff);
	LCDGotoXY(TOT_COL,PROD_LINE);
	if(total!=-1) {
		sprintf(formatBuff, "%c%d%s", '%', (DISP_LEN-TOT_COL-3), "ld.%02d");
		sprintf(buff, formatBuff, (total/100), (total%100));
	}
	else {
		sprintf(buff, " ");
	}
	LCDPutString(buff);
	
	LCDGotoXY(0,MSG_LINE);
	LCDPutString(message);

	LCDGotoXY(0,INPUT_LINE);
}

void SetMode(ScreenMode t) {
	switch(t)
	{
		case IDLE:
			sprintf(customer, "Bob's Shop");
			sprintf(product, "Welcome");
			sprintf(message, DEFAULT_IDLE_MSG);
			total=-1;
			quantity=-1;
			break;
		case BILLING:
			sprintf(product, " ");
			sprintf(message, DEFAULT_PIN_MSG);
			total=-1;
			quantity=-1;
			break;
		case CHECKING:
			sprintf(message, DEFAULT_SCAN_MSG);
			break;
	}
	
	RefreshScreen();
}

void SetCustomer(char *custName) {
	sprintf(customer, "%s", custName);
	RefreshScreen();
}

void SetProduct(char *prodDescr, int newQty, int newTotal) {
	sprintf(product, "%s", prodDescr);
	total=newTotal;
	quantity=newQty;
	RefreshScreen();
}
	
void SetMessage(char *msg) {
	sprintf(message, "%s", msg);
	RefreshScreen();
}