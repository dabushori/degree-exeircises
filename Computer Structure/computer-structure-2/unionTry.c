#include <stdint.h>
#include <stdio.h>
#include <string.h>

#define BYTE unsigned char

typedef union UTF16Data {
  BYTE bytesArray[2];
  uint16_t integer16;
  // int integer32;
} UTF16Data;

int main() { return 0; }

/*
int main() {
  // printf("%d\n", sizeof(UTF16Data));
  UTF16Data data[2] = {};
  data[0].integer16 = 0x000D;
  data[1].integer16 = 0x000A;
  // data.integer32 = 1 << 31;
  printf("As byte: %X %X\n", data[0].bytesArray[0], data[0].bytesArray[1]);
  printf("As byte: %X %X\n", data[1].bytesArray[0], data[1].bytesArray[1]);
  // printf("Address %X\n", data.bytesArray);
  // printf("As integer16: %d %X\n", data.integer16, data.integer16);
  // printf("Address %X\n", data.integer16);
  // printf("As integer32: %d %X\n", data.integer32, data.integer32);
  // printf("Address %X\n", (unsigned int)&data.integer32);
  return 0;
}
*/