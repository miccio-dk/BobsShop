/**
\file display.c
\brief Functions for display. For AVR microcontroller.this display module utilizes the teachers provided lcd module and appends to its functionality to suit the needs of this project
\author Riccardo Miccini
\author Documented by Rudolf Anton Fortes
\version 1.2
\date Dec-2013
*/
#include "display.h"

static char customer[BAL_COL];	/**<customer name max length up to where the balance column starts. */
static char product[QTY_COL];	/**<Product name max length up to where Quantity column starts */
static char message[DISP_LEN];	/**<message length total length of the screen */
static long balance;			/**<balance information */
static int price;				/**<price information */
static int quantity;			/**<quantity */


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
	LCDGotoXY(BAL_COL,USER_LINE);
	if(balance!=-1) {
		sprintf(formatBuff, "%c%d%s", '%', (DISP_LEN-BAL_COL), "ld");
		sprintf(buff, formatBuff, balance);
	}
	else {
		sprintf(buff, " ");
	}
	LCDPutString(buff);
	
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
	LCDGotoXY(PRICE_COL,PROD_LINE);
	if(price!=-1) {
		sprintf(formatBuff, "%c%d%s", '%', (DISP_LEN-PRICE_COL-3), "d.%02d");
		sprintf(buff, formatBuff, (price/100), (price%100));
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
			sprintf(customer, "Bob");
			sprintf(product, "Welcome");
			sprintf(message, DEFAULT_IDLE_MSG);
			balance=-1;
			price=-1;
			quantity=-1;
			break;
		case LOGIN:
			sprintf(product, " ");
			sprintf(message, DEFAULT_PIN_MSG);
			balance=-1;
			price=-1;
			quantity=-1;
			break;
		case SHOPPING:
			sprintf(message, DEFAULT_SHOPPING_MSG);
			break;
	}
	
	RefreshScreen();
}

void SetBalance(long newBalance) {
	balance=newBalance;
	RefreshScreen();
}

void SetCustomer(char *custName) {
	sprintf(customer, "%s", custName);
	RefreshScreen();
}

void SetProduct(char *prodDescr, int prodPrice) {
	sprintf(product, "%s", prodDescr);
	price=prodPrice;
	quantity=1;
	RefreshScreen();
}

void SetQuantity(int newQty) {
	quantity=newQty;
	RefreshScreen();
}
	
void SetMessage(char *msg) {
	sprintf(message, "%s", msg);
	RefreshScreen();
}