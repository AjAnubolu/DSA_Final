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

# Documentation
Structure will look like this: 
![Structure](./images/image.png)

## `ChessBoardGUI Class`

### Package
- Package: `ajay`

### Imports
- JavaFX libraries for GUI components
- Java IO and Net for handling images

### Attributes
- `board`: Represents the chess board
- `imagePath`: Path to the image assets
- `gridPane`: `GridPane` for the chess board layout
- `selectedSquare`: Currently selected square
- `selectedPiece`: Currently selected chess piece

### Constructor
- `ChessBoardGUI(board: Board)`
  - Initialize `board`, `selectedPiece`, `selectedSquare`, `gridPane`, `movesTable`, `movesList`

### Methods

#### `createContent()`: `Parent`
- Initialize `HBox` root
- Clear `gridPane` children
- Loop through a 8x8 grid
  - Create a `Button` for each square
  - Set 'Square' color based on position
  - If 'Square' has a 'Piece', set its image
  - Set action on square click to `handleSquareClick` method
- Add `gridPane` (and optionally `movesTable`) to root
- Return root

#### `getImageForPiece(piece: Piece)`: `Image`
- Find image URL using 'Piece' type and `imagePath`
- Return `Image` created from URL

#### `handleSquareClick(x: int, y: int)`: `void`
- If no 'Square' is selected
  - Select 'Square' and 'Piece'
  - Print selected 'Piece'
- Else
  - Try to move 'Piece'
  - Refresh GUI
  - Reset `selectedSquare` and `selectedPiece`

#### `updateGridPane()`: `void`
- Similar to `createContent` but updates existing `gridPane`

#### `refreshBoardGUI()`: `void`
- Call `updateGridPane` to refresh the GUI




## `Board Class`

### Package
- Package: `ajay`

### Attributes
- `board`: 2D Array of `Square` containing state of board
- `whiteCheckmate`: boolean if white king is in 'check'
- `blackCheckmate`: boolean if black king is in 'check'
- `stalemate`: boolean if game state is 'stalemate'
- `isWhiteTurn`: boolean (initially true, indicating it's White's turn, swaps after every `movePiece` call)

### Constructor
- `Board()`
  - Initialize the 8x8 `board` array with `Square` objects
  - Call `setupPieces()` to place chess pieces in their initial positions

### Methods

#### `isWhiteCheck()`: boolean
- Check if White is in check using `Piece` class method 'allowsWhiteCheck'

#### `isBlackCheck()`: boolean
- Check if Black is in check using `Piece` class method 'allowsBlackCheck'

#### `movePiece(startX: int, startY: int, endX: int, endY: int)`: boolean
- Validate move coordinates
- Get start and end `Square` objects
- Validate the moving piece
- Calculate valid moves for the piece
- If move is valid:
  - Execute special moves like castling or pawn promotion
  - Update squares with new piece positions
  - Check for check, checkmate, and stalemate conditions
  - Toggle `isWhiteTurn`
- Return true if move is made, false otherwise

#### `locateWhiteKing()`: int[]
- Return coordinates of the White King

#### `locateBlackKing()`: int[]
- Return coordinates of the Black King

#### `setupPieces()`
- Place chess pieces on the board in their initial positions

#### `getSquare(x: int, y: int)`: `Square`
- Return the `Square` object at the given coordinates

#### `botMove()`
- Execute a move for the bot based on greedy algorithm(assigning a point value to each piece) and an element of randomness so that top left piece is not always moved if values are equal

#### `evaluateMove(piece: Piece, move: int[], x: int, y: int)`: int
- Evaluate the given move's worth based on `getPieceValue`

#### `getPieceValue(piece: Piece)`: int
- Return a numeric value representing the piece's importance

#### `botMove2()`
- An alternate bot move strategy implementing similar greedy algorithm with randomness but also accounts for position, mobility, check, and checkmate


#### 'evaluateMove2'(piece: Piece, move: int[], x: int, y: int): int
- Another method for evaluating moves, including positional and mobility considerations using 'getPieceValue2', 'getPositionalValue', 'getMobilityValue', 'resultsInCheck', and 'resultsInCheckmate'

#### 'getPieceValue2'(piece: Piece): int
- Return a numeric value for a piece with different weightage than getPieceValue

#### 'getPositionalValue'(piece: Piece, x: int, y: int): int
- Calculate a value based on a piece's position on the board

#### 'getMobilityValue'(piece: Piece, board: Square[][], x: int, y: int): int
- Calculate a value based on how many moves a piece can make

#### 'resultsInCheck'(board: Square[][], x: int, y: int, newX: int, newY: int, piece: Piece): boolean
- Check if a move results in a check against the opponent using 'copyBoard' from 'Piece' class

#### 'resultsInCheckmate'(board: Square[][], x: int, y: int, newX: int, newY: int, piece: Piece): boolean
- Check if a move results in checkmate against the opponent using 'copyBoard' from 'Piece' class



## Acknowledgment
-Thank you to <ins>Ms. Shahin</ins> for teaching me how to code throughout high school!
  
### Libraries
- `java.util` and `JavaFX`
