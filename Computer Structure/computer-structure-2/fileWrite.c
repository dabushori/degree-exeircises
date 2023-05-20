#include <stdio.h>

// fwrite(addressData, sizeData, numbersData, pointerToFile);
// fread(addressData, sizeData, numbersData, pointerToFile);

#define BYTE unsigned char

int main() {
  FILE *file = fopen("try.bin", "wb");
  BYTE str[] = {0x12, 0x34, 0x56, 0x78, 0x9a, 0xbc, 0xde, 0xf0};
  fwrite(str, sizeof(str) * sizeof(BYTE), 1, file);
  return 0;
}