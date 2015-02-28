#ifndef DISPLAY_H_
#define DISPLAY_H_

#include <stdio.h>	
#include "lcd.h"
//#include "defines.h"

#define DISP_LEN		20
#define USER_LINE		0
#define BAL_COL			9
#define PROD_LINE		1
#define QTY_COL			9
#define PRICE_COL		12
#define MSG_LINE		2
#define INPUT_LINE		3

typedef enum {IDLE, LOGIN, SHOPPING} ScreenMode;


void SetMode(ScreenMode t);
void SetBalance(long newBalance);
void SetCustomer(char *custName);
void SetProduct(char *prodDescr, int prodPrice);
void SetQuantity(int newQty);
void SetMessage(char *msg);



#endif /* DISPLAY_H_ */