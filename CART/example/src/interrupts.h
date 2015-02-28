/**
\file interrupts.h
\brief Define settings for the interrupt. For AVR microcontroller.
\author Documented by Rudolf Anton Fortes
\version 1.2
\date Dec-2013
*/
#ifndef INTERRUPTS_H_
#define INTERRUPTS_H_

#include <avr/interrupt.h>
#include "defines.h"
#include "keypad.h"
#include "display.h"

/********************************************************************
*             Set timer options
********************************************************************
// These defines are used in the code below for turning on and off
// timer0, timer1 and timer2 and setting frequency etc.

// | prescaler frequency | timer0 code | timer1 code | timer2 code |
// +---------------------+-------------+-------------+-------------+
// |  timer not used     |      0      |      0      |      0      |
// |    10,000,000       |      1      |      1      |      1      |
// |     1,250,000       |      2      |      2      |      2      |
// |       312,500       |      -      |      -      |      3      |
// |       156,250       |      3      |      3      |      4      |
// |        78,125       |      -      |      -      |      5      |
// |        39,062.5     |      4      |      4      |      6      |
// |         9,765.625   |      5      |      5      |      7      |
// +---------------------+-------------+-------------+-------------+
// Set timer prescaler frequencies according to table above:*/
#define TIMER0_PRESCALER  0
#define TIMER1_PRESCALER  2
#define TIMER2_PRESCALER  0

/* The timer frequency is the prescaler frequency divided by the divisor.
 Set the divisor for each timer*/
#define TIMER0_DIVISOR     1  // allowed range: 1 - 255
#define TIMER1_DIVISOR  1250  // allowed range: 1 - 65535
#define TIMER2_DIVISOR     1  // allowed range: 1 - 255

/* Set the defines below if you want an output pin to toggle for each
// time a timer reaches its final count. The output will be a square
// wave with half the timer frequency:*/
#define TIMER0_TOGGLE     0  // (port B bit 3)
#define TIMER1_TOGGLE     1  // (port D bit 5)
#define TIMER2_TOGGLE     0  // (port D bit 7)

#endif /* INTERRUPTS_H_ */