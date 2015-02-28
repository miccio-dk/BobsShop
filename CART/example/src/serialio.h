/**
\file serialio.h
\brief Functions for serial communication. For AVR microcontroller. functionality was provided to the students in the course.
\code 
 The default communications settings are 19200 baud, 8 data bits, no
 parity, 1 stop bit.

 A driver for USB to serial converter is needed when connecting to a
 PC through a USB cable.

 RXD is connected to port D bit 0.
 TXD is connected to port D bit 1.
\endcode
\author AgF
\author Doxygen by Rudolf Anton Fortes
\date 2011-05-07
*/
//                        SERIALIO.C                          2010-02-02 AgF
//
// Functions for serial communication. For AVR microcontroller.
//
// The default communications settings are 19200 baud, 8 data bits, no
// parity, 1 stop bit.
//
// A driver for USB to serial converter is needed when connecting to a
// PC through a USB cable.
//
// RXD is connected to port D bit 0.
// TXD is connected to port D bit 1.
/////////////////////////////////////////////////////////////////////////

#include <stdarg.h>
#include <stdio.h>


//////////////////////////////////////////////////////////////////////
//
//            functions
//
//////////////////////////////////////////////////////////////////////

#ifdef __cplusplus  /** use C-style linking when compiled as C++*/
extern "C" {
#endif

/**
 * \brief initialize UART
 * 
 * 
 * \return void
 */
void SerialInitialize();


/**
 * \brief Write one character to serial connection
 * 
 * \param c int
 * 
 * \return int
 */
int SerialPutChar(int c);


/**
 * \brief Read one character from serial connection. Will wait if no character ready
 * 
 * \param 
 * 
 * \return char
 */
char SerialGetChar(void);


/**
 * \brief Tell if input is ready for reading.
 * 
 * 
 * \return char Returns 1 if there is input, 0 if no input.

 */
char SerialInputReady();


// 
/**
 * \brief Write a zero-terminated ASCII string followed by a newline ("\r\n")
 * 
 * \param str const char *
 * 
 * \return int
 */
int SerialPutString(const char *str);


/**
 * \brief Reads a character string terminated by the newline character ('\r' or '\n')
 * \code
Reads a character string terminated by the newline character ('\r' or '\n')
The newline character will be replaced with 0.
The maximum length of the string is len. If len characters were read
without encountering the newline character, then the string is terminated
with 0 and the function returns.
str must be a character array of at least len characters.
 \endcode
 * \param str char *
 * \param len unsigned char
 * 
 * \return char *
 */
char *SerialGetString(char *str, unsigned char len);

// Write formatted output. See any C language textbook for details.
// Warning: Will fail if string longer than 81 characters!
// Floating point not supported.
/** 
\brief Write formatted output.
\code
Write formatted output. See any C language textbook for details.
Warning: Will fail if string longer than 81 characters!
Floating point not supported.
\endcode
\param const char *format
\return int
*/
int SerialPrintf(const char *format, ...)__attribute__ ((format (printf,1,2))); /** additional syntax check*/

#ifdef __cplusplus
}
#endif
