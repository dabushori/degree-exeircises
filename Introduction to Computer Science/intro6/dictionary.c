/**********
*Ori Dabush
*212945760
*01 - CS
*ass06
**********/

#include <stdio.h>
#include <stdlib.h>
#include "element.h"
#include "dictionary.h"

//defines for the details of the initiate
#define INIT_SIZE 1
#define INIT_NUM_OF_ELEMENTS 0

//define of an index of a key that is not in the dictionary
#define NOT_IN_DICTIONARY -1

//a struct for the dictionary itself
struct Dictionary {
	Element* content;
	int numOfElements, size;
};

/***********************************************************************
*Function Name: initDictionary
*Input: none
*Output: Dictionary*
*Function Operation: this function allocates memory for a new dictionary
***********************************************************************/
Dictionary* initDictionary() {
	Dictionary* newDict = (Dictionary*)malloc(sizeof(Dictionary));
	//check if the alloction failed
	if (newDict == NULL) {
		return NULL;
	}
	//set the number of elements in the dictionary
	newDict->numOfElements = INIT_NUM_OF_ELEMENTS;
	//set the dictionary size
	newDict->size = INIT_SIZE;
	//dinamically allocating memory for the content
	newDict->content = createElementArray();
	//check if the allocation failed
	if (newDict->content == NULL) {
		//free the dictionary memory if the content allocation failed
		free(newDict);
		return NULL;
	}
	//return a pointer to the new dictionary, if there were no problems
	return newDict;
}

/***********************************************************************************************
*Function Name: destroyDictionary
*Input: Dictionary* d
*Output: none
*Function Operation: this function releases all the memory that was allocated for the dictionary
***********************************************************************************************/
void destroyDictionary(Dictionary* d) {
	//free the memory that was allocated for the content
	destroyElementArray(d->content);
	//free the memory that was allocated for the dictionary
	free(d);
}

/**********************************************************************************
*Function Name: sizeOfDictionary
*Input: Dictionary* d
*Output: int
*Function Operation: this function returns the number of elements in the dictionary
**********************************************************************************/
int sizeOfDictionary(Dictionary* d) {
	//return the number of elements in the dictionary
	return d->numOfElements;
}

/*******************************************************************************************************************
*Function Name: findIndexByKey
*Input: Dictionary* d, int key
*Output: int
*Function Operation: this function checks if the key is in the dictionary and returns the index of it in the content
*					 array. if it is not, it returns -1
*******************************************************************************************************************/
int findKeyIndex(Dictionary* d, int key) {
	//check if the key is in the dictionary
	for (int i = 0; i < d->numOfElements; i++) {
		if (getKey(getElementInArray(d->content, i)) == key) {
			//return its index if it is
			return i;
		}
	}
	//return -1 if the key is not in the dictionary
	return NOT_IN_DICTIONARY;
}

/**************************************************************************
*Function Name: putInDictionary
*Input: Dictionary* d, int key, int value
*Output: Result (SUCCESS, FAILURE, MEM_ERROR)
*Function Operation: this function puts the key and value in the dictionary
**************************************************************************/
Result putInDictionary(Dictionary* d, int key, int value) {
	//check if there's memory available for the desired new element
	if (d->numOfElements == d->size) {
		return MEM_ERROR;
	}
	//check if the desired key already exists in the dictionary
	int keyIndex = findKeyIndex(d, key);
	//if the desired key doesn't exist 
	if (keyIndex == NOT_IN_DICTIONARY) {
		//change the last element's key and value
		setKey(getElementInArray(d->content, d->numOfElements), key);
		setValue(getElementInArray(d->content, d->numOfElements), value);
		//add 1 to the number of elements in the dictionary
		d->numOfElements++;
		//check if the memory need to be doubled
		if (d->numOfElements == d->size) {
			//double the content array's size using the element's function
			Element* temp = doubleElementArray(d->content, d->size);
			//check if the allocation failed
			if (temp == NULL) {
				return MEM_ERROR;
			}
			//if it didn't failed, update the content pointer and double the size
			d->content = temp;
			d->size *= 2;
		}
		return SUCCESS;
	}
	//if the desired key already exists in the dictionary, changing the value of the element in the key's index
	setValue(getElementInArray(d->content, keyIndex), value);
	return SUCCESS;
}

/********************************************************************************************************************
*Function Name: getFromDictionary
*Input: Dictionary* d, int key
*Output: int
*Function Operation: this function finds the key in the dictionary and returns its value. it the key doesn't exist in
*					 the dictionary, it returns 0.
********************************************************************************************************************/
int getFromDictionary(Dictionary* d, int key) {
	//find the index of the desired key in the content array
	int keyIndex = findKeyIndex(d, key);
	//if the desired key is not in the content array
	if (keyIndex == NOT_IN_DICTIONARY) {
		return 0;
	}
	//returning the value of the desired key
	return getValue(getElementInArray(d->content, keyIndex));
}

/********************************************************************************************************************
*Function Name: dictReducer
*Input: Dictionary* d, int index
*Output: none
*Function Operation: this function removes the element in index from the dictionary and moving all the other elements
********************************************************************************************************************/
void dictReducer(Dictionary* d, int index) {
	for (int i = index + 1; i < d->numOfElements; i++) {
		//swap elements in index i and i-1
		swapElement(getElementInArray(d->content, i - 1), getElementInArray(d->content, i));
	}
	//decrease the number of elements int the dictionary by 1
	d->numOfElements--;
}

/*******************************************************************************
*Function Name: removeFromDictionary
*Input: Dictionary* d, int key
*Output: Result (SUCCESS, FAILURE, MEM_ERROR)
*Function Operation: this function removes key and its value from the dictionary
*******************************************************************************/
Result removeFromDictionary(Dictionary* d, int key) {
	//find the index of the desired key in the content array
	int keyIndex = findKeyIndex(d, key);
	//check if the key exists in the dictionary
	if (keyIndex == NOT_IN_DICTIONARY) {
		return FAILURE;
	}
	dictReducer(d, keyIndex);
	if (d->size / 2 > d->numOfElements) {
		//divide the content array's size by 2 using the element's function
		Element* temp = halfElementArray(d->content, d->size);
		//check if the allocation failed
		if (temp == NULL) {
			return MEM_ERROR;
		}
		//if it didn't failed, update the content pointer and divide the size by 2
		d->content = temp;
		d->size /= 2;
	}
	return SUCCESS;
}

/**********************************************************************
*Function Name: printDictionary
*Input: Dictionary* d
*Output: none
*Function Operation: this function prints the dictionary sorted by keys
**********************************************************************/
void printDictionary(Dictionary* d) {
	//sort the dictionary by keys
	sortElementArray(d->content, d->numOfElements);
	//print the elements
	printf("{");
	for (int i = 0; i < d->numOfElements; i++) {
		printElement(getElementInArray(d->content, i));
	}
	printf("}");
}

/***************************************************************************
*Function Name: createDictionaryFromArrays
*Input: int keys[], int values[], int size
*Output: Dictionary*
*Function Operation: this function create a dictionary from the given arrays
***************************************************************************/
Dictionary* createDictionaryFromArrays(int keys[], int values[], int size) {
	//create a new dictionary
	Dictionary* newDict = initDictionary();
	//put the elements in the dictionary
	for (int i = 0; i < size; i++) {
		putInDictionary(newDict, keys[i], values[i]);
	}
	//return a pointer to the new dictionary
	return newDict;
}