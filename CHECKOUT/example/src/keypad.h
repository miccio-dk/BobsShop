/** 
\file keypad.h
\brief Functions for keypad. For AVR microcontroller.
\author Riccardo Miccini
\author Documented by Rudolf Anton Fortes
\version 1.2
\date Dec-2013
*/

#ifndef F_CPU
#define F_CPU			10000000UL	/**< F_CPU set to use delay with the microcontrollers speed*/
#endif
#include <util/delay.h>
#include "defines.h" 

#define KEYPRESS_PIN	0b00000100	/**< Key Detected Pin */

#define ENTER_KEY		'A'	/**< Enter Key */
#define BCKSP_KEY		'B'	/**< Backspace Key */
#define ERASE_KEY		'C'	/**< Erase Key */
#define EXIT_KEY		'F'	/**< Exit Key */

/**
 * \brief This Function goes through the logic of receiving a PIN code from the user
 * 
 * \param code the Pin code
 * \param lenght length of the desired pin.
 * 
 * \return void
 */
void KeypadGetPIN(char *code, int lenght);

/**
 * \brief This Function was used to retrieve the 
 * 
 * \param code the RFID code
 * \deprecated only for testing purposes
 * \return void
 */
void KeypadGetRFIDCode(char *code);

/**
 * \brief This allows the user to enter 2 digits < 99 then press accept 
 * 
 * \param qty contains the quantity (string)
 * \param lenght is the number of digits
 * 
 * \return void
 */
void KeypadGetQty(char *qty, int lenght);

/**
 * \brief This allows the user to enter 2 digits < 99 then press accept but has a time limit
 * 
 * \param qty contains the quantity (string)
 * \param lenght is the number of digits
 * \param time duration given for the user to type in a quantity in ms
 * 
 * \return bool
 */
 bool KeypadGetQtyTimed(char *qty, int lenght, int time);


/**
 * \brief converts rows and columns into a symbol in the keypad
 * 
 * \param row integer representation of row 1-4
 * \param col integer representation of collumn 1-4
 * 
 * \return char
 */
 char CoordToChar(int row, int col);

/**
 * \brief This allows the user to answer to a Y/N prompt dialog 
 *
 * 
 * \return bool 
 */
bool KeypadGetResponce();
