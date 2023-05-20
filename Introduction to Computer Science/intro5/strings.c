/**********
*Ori Dabush
*212945760
*01 - CS
*ass05
**********/

//strings file

#include <stdio.h>
#include <string.h>
#include "strings.h"
#include "stack.h"

//define constants for the barckets
#define CIRCLE_OPEN '('
#define CIRCLE_CLOSE ')'
#define SQUARE_OPEN '['
#define SQUARE_CLOSE ']'
#define SPIRAL_OPEN '{'
#define SPIRAL_CLOSE '}'
#define TRIANGLE_OPEN '<'
#define TRIANGLE_CLOSE '>'

/********************************************************************************
*Function Name: isAOpenBarcket
*Input: char c
*Output: int (0 or 1)
*Function Operation: this function checks if c is an open barcket - ( , { , [ , <
********************************************************************************/
int isAOpenBarcket(char c) {
	if (c == CIRCLE_OPEN || c == SQUARE_OPEN || c == SPIRAL_OPEN || c == TRIANGLE_OPEN) {
		return 1;
	}
	return 0;
}

/********************************************************************************
*Function Name: isACloseBarcket
*Input: char c
*Output: int (0 or 1)
*Function Operation: this function checks if c is a close barcket - ) , } , ] , >
********************************************************************************/
int isACloseBarcket(char c) {
	if (c == CIRCLE_CLOSE || c == SQUARE_CLOSE || c == SPIRAL_CLOSE || c == TRIANGLE_CLOSE) {
		return 1;
	}
	return 0;
}

/****************************************************************************
*Function Name: toOpen
*Input: char c
*Output: char
*Function Operation: this function turns an open barcket into a close barcket
****************************************************************************/
char toOpen(char c) {
	if (c == CIRCLE_CLOSE) {
		return CIRCLE_OPEN;
	}
	if (c == SQUARE_CLOSE) {
		return SQUARE_OPEN;
	}
	if (c == SPIRAL_CLOSE) {
		return SPIRAL_OPEN;
	}
	if (c == TRIANGLE_CLOSE) {
		return TRIANGLE_OPEN;
	}
	return 0;
}

/*****************************************************************
*Function Name: charToElement
*Input: char c
*Output: Element
*Function Operation: this function returns an element with c in it
*****************************************************************/
Element charToElement(char c) {
	Element e = { c };
	return e;
}

/*******************************************************************************************************************
*Function Name: isLegalString
*Input: char str[]
*Output: int (0 or 1)
*Function Operation: this function checks if the string is legal, which means every open barcket has a close barcket
*******************************************************************************************************************/
int isLegalString(char str[]) {
	Stack* stack = initStack();
	//for loop that checks every char in str
	for (int i = 0; i < strlen(str); i++) {
		//if it is an open barcket, it pushes it into the stack
		if (isAOpenBarcket(str[i])) {
			push(stack, charToElement(str[i]));
		}
		//if it is a close barcket, it checks if the top element in the stack is it's open barcket
		if (isACloseBarcket(str[i])) {
			//if it is not then the string is illegal
			if (top(stack).c != toOpen(str[i])) {
				destroyStack(stack);
				return 0;
			}
			//if it is, pop the element and continue checking
			else {
				pop(stack);
			}
		}
	}
	//after checking all the chars in str, check that the stack is empty
	if (!isStackEmpty(stack)) {
		//destroying the stack so there won't be a memory without access to it
		destroyStack(stack);
		return 0;
	}
	//destroying the stack so there won't be a memory without access to it
	destroyStack(stack);
	return 1;
}