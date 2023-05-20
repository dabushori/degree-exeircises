#include <stdio.h>
#include "dictionary.h"

void showDictionary(Dictionary* dictionary) {
    printf("Size: %d\nDictionary: ", sizeOfDictionary(dictionary));
    printDictionary(dictionary);
    printf("\n");
}

///***test***////
void main() {
	printf("\n##------------------##\n\ntests start here\n\n##------------------##\n");


	int newKeys[] = { 73, 81, -17, -62, 1, 74, 79, 1, -25, 17, 18, 0 };
	int newVals[] = { -37, -18, 71, 26, 165, -47, -97, -1, 52, -71, -81, 0 };
	int newSize = sizeof(newKeys) / sizeof(int);


	printf("\n\n------------------\nEmpty dictionary:\n------------------\n\n");
	Dictionary* newDict = initDictionary();
	showDictionary(newDict);
	for (int i = 0; i < newSize; i++) {
		printf("% 3d: % 3d\n", newKeys[i], getFromDictionary(newDict, newKeys[i]));
	}
	destroyDictionary(newDict);


	printf("\n\n------------------\nDictionary from array\n------------------\n\n");
	newDict = createDictionaryFromArrays(newKeys, newVals, newSize);
	showDictionary(newDict);
	destroyDictionary(newDict);


	printf("\n\n------------------\nkey+3, value+1\n------------------\n\n");
	newDict = initDictionary();
	showDictionary(newDict);
	int aKey;
	for (int v = 0; v < 12; v++) {
		aKey = 3 * v;
		printDictionary(newDict);
		printf("\n");
		putInDictionary(newDict, aKey, v);
	}
	destroyDictionary(newDict);


	printf("\n\n------------------\nkey+1, value+5\n------------------\n\n");
	newDict = initDictionary();
	int aVal = 0;
	for (int k = -6; k <= 6; k++) {
		aVal = k * 5;
		putInDictionary(newDict, k, aVal);
		printDictionary(newDict);
		printf("\n");
	}


	printf("\n\n------------------\nonly 0 / Z*10 values\n------------------\n\n");
	for (int i = -5; i <= 6; i += 2) {
		removeFromDictionary(newDict, i);
	}
	showDictionary(newDict);

	for (int i = -6; i <= 6; i++) {
		printf("% 2d: % 3d\n", i, getFromDictionary(newDict, i));
	}


	printf("\n\n------------------\nonly 1 / 0 values\n------------------\n\n");
	for (int i = -6; i <= 6; i += 2) {
		putInDictionary(newDict, i, 1);
	}
	showDictionary(newDict);
	for (int i = -6; i <= 6; i++) {
		printf("% 2d: %d\n", i, getFromDictionary(newDict, i));
	}
	destroyDictionary(newDict);


	///***end of test***////
}