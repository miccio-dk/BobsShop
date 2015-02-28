#include "display.h"

static char customer[DISP_LEN-BAL_COL];
static char product[DISP_LEN-QTY_COL];
static char message[DISP_LEN];
static long balance;
static int price;
static int quantity;


void RefreshScreen(void) {
	char buff[DISP_LEN];
	LCDClear();
	
	LCDGotoXY(0,USER_LINE);
	LCDPutString(customer);
	LCDGotoXY(BAL_COL,USER_LINE);
	if(balance!=-1)
		sprintf(buff, "%*ld", (DISP_LEN-BAL_COL), balance);
	else
		sprintf(buff, " ");
	LCDPutString(buff);
	
	LCDGotoXY(0,PROD_LINE);
	LCDPutString(product);
	LCDGotoXY(QTY_COL,PROD_LINE);
	if(quantity!=-1)
		sprintf(buff, "%02d", quantity);
	else
		sprintf(buff, " ");
	LCDPutString(buff);
	LCDGotoXY(PRICE_COL,PROD_LINE);
	if(price!=-1)
		sprintf(buff, "%*d.%d", (DISP_LEN-PRICE_COL-3), (price/100), (price%100));
	else
		sprintf(buff, " ");
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
			sprintf(product, " ");
			sprintf(message, "Swipe card to start");
			balance=-1;
			price=-1;
			quantity=-1;
			break;
		case LOGIN:
			sprintf(product, " ");
			sprintf(message, "Type pin 6 press A");
			balance=-1;
			price=-1;
			quantity=-1;
			break;
		case SHOPPING:
			sprintf(message, "Swipe next product");
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