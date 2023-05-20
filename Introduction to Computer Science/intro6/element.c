/**********
*Ori Dabush
*212945760
*01 - CS
*ass06
**********/

#include <stdio.h>
#include <stdlib.h>
#include "element.h"

//a struct for an element in the dictionary
struct Element {
	int key, value;
};

/*******************************************************************************************
*Function Name: createElementArray
*Input: none
*Output: Element* e
*Function Operation: this function frees the memory that was allocated for the element array
*******************************************************************************************/
Element* createElementArray() {
	Element* newArray = (Element*)malloc(sizeof(Element));
	if (newArray == NULL) {
		return NULL;
	}
	return newArray;
}

/*****************************************************
*Function Name: doubleElementArray
*Input: Element* array, int currentSize
*Output: Element*
*Function Operation: this function doubles the array's
*****************************************************/
Element* doubleElementArray(Element* array, int currentSize) {
	//double the array's size
	Element* temp = realloc(array, (2 * currentSize) * sizeof(Element));
	//checking if the allocation failed
	if (temp == NULL) {
		return NULL;
	}
	return temp;
}

/***************************************************************
*Function Name: halfElementArray
*Input: Element* array, int currentSize
*Output: Element*
*Function Operation: this function divides the array's size by 2
***************************************************************/
Element* halfElementArray(Element* array, int currentSize) {
	//divide the array's size by 2
	Element* temp = realloc(array, (currentSize / 2) * sizeof(Element));
	//checking if the allocation failed
	if (temp == NULL) {
		return NULL;
	}
	return temp;
}

/*******************************************************************************************
*Function Name: destroyElementArray
*Input: Element* e
*Output: none
*Function Operation: this function frees the memory that was allocated for the element array
*******************************************************************************************/
void destroyElementArray(Element* e) {
	free(e);
}

/****************************************************************
*Function Name: getKey
*Input: Element* e
*Output: int
*Function Operation: this function returns the key of the element
****************************************************************/
int getKey(Element* e) {
	return e->key;
}

/******************************************************************
*Function Name: getValue
*Input: Element* e
*Output: int
*Function Operation: this function returns the value of the element
******************************************************************/
int getValue(Element* e) {
	return e->value;
}

/************************************************************************
*Function Name: getElementInArray
*Input: Element* array, int index
*Output: Element*
*Function Operation: this function returns the index element of the array
************************************************************************/
Element* getElementInArray(Element* array, int index) {
	return &(array[index]);
}

/****************************************************************
*Function Name: setKey
*Input: Element* e, int key
*Output: none
*Function Operation: this function changes the element key to key
****************************************************************/
void setKey(Element* e, int key) {
	e->key = key;
}

/********************************************************************
*Function Name: setValue
*Input: Element* e, int value
*Output: none
*Function Operation: this function changes the element value to value
********************************************************************/
void setValue(Element* e, int value) {
	e->value = value;
}

/********************************************************************************
*Function Name: printElement
*Input: Element* e
*Output: none
*Function Operation: this function prints the element in [<key>:<value>] building
********************************************************************************/
void printElement(Element* e) {
	printf("[%d:%d]", e->key, e->value);
}

/********************************************************************************************
*Function Name: compareElementByKeys
*Input: Element e1, Element e2
*Output: int
*Function Operation: this function returns a negative number if e1's key is smaller then e2's
*                    key, 0 if they are equal and a positive number if e1's key is bigger
********************************************************************************************/
int compareElementByKeys(Element* e1, Element* e2) {
	return e1->key - e2->key;
}

/*****************************************************************
*Function Name: swapElement
*Input: Element* e1, Element* e2
*Output: none
*Function Operation: this function swaps e1 and e2's key and value
*****************************************************************/
void swapElement(Element* e1, Element* e2) {
	Element temp;
	temp.key = e1->key;
	temp.value = e1->value;
	e1->key = e2->key;
	e1->value = e2->value;
	e2->key = temp.key;
	e2->value = temp.value;
}

/**************************************************************************
*Function Name: sortElementArray
*Input: Element* array, int size
*Output: none
*Function Operation: this function sorts the array by its the elements keys
**************************************************************************/
void sortElementArray(Element* array, int size) {
	for (int i = 0; i < size - 1; i++) {
		for (int j = 0; j < size - 1 - i; j++) {
			if (compareElementByKeys(&(array[j]), &(array[j + 1])) > 0) {
				swapElement(&(array[j]), &(array[j + 1]));
			}
		}
	}
}