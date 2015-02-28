/**
\file lcd.h
\brief Functions for LCD display. For AVR microcontroller. Was provided to the students in this course.
\code 
 Instructions:
 Connect LCD display to the ports and pins defined below.
 Call lcd_init() first with the number of columns on the display.
 note:	due to the port to c style code Doxygen does not read beyond the declaration 
		of the first "#endif" it does include all function information just not sourcecode.
\endcode
\author AgF
\author Doxygen by Rudolf Anton Fortes
\date 2011-05-07
*/
#include <avr/interrupt.h>

//////////////////////////////////////////////////////////////////////
//
//            Port and pin definitions
//
//////////////////////////////////////////////////////////////////////

// Define which port the LCD display is connected to:
#define  LCD_PORT       PORTA		/**< [Port and pin definitions]LCD_PORT: PORTA */
#define  LCD_DIRECTION  DDRA		/**< [Port and pin definitions]LCD_DIRECTION: DDRA */

// Define control and data connections
#define  LCD_RD     1				/**< [Port and pin definitions]RD connected to bit 1 on port*/
#define  LCD_RS     2				/**< [Port and pin definitions]RS connected to bit 2 on port*/
#define  LCD_ENABLE 3				/**< [Port and pin definitions]EN connected to bit 3 on port*/
#define  LCD_DATA4  4				/**< [Port and pin definitions]D4 - D7 connected to bit 4 - 7 on port*/


//////////////////////////////////////////////////////////////////////
//
//            Function prototypes:
//
//////////////////////////////////////////////////////////////////////

#ifdef __cplusplus  /** use C-style linking when compiled as C++*/
extern "C" {
#endif
/** 
 * \brief Initialize the LCD controller. Set number of columns.
 * \param lcd_columns unsigned char
 * \return void
*/
void LCDInit(unsigned char lcd_columns);


/** 
 * \brief Clear the LCD display
 *
*/
void LCDClear(void);

/**
 * \brief Set the LCD display position.
 * 
 * \param x column: starting at 0
 * \param y row, starting at 0.
 * 
 * \return void
 */
void LCDGotoXY(unsigned char x, unsigned char y);

/**
 * \brief Write a character to the LCD
 * 
 * \param c char
 * 
 * \return void
 */
void LCDPutChar(char c);

/**
 * \brief Write a zero-terminated ASCII string to the LCD
 * 
 * \param str referenced char
 * 
 * \return void
 */
void LCDPutString(const char *str);

// Delay. Approximately 200 microseconds at 10 MHz

/** 
 * \brief Delay. Circa 200 us at 10 MHz
 * \code
 The data sheet says that 40 us is sufficient, but the displays we have
 require more than 100 us to work properly. 
 \endcode
 */
void LCD_delay(void) __attribute__ ((noinline));

/**
 * \brief Long delay. Approximately 26 miliseconds at 10 MHz
 * 
 * \return 
 */
void LCD_long_delay(void) __attribute__ ((noinline));

#ifdef __cplusplus/** end if use C-style linking when compiled as C++*/
}
#endif
