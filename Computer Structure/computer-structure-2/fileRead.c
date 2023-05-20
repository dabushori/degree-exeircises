#include <stdio.h>

// fwrite(addressData, sizeData, numbersData, pointerToFile);
// fread(addressData, sizeData, numbersData, pointerToFile);

#define SIZE 8
#define BYTE unsigned char

int main(int argc, char *argv[]) {
  FILE *file = fopen(argv[1], "rb");
  if (file == NULL) {
    printf("ERROR!\n");
    return 1;
  }

  BYTE str[2] = {};
  size_t ret = fread(str, sizeof(BYTE), sizeof(str) / sizeof(BYTE), file);
  for (int i = 0; i < 2; i++) {
    printf("byte #%d = %X\n", i, str[i]);
  }
  printf("isBigEndian = %d\n", (str[0] == 0xFE));
  return 0;
}