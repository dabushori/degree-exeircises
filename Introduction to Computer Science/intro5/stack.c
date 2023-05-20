/**********
*Ori Dabush
*212945760
*01 - CS
*ass05
**********/

//stack file

#include <stdio.h>
#include <stdlib.h>
#include "stack.h"

//define constants of initiated stacks
#define INIT_SIZE 1
#define INIT_TOPINDEX -1

/***************************************************************************
*Function Name: errorStack
*Input: char func[]
*Output: none
*Function Operation: this function prints an heap memory error massage
*					 it is a const char because the __func__ is a const char
***************************************************************************/
void errorStack(const char func[]) {
    printf("%s: %s: Heap memory error!\n", __FILE__, func);
}

/*************************************************************************************************
*Function Name: initStack
*Input: none
*Output: Stack*
*Function Operation: this function creates a new stack and restart it with the required constants,
*                    and returns a pointer to the stack
*************************************************************************************************/
Stack* initStack() {
    //dinamically allocating memory for the stack
    Stack* stack=(Stack*)malloc(sizeof(Stack));
    //checking if the allocation failed
    if(stack == NULL) {
		errorStack(__func__);
        return NULL;
    }
    //setting the stack size
    stack->size=INIT_SIZE;
    //setting the top index size
    stack->topIndex=INIT_TOPINDEX;
    //dinamically allocating memory for the contant
    stack->content=(Element*)malloc(INIT_SIZE*sizeof(Element));
    //checking if the allocation failed
    if(stack->content == NULL) {
		errorStack(__func__);
        return NULL;
    }
    return stack;
}

/******************************************************************************************
*Function Name: destroyStack
*Input: Stack* stack
*Output: none
*Function Operation: this function releases all the memory that was allocated for the stack
******************************************************************************************/
void destroyStack(Stack* stack) {
    //freeing the memory that was allocated for the contant
    free(stack->content);
    //freeing the memory that was allocated for the stack
    free(stack);
}

/**************************************************************
*Function Name: isStackEmpty
*Input: Stack* stack
*Output: int (0 or 1)
*Function Operation: this function checks if the stack is empty
**************************************************************/
int isStackEmpty(Stack* stack) {
    if(stack->size==INIT_SIZE && stack->topIndex==INIT_TOPINDEX) {
        return 1;
    }
    return 0;
}

/************************************************************************
*Function Name: lenOfStack
*Input: Stack* stack
*Output: int 
*Function Operation: this function checks what is the length of the stack
************************************************************************/
int lenOfStack(Stack* stack) {
    //the length of the stack is the top index (last element's index) +1
    return stack->topIndex+1;
}

/**********************************************************************
*Function Name: capacityOfStack
*Input: Stack* stack
*Output: int 
*Function Operation: this function checks what is the size of the stack
**********************************************************************/
int capacityOfStack(Stack* stack) {
    return stack->size;
}

/*******************************************************************
*Function Name: push
*Input: Stack* stack, Element element
*Output: none 
*Function Operation: this functions inserts the element to the stack
*******************************************************************/
void push(Stack* stack, Element element) {
    //insterting the element into the stack
    stack->topIndex++;
    stack->content[stack->topIndex]=element;
    //double the memory of the content if there's no space
    if(capacityOfStack(stack) == lenOfStack(stack)) {
		//saving the pointer to the content so the memory won't lost in case of memory allocation won't work
		Element* tempPTR = stack->content;
        //reallocating the content memory
		tempPTR =(Element*)realloc(stack->content, 2*capacityOfStack(stack));
        //checking if the allocation failed
        if(tempPTR == NULL) {
			errorStack(__func__);
        }
        //if it didn't fail, double the size of the stack
        else {
			stack->content = tempPTR;
            stack->size*=2;
        }
    }
}

/********************************************************************************************************
*Function Name: pop
*Input: Stack* stack
*Output: Element 
*Function Operation: this functions returns the last element that was entered to the stack and removes it
********************************************************************************************************/
Element pop(Stack* stack) {
    //saving the element that is erased
    Element temp=stack->content[stack->topIndex];
    //erasing the element by reducing the top index by 1
    stack->topIndex--;
    //checking if the content memory can be smaller (half of the current size)
    if(capacityOfStack(stack)/2 > lenOfStack(stack)) {
		//saving the pointer to the content so the memory won't lost in case of memory allocation won't work
		Element* tempPTR = stack->content;
        //reallocating the content memory
        tempPTR=(Element*)realloc(stack->content, capacityOfStack(stack)/2);
        //checking if the allocation failed
        if(tempPTR == NULL) {
			errorStack(__func__);
        }
        //if it didn't fail, double the size of the stack
        else {
			stack->content = tempPTR;
            stack->size=capacityOfStack(stack)/2;
        }
    }
    //returning the erased element
    return temp;
}

/*****************************************************************************************
*Function Name: top
*Input: Stack* stack
*Output: Element 
*Function Operation: this functions returns the last element that was entered to the stack
*****************************************************************************************/
Element top(Stack* stack) {
    //returning the last element that was entered
    return stack->content[stack->topIndex];
}

/**********************************************************************
*Function Name: printStack
*Input: Stack* stack
*Output: none 
*Function Operation: this function prints all the elements in the stack
**********************************************************************/
void printStack(Stack* stack) {
    for(int i=stack->topIndex;i>=0;i--) {
        printf("%d: ", i+1);
        printElement(stack->content[i]);
        printf("\n");
    }
}