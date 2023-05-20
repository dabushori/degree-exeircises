/**********
*Ori Dabush
*212945760
*01 - CS
*ass05
**********/

//queue file

#include <stdio.h>
#include <stdlib.h>
#include "queue.h"

/***************************************************************************
*Function Name: errorQueue
*Input: char func[]
*Output: none
*Function Operation: this function prints an heap memory error massage
*					 it is a const char because the __func__ is a const char
***************************************************************************/
void errorQueue(const char func[]) {
	printf("%s: %s: Heap memory error!\n", __FILE__, func);
}

/****************************************************************************************
*Function Name: initQueue
*Input: none
*Output: Queue*
*Function Operation: this function creates a new queue and returns a pointer to the queue
****************************************************************************************/
Queue* initQueue() {
	//dinamically allocating memory for the queue
	Queue* queue = (Queue*)malloc(sizeof(Queue));
	//checking if the allocation failed
	if (queue == NULL) {
		errorQueue(__func__);
		return NULL;
	}
	//creating 2 stacks for the queue
	queue->s1 = initStack();
	queue->s2 = initStack();
	return queue;
}

/******************************************************************************************
*Function Name: destroyQueue
*Input: Queue* queue
*Output: none
*Function Operation: this function releases all the memory that was allocated for the queue
******************************************************************************************/
void destroyQueue(Queue* queue) {
	//destroying stack 1
	destroyStack(queue->s1);
	//destroying stack 2
	destroyStack(queue->s2);
	//freeing the memory that was allocated for the queue
	free(queue);
}

/**************************************************************
*Function Name: isQueueEmpty
*Input: Queue* queue
*Output: int (0 or 1)
*Function Operation: this function checks if the queue is empty
**************************************************************/
int isQueueEmpty(Queue* queue) {
	//the queue is empty only if the stack is empty
	if (isStackEmpty(queue->s1)) {
		return 1;
	}
	return 0;
}

/************************************************************************
*Function Name: lenOfQueue
*Input: Queue* queue
*Output: int
*Function Operation: this function checks what is the length of the queue
************************************************************************/
int lenOfQueue(Queue* queue) {
	//the length of the queue is the length of s1
	return lenOfStack(queue->s1);
}

/**********************************************************************
*Function Name: capacityOfQueue
*Input: Queue* queue
*Output: int
*Function Operation: this function checks what is the size of the queue
**********************************************************************/
int capacityOfQueue(Queue* queue) {
	return capacityOfStack(queue->s1) + capacityOfStack(queue->s2);
}

/******************************************************************
*Function Name: enqueue
*Input: Queue* queue, Element element
*Output: none
*Function Operation: this function inserts the element to the queue
******************************************************************/
void enqueue(Queue* queue, Element element) {
	//pushing the element into s1
	push(queue->s1, element);
}

/**********************************************************************************************************
*Function Name: moveAllElements
*Input: Stack* src, Stack* dst
*Output: none
*Function Operation: this functions move all the elements in src to dst, the first one that gets out of src 
*				     gets into dst
**********************************************************************************************************/
void moveAllElements(Stack* src, Stack* dst) {
	int lenOfSrc = lenOfStack(src);
	//poping all the elements of src and pushing what was returned to dst
	for (int i = 0; i < lenOfSrc; i++) {
		push(dst, pop(src));
	}
}

/*********************************************************************************************************
*Function Name: dequeue
*Input: Queue* queue
*Output: Element
*Function Operation: this functions returns the first element that was entered to the queue and removes it
*********************************************************************************************************/
Element dequeue(Queue* queue) {
	//moving all the elements from s1 to s2
	moveAllElements(queue->s1, queue->s2);
	
	/*removing the last element of s2 (which is the first element of s1 and the queue) and keeping it so 
	it could be returned*/
	Element temp = pop(queue->s2);
	//moving all the elements back to s1 without the element that was removed
	moveAllElements(queue->s2, queue->s1);
	//returning the removed element
	return temp;
}

/******************************************************************************************
*Function Name: peek
*Input: Queue* queue
*Output: Element
*Function Operation: this functions returns the first element that was entered to the queue
******************************************************************************************/
Element peek(Queue* queue) {
	//moving all the elements from s1 to s2
	moveAllElements(queue->s1, queue->s2);
	//keeping the last element of s2 (which is the first element of s1 and the queue) so it could be returned
	Element temp = top(queue->s2);
	//moving all the elements back to s1 without the element that was removed
	moveAllElements(queue->s2, queue->s1);
	return temp;
}