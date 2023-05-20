#include <stdio.h>
#include <string.h>
#include "pstring.h"

// #define CHECKED "aBcDeFgHiJk123456789!@#$%%^&*()"

int main() {
    Pstring p1 = {0, "a"};
    p1.len = (char) strlen(p1.str);
	Pstring p2 = {0, "d"};
    p2.len = (char) strlen(p2.str);

    printf("dst = %s\n", pstrijcpy(&p1, &p2, 0,0)->str);

	// int len;
	// int opt;
    
    // printf("str1 = \'%s\'\nlen = %d\n", p1.str, p1.len);
    // printf("str2 = \'%s\'\nlen = %d\n", p2.str, p2.len);

    // char i = 0, j = 3;
    // printf("cmp(p1, p2, %d, %d) = %d\n", i, j, pstrijcmp(&p1, &p2, i, j));

    // printf("str1 swapped: \'%s\'\n", swapCase(&p1)->str);
    // printf("str2 swapped: \'%s\'\n", swapCase(&p2)->str);

	// // initialize first pstring
	// scanf("%d", &len);
    // if (len != 0) 
	//     scanf("%s", p1.str);
	// p1.len = len;

	// // initialize second pstring
	// scanf("%d", &len);
	// if (len != 0) 
	//     scanf("%s", p2.str);
	// p2.len = len;

    // int i = 0, j = 0;
    // scanf("%d%d", &i, &j);
    // printf("str1 = \'%s\'\n", p1.str);
    // printf("str2 = \'%s\'\n", p2.str);
    // printf("str2 after copying str1[%d:%d] to it = \'%s\'\n", i, j, pstrijcpy(&p2, &p1, i, j)->str);

    return 0;
}