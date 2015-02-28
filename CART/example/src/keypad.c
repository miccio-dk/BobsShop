/**
\file keypad.c
\brief Functions for keypad. For AVR microcontroller.
\author Riccardo Miccini
\author Documented by Rudolf Anton Fortes
\version 1.2
\date Dec-2013
\headerfile keypad.h ""
*/
#include "keypad.h"
#include "interrupts.h"


static unsigned char currChar = 0;	/**< currChar current character used to save the character being processed*/
static unsigned char row = 0;		/**< row Row of the keypad*/
static unsigned char col = 0;		/**< col Column of the keypad*/
static bool enter = false;			/**< enter whether the enter key has been typed*/
extern volatile int milliseconds;	/**< milliseconds external link to milliseconds in the main */

char CoordToChar(int row, int col)
{
	if(row<4 && col <4)			return (char)(48+col+(row-1)*3);
	else if(row<5 && col==4)	return (char)(67+(4-row));
	else if(col==1)				return 'A';
	else if(col==2)				return '0';
	else if(col==3)				return 'B';
	else						return 0;
}

/**
 * \brief empties the variable (extra safety is needed when dealing with 0-terminated strings)
 * can now be replaced by memset(str, 0, length);
 * \param str
 * \param lenght
 * \deprecated
 * 
 * \return void
 */
void EraseString(char *str, int lenght)
{
	int i=0;
	for(i=0;i<lenght;i++)
	{
		str[i] = 0;
	}
}


void KeypadGetPIN(char *code, int lenght) {
	int i = 0;
	EraseString(code,lenght);/** empties the variable (extra safety is needed when dealing with 0-terminated strings)*/
	while(true)		/** Infinite while loop waits till return statment exits function*/
	{
		if(enter)	/** If enter button pressed, exits*/
			{
			enter = false;
			return;
			}
		
		PORTB = ++PORTB%4;/** column Switching*/
		_delay_us(5);

		if(PINB&KEYPRESS_PIN)	// key detection and de-bouncing
		{
			_delay_ms(30);
			if(PINB&KEYPRESS_PIN)
			{
				col = 4-PORTB;	// reversed columns due to hardware
				row = (PINA&0b00000011)+1;
				
				currChar = CoordToChar(row,col);	//value of the last pressed key
				
				if(i<lenght && currChar>='0' && currChar<='9')	//if it's a digit and there's still space..
				{
					LCDPutChar('*');	//..put it in the string and move the cursor
					code[i]=currChar;
					i++;
					_delay_ms(220);
				}
				if(i==lenght && currChar==ENTER_KEY)	//if all digits are filled and user presses enter..
				{
					enter = true;						//..set this flag
				}

				if(i>0 && currChar==BCKSP_KEY)			//if user presses backspace after typing at least one digit..
				{
					//..erase the last digit and replace the cursor
					LCDGotoXY(i-1,INPUT_LINE);
					LCDPutChar(' ');
					LCDGotoXY(i-1,INPUT_LINE);
					code[i]=0;
					i--;
					_delay_ms(220);
				}
				if(i>0 && currChar==ERASE_KEY)			//if user presses the cancel key..
				{
					//..erase the whole string and re-place the cursor
					LCDGotoXY(0,INPUT_LINE);
					LCDPutString("                  ");
					LCDGotoXY(0,INPUT_LINE);
					EraseString(code,lenght);
					i=0;
					_delay_ms(220);
				}
			}
		}
	}
}


void KeypadGetQty(char *qty, int lenght) {
	int i = 0;		/** local variable Int i represents the location of the cursor starting at 0 */
	
	EraseString(qty,lenght);
	
	while(true)		/** infinite while loop waits till return statement exits function*/
	{
		if(enter)	/** If enter button pressed, exits*/
		{
			enter = false;
			return;
		}

		PORTB = ++PORTB%4;	/** column Switching*/
		_delay_us(5);

		if(PINB&KEYPRESS_PIN)	// key detection and de-bouncing
		{
			_delay_ms(30);
			if(PINB&KEYPRESS_PIN)
			{
				col = 4-PORTB;						// reversed columns due to hardware
				row = (PINA&0b00000011)+1;			//save row - convert numbers from 0:3 to 1:4
				
				currChar = CoordToChar(row,col);	//value of the last pressed key
				
				if(i==0 && currChar=='0')			//add zero value before first digit
				{
					LCDPutChar(currChar);
					qty[i]=currChar;
					i++;
					_delay_ms(220);
				}
				if(i<lenght && currChar>='0' && currChar<='9' && qty[i-1]!='0')
				{
					LCDPutChar(currChar);
					qty[i]=currChar;
					i++;
					_delay_ms(220);
				}
				if(i>0 && currChar==ENTER_KEY)
				{
					enter = true;
				}
				if(i>0 && currChar==BCKSP_KEY)
				{
					LCDGotoXY(i-1,INPUT_LINE);
					LCDPutChar(' ');
					LCDGotoXY(i-1,INPUT_LINE);
					qty[i]=0;
					i--;
					_delay_ms(220);
				}
				if(i>0 && currChar==ERASE_KEY)
				{
					LCDGotoXY(0,INPUT_LINE);
					LCDPutString("         ");
					LCDGotoXY(0,INPUT_LINE);
					EraseString(qty,lenght);
					i=0;
					_delay_ms(220);
				}
			}
		}
	}
}


bool KeypadGetQtyTimed(char *qty, int lenght, int time) {
	int i = 0;
	
	EraseString(qty,lenght);
	milliseconds = 0;
	while(milliseconds<time)
	{
		if(enter)
		{
			enter = false;
			return true;
		}

		PORTB = ++PORTB%4;
		_delay_us(5);
		
		if(PINB&KEYPRESS_PIN)
		{
			_delay_ms(30);
			
			if(PINB&KEYPRESS_PIN)
			{
				col = 4-PORTB;
				row = (PINA&0b00000011)+1;
				
				currChar = CoordToChar(row,col);
				
				if(i==0 && currChar=='0')
				{
					LCDPutChar(currChar);
					qty[i]=currChar;
					i++;
					_delay_ms(220);
				}
				if(i<lenght && currChar>='0' && currChar<='9' && qty[i-1]!='0')
				{
					LCDPutChar(currChar);
					qty[i]=currChar;
					i++;
					_delay_ms(220);
				}
				if(i>0 && currChar==ENTER_KEY)
				{
					enter = true;
				}
				if(i>0 && currChar==BCKSP_KEY)
				{
					LCDGotoXY(i-1,INPUT_LINE);
					LCDPutChar(' ');
					LCDGotoXY(i-1,INPUT_LINE);
					qty[i]=0;
					i--;
					_delay_ms(220);
				}
				if(i>0 && currChar==ERASE_KEY)
				{
					LCDGotoXY(0,INPUT_LINE);
					LCDPutString("         ");
					LCDGotoXY(0,INPUT_LINE);
					EraseString(qty,lenght);
					i=0;
					_delay_ms(220);
				}
			}
		}
	}
	return false;
}


void KeypadGetRFIDCode(char *code) {
	int i = 0;
	int lenght = 10;
	
	EraseString(code,lenght);
	
	while(true)
	{
		if(enter)
		{
			enter = false;
			return;
		}

		PORTB = ++PORTB%4;
		_delay_us(5);

		if(PINB&KEYPRESS_PIN)
		{
			_delay_ms(30);
			
			if(PINB&KEYPRESS_PIN)
			{
				col = 4-PORTB;
				row = (PINA&0b00000011)+1;
				
				currChar = CoordToChar(row,col);
				
				if(i<lenght-1)
				{
					LCDPutChar(currChar);
					code[i]=currChar;
					i++;
					_delay_ms(220);
				}
				if(i==lenght)
				{
					enter = true;
				}
				if(i>0 && currChar==BCKSP_KEY)
				{
					LCDGotoXY(i-1,INPUT_LINE);
					LCDPutChar(' ');
					LCDGotoXY(i-1,INPUT_LINE);
					code[i]=0;
					i--;
					_delay_ms(220);
				}
				if(i>0 && currChar==ERASE_KEY)
				{
					LCDGotoXY(0,INPUT_LINE);
					LCDPutString("         ");
					LCDGotoXY(0,INPUT_LINE);
					EraseString(code,lenght);
					i=0;
					_delay_ms(220);
				}
			}
		}
	}
}

