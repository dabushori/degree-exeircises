#ifndef __ELEMENT_H
#define __ELEMENT_H

typedef struct Element Element;

Element* createElementArray();
Element* doubleElementArray(Element* array, int currentSize);
Element* halfElementArray(Element* array, int currentSize);
void destroyElementArray(Element* e);

int getKey(Element* e);
int getValue(Element* e);
Element* getElementInArray(Element* array, int index);
void setKey(Element* e, int key);
void setValue(Element* e, int value);

void printElement(Element* e);
int compareElementByKeys(Element* e1, Element* e2);
void swapElement(Element* e1, Element* e2);
void sortElementArray(Element* array, int size);

#endif