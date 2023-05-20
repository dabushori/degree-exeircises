#include <stdint.h>
#include <stdio.h>
#include <string.h>

#define WIN_FLAG "-win"
#define MAC_FLAG "-mac"
#define UNIX_FLAG "-unix"
#define SWAP_FLAG "-swap"
#define KEEP_FLAG "-keep"

#define BYTE unsigned char

typedef union UTF16Data {
  BYTE bytesArray[2];
  uint16_t integer;
} UTF16Data;

typedef enum OS {
  NONE,
  WIN,
  MAC,
  UNIX,
} OS;

int isValidSrcFile(char *name) {
  FILE *src = fopen(name, "rb");
  if (src == NULL) {
    return 0;
  }
  fclose(src);
  return 1;
}

int isValidOsFlags(char *src, char *dst) {
  return (src == NULL && dst == NULL) ||
         ((strcmp(src, WIN_FLAG) == 0 || strcmp(src, MAC_FLAG) == 0 ||
           strcmp(src, UNIX_FLAG) == 0) &&
          (strcmp(dst, WIN_FLAG) == 0 || strcmp(dst, MAC_FLAG) == 0 ||
           strcmp(dst, UNIX_FLAG) == 0));
}

int isValidSwap(char *str) {
  return str == NULL || strcmp(str, SWAP_FLAG) == 0 ||
         strcmp(str, KEEP_FLAG) == 0;
  ;
}

int isUTF16FileBigEndian(BYTE firstByte) { return firstByte == 0xFE; }

OS getOsByFlag(char *flag) {
  if (flag == NULL) {
    return NONE;
  }
  if (!strcmp(flag, WIN_FLAG)) {
    return WIN;
  }
  if (!strcmp(flag, MAC_FLAG)) {
    return MAC;
  }
  if (!strcmp(flag, UNIX_FLAG)) {
    return UNIX;
  }
  return NONE;
}

// puts 0 if the size of the token is only 2 bytes
void getToken(OS os, UTF16Data *token, int isBigEndian) {
  if (isBigEndian) {
    switch (os) {
    case NONE:
      break;
    case WIN:
      token[0].bytesArray[0] = 0x00;
      token[0].bytesArray[1] = 0x0D;
      token[1].bytesArray[0] = 0x00;
      token[1].bytesArray[1] = 0x0A;
      break;
    case MAC:
      token[0].bytesArray[0] = 0x00;
      token[0].bytesArray[1] = 0x0D;
      token[1].integer = 0;
      break;
    case UNIX:
      token[0].bytesArray[0] = 0x00;
      token[0].bytesArray[1] = 0x0A;
      token[1].integer = 0;
      break;
    }
  } else {
    switch (os) {
    case NONE:
      break;
    case WIN:
      token[0].bytesArray[0] = 0x0D;
      token[0].bytesArray[1] = 0x00;
      token[1].bytesArray[0] = 0x0A;
      token[1].bytesArray[1] = 0x00;
      break;
    case MAC:
      token[0].bytesArray[0] = 0x0D;
      token[0].bytesArray[1] = 0x00;
      token[1].integer = 0;
      break;
    case UNIX:
      token[0].bytesArray[0] = 0x0A;
      token[0].bytesArray[1] = 0x00;
      token[1].integer = 0;
      break;
    }
  }
}

void writeTokenToFile(FILE *file, UTF16Data token[2]) {
  for (int i = 0; i < 2; i++) {
    if (token[i].integer != 0) {
      fwrite(token[i].bytesArray, sizeof(BYTE),
             sizeof(UTF16Data) / sizeof(BYTE), file);
    }
  }
}

int isTokenMatches(UTF16Data token[2], UTF16Data osToken[2]) {
  return token[0].integer == osToken[0].integer &&
         token[1].integer == osToken[1].integer;
}

void swap(UTF16Data *data) {
  BYTE tmp = data->bytesArray[0];
  data->bytesArray[0] = data->bytesArray[1];
  data->bytesArray[1] = tmp;
}

// 1 if good
int transferFile(char *srcFile, char *dstFile, char *sourceFileOsFlag,
                 char *destFileOsFlag, char *swapFlag) {
  FILE *src = fopen(srcFile, "rb");
  FILE *dst = fopen(dstFile, "wb");

  int shouldSwap = ((swapFlag == NULL) ? 0 : !strcmp(swapFlag, SWAP_FLAG));

  // printf("srcFile == %s\n", srcFile);
  // printf("dstFile == %s\n", dstFile);
  // printf("sourceFileOsFlag == %s\n", sourceFileOsFlag);
  // printf("newFileOsFlag == %s\n", destFileOsFlag);
  // printf("swap == %s\n", swapFlag);

  OS srcOs = getOsByFlag(sourceFileOsFlag), dstOs = getOsByFlag(destFileOsFlag);
  UTF16Data srcToken[2] = {}, dstToken[2] = {}, currToken[2] = {};

  UTF16Data current = {}, prev = {};
  size_t bytesRead = fread(current.bytesArray, sizeof(BYTE),
                           sizeof(current) / sizeof(BYTE), src);
  int isSrcBigEndian = isUTF16FileBigEndian(current.bytesArray[0]);

  // printf("two first bytes == %X %X\n", current.bytesArray[0],
  //        current.bytesArray[1]);

  getToken(srcOs, srcToken, isSrcBigEndian);
  getToken(dstOs, dstToken, shouldSwap ? !isSrcBigEndian : isSrcBigEndian);

  if (isTokenMatches(srcToken, dstToken)) {
    srcOs = NONE;
  }

  // printf("shouldSwap == %d\n", shouldSwap);
  // printf("srcOs == %d\n", srcOs);
  // printf("dstOs == %d\n", dstOs);
  // printf("src token: %X %X %X %X\n", srcToken[0].bytesArray[0],
  //        srcToken[0].bytesArray[1], srcToken[1].bytesArray[0],
  //        srcToken[1].bytesArray[1]);
  // printf("dst token: %X %X %X %X\n", dstToken[0].bytesArray[0],
  //        dstToken[0].bytesArray[1], dstToken[1].bytesArray[0],
  //        dstToken[1].bytesArray[1]);
  // printf("isSrcBigEndian == %d\n", isSrcBigEndian);

  int tokenFound = 0;
  while (bytesRead != 0) {
    prev = current;
    bytesRead = fread(current.bytesArray, sizeof(BYTE),
                      sizeof(current) / sizeof(BYTE), src);

    switch (srcOs) {
    case NONE:
      if (shouldSwap) {
        swap(&prev);
      }
      fwrite(prev.bytesArray, sizeof(BYTE), sizeof(UTF16Data) / sizeof(BYTE),
             dst);
      break;

    case WIN:
      if (bytesRead != 0) {
        currToken[0] = prev;
        currToken[1] = current;
        if (isTokenMatches(currToken, srcToken)) {
          tokenFound = 1;
          writeTokenToFile(dst, dstToken);
        } else if (!tokenFound) {
          if (shouldSwap) {
            swap(&prev);
          }
          fwrite(prev.bytesArray, sizeof(BYTE),
                 sizeof(UTF16Data) / sizeof(BYTE), dst);
        } else {
          tokenFound = 0;
        }
      } else {
        if (!tokenFound) {
          if (shouldSwap) {
            swap(&prev);
          }
          fwrite(prev.bytesArray, sizeof(BYTE),
                 sizeof(UTF16Data) / sizeof(BYTE), dst);
        }
      }
      break;

    case MAC:
      currToken[0] = prev;
      currToken[1].integer = 0;
      if (isTokenMatches(currToken, srcToken)) {
        writeTokenToFile(dst, dstToken);
      } else {
        if (shouldSwap) {
          swap(&prev);
        }
        fwrite(prev.bytesArray, sizeof(BYTE), sizeof(UTF16Data) / sizeof(BYTE),
               dst);
      }
      break;

    case UNIX:
      currToken[0] = prev;
      currToken[1].integer = 0;
      if (isTokenMatches(currToken, srcToken)) {
        writeTokenToFile(dst, dstToken);
      } else {
        if (shouldSwap) {
          swap(&prev);
        }
        fwrite(prev.bytesArray, sizeof(BYTE), sizeof(UTF16Data) / sizeof(BYTE),
               dst);
      }
      break;
    }
  }

  fclose(src);
  fclose(dst);
  return 1;
}

int main(int argc, char *argv[]) {
  char *srcFile = NULL, *dstFile = NULL, *sourceFileOsFlag = NULL,
       *newFileOsFlag = NULL, *swap = NULL;
  switch (argc) {
  case 6:
    swap = argv[5];
  case 5:
    sourceFileOsFlag = argv[3];
    newFileOsFlag = argv[4];
  case 3:
    srcFile = argv[1];
    dstFile = argv[2];
    break;
  default:
    return 1;
  }

  // printf("isValidSrcFile(srcFile) == %d\n", isValidSrcFile(srcFile));
  // printf("isValidOsFlags(sourceFileOsFlag, newFileOsFlag) == %d\n",
  //        isValidOsFlags(sourceFileOsFlag, newFileOsFlag));
  // printf("isValidSwap(swap) == %d\n", isValidSwap(swap));

  int isValid = isValidSrcFile(srcFile) &&
                isValidOsFlags(sourceFileOsFlag, newFileOsFlag) &&
                isValidSwap(swap);
  if (!isValid) {
    return 1;
  }

  return !transferFile(srcFile, dstFile, sourceFileOsFlag, newFileOsFlag, swap);
}