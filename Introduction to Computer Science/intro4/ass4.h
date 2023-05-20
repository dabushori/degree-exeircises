#ifndef __ASS4_H
#define __ASS4_H

//struct for the PGN code
typedef struct {
    char srcPiece, destPiece;
    int iSrc, jSrc, iDest, jDest;
    int isWhite, isCapture, isPromotion, isCheck, isMate, isLegal;
} Move;

int isADigit(char src);
int toDigit(char src);
int isAFENPiece(char src);
void lineMaker(char src[], char dst[]);
void createBoard(char board[][SIZE], char fen[]);
void printColumns();
void printSpacers();
void printRow(char row[], int rowIdx);
void printBoard(char board[][SIZE]);
int isPGNPiece(char piece);
char fromPGNToFEN(char src, int isWhite);
char fromFENToPGN(char src);
int isAColumn(char src);
int columnToIndex(char column);
char indexToColumn(int index);
int isARow(char src);
int rowToIndex(char row);
char indexToRow(int index);
int isOpponentPiece(char piece, int isWhite);
int isEmpty(char piece);
int minNum(int x, int y);
int maxNum(int x, int y);
int isPawnMoveAvailable(Move move);
int isPawnWayClear(char board[][SIZE], Move move);
int isRookMoveAvailable(Move move);
int isRookWayClear(char board[][SIZE], Move move);
int isKnightMoveAvailable(Move move);
int isKnightWayClear(char board[][SIZE], Move move);
int isBishopMoveAvailable(Move move);
int isBishopWayClear(char board[][SIZE], Move move);
int isQueenWayClear(char board[][SIZE], Move move);
int isKingMoveAvailable(Move move);
int isKingWayClear(char board[][SIZE], Move move);
int isMovePossible(char board[][SIZE], Move move);
int findPieceI(char board[][SIZE], char piece);
int findPieceJ(char board[][SIZE], char piece);
int isCheckOnWhite(char board[][SIZE]);
int isCheckOnBlack(char board[][SIZE]);
int isMoveValid(char board[][SIZE], Move move);
void findIAndJSrc(char board[][SIZE], Move* move);
void findISrcByJSrc(char board[][SIZE], Move* move);
void findJSrcByISrc(char board[][SIZE], Move* move);
char findSrcPiece(char pgn[]);
int isCapture(char pgn[]);
int isPromotion(char pgn[], Move* move);
int isCheck(char pgn[]);
int isMate(char pgn[]);
void findSrcAndDest(char board[][SIZE], char pgn[], Move* move);
void findIsLegal(char board[][SIZE], Move* move);
void changeBoard(char board[][SIZE], Move move);
int makeMove(char board[][SIZE], char pgn[], int isWhiteTurn);

#endif