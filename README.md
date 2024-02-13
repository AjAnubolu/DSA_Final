# JavaCheckmate Live by Ajay Anubolu

## Table of Contents
1. [Description](#description)
2. [Usage](#usage)
3. [Documentation](#documentation)
4. [Acknowledgment](#acknowledgment)

## Description
**JavaCheckmate Live** is a live chess board that enables two players to play a game of chess against each other. This project implements a standard 8x8 chessboard, following the standard rule set. Users can enter their moves using chess notation into a move list, and the board will update with each entry.

## Usage
To play a basic chess game, follow these steps:
1. Run the program.
2. Type an opening move, like e4.
3. Switch turns with your friend to input their moves.
4. Continue alternating turns until the game is complete.

Here is a link to the [rules of chess notation](https://www.chess.com/terms/chess-notation) for reference.

## Documentation
Structure will look like this: 
![Structure](./images/image.png)

# ChessBoardGUI Class Pseudocode

## Package
- Package: ajay

## Imports
- JavaFX libraries for GUI components
- Java IO and Net for handling images

## Class Definition: ChessBoardGUI
- Class: ChessBoardGUI

### Variables
- board: Represents the chess board
- imagePath: Path to the image assets
- gridPane: GridPane for the chess board layout
- selectedSquare: Currently selected square
- selectedPiece: Currently selected chess piece

### Constructor
- ChessBoardGUI(board: Board)
  - Initialize board, selectedPiece, selectedSquare, gridPane, movesTable, movesList

### Methods

#### createContent(): Parent
- Initialize HBox root
- Clear gridPane children
- Loop through a 8x8 grid
  - Create a Button for each square
  - Set square color based on position
  - If square has a piece, set its image
  - Set action on square click to handleSquareClick method
- Add gridPane (and optionally movesTable) to root
- Return root

#### getImageForPiece(piece: Piece): Image
- Find image URL using piece type and imagePath
- Return Image created from URL

#### handleSquareClick(x: int, y: int): void
- If no square is selected
  - Select square and piece
  - Print selected piece
- Else
  - Try to move piece
  - Refresh GUI
  - Reset selectedSquare and selectedPiece

#### updateGridPane(): void
- Similar to createContent but updates existing gridPane

#### refreshBoardGUI(): void
- Call updateGridPane to refresh the GUI

### `Piece` (Abstract Class)
- `color` (white or black)
- `position` (e.g., a2, g4)
- `availableMoves()`

### `Pawn` (Extends `ChessPiece`)
- `color`
- `position`
- `availableMoves()` (specific movement logic for Pawn)
- `setPosition(newPosition)` (if move is in availableMoves, move; else don't)

### `Knight` (Extends `ChessPiece`)
- `color`
- `position`
- `availableMoves()` (specific movement logic for Knight)
- `setPosition(newPosition)` (if move is in availableMoves, move; else don't)

### `Bishop` (Extends `ChessPiece`)
- `color`
- `position`
- `availableMoves()` (specific movement logic for Bishop)
- `setPosition(newPosition)` (if move is in availableMoves, move; else don't)

### `Rook` (Extends `ChessPiece`)
- `color`
- `position`
- `availableMoves()` (specific movement logic for Rook)
- `setPosition(newPosition)` (if move is in availableMoves, move; else don't)

### `Queen` (Extends `ChessPiece`)
- `color`
- `position`
- `availableMoves()` (specific movement logic for Queen)
- `setPosition(newPosition)` (if move is in availableMoves, move; else don't)

### `King` (Extends `ChessPiece`)
- `color`
- `position`
- `availableMoves()` (specific movement logic for King)
- `setPosition(newPosition)` (if move is in availableMoves, move; else don't)

### `Square`
- `piece` (piece type or null)

### `Board`
- `squares[8][8]` (2D array of squares representing the chessboard)
- `initializeBoard()` (place pieces in starting positions)
- `displayBoard()` (print the board to console or GUI)
- `isMoveValid(fromPosition, toPosition)`
  - (if piece is null or does not belong to the current player, return false)
  - (if toPosition is not within the piece's availableMoves(), return false)
  - (if the move puts the current player's king in check, return false)
- `setPosition(toPosition)` (return true if successful)
- `movePiece(fromPosition, toPosition)` (calls `isMoveValid`, checking if the move is valid and updating the position on the board if so)

### `ChessGame`
- `board` (instance of `Board`)
- `gameStatus` (active, checkmate, stalemate if player cannot move and is not in CHECK)
- `whiteStatus` (if neutral keep playing, if in check limit moves to stop check, if checkmate/stalemate change `gameStatus` appropriately and end game, printing result)
- `blackStatus` (if neutral keep playing, if in check limit moves to stop check, if checkmate/stalemate change `gameStatus` appropriately and end game, printing result)
- `updateGameStatus()`
- `initializeGame()`
  - (set `gameStatus` to Active, start prompting for move inputs, beginning from white)
- `promptMove` (ask the player for a move as input and update the piece's position, switching players afterwards if the game is still active)
- `switchPlayer()` (switch player's turn, will be intializing `int moveCount`, white will move on `moveCount%2==1`; else black will move)
- `playGame()`
  - (while `gameStatus` is active, display the board)
  - (prompt the current player for a move in chess notation)
    - (if the move is valid, move the piece on the board, switch players, and update the game status)
    - (else, prompt again)
-`parseChessNotation(notation)`
  - (convert notation, e.g., e3, to board positions)
-implement graphics using javaFX
### `ChessGameTester`
  -create instance of `chessGame` and start program
## Acknowledgment
### Libraries
- `java.util` and `JavaFX`
