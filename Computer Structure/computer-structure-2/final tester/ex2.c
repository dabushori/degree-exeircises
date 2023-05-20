// 213251861 Yorai Roth Hazan
#include <stdio.h>
#include <string.h>

#define ARG_LENGTH_OPTION_ONE 3
#define ARG_LENGTH_OPTION_TWO 5
#define ARG_LENGTH_OPTION_THREE 6

#define MAC_STRING "-mac"
#define UNIX_STRING "-unix"
#define WIN_STRING "-win"

#define SIZE_OF_BYTE sizeof(unsigned char)

#define FIRST_HALF_OF_ENTER 0x00
#define SECOND_HALF_OF_R 0x0d
#define SECOND_HALF_OF_N 0x0a

#define SWAP_FLAG "-swap"
#define KEEP_FLAG "-keep"

typedef enum { LITTLE, BIG } endian;
typedef enum { MAC, UNIX, WIN, NONE } flag;

void swapBytes(unsigned char *array) {
  char temp = array[0];
  array[0] = array[1];
  array[1] = temp;
}

void copyFile(char *input, char *output, char *swapFlag) {
  FILE *fRead;
  FILE *fWrite;
  fRead = fopen(input, "rb");
  if (fRead == NULL) {
    return;
  }
  fWrite = fopen(output, "wb+");
  unsigned char twoBytes[2];
  while (fread(twoBytes, SIZE_OF_BYTE, sizeof(twoBytes) / SIZE_OF_BYTE, fRead) >
         0) {
    if (strcmp(swapFlag, SWAP_FLAG) == 0) {
      swapBytes(twoBytes);
    }
    fwrite(twoBytes, SIZE_OF_BYTE, sizeof(twoBytes) / SIZE_OF_BYTE, fWrite);
  }
  fclose(fRead);
  fclose(fWrite);
}

/**
 * @brief the function return a flag that matches the flagName
 * we can assume that is only the 3 flags so I put else at the end and
 * not else if for the windows.
 * @param flagName the name of the flag as a string
 * @return flag the flag that matches the flagNAme
 */
flag getFLag(char *flagName) {
  if (strcmp(flagName, MAC_STRING) == 0) {
    return MAC;
  } else if (strcmp(flagName, UNIX_STRING) == 0) {
    return UNIX;
  } else if (strcmp(flagName, WIN_STRING) == 0) {
    return WIN;
  } else {
    return NONE;
  }
}

void setEnter(flag flag, unsigned char *enter, endian endian) {
  switch (flag) {
  case MAC:
    if (endian == BIG) {
      enter[0] = 0x00;
      enter[1] = 0x0d;
      enter[2] = 0x00;
      enter[3] = 0x00;
    } else {
      enter[0] = 0x0d;
      enter[1] = 0x00;
      enter[2] = 0x00;
      enter[3] = 0x00;
    }
    break;
  case UNIX:
    if (endian == BIG) {
      enter[0] = 0x00;
      enter[1] = 0x0a;
      enter[2] = 0x00;
      enter[3] = 0x00;
    } else {
      enter[0] = 0x0a;
      enter[1] = 0x00;
      enter[2] = 0x00;
      enter[3] = 0x00;
    }
    break;
  case WIN:
    if (endian == BIG) {
      enter[0] = 0x00;
      enter[1] = 0x0d;
      enter[2] = 0x00;
      enter[3] = 0x0a;
    } else {
      enter[0] = 0x0d;
      enter[1] = 0x00;
      enter[2] = 0x0a;
      enter[3] = 0x00;
    }
    break;
  case NONE:
    break;
  }
}

void copyFileWithFlags(char *input, char *output, char *inFlag, char *outFlag,
                       char *swapFlag) {
  flag inputFlag = getFLag(inFlag);
  flag outputFlag = getFLag(outFlag);
  if (inputFlag == outputFlag) {
    copyFile(input, output, swapFlag);
    return;
  }
  unsigned char bytes[2];
  FILE *fRead = fopen(input, "rb");
  FILE *fWrite = fopen(output, "wb+");
  fread(bytes, SIZE_OF_BYTE, sizeof(bytes) / SIZE_OF_BYTE, fRead);
  endian endian = LITTLE;
  if (bytes[0] == 0xFE) {
    endian = BIG;
  }
  if (strcmp(swapFlag, SWAP_FLAG) == 0) {
    swapBytes(bytes);
  }
  fwrite(bytes, SIZE_OF_BYTE, sizeof(bytes) / SIZE_OF_BYTE, fWrite);
  unsigned char inputEnter[4], outputEnter[4];
  setEnter(inputFlag, inputEnter, endian);
  setEnter(outputFlag, outputEnter, endian);
  if (strcmp(swapFlag, SWAP_FLAG) == 0) {
    swapBytes(outputEnter);
    if (outputFlag == WIN) {
      swapBytes(outputEnter + 2);
    }
  }
  if (inputFlag == WIN) {
    while (fread(bytes, SIZE_OF_BYTE, sizeof(bytes) / SIZE_OF_BYTE, fRead) >
           0) {
      if (bytes[0] == inputEnter[0] && bytes[1] == inputEnter[1]) {
        char tempBytes[2];
        fread(tempBytes, SIZE_OF_BYTE, sizeof(bytes) / SIZE_OF_BYTE, fRead);
        if (tempBytes[0] == inputEnter[2] && tempBytes[1] == inputEnter[3]) {
          fwrite(outputEnter, SIZE_OF_BYTE,
                 sizeof(outputEnter) / (2 * SIZE_OF_BYTE), fWrite);
        } else {
          fwrite(bytes, SIZE_OF_BYTE, sizeof(bytes) / SIZE_OF_BYTE, fWrite);
          fwrite(tempBytes, SIZE_OF_BYTE, sizeof(tempBytes) / SIZE_OF_BYTE,
                 fWrite);
        }
      } else {
        if (strcmp(swapFlag, SWAP_FLAG) == 0) {
          swapBytes(bytes);
        }
        fwrite(bytes, SIZE_OF_BYTE, sizeof(bytes) / SIZE_OF_BYTE, fWrite);
      }
    }
  } else {
    while (fread(bytes, SIZE_OF_BYTE, sizeof(bytes) / SIZE_OF_BYTE, fRead) >
           0) {
      if (bytes[0] == inputEnter[0] && bytes[1] == inputEnter[1]) {
        if (outputFlag == WIN) {
          fwrite(outputEnter, SIZE_OF_BYTE, sizeof(outputEnter) / SIZE_OF_BYTE,
                 fWrite);
        } else {
          fwrite(outputEnter, SIZE_OF_BYTE,
                 sizeof(outputEnter) / (2 * SIZE_OF_BYTE), fWrite);
        }
      } else {
        if (strcmp(swapFlag, SWAP_FLAG) == 0) {
          swapBytes(bytes);
        }
        fwrite(bytes, SIZE_OF_BYTE, sizeof(bytes), fWrite);
      }
    }
  }
}

int isValidFiles(char *fileOne, char *fileTwo) {
  if (fileOne == NULL || fileTwo == NULL || (strcmp(fileOne, fileTwo) == 0))
    return 0;
  return 1;
}

int isValidFLags(char *flagOne, char *flagTwo) {
  if (flagOne == NULL || flagTwo == NULL ||
      !((strcmp(flagOne, MAC_STRING) == 0) ||
        (strcmp(flagOne, WIN_STRING) == 0) ||
        (strcmp(flagOne, UNIX_STRING) == 0)) ||
      !((strcmp(flagTwo, MAC_STRING) == 0) ||
        (strcmp(flagTwo, WIN_STRING) == 0) ||
        (strcmp(flagTwo, UNIX_STRING) == 0)))
    return 0;
  return 1;
}

int isValidSwap(char *swapFLag) {
  if (swapFLag == NULL || !((strcmp(swapFLag, SWAP_FLAG) == 0) ||
                            (strcmp(swapFLag, KEEP_FLAG) == 0)))
    return 0;
  return 1;
}

int main(int argc, char *argv[]) {
  if (argc < ARG_LENGTH_OPTION_ONE) {
    return 1;
  } else if (!isValidFiles(argv[1], argv[2])) {
    return 1;
  }
  if (argc == ARG_LENGTH_OPTION_ONE) {
    copyFileWithFlags(argv[1], argv[2], NULL, NULL, KEEP_FLAG);
  } else if (argc == ARG_LENGTH_OPTION_TWO) {
    if (isValidFLags(argv[3], argv[4]))
      copyFileWithFlags(argv[1], argv[2], argv[3], argv[4], KEEP_FLAG);
  } else if (argc == ARG_LENGTH_OPTION_THREE) {
    if (isValidFLags(argv[3], argv[4])) {
      if (isValidSwap(argv[5]))
        copyFileWithFlags(argv[1], argv[2], argv[3], argv[4], argv[5]);
    }
  } else {
    return 1;
  }
  return 0;
}
