/**
\file interrupts.c
\brief Define all settings for the interrupt. For AVR microcontroller.
\author Documented by Rudolf Anton Fortes
\version 1.2
\date Dec-2013
*//*********************************************************
*             Interrupt service routines
*********************************************************/

#include "interrupts.h"

extern volatile bool CardPresent;	/**<boolean is card present set when interrupt triggerd or data processed*/
extern volatile bool SPI_dataready;	/**<boolean is SPI dataready set when interrupt triggerd or data processed */
extern volatile bool doneShopping;	/**<boolean is done shopping set when logic dictates done shopping */
extern volatile int milliseconds;	/**<milliseconds value of milliseconds set in the main*/

/** \brief RFID CardPresent interrupt
*/
ISR(INT0_vect) //RFID CardPresent
{
	CardPresent = true;
}

/** \brief SPI Data Ready interrupt
*/
ISR(INT1_vect) //SPI Data Ready
{
	SPI_dataready = true;
}

/** \brief Keypad interrupt not in use in this itteration
*/
ISR(INT2_vect)
{	
}

#if TIMER0_PRESCALER
/** \brief Timer 2 output compare interrupt service routine. TIMER0_COMP_vect not in use in this iteration
*/
ISR(TIMER0_COMP_vect)			// Timer 0 output compare interrupt service routine.
{
	// put code here for timer 0
}
#endif
/** \brief Timer 2 output compare interrupt service routine. TIMER1_COMPA_vect milliseconds
*/
#if TIMER1_PRESCALER
ISR(TIMER1_COMPA_vect)			// Timer 1 output compare A interrupt service routine.
{
	milliseconds++;
}
#endif

#if TIMER2_PRESCALER
/** \brief Timer 2 output compare interrupt service routine. TIMER2_COMP_vect not in use in this iteration
*/
ISR(TIMER2_COMP_vect)
{
	// put code here for timer 2
}
#endif