#include <stdio.h>
#include <math.h>

/**********
*Ori Dabush
*212945760
*01 - CS
*ass03
**********/

//a global variable to check the efficiency of the functions
int counter = 0;

/*********************************************************************************
*Function Name: recEffiPow
*Input: double x, int y
*Output: double
*Opperation: this function calculates x^y using the efficient recursive definition
*********************************************************************************/
double recEffiPow(double x, int y) {
    //efficiency counter
    counter++;
    //case of 0 ^ negative number
    if(x==0 && y<0) {
        return 1/x;
    }
    //case of number ^ 0
    if(y==0) {
        return 1;
    }
    //case of number ^ 1
    if(y==1) {
        return x;
    }
    //case of 0 ^ positive number
    if(x==0 && y>0) {
        return 0;
    }
    //recursive part
    return recEffiPow(x,y/2)*recEffiPow(x,y/2)*recEffiPow(x,y%2);
}

/*****************************************************************************
*Function Name: recPow
*Input: double x, int y
*Output: double
*Opperation: this function calculates x^y using the naive recursive definition
*****************************************************************************/
double recPow(double x, int y) {
    //efficiency counter
    counter++;
    //case of 0 ^ negative number
    if(x==0 && y<0) {
        return 1/x;
    }
    //case of number ^ 0
    if(y==0) {
        return 1;
    }
    //case of number ^ 1
    if(y==1) {
        return x;
    }
    //case of 0 ^ positive number
    if(x==0 && y>0) {
        return 0;
    }
    //recursive part
    return x*recPow(x,y-1);
}

/***********************************************************************
*Function Name: iterPow
*Input: double x, int y
*Output: double 
*Opperation: this function calculates x^y using the iterative definition
***********************************************************************/
double iterPow(double x, int y) {
    double pow=1;
    //case of 0 ^ negative number
    if(x==0 && y<0) {
        return 1/x;
    }
    //case of number ^ 0
    if(y==0) {
        return 1;
    }
    //case of number ^ 1
    if(y==1) {
        return x;
    }
    //case of 0 ^ positive number
    if(x==0 && y>0) {
        return 0;
    }
    //for loop that multiplies x by himself y times
    for(int i=0;i<y;i++) {
        pow*=x;
        //efficiency counter
        counter++;
    }
    return pow;
}

/*****************************************************************************************
*Function Name: isPermutation
*Input: int arr1[], int size1 (size of arr1), int arr2[], int size2 (size of arr2)
*Output: int (1 or 0)
*Opperation: this function checks if one of the arrays is a permutation of the other array
*****************************************************************************************/
int isPermutation(int arr1[], int size1, int arr2[], int size2) {
    //condition to check the case when the sizes of the arrays are different
    if(size1!=size2) {
        return 0;
    }
    int numOfMatches,values1[MAX]={0},values2[MAX]={0},isPerm=1;

    /*loop that counts how many times every number between 0,MAX-1 is appearing in the arrays. the count 
    is done in new arrays for every given array (value1 counts the numbers in array1, and so for value 2)*/
    for(int i=0;i<size1;i++) {
        values1[arr1[i]]++;
        values2[arr2[i]]++;
    }
    
    /*condition that checks that the number of times every number between 0,MAX-1 is appearing in the arrays
    is equal if it's not equal, it turns off a boolean variable (isPerm)*/
    for(int i=0;i<MAX;i++) {
        if(values1[i]!=values2[i]) {
            isPerm=0;
        }
    }
    
    /*condition that checks if the boolean variable is on. if he's on, that means that one array is a
    permutation of the other array*/
    if(isPerm==1) {
        return 1;
    }
    return 0;
}

/**********************************************************************
*Function Name: printArr2D
*Input: int arr[][MAX], int rows, int columns
*Output: -none-
*Opperation: this function prints the given array in a symmetric table
**********************************************************************/
void printArr2D(int arr[][MAX], int rows, int columns) {
    int maxDigitNumber=0;
    
    /*this loops put the maximum number of digits in the values in the array (int)(log10(arr[i][j]))+1 is the 
    number of digits in arr[i][j]*/
    for(int i=0;i<rows;i++) {
        for(int j=0;j<columns;j++) {
            if(arr[i][j]!=0 && ((int)(log10(arr[i][j]))+1)>maxDigitNumber) {
                maxDigitNumber=(int)(log10(arr[i][j]))+1;
            }
        }
    }
    //this loops print the table in the maximun-number length (maxDigitNumber)
    for(int i=0;i<rows;i++) {
        for(int j=0;j<columns;j++) {
            printf("%0*d ",maxDigitNumber,arr[i][j]);
        }
        printf("\n");
    }
}