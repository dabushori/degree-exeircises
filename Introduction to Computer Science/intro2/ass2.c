/**********
*Ori Dabush
*212945760
*01 - CS
*ass02
**********/
#include <stdio.h>
int main(){
    int assignment,number=0,isOdd=0,isIncreasing=0,isDecreasing=0,isConstant=0,indexOfDigit=0,
    digitValue=1,isError=0,numOfMinuses,numOfLines,numOfSpaces,base,numberTemp,bitcounter=0;
    char previousCharacter,character,baseMax,clear='0';
    const char charMin='a',charMax='z',hexMax='e',hexMin='a',HexMax='E',HexMin='A',baseMin='0';
    const int diff='1'-1;
    printf("Choose an option:\n1: Draw\n2: Even or Odd\n3: Text type\
            \n4: Hex to Dec\n5: Base to Dec\n6: Count bits\n0: Exit\n");
    scanf("%d", &assignment);
    while(assignment!=0){

        //1:draw
        if(assignment==1){ 
            printf("Enter a number: ");
            scanf("%d", &number);
            //case of 1x1 square
            if(number==0) {printf("%C\n", 'X');} 
            else{

                //the first line of the square
                printf("%c", '+'); 
                //number of minuses between the '+' signs
                numOfMinuses=2*number-1; 
                for(int i=0;i<numOfMinuses;i++) {printf("%c", '-');} 
                printf("%c\n", '+');
                
                //lines 2 - number of the square

                //number of lines from 2 to number
                numOfLines=number-1; 
                for(int i=0;i<numOfLines;i++){ 
                    printf("%c", '|');
                    //number of spaces between '|' and '\' in line 2+i
                    numOfSpaces=i; 
                    for(int j=0;j<numOfSpaces;j++) {printf("%c", ' ');}
                    printf("%c", '\\');
                    //number of space between '\' and '/' in line 2+i
                    numOfSpaces=2*number-3-2*i; 
                    for(int j=0;j<numOfSpaces;j++) {printf("%c", ' ');}
                    printf("%c", '/');
                    //number of spaces between '/' and '|' in line 2+i
                    numOfSpaces=i; 
                    for(int j=0;j<numOfSpaces;j++) {printf("%c", ' ');}
                    printf("%c\n", '|');
                }

                //line number+1 of the square
                printf("%c", '|'); 
                //number of spaces between '|' and 'X' and between 'X' and '|'
                numOfSpaces=number-1; 
                for(int i=0;i<numOfSpaces;i++) {printf("%c", ' ');}
                printf("%c", 'X');
                for(int i=0;i<numOfSpaces;i++) {printf("%c", ' ');}
                printf("%c\n", '|');

                //lines number+2 - 2*number of the square

                //number of lines from number+2 to 2*number 
                numOfLines=number-2; 
                for(int i=numOfLines;i>=0;i--){ 
                    printf("%c", '|');
                    //number of spaces between '|' and '/' in line number+2+i
                    numOfSpaces=i; 
                    for(int j=0;j<numOfSpaces;j++) {printf("%c", ' ');}
                    printf("%c", '/');
                    //number of spaces between '/' and '\' in line number+2+i
                    numOfSpaces=2*number-3-2*i; 
                    for(int j=0;j<numOfSpaces;j++) {printf("%c", ' ');}
                    printf("%c", '\\'); 
                    //number of spaces between '\' and '|' in line number+2+i
                    numOfSpaces=i; 
                    for(int j=0;j<numOfSpaces;j++) {printf("%c", ' ');}
                    printf("%c\n", '|');
                }

                //the last line of the sqaure
                printf("%c", '+'); 
                //number of minuses between the '+' signs
                numOfMinuses=2*number-1; 
                for(int i=0;i<numOfMinuses;i++) {printf("%c", '-');}
                printf("%c\n", '+');
            }
            while(clear!='\n') {scanf("%c", &clear);}
        }

        //2:even or odd
        if(assignment==2){ 
            printf("Enter text: ");
            scanf(" %c", &character);
            while(character!='\n'){

                /*boolean variable that is 0 when the index (starting from 1) of the character is even, 
                and 1 when the index of the character is odd*/
                isOdd=1-isOdd;
                scanf("%c", &character);
            }
            //condition to see if the length (the last character's index) is even or odd
            if(isOdd==1) {printf("Your text's length is odd\n");} 
            else {printf("Your text's length is even\n");}
            while(clear!='\n') {scanf("%c", &clear);}
            isOdd=0; 
        }

        //3:text type
        if(assignment==3){ 
            printf("Enter text: ");
            scanf(" %c", &character);
            //case of invalid text in the first character
            if((character<charMin || character>charMax) && character!='\n') {isError=1;} 
            else{
                while(character!='\n'){

                    /*every iteration of the while loop a new character is entered and compared to the 
                    previous one. if the character's ascii value is bigger than the previous character's value,
                    the increasing boolean variable is turned on. if it's smaller, the decreasing boolean
                    variable is turned on and if it's equal to him the constant boolean variable is turned on*/
                    previousCharacter=character; 
                    scanf("%c", &character);
                    if((character<charMin || character>charMax) && character!='\n') {isError=1;}
                    if(previousCharacter>character && character!='\n') {isDecreasing=1;}
                    if(previousCharacter<character && character!='\n') {isIncreasing=1;}
                    if(previousCharacter==character && character!='\n') {isConstant=1;}
                }
                //conditions to see if the text is increasing, decreasing, constant or mixed.
                if(isError==1) {printf("your text is invalid\n");}
                else{
                if(isIncreasing==1 && isDecreasing==0) {printf("your text is increasing\n");}
                if(isIncreasing==0 && isDecreasing==1) {printf("your text is decreasing\n");}
                if(isIncreasing==1 && isDecreasing==1) {printf("your text is mixed\n");}
                if(isIncreasing==0 && isDecreasing==0 && isConstant==1) {printf("your text is constant\n");}
                }
            }
            while(clear!='\n') {scanf("%c", &clear);}
            isConstant=isDecreasing=isIncreasing=isError=0;
        }

        //4:hex to dec
        if(assignment==4){ 
            number=0;
            printf("Enter a reversed number in base 16: ");
            scanf(" %c", &character);
            while(character!='\n'){

                /*switch-case that takes the character, multiply its value and the value
                of its digit (16^index) and sum it into the number variable. 
                the for loops calculate the digit's value (16^index)*/
                switch(character) {
                    case 'a':
                    case 'A': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(10*digitValue);
                              break;
                    case 'b':
                    case 'B': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(11*digitValue);

                              break;
                    case 'c':
                    case 'C': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(12*digitValue);
                              break;
                    case 'd':
                    case 'D': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(13*digitValue);
                              break;
                    case 'e':
                    case 'E': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(14*digitValue);
                              break;
                    case 'f':
                    case 'F': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(15*digitValue);
                              break;
                    case '0': break;
                    case '1': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=digitValue;
                              break;
                    case '2': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(2*digitValue);
                              break;
                    case '3': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(3*digitValue);
                              break;
                    case '4': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(4*digitValue);
                              break;
                    case '5': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(5*digitValue);
                              break;
                    case '6': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(6*digitValue);
                              break;
                    case '7': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(7*digitValue);
                              break;
                    case '8': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(8*digitValue);
                              break;
                    case '9': for(int i=0;i<indexOfDigit;i++) {digitValue*=16;}
                              number+=(9*digitValue);
                              break;
                              
                    /*boolean variable that turns on if there is a wrong character and prevent the 
                    printing of the number that was calculated until the wrong character has entered*/
                    default:  {printf("Error! %c is not a valid digit in base 16\n", character);
                              isError=1;}
                }

                /*resetting the variable that calculate the digit's value so
                it won't start with the previous character's value in it*/
                digitValue=1;
                //adding 1 to the digit's index every time a new character is entered
                indexOfDigit++;
                scanf("%c", &character);
            }
            //condition that prints the number only if there were no wrong characters
            if(isError==0) {printf("%d\n", number);}
            while(clear!='\n') {scanf("%c", &clear);}
            isError=indexOfDigit=0;
        }

        //5:base to dec
        if(assignment==5){
            number=0;
            printf("Enter a base (2-10): ");
            scanf("%d", &base);
            //a variable that defines the maximum digit that is valid in the chosen base
            baseMax=base-1+diff;
            printf("Enter a reversed number in base %d: ", base);
            scanf(" %c", &character);

            /*while-loop that takes every digit, multiply its value (character-diff)
            and the value of its digit (base^index) and sum it into the number variable.
            the for loop calculates the digit's value (base^index)*/
            while(character!='\n'){

                /*condition that checks if the digit is valid in the chosen base. in the condition there
                is a boolean variable that turns on if there is a wrong character and prevent the printing
                of the number that was calculated until the wrong character has entered*/
                if(character<baseMin || character>baseMax){
                    printf("Error! %c is not a valid digit in base %d\n", character, base);
                    isError=1;
                }
                for(int i=0;i<indexOfDigit;i++) {digitValue*=base;}
                number+=(digitValue*(character-diff));
                
                /*resetting of the variable that calculate the digit's value so 
                it won't start with the previous character's value in it*/
                digitValue=1;
                //adding 1 to the digit's index every time a new character is entered
                indexOfDigit++;
                scanf("%c", &character);
            }
            //condition that prints the number only if there were no wrong characters
            if(isError==0) {printf("%d\n", number);}
            while(clear!='\n') {scanf("%c", &clear);}
            indexOfDigit=isError=0;
        }

        //6:count bits
        if(assignment==6){
            printf("Enter a number: ");
            scanf("%d", &number);
            /*temporary variable which is equal to the number that was 
            entered so it can be changed and still be printed in the end*/
            numberTemp=number;
            //a while loop that "calculates" the number in base 2 and sums its digits
            for(int i=0;i<32;i++){
                bitcounter+=(number&1);
                number>>=1;
            }
            printf("The bit count of %d is %d\n", numberTemp, bitcounter);
            while(clear!='\n') {scanf("%c", &clear);}
            bitcounter=0;
        }

        //massage if the character that was entered is wrong
        if(assignment<0 || assignment>6) {printf("Wrong option!\n");}

        printf("Choose an option:\n1: Draw\n2: Even or Odd\n3: Text type\
                \n4: Hex to Dec\n5: Base to Dec\n6: Count bits\n0: Exit\n");
        scanf("%d", &assignment);
    }
    return 0;
}