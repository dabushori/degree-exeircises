/**********
*Ori Dabush
*212945760
*01 - CS
*ass04
**********/

#include <stdio.h>
#include <string.h> 
#include <math.h>

#define SIZE 8

// PGN characters
#define PAWN 'P'
#define ROOK 'R'
#define KNIGHT 'N'
#define BISHOP 'B'
#define QUEEN 'Q'
#define KING 'K'
#define CAPTURE 'x'
#define PROMOTION '='
#define CHECK '+'
#define MATE '#'
#define FIRST_COL_PGN 'a'
#define FIRST_ROW_PGN '1'

// FEN & Board characters
#define WHITE_PAWN 'P'
#define WHITE_ROOK 'R'
#define WHITE_KNIGHT 'N'
#define WHITE_BISHOP 'B'
#define WHITE_QUEEN 'Q'
#define WHITE_KING 'K'
#define BLACK_PAWN 'p'
#define BLACK_ROOK 'r'
#define BLACK_KNIGHT 'n'
#define BLACK_BISHOP 'b'
#define BLACK_QUEEN 'q'
#define BLACK_KING 'k'

// FEN separator for strtok()
#define SEP "/"

// Board characters
#define EMPTY ' '
#define FIRST_COL 'A'

//struct for the PGN code
typedef struct {
    char srcPiece, destPiece;
    int iSrc, jSrc, iDest, jDest;
    int isWhite, isCapture, isPromotion, isCheck, isMate, isLegal;
} Move;

//declaration of this function, which used in functions that was created before her
void changeBoard(char board[][SIZE], Move move);

/********************************************************************
*Function Name: isADigit
*Input: char src
*Output: int (0 or 1)
*Function Operation: this function checks if a char is a digit or not
********************************************************************/
int isADigit(char src) {
    if('0'<=src && src<='9') {
        return 1;
    }
    return 0;
}

/***************************************************************************************************
*Function Name: toDigit
*Input: char src
*Output: int (0-9)
*Function Operation: this function turns a char that is a digit into int with the value of the digit
***************************************************************************************************/
int toDigit(char src) {
    //const char that has the value of the difference between a digit and a character that represents it
    const char diff='0'-0;
    return src-diff;
}

/********************************************************************************************************
*Function Name: isAFENPiece
*Input: char src
*Output: int (0 or 1)
*Function Operation: this function checks if a char represent a FEN chess piece (q,Q,k,K,b,B,r,R,p,P,n,N)
********************************************************************************************************/
int isAFENPiece(char src) {
    switch(src) {
        case WHITE_PAWN:
        case WHITE_ROOK:
        case WHITE_KNIGHT:
        case WHITE_BISHOP:
        case WHITE_QUEEN:
        case WHITE_KING:
        case BLACK_PAWN:
        case BLACK_ROOK:
        case BLACK_KNIGHT:
        case BLACK_BISHOP:
        case BLACK_QUEEN:
        case BLACK_KING:    return 1;
        default:            return 0;
    }
}

/****************************************************************************************************
*Function Name: lineMaker
*Input: char src[], char dst[]
*Output: none
*Function Operation: this function gets one line in FEN and a destination string, and "translates"
*            the FEN code into a line on chess board (with spaces if there is no piece in the square)
****************************************************************************************************/
void lineMaker(char src[], char dst[]) {
    //counter the next index in the line that the function needs to enter a value to 
    int indxCounter=0;
    for(int i=0;i<SIZE;i++) {

        /*condition that checks if the line has ended. it is useful for cases of lines like "4B3", 
        when the number of characters in the FEN code is different than SIZE*/
        if(indxCounter>=SIZE) {
            break;
        }
        //condition that checks if the i-character in the FEN code of the line is a digit
        if(isADigit(src[i])) {
            //if it is, the function enters ' ' into the destination string in the proper index (indxCounter)
            for(int j=0;j<toDigit(src[i]);j++) {
                dst[indxCounter]=EMPTY;
                indxCounter++;
            }
        }
        //if it is not, the function checks if it is a chess piece
        else {
            if(isAFENPiece(src[i])) {
                //if it is, it enters the piece into the destination string in the proper index (indxCounter)
                dst[indxCounter]=src[i];
                indxCounter++;
            }
        }
    }
}

/**************************************************************************************************
*Function Name: createBoard
*Input: char board[][SIZE], char fen[]
*Output: none
*Function Operation: this function organize the FEN code in the board array like a real chess board
**************************************************************************************************/
void createBoard(char board[][SIZE], char fen[]) {
    char *line;
    //line pointer to use the strtok function in order to cut the parts of the FEN code that are between the /'s
    line=strtok(fen,SEP);
    int i=0;
    while(line!=NULL) {

        /*every iteration of the loop, the funtion takes one part of the FEN code that represents a line, and puts it
        "translated" in the proper line in the board*/
        lineMaker(line,board[i++]);
        //the line that continues the strtok function
        line=strtok(NULL,SEP);
    }
}

