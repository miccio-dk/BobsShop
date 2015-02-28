/**
\file display.h
\brief Functions for display. For AVR microcontroller.this display module utilizes the teachers provided lcd module and appends to its functionality to suit the needs of this project
\author Riccardo Miccini
\author Documented by Riccardo Miccini
\version 1.2
\date Dec-2013
*/
#ifndef DISPLAY_H_
#define DISPLAY_H_

#include <stdio.h>
#include <serialio.h>
#include "lcd.h"
#include "defines.h"

#define DISP_LEN		20	/**<Display Length in our case 20 characters*/
#define USER_LINE		0 	/**<User line the row used to display user information*/
#define PROD_LINE		1   /**<Product line the row used to display product information*/
#define QTY_COL			9   /**<quantity column the column to start displaying quantity information*/
#define TOT_COL			12  /**<Total column the column to start displaying the running total*/
#define MSG_LINE		2   /**<Message line the row used to display system messages*/
#define INPUT_LINE		3   /**<Input line the row dedicated to user input*/

#define DEFAULT_IDLE_MSG "Swipe card to chkout"		/**<Common system messages to ensure valid user interaction*/
#define DEFAULT_PIN_MSG "type PIN & press A"		/**<Common system messages to ensure valid user interaction*/
#define DEFAULT_SCAN_MSG "Swipe next product"		/**<Common system messages to ensure valid user interaction*/
#define DEFAULT_GOODBYE_MSG "Thanks & come again"	/**<Common system messages to ensure valid user interaction*/
#define DEFAULT_CART_MSG "Purchase anyway?"			/**<Common system messages to ensure valid user interaction*/

#define DEFAULT_PROD_ERROR "Invalid product"		/**<Common system messages to ensure valid user interaction*/
#define DEFAULT_CUST_ERROR "Invalid cust. card"		/**<Common system messages to ensure valid user interaction*/
#define DEFAULT_PIN_ERROR "Invalid pin."			/**<Common system messages to ensure valid user interaction*/
#define DEFAULT_CART_ERROR "Content not matching"	/**<Common system messages to ensure valid user interaction*/

#define DEFAULT_INTERNAL_ERROR "Error: try again."	/**<Common system messages to ensure valid user interaction*/
#define DEFAULT_PURC_ERROR "Transaction aborted"	/**<Common system messages to ensure valid user interaction*/
#define DEFAULT_ERROR "Error"						/**<Common system messages to ensure valid user interaction*/


typedef enum {IDLE, BILLING, CHECKING} ScreenMode;	/**<enumerated type to show what modes the cart can be in. */

/**
 * \brief set the screen mode of the product
 * 
 * \param t
 * 
 * \return void
 */
void SetMode(ScreenMode t);

/**
 * \brief set the customer name
 * 
 * \param custName char array
 * 
 * \return void
 */
void SetCustomer(char *custName);

/**
 * \brief set the product
 * 
 * \param prodDescr product description / name
 * \param newQty product quantity
 * \param newTotal new running total 
 * 
 * \return void
 */
void SetProduct(char *prodDescr, int newQty, int newTotal);

/**
 * \brief set the system message see the "Common system messages to ensure valid user interaction" in display.h for hints
 * 
 * \param msg
 * 
 * \return void
 */
void SetMessage(char *msg);



#endif /* DISPLAY_H_ */