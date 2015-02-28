/**
\file lcd.c
\brief Functions for LCD display. For AVR microcontroller. Was provided to the students in this course.
\code
 Instructions:
 Connect LCD display to the ports and pins defined below.
 Call lcd_init() first with the number of columns on the display.
 This version has no handshaking. You may connect LCD_RD to ground
\endcode
\author AgF
\author Doxygen by Rudolf Anton Fortes
\date 2011-05-07
*/
/////////////////////////////////////////////////////////////////////////
//                        LCD.C                         2010-02-05 AgF //
//                                                                     //
// Functions for LCD display. For AVR microcontroller.                 //
//                                                                     //
// Instructions:                                                       //
// Connect LCD display to the ports and pins defined in lcd.h          //
// Call LCDInit() first with the number of columns on the display.     //
//                                                                     //
// This version has no handshaking. You may connect LCD_RD to ground   //
//                                                                     //
/////////////////////////////////////////////////////////////////////////

#include "lcd.h"


/////////////////////////////////////////////////////////////////////////
//                       Local variables                               //
/////////////////////////////////////////////////////////////////////////
static unsigned char LCD_base_y[4] = {0x80, 0xC0, 0, 0};
static unsigned char LCD_x, LCD_y, LCD_maxx;


/////////////////////////////////////////////////////////////////////////
//                    Functions used internally                        //
/////////////////////////////////////////////////////////////////////////

void LCD_delay(void) __attribute__ ((noinline)); /** noinline prevents problems when optimizing with option -O3*/
void LCD_delay(void)  {
   __asm__ __volatile__("ldi r31,65 \n 1:\n dec r31 \n brne 1b" ::: "r31");
}
void LCD_long_delay(void) __attribute__ ((noinline));
void LCD_long_delay(void) {
   __asm__ __volatile__("clr r26 \n clr r27 \n 2:\n sbiw r26,1\n brne 2b \n" ::: "r26", "r27");
}

/**
 * \brief Write 4 bits
 * 
 * \param data char
 * 
 * \return void
 */
static void LCD_write_nibble(char data) {
   const char DataMask = 0x0F << LCD_DATA4;
   LCD_PORT = (LCD_PORT & ~DataMask) | (data & DataMask);
   LCD_PORT |= (1<<LCD_ENABLE); // EN=1
   LCD_delay();
   LCD_PORT &= ~(1<<LCD_ENABLE); // EN=0
   LCD_delay();
}

/**
 * \brief Write 8 bits. RS = 0 or 1
 * 
 * \param data unsigned char
 * 
 * \return void
 */
static void LCD_write_data(unsigned char data) {
   LCD_write_nibble(data);           /**< RD=0, write MSN*/
   LCD_write_nibble(data << 4);      /**< write LSN*/
}

/**
 * \brief Write a byte to the LCD character generator or display RAM
 * 
 * \param addr unsigned char
 * \param data unsigned char
 * 
 * \return void
 */
void LCD_write_byte(unsigned char addr, unsigned char data) {
   LCD_delay();
   LCD_PORT &= ~(1<<LCD_RS);       // RS=0
   LCD_write_data(addr);
   LCD_delay();
   LCD_PORT |= (1<<LCD_RS);        // RS=1
   LCD_write_data(data);
}

/////////////////////////////////////////////////////////////////////////
//                                                                     //
//                       Public functions                              //
//                                                                     //
/////////////////////////////////////////////////////////////////////////

// Initialize the LCD controller. Specify the number of columns
void LCDInit(unsigned char lcd_columns) {
   LCD_PORT &= ~((1<<LCD_ENABLE) | (1<<LCD_RS)); // EN=0, RS=0
   LCD_PORT &= ~(1<<LCD_RD); // Set RD = 0 in case it is connected
   LCD_DIRECTION |= (0xF << LCD_DATA4) | (1<<LCD_RS) | (1<<LCD_ENABLE) ; // set all as output
   //LCD_DIRECTION |= (1<<LCD_RD); // set RD as output if it is connected
   LCD_maxx = lcd_columns;
   LCD_base_y[2] = lcd_columns+0x80;
   LCD_base_y[3] = lcd_columns+0xc0;
   LCD_long_delay();
   LCD_write_nibble(0x30);
   LCD_long_delay();
   LCD_write_nibble(0x30);
   LCD_long_delay();
   LCD_write_nibble(0x30);
   LCD_long_delay();
   LCD_write_nibble(0x20);
   LCD_long_delay();
   LCD_write_data(0x28);
   LCD_long_delay();
   LCD_write_data(4);
   LCD_long_delay();
   LCD_write_data(0x85);
   LCD_long_delay();
   LCD_PORT &= ~(1<<LCD_RS);
   LCD_write_data(6);
   LCD_long_delay();
   LCDClear();
}

// Clear the LCD display
void LCDClear(void) {
   LCD_long_delay();
   LCD_PORT &= ~(1<<LCD_RS);          // RS=0
   LCD_write_data(2);                 // cursor home
   LCD_long_delay();
   LCD_write_data(0xC);               // cursor off
   LCD_long_delay();
   LCD_write_data(1);                 // clear
   LCD_long_delay();
   LCD_x = LCD_y = 0;
}

// Set the LCD display position
void LCDGotoXY(unsigned char x, unsigned char y) {
   LCD_delay();
   LCD_PORT &= ~(1<<LCD_RS);       // RS=0
   LCD_write_data(LCD_base_y[y]+x);
   LCD_x = x;
   LCD_y = y;
   LCD_delay();
   //LCD_long_delay();
}

// write a zero-terminated ASCII string to the display
void LCDPutString(const char *str) {
   char c;
   for (; (c = *str) != 0; str++) LCDPutChar(c);
}

// write a single ASCII character to the display
void LCDPutChar(char c) {
   if (c == '\n') {
      // newline character goes to next line
      ++LCD_y;
      LCDGotoXY(0,LCD_y);
   }
   else {
      // any other character
      ++LCD_x;
      if (LCD_x>LCD_maxx) {
         // end of line. go to next line
         ++LCD_y;
         LCDGotoXY(0,LCD_y);
      }
      LCD_delay();
      LCD_PORT |= (1<<LCD_RS);  // RS = 1
      LCD_write_data(c);
      LCD_delay();
   }
}