/**********************************************************************
*Function Name: printColumns
*Input: none
*Output: none
*Function Operation: this function prints a line of the columns letters
**********************************************************************/
void printColumns() {
    //a variable that represents the column and starts from 'A'
    char column = FIRST_COL;
    printf("* |");
    //for loop that prints the part of the letters in this line
    for (int i = 0; i < SIZE; i++) {
        if (i) {
            printf(" ");
        }
        printf("%c", column);
        column++;
    }
    printf("| *\n");
}

/********************************************************************************************************************
*Function Name: printSpacers
*Input: none
*Output: none
*Function Operation: this function prints the line that seperates the row of the columns letters and the board itself
********************************************************************************************************************/
void printSpacers() {
    printf("* -");
    for (int i = 0; i < SIZE; i++) {
        printf("--");
    }
    printf(" *\n");
}

/***************************************************************************
*Function Name: printRow
*Input: char row[], int rowIdx
*Output: none
*Function Operation: this function prints the row number rowIdx in the board
***************************************************************************/
void printRow(char row[], int rowIdx) {
    printf("%d |", rowIdx);
    //for loop that prints the pieces in the line that is printed
    for(int i=0;i<SIZE;i++) {
        printf("%c|",row[i]);
    }
    printf(" %d\n", rowIdx);
}

/****************************************************************************************
*Function Name: printBoard
*Input: char board[][SIZE]
*Output: none
*Function Operation: this function prints the board that she gets like a real chess board
****************************************************************************************/
void printBoard(char board[][SIZE]) {
    printColumns();
    printSpacers();
    //for loop that prints the rows of the board, starting from the last row
    for(int i=SIZE;i>0;i--) {
        printRow(board[SIZE-i],i);
    }
    printSpacers();
    printColumns();
}

/**************************************************************************************
*Function Name: isPGNPiece
*Input: char piece
*Output: int (0 or 1)
*Function Operation: this function checks if the char that had entered is a chess piece
**************************************************************************************/
int isPGNPiece(char piece) {
    switch (piece)
    {
        case PAWN:      
        case ROOK:      
        case KING:      
        case KNIGHT:    
        case BISHOP:    
        case QUEEN:     return 1;
        default:        return 0;
    }
}

/***************************************************************
*Function Name: fromPGNToFEN
*Input: char src, int isWhite
*Output: char
*Function Operation: this function turns a piece from PGN to FEN
***************************************************************/
char fromPGNToFEN(char src, int isWhite) {
    if(isWhite) {
        //if the piece is a white piece, it returns the capital letter of the piece
        return src;
    }
    //if the piece isn't white, it replace it with a small letter of the piece
    switch (src)
    {
        case PAWN:      return BLACK_PAWN;
        case ROOK:      return BLACK_ROOK;
        case KING:      return BLACK_KING;
        case KNIGHT:    return BLACK_KNIGHT;
        case BISHOP:    return BLACK_BISHOP;
        case QUEEN:     return BLACK_QUEEN;
        default:        return EMPTY;
    }
}

/***************************************************************
*Function Name: fromFENToPGN
*Input: char src
*Output: char
*Function Operation: this function turns a piece from FEN to PGN
***************************************************************/
char fromFENToPGN(char src) {
    switch(src) {
        case WHITE_PAWN:
        case BLACK_PAWN:    return PAWN;
        case WHITE_KNIGHT:
        case BLACK_KNIGHT:  return KNIGHT;
        case BLACK_QUEEN:
        case WHITE_QUEEN:   return QUEEN;
        case WHITE_ROOK:
        case BLACK_ROOK:    return ROOK;
        case WHITE_BISHOP:
        case BLACK_BISHOP:  return BISHOP;
        case WHITE_KING:
        case BLACK_KING:    return KING;
        default:            return 0;
    }
}

/******************************************************************
*Function Name: isAColumn
*Input: char src
*Output: int (0 or 1)
*Function Operation: this function checks if src represent a column
******************************************************************/
int isAColumn(char src) {
    if(FIRST_COL_PGN<=src && src<=(FIRST_COL_PGN+SIZE-1)) {
        return 1;
    }
    return 0;
}

/*******************************************************************************************
*Function Name: columnToIndex
*Input: char column
*Output: int
*Function Operation: this function turns a column to an index of the same column in an array
*******************************************************************************************/
int columnToIndex(char column) {
    const char diff=FIRST_COL_PGN-0;
    return column-diff;
}

/**************************************************************************************************************
*Function Name: indexToColumn
*Input: int index
*Output: char
*Function Operation: this function turns an index of a column in an array to the same column in the chess board 
**************************************************************************************************************/
char indexToColumn(int index) {
    const char diff=FIRST_COL_PGN-0;
    return index+diff;
}

/***************************************************************
*Function Name: isARow
*Input: char src
*Output: int (0 or 1)
*Function Operation: this function checks if src represent a row
***************************************************************/
int isARow(char src) {
    if(FIRST_ROW_PGN<=src && src<=(FIRST_ROW_PGN+SIZE-1)) {
        return 1;
    }
    return 0;
}

/*************************************************************************************
*Function Name: rowToIndex
*Input: char row
*Output: int
*Function Operation: this function turns a row to an index of the same row in an array
*************************************************************************************/
int rowToIndex(char row) {
    return SIZE-toDigit(row);
}

/********************************************************************************************************
*Function Name: indexToRow
*Input: int index
*Output: char
*Function Operation: this function turns an index of a row in an array to the same row in the chess board
********************************************************************************************************/
char indexToRow(int index) {
    const char diff='0'-0;
    return (SIZE-index)+diff;
}

/**************************************************************************
*Function Name: isOpponentPiece
*Input: char piece, int isWhite
*Output: int (0 or 1)
*Function Operation: this function checks if the piece is an opponent piece
**************************************************************************/
int isOpponentPiece(char piece, int isWhite) {
    //if it's the white's turn, it check if the piece is black
    if(isWhite) {
        switch(piece) {
            case BLACK_BISHOP:
            case BLACK_KING:
            case BLACK_KNIGHT:
            case BLACK_PAWN:
            case BLACK_QUEEN:
            case BLACK_ROOK:    return 1;
            default:            return 0;
        }
    }
    //if it's the black's turn, it check if the piece is white
    switch(piece) {
            case WHITE_BISHOP:
            case WHITE_KING:
            case WHITE_KNIGHT:
            case WHITE_PAWN:
            case WHITE_QUEEN:
            case WHITE_ROOK:    return 1;
            default:            return 0;
    }
}

/********************************************************************************
*Function Name: isEmpty
*Input: char piece
*Output: int (0 or 1)
*Function Operation: this function checks if a squarein the board is empty or not
********************************************************************************/
int isEmpty(char piece) {
    if(piece==EMPTY) {
        return 1;
    }
    return 0;
}

/****************************************************************
*Function Name: minNum
*Input: int x, int y
*Output: int 
*Function Operation: this function returns the minimum of x and y
****************************************************************/
int minNum(int x, int y) {
    if(x>y) {
        return y;
    }
    return x;
}

/****************************************************************
*Function Name: maxNum
*Input: int x, int y
*Output: int 
*Function Operation: this function returns the maximum of x and y
****************************************************************/
int maxNum(int x, int y) {
    if(x<y) {
        return y;
    }
    return x;
}


//functions that checks if a move is possible

/******************************************************************************************************************
*Function Name: isPawnMoveAvailable
*Input: Move move
*Output: int (0 or 1)
*Function Operation: this function checks if a pawn can go from the source square to the destination square of move
******************************************************************************************************************/
int isPawnMoveAvailable(Move move) {
    //checking the case of white pawn
    if(move.isWhite) {
        //checking the case of pawn captures another piece
        if(move.isCapture && move.iDest==move.iSrc-1 && (move.jDest==move.jSrc-1 || move.jDest==move.jSrc+1)) {
            return 1;
        }
        //checking the case when there isn't a capture
        if(!move.isCapture && move.jDest==move.jSrc) {
            //checking the case when the pawn is on the 2nd line and he can move 2 squares
            if(move.iSrc==SIZE-2 && move.iDest==move.iSrc-2) {
                return 1;
            }
            else {
                //checking the case of regular pawn movement
                if(move.iDest==move.iSrc-1) {
                    return 1;
                }
            }
        }
    }
    //checking the case of black pawn
    if(!move.isWhite) {
        //checking the case of pawn captures another piece
        if(move.isCapture && move.iDest==move.iSrc+1 && (move.jDest==move.jSrc-1 || move.jDest==move.jSrc+1)) {
            return 1;
        }
        //checking the case when there isn't a capture
        if(!move.isCapture && move.jDest==move.jSrc) {
            //checking the case when the pawn is on the 2nd line and he can move 2 squares
            if(move.iSrc==1 && move.iDest==move.iSrc+2) {
                return 1;
            }
            else {
                //checking the case of regular pawn movement
                if(move.iDest==move.iSrc+1) {
                    return 1;
                }
            }
        }
    }
    return 0;
}

/***********************************************************************
*Function Name: isPawnWayClear
*Input: char board[][SIZE], Move move
*Output: int (0 or 1)
*Function Operation: this function checks if the pawn's move is possible
***********************************************************************/
int isPawnWayClear(char board[][SIZE], Move move) {
    //checking if the move is available
    if(!isPawnMoveAvailable(move)) {
        return 0;
    }
    //checking the case of pawn capturing a piece that isn't an opponent piece
    if(move.isCapture && !isOpponentPiece(board[move.iDest][move.jDest],move.isWhite)) {
        return 0;
    }
    //checking if the pawn's way is clear
    if(abs(move.iSrc-move.iDest)==2 && (!isEmpty(board[(move.iSrc+move.iDest)/2][move.jDest]) || 
    !isEmpty(board[move.iDest][move.jDest]))) {
        return 0;
    }
    if(!move.isCapture && abs(move.iSrc-move.iDest)==1 && !isEmpty(board[move.iDest][move.jDest])) {
        return 0;
    }
    return 1;
}

/******************************************************************************************************************
*Function Name: isRookMoveAvailable
*Input: Move move
*Output: int (0 or 1)
*Function Operation: this function checks if a rook can go from the source square to the destination square of move
******************************************************************************************************************/
int isRookMoveAvailable(Move move) {
    if(move.jDest==move.jSrc || move.iDest==move.iSrc) {
        return 1;
    }
    return 0;
}

/***********************************************************************
*Function Name: isRookWayClear
*Input: char board[][SIZE], Move move
*Output: int (0 or 1)
*Function Operation: this function checks if the rook's move is possible
***********************************************************************/
int isRookWayClear(char board[][SIZE], Move move) {
    //checking if the move is available
    if(!isRookMoveAvailable(move)) {
        return 0;
    }
    //checking the destination square
    if(!isEmpty(board[move.iDest][move.jDest])) {
        //checking the case of rook capturing a piece that isn't an opponent piece
        if(move.isCapture && !isOpponentPiece(board[move.iDest][move.jDest],move.isWhite)) {
            return 0;
        }
        //checking the case of rook trying to reach a square that is not empty
        if(!move.isCapture) {
            return 0;
        }
    }
    else {
        //checking the case of rook that "captures" an empty square
        if(move.isCapture) {
            return 0;
        }
    }
    //checking if the rook's way is clear
    if(move.iSrc==move.iDest) {
        int thisLine=move.iSrc;
        for(int j=minNum(move.jSrc,move.jDest)+1;j<maxNum(move.jSrc,move.jDest);j++) { 
            if(!isEmpty(board[thisLine][j])) {
                return 0;
            }
        }
    }
    if(move.jSrc==move.jDest) {
        int thisCol=move.jSrc;
        for(int i=minNum(move.iSrc,move.iDest)+1;i<maxNum(move.iSrc,move.iDest);i++) { 
            if(!isEmpty(board[i][thisCol])) {
                return 0;
            }
        }
    }
    return 1;
}

/********************************************************************************************************************
*Function Name: isKnightMoveAvailable
*Input: Move move
*Output: int (0 or 1)
*Function Operation: this function checks if a knight can go from the source square to the destination square of move
********************************************************************************************************************/
int isKnightMoveAvailable(Move move) {
    if((abs(move.jDest-move.jSrc)==2 && abs(move.iDest-move.iSrc)==1) || (abs(move.jDest-move.jSrc)==1 &&
        abs(move.iDest-move.iSrc)==2)) {
        return 1;
    }
    return 0;
}

/*************************************************************************
*Function Name: isKnightWayClear
*Input: char board[][SIZE], Move move
*Output: int (0 or 1)
*Function Operation: this function checks if the knight's move is possible
*************************************************************************/
int isKnightWayClear(char board[][SIZE], Move move) {
    //checking if the move is available
    if(!isKnightMoveAvailable(move)) {
        return 0;
    }
    //checking if the knight tries to capture that isn't an opponent piece
    if(move.isCapture && !isOpponentPiece(board[move.iDest][move.jDest],move.isWhite)) {
        return 0;
    }
    //checking the case of knight trying to reach a square that is not empty
    if(!move.isCapture && !isEmpty(board[move.iDest][move.jDest])) {
        return 0;
    }
    return 1;
}

/********************************************************************************************************************
*Function Name: isBishopMoveAvailable
*Input: Move move
*Output: int (0 or 1)
*Function Operation: this function checks if a bishop can go from the source square to the destination square of move
********************************************************************************************************************/
int isBishopMoveAvailable(Move move) {
    if(abs(move.jDest-move.jSrc)==abs(move.iDest-move.iSrc)) {
        return 1;
    }
    return 0;
}

/*************************************************************************
*Function Name: isBishopWayClear
*Input: char board[][SIZE], Move move
*Output: int (0 or 1)
*Function Operation: this function checks if the knight's move is possible
*************************************************************************/
int isBishopWayClear(char board[][SIZE], Move move) {
    //checking if the move is available
    if(!isBishopMoveAvailable(move)) {
        return 0;
    }
    //checking if the bishop's way is clear
    if((move.iSrc<move.iDest && move.jSrc<move.jDest) || (move.iSrc>move.iDest && move.jSrc>move.jDest)) {
        for(int i=minNum(move.iSrc,move.iDest)+1,j=minNum(move.jSrc,move.jDest)+1;i<maxNum(move.iSrc,move.iDest);
            i++,j++) {
            if(!isEmpty(board[i][j])) {
                return 0;
            }
        }
    }
    if((move.iSrc<move.iDest && move.jSrc>move.jDest) || (move.iSrc>move.iDest && move.jSrc<move.jDest)) {
        for(int i=minNum(move.iSrc,move.iDest)+1,j=maxNum(move.jSrc,move.jDest)-1;i<maxNum(move.iSrc,move.iDest);
            i++,j--) {
            if(!isEmpty(board[i][j])) {
                return 0;
            }
        }
    }
    //checking the case of bishop trying to capture a piece that isn't an opponent piece
    if(move.isCapture && !isOpponentPiece(board[move.iDest][move.jDest],move.isWhite)) {
        return 0;
    }
    //checking the case of bishop trying to reach a square that is not empty
    if(!move.isCapture && !isEmpty(board[move.iDest][move.jDest])) {
        return 0;
    }
    return 1;
}

/************************************************************************
*Function Name: isQueenWayClear
*Input: char board[][SIZE], Move move
*Output: int (0 or 1)
*Function Operation: this function checks if the queen's move is possible
************************************************************************/
int isQueenWayClear(char board[][SIZE], Move move) {
    //a queen can move like a rook or like a bishop. if it moves like a rook, it checks it like a rook
    if(move.iSrc==move.iDest || move.jSrc==move.jDest) {
        return isRookWayClear(board,move);
    }
    
    /*if it moves like a bishop it checks it like a bishop. if it doesn't move like rook or bishop, 
    it still checks it like a bishop and returns 0 in the check*/
    return isBishopWayClear(board,move);
}

/******************************************************************************************************************
*Function Name: isKingMoveAvailable
*Input: Move move
*Output: int (0 or 1)
*Function Operation: this function checks if a king can go from the source square to the destination square of move
******************************************************************************************************************/
int isKingMoveAvailable(Move move) {
    if((abs(move.jDest-move.jSrc)==0 || abs(move.jDest-move.jSrc)==1) && (abs(move.iDest-move.iSrc)==0 ||
    abs(move.iDest-move.iSrc)==1)) {
        return 1;
    }
    return 0;
}

/***********************************************************************
*Function Name: isKingWayClear
*Input: char board[][SIZE], Move move
*Output: int (0 or 1)
*Function Operation: this function checks if the king's move is possible
***********************************************************************/
int isKingWayClear(char board[][SIZE], Move move) {
    //checking if the move is available
    if(!isKingMoveAvailable(move)) {
        return 0;
    }
    //checking the case of king trying to capture a piece that isn't an opponent piece 
    if(move.isCapture && !isOpponentPiece(board[move.iDest][move.jDest], move.isWhite)) {
        return 0;
    }
    //checking the case of king trying to reach a square that is not empty
    if(!move.isCapture && !isEmpty(board[move.iDest][move.jDest])) {
        return 0;
    }
    return 1;
}

/************************************************************************
*Function Name: isMovePossible
*Input: char board[][SIZE], Move move
*Output: int (0 or 1)
*Function Operation: this function checks if the piece's move is possible
************************************************************************/
int isMovePossible(char board[][SIZE], Move move) {
    switch(move.srcPiece) {
        case PAWN:      return isPawnWayClear(board, move);
        case ROOK:      return isRookWayClear(board, move);
        case KING:      return isKingWayClear(board, move);
        case KNIGHT:    return isKnightWayClear(board, move);
        case BISHOP:    return isBishopWayClear(board, move);
        case QUEEN:     return isQueenWayClear(board, move);
        default:        return 0;
    }
}


//functions that finds pieces in the board

/**********************************************************************************
*Function Name: findPieceI
*Input: char board[][SIZE], char piece
*Output: int 
*Function Operation: this function finds the i coordinate of the piece in the board
**********************************************************************************/
int findPieceI(char board[][SIZE], char piece) {
    for(int i=0;i<SIZE;i++) {
        for(int j=0;j<SIZE;j++) {
            if(board[i][j]==piece) {
                return i;
            }
        }
    }
    //a case that the piece is not in the board
    return -1;
}

/**********************************************************************************
*Function Name: findPieceJ
*Input: char board[][SIZE], char piece
*Output: int
*Function Operation: this function finds the j coordinate of the piece in the board
**********************************************************************************/
int findPieceJ(char board[][SIZE], char piece) {
    for(int i=0;i<SIZE;i++) {
        for(int j=0;j<SIZE;j++) {
            if(board[i][j]==piece) {
                return j;
            }
        }
    }
    //a case that the piece is not in the board
    return -1;
}


//functions that check the check situation

/*****************************************************************************
*Function Name: isCheckOnWhite
*Input: char board[][SIZE]
*Output: int (0 or 1)
*Function Operation: this function checks if there's a check on the white king
*****************************************************************************/
int isCheckOnWhite(char board[][SIZE]) {
    //creating a move, and putting in the destination the coordinates of the white king
    Move temp;
    temp.iDest=findPieceI(board, WHITE_KING);
    temp.jDest=findPieceJ(board, WHITE_KING);
    temp.isCapture=1;
    temp.isWhite=0;
    for(int i=0;i<SIZE;i++) {
        for(int j=0;j<SIZE;j++) {
            //checking if every black piece's move from it's square to the white king's square is possible
            if(isOpponentPiece(board[i][j],!temp.isWhite)) {
                temp.srcPiece=fromFENToPGN(board[i][j]);
                temp.iSrc=i;
                temp.jSrc=j;
                if(isMovePossible(board,temp)) {
                    return 1;
                }
            }
        }
    }
    return 0;
}

/*****************************************************************************
*Function Name: isCheckOnBlack
*Input: char board[][SIZE]
*Output: int (0 or 1)
*Function Operation: this function checks if there's a check on the black king
*****************************************************************************/
int isCheckOnBlack(char board[][SIZE]) {
    //creating a move, and putting in the destination the coordinates of the black king
    Move temp;
    temp.iDest=findPieceI(board, BLACK_KING);
    temp.jDest=findPieceJ(board, BLACK_KING);
    temp.isCapture=1;
    temp.isWhite=1;
    for(int i=0;i<SIZE;i++) {
        for(int j=0;j<SIZE;j++) {
            //checking if every white piece's move from it's square to the black king's square is possible
            if(isOpponentPiece(board[i][j],!temp.isWhite)) {
                temp.srcPiece=fromFENToPGN(board[i][j]);
                temp.iSrc=i;
                temp.jSrc=j;
                if(isMovePossible(board,temp)) {
                    return 1;
                }
            }
        }
    }
    return 0;
}


//the function that checks if the move is valid

/***************************************************************************************
*Function Name: isMoveValid
*Input: char board[][SIZE], Move move
*Output: int (0 or 1)
*Function Operation: this function checks if the move is valid, which means possible and
*                    don't create an unwanted check situation
***************************************************************************************/
int isMoveValid(char board[][SIZE], Move move) {
    //checking if the move is possible
    if(!isMovePossible(board,move)) {
        return 0;
    }
    //creating a temp board and copying the original board to it
    char temp[SIZE][SIZE];
    for(int i=0;i<SIZE;i++) {
        strcpy(temp[i],board[i]);
    }
    //changing the temp board by the move
    changeBoard(temp, move);
    //checking that the move doesn't cause a check on the piece's color king
    if(move.isWhite && isCheckOnWhite(temp)) {
        return 0;
    }
    if(!move.isWhite && isCheckOnBlack(temp)) {
        return 0;
    }
    //checking that if there's a '+' in the pgn code, there is really a check on the opponent's king
    if(move.isCheck) {
        if(move.isWhite && !isCheckOnBlack(temp)) {
            return 0;
        }
        if(!move.isWhite && !isCheckOnWhite(temp)) {
            return 0;
        }
    }
    return 1;
}

//functions that finds the source coordinate

/***************************************************************************************************************
*Function Name: findIAndJSrc
*Input: char board[][SIZE], Move* move
*Output: none
*Function Operation: this function finds and changes the source coordinates of the piece that did the given move
***************************************************************************************************************/
void findIAndJSrc(char board[][SIZE], Move* move) {
    //creating a temp move with the same details of the original one except the source
    Move temp=*move;
    for(int i=0;i<SIZE;i++) {
        for(int j=0;j<SIZE;j++) {

            /*checking if the move from every square with the right colored piece is valid (doesn't create an unwanted
            check and so...). if it finds the right source, it changes the original move's source coordinates*/
            temp.iSrc=i;
            temp.jSrc=j;
            if(board[i][j]==fromPGNToFEN(move->srcPiece,move->isWhite) && isMoveValid(board, temp)) {
                move->iSrc=i;
                move->jSrc=j;
            }
        }
    }
}

/**********************************************************************************************
*Function Name: findISrcByJSrc
*Input: char board[][SIZE], Move* move
*Output: none
*Function Operation: this function finds and changes the I coordinate of the source square when
*                    the J coordinate is given
**********************************************************************************************/
void findISrcByJSrc(char board[][SIZE], Move* move) {
    //creating a temp move with the same details of the original one except the source
    Move temp=*move;
    for(int i=0;i<SIZE;i++) {
        temp.iSrc=i;
        /*checking if the move from every square with the right colored piece in the J-column is valid. 
        if it finds the right source, it changes the original move's I source coordinates*/
        if(board[i][move->jSrc]==fromPGNToFEN(move->srcPiece,move->isWhite) && isMoveValid(board, temp)) {
            move->iSrc=i;
        }
    }
}

/************************************************************************************************************************
*Function Name: findJSrcByISrc
*Input: char board[][SIZE], Move* move
*Output: none
*Function Operation: this function finds and changes the J coordinate of the source square when the I coordinate is given
************************************************************************************************************************/
void findJSrcByISrc(char board[][SIZE], Move* move) {
    //creating a temp move with the same details of the original one except the source
    Move temp=*move;
    for(int j=0;j<SIZE;j++) {
        temp.jSrc=j;
        /*checking if the move from every square with the right colored piece in the I-row is valid. 
        if it finds the right source, it changes the original move's J source coordinates*/
        if(board[move->iSrc][j]==fromPGNToFEN(move->srcPiece,move->isWhite) && isMoveValid(board, temp)) {
            move->jSrc=j;
        }
    }
}


//functions that checks the details of the Move variable

/*********************************************************************************
*Function Name: findSrcPiece
*Input: char pgn[]
*Output: char
*Function Operation: this function checks what is the source piece in the PGN code
*********************************************************************************/
char findSrcPiece(char pgn[]) {
    //checking if the 1st character is a piece
    if(isPGNPiece(pgn[0])) {
        return pgn[0];
    }
    //if it isn't, it means that the source piece is pawn
    return PAWN;
}

/************************************************************************************
*Function Name: isCapture
*Input: char pgn[]
*Output: int (0 or 1)
*Function Operation: this function checks if there is a capture ('x' in the PGN code)
************************************************************************************/
int isCapture(char pgn[]) {
    for(int i=0;i<strlen(pgn);i++) {
        if(pgn[i]=='x') {
            return 1;
        }
    }
    return 0;
}

/*******************************************************************************************************************
*Function Name: isPromotion
*Input: char pgn[], Move* move
*Output: int (0 or 1)
*Function Operation: this function checks if there is a promotion ('=' in the PGN code). if there is, it changes the 
*                    destination piece to the piece that it turns into
*******************************************************************************************************************/
int isPromotion(char pgn[], Move* move) {
    for(int i=0;i<strlen(pgn);i++) {
        if(pgn[i]=='=') {
            //changing the destination piece to the character that is after the '='
            move->destPiece=pgn[i+1];
            return 1;
        }
    }
    //if there isn't a promotion, the destination piece doesn't change
    move->destPiece=move->srcPiece;
    return 0;
}

/**********************************************************************************
*Function Name: isCheck
*Input: char pgn[]
*Output: int (0 or 1)
*Function Operation: this function checks if there is a check ('+' in the PGN code)
**********************************************************************************/
int isCheck(char pgn[]) {
    for(int i=0;i<strlen(pgn);i++) {
        if(pgn[i]=='+') {
            return 1;
        }
    }
    return 0;
}

/*********************************************************************************
*Function Name: isMate
*Input: char pgn[]
*Output: int (0 or 1)
*Function Operation: this function checks if there is a mate ('#' in the PGN code)
*********************************************************************************/
int isMate(char pgn[]) {
    for(int i=0;i<strlen(pgn);i++) {
        if(pgn[i]=='#') {
            return 1;
        }
    }
    return 0;
}

/***************************************************************************************
*Function Name: findSrcAndDest
*Input: char board[][SIZE], char pgn[], Move* move
*Output: none
*Function Operation: this function finds and changes the source of the piece by the move
***************************************************************************************/
void findSrcAndDest(char board[][SIZE], char pgn[], Move* move) {
    int iStart, jStart, rowsCounter=0, columnsCounter=0;
    //finding the 1st row in the PGN code
    for(int i=0;i<strlen(pgn);i++) {
        if(isARow(pgn[i])) {
            //changing the source i to the 1st row index that has found
            move->iSrc=rowToIndex(pgn[i]);
            //adding 1 to the counter that counts the number of rows that has found
            rowsCounter++;
            //start index for the next loop
            iStart=++i;
            break;
        }
    }
    //finding the 2nd row in the PGN code (if there is)
    for(int i=iStart;i<strlen(pgn);i++) {
        if(isARow(pgn[i])) {
            //changing the destination i to the 2nd row index that has found
            move->iDest=rowToIndex(pgn[i]);
            //adding 1 to the counter that counts the number of rows that has found
            rowsCounter++;
            break;
        }
    }
    //finding the 1st column in the PGN code
    for(int j=0;j<strlen(pgn);j++) {
        if(isAColumn(pgn[j])) {
            //changing the source j to the 1st column index that has found
            move->jSrc=columnToIndex(pgn[j]);
            //adding 1 to the counter that counts the number of columns that has found
            columnsCounter++;
            //start index for the next loop
            jStart=++j;
            break;
        }
    }
    //finding the 2nd column in the PGN code (if there is)
    for(int j=jStart;j<strlen(pgn);j++) {
        if(isAColumn(pgn[j])) {
            //changing the destination j to the 2nd column index that has found
            move->jDest=columnToIndex(pgn[j]);
            //adding 1 to the counter that counts the number of columns that has found
            columnsCounter++;
            break;
        }
    }
    //case that 2 rows and 1 column have found
    if(rowsCounter==2 && columnsCounter==1) {
        //changing the destination j to the column index that has found in the PGN code
        move->jDest=move->jSrc;
        //finding the jSrc with help of iSrc
        findJSrcByISrc(board, move);
    }
    //case that 1 row and 2 columns have found
    if(rowsCounter==1 && columnsCounter==2) {
        //changing the destination i to the row index that has found in the PGN code
        move->iDest=move->iSrc;
        //finding the iSrc with help of jSrc
        findISrcByJSrc(board, move);
    }
    //case that 1 row and 1 column have found
    if(rowsCounter==1 && columnsCounter==1) {
        //changing the destination i and j to the row and column index that has found in the PGN code
        move->iDest=move->iSrc;
        move->jDest=move->jSrc;
        //finding iSrc and jSrc without help :)
        findIAndJSrc(board, move);
    }
    if(move->iDest==move->iSrc && move->jDest==move->jSrc) {
        move->jDest=-1;
        move->iDest=-1;
    }
}

/*************************************************************************
*Function Name: findIsLegal
*Input: char board[][SIZE], Move* move
*Output: none
*Function Operation: this function checks and changes if the move is legal
*************************************************************************/
void findIsLegal(char board[][SIZE], Move* move) {
    //changing the isLegal variable of the move to 1 if the move is legal and 0 if it's not legal
    move->isLegal=isMoveValid(board,*move);
    //checking if it is a legal promotion
    if(move->isPromotion) {
        if(move->isWhite && move->iDest!=0) {
            move->isLegal=0;
        }
        if(!move->isWhite && move->iDest!=SIZE-1) {
            move->isLegal=0;
        }
    }
    //checking the case when the source wasn't found, which means the move isn't legal
    if(move->jDest==-1 || move->iDest==-1) {
        move->isLegal=0;
    }
}

/***************************************************************
*Function Name: changeBoard
*Input: char board[][SIZE], Move move
*Output: none
*Function Operation: this function changes the board by the move
***************************************************************/
void changeBoard(char board[][SIZE], Move move) {
    board[move.iDest][move.jDest]=fromPGNToFEN(move.destPiece,move.isWhite);
    board[move.iSrc][move.jSrc]=EMPTY;
}

/*****************************************************************************************************************
*Function Name: makeMove
*Input: char board[][SIZE], char pgn[], int isWhiteTurn
*Output: int (0 or 1)
*Function Operation: this function checks if a PGN code's move is legal, changes the board and returns 1 if it is,
*                    and returns 0 if it isn't legal
*****************************************************************************************************************/
int makeMove(char board[][SIZE], char pgn[], int isWhiteTurn) {
    //creating a Move variable for the given PGN code's move
    Move move;
    //setting the isWhite variable of the move
    move.isWhite=isWhiteTurn;
    //finding the source piece
    move.srcPiece=findSrcPiece(pgn);
    //setting the boolean details of the move
    move.isCapture=isCapture(pgn);
    move.isCheck=isCheck(pgn);
    move.isMate=isMate(pgn);
    move.isPromotion=isPromotion(pgn, &move);
    //setting the source coordinates of the move
    findSrcAndDest(board, pgn, &move);
    //setting the isLegal information of the move
    findIsLegal(board, &move);
    if(move.isLegal) {
        //changing the board and returning 1 if the move is legal
        changeBoard(board, move);
        return 1;
    }
    //returning 0 if the move isn't legal
    return 0;
}