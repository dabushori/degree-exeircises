// 212945760 Ori Dabush

#include <stdint.h>
#include <stdio.h>
#include <string.h>

// Some defines for the different flags
#define WIN_FLAG "-win"
#define MAC_FLAG "-mac"
#define UNIX_FLAG "-unix"
#define SWAP_FLAG "-swap"
#define KEEP_FLAG "-keep"

// Byte variable for convenience reasons
#define BYTE unsigned char

// Union for a single UTF16 Data unit (assuming it is only 16bits)
typedef union UTF16Data {
  BYTE bytesArray[2];
  uint16_t integer;
} UTF16Data;

// Enum for the different operating systems
typedef enum OS {
  NONE,
  WIN,
  MAC,
  UNIX,
} OS;

/**
 * @brief Check if the given string is a valid file name
 *
 * @param name The given string
 * @return int 1 if it is a valid file name, 0 otherwise
 */
int isValidSrcFile(char *name) {
  FILE *src = fopen(name, "rb");
  if (src == NULL) {
    return 0;
  }
  fclose(src);
  return 1;
}

/**
 * @brief Check if the os flags are valid, which means they are both NULL
 * (both of them are not given) or they are both one of the following options:
 * "-win", "-mac", -"unix"
 *
 * @param src The source os parameter
 * @param dst The destination os parameter
 * @return int 1 if they are valid flags, 0 otherwise
 */
int isValidOsFlags(char *src, char *dst) {
  return (src == NULL && dst == NULL) ||
         ((strcmp(src, WIN_FLAG) == 0 || strcmp(src, MAC_FLAG) == 0 ||
           strcmp(src, UNIX_FLAG) == 0) &&
          (strcmp(dst, WIN_FLAG) == 0 || strcmp(dst, MAC_FLAG) == 0 ||
           strcmp(dst, UNIX_FLAG) == 0));
}

/**
 * @brief Check if the swap flag is valid, which means that it is "-swap" or
 * "-keep" or NULL (not given)
 *
 * @param str The swap parameter
 * @return int 1 if it is valid, 0 otherwise
 */
int isValidSwap(char *str) {
  return str == NULL || strcmp(str, SWAP_FLAG) == 0 ||
         strcmp(str, KEEP_FLAG) == 0;
  ;
}

/**
 * @brief Check if a UTF-16 file is written in big-endian by its first byte
 * (assuming BOM in the start of the file). If the file is written in
 * big-endian, its BOM should look like FE FF, so its first byte should be equal
 * to 0xFE.
 *
 * @param firstByte The first byte of the file
 * @return int 1 if it is written in big endian, 0 otherwise.
 */
int isUTF16FileBigEndian(BYTE firstByte) { return firstByte == 0xFE; }

/**
 * @brief Get the os by the flag (for example "-win" will get WIN's value in the
 * OS enum)
 *
 * @param flag The os flag
 * @return OS the os value in the OS enum (NONE if the flag is NULL or anything
 * else)
 */
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
/**
 * @brief Get the line ending token for a given os. For convenience reasons I
 * used a 2 UTF16Data array (which is 4 bytes) to store the token (I did it
 * because the line ending token in windows contains 2 characters so it takes 4
 * bytes to store it). If the line ending token only takes 2 bytes, I stored 0
 * in the other 2 bytes.
 *
 * @param os The os that we want its line ending token
 * @param token The array that the token is going to be written to
 * @param isBigEndian Is the given os is using big or little endian
 */
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

/**
 * @brief Write the line ending token to a given file (it needs a function to
 * ignore the zero bytes of the os with the 2 bytes line ending token).
 *
 * @param file The file to write to
 * @param token The token to be written
 */
void writeTokenToFile(FILE *file, UTF16Data token[2]) {
  for (int i = 0; i < 2; i++) {
    if (token[i].integer != 0) {
      fwrite(token[i].bytesArray, sizeof(BYTE),
             sizeof(UTF16Data) / sizeof(BYTE), file);
    }
  }
}

/**
 * @brief Check if 2 token are equal (just comparing the arrays).
 *
 * @param token1 the first token
 * @param token2 the second token
 * @return int 1 if they are equal, 0 otherwise
 */
int isTokenMatches(UTF16Data token1[2], UTF16Data token2[2]) {
  return token1[0].integer == token2[0].integer &&
         token1[1].integer == token2[1].integer;
}

/**
 * @brief Swap the bytes of a UTF-16 character (used if the endians is being
 * changed).
 *
 * @param data the UTF16Data that its bytes is going to be swapped
 */
void swap(UTF16Data *data) {
  BYTE tmp = data->bytesArray[0];
  data->bytesArray[0] = data->bytesArray[1];
  data->bytesArray[1] = tmp;
}

// 1 if good

/**
 * @brief Change the file's line feed token and endianess by the given flags and
 * write the new file to the destination file. There can be a few cases:
 * 1) If only the files' names are given, the file should be copied without any
 * changes.
 * 2) If the names and the os flags are given (without the swap flag)
 * the os need to be changed by the given flags, but the endianess doesn't need
 * to be changed.
 * 3) If all the parameters are given, the os and endianess need
 * to be changed by the given flags.
 * os flags - "-win", "-mac", "-unix"
 * endianess flags - "-swap", "-keep"
 *
 * @param srcFile the source file name
 * @param dstFile the destination file name
 * @param sourceFileOsFlag the os flag of the src file
 * @param destFileOsFlag the os flag of the destination file
 * @param swapFlag the swap flag
 * @return int 1 if eveything is valid and good, 0 if there's a problem in the
 * flags or in anything else.
 */
int transferFile(char *srcFile, char *dstFile, char *sourceFileOsFlag,
                 char *destFileOsFlag, char *swapFlag) {
  // open the files
  FILE *src = fopen(srcFile, "rb");
  FILE *dst = fopen(dstFile, "wb");

  if (src == NULL || dst == NULL) {
    return 0;
  }

  // check if we should swap
  int shouldSwap = ((swapFlag == NULL) ? 0 : !strcmp(swapFlag, SWAP_FLAG));

  // get the os details (enum OS values)
  OS srcOs = getOsByFlag(sourceFileOsFlag), dstOs = getOsByFlag(destFileOsFlag);
  UTF16Data srcToken[2] = {}, dstToken[2] = {}, currToken[2] = {};

  UTF16Data current = {}, prev = {};

  // checking the endianess of the file by its BOM
  size_t bytesRead = fread(current.bytesArray, sizeof(BYTE),
                           sizeof(current) / sizeof(BYTE), src);
  int isSrcBigEndian = isUTF16FileBigEndian(current.bytesArray[0]);

  // get the os line ending tokens
  getToken(srcOs, srcToken, isSrcBigEndian);
  getToken(dstOs, dstToken, shouldSwap ? !isSrcBigEndian : isSrcBigEndian);

  // An optimization so the program won't search for line ending tokens if they
  // don't need to be changed.
  if (srcOs != NONE && isTokenMatches(srcToken, dstToken)) {
    srcOs = NONE;
  }

  int tokenFound = 0;
  while (bytesRead != 0) {
    // read the next UTF-16 character and save the previous one
    prev = current;
    bytesRead = fread(current.bytesArray, sizeof(BYTE),
                      sizeof(current) / sizeof(BYTE), src);

    // dividing into different os
    switch (srcOs) {
    // when the file only needs to be copied
    case NONE:
      if (shouldSwap) {
        swap(&prev);
      }
      fwrite(prev.bytesArray, sizeof(BYTE), sizeof(UTF16Data) / sizeof(BYTE),
             dst);
      break;

    // case of source os that is windows
    case WIN:
      if (bytesRead != 0) {
        currToken[0] = prev;
        currToken[1] = current;
        // check if the token that is created by the previous and the current
        // UTF-16 character is the line ending token
        if (isTokenMatches(currToken, srcToken)) {
          tokenFound = 1;
          writeTokenToFile(dst, dstToken);
        } else if (!tokenFound) {
          // writing the previous character to the file if the token wasn't
          // found in the last iteration (if it was found it means that the
          // privous character was already written so nothing should be done in
          // the current iteration)
          if (shouldSwap) {
            swap(&prev);
          }
          fwrite(prev.bytesArray, sizeof(BYTE),
                 sizeof(UTF16Data) / sizeof(BYTE), dst);
        } else {
          // if the token was found, do nothing (because the previous character
          // was already written) and turn off the tokenFound flag
          tokenFound = 0;
        }
      } else {
        // a case when no bytes are read to the current character but the
        // previous character wasn't written
        if (!tokenFound) {
          if (shouldSwap) {
            swap(&prev);
          }
          fwrite(prev.bytesArray, sizeof(BYTE),
                 sizeof(UTF16Data) / sizeof(BYTE), dst);
        }
      }
      break;

    // case of source os that is mac
    case MAC:
      currToken[0] = prev;
      currToken[1].integer = 0;

      // check if the token that is created by the urrent UTF-16 character is
      // the line ending token (the 0 in the second element is because of the
      // mac token is only one character)
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

    // case of source os that is unix
    case UNIX:
      currToken[0] = prev;
      currToken[1].integer = 0;

      // check if the token that is created by the urrent UTF-16 character is
      // the line ending token (the 0 in the second element is because of the
      // mac token is only one character)
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

  // close the file streams
  fclose(src);
  fclose(dst);

  // return the good value :)
  return 1;
}

int start(int argc, char *argv[]) {
  char *srcFile = NULL, *dstFile = NULL, *sourceFileOsFlag = NULL,
       *newFileOsFlag = NULL, *swap = NULL;

  // parsing the arguments. The arguments should be in that order:
  // ./a.out srcFile dstFile srcOsFlag dstOsFlag swapFlag
  // argc can be 3 if only the files' names are given, 5 if also the os flags
  // are given and 6 if the swap flag is also given.
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
    // if argc is not 3, 5 or 6 return 1 - bad value :(
    return 1;
  }

  // checking the propriety of the given flags
  int isValid = isValidSrcFile(srcFile) &&
                isValidOsFlags(sourceFileOsFlag, newFileOsFlag) &&
                isValidSwap(swap);
  if (!isValid) {
    // returning bad value if the arguments are not good :(
    return 1;
  }

  // return 0 if everything is good :) (the function returns 1 if eveything is
  // good), and 1 if not everything is good :(
  return !transferFile(srcFile, dstFile, sourceFileOsFlag, newFileOsFlag, swap);
}
