# JavaCheckmate Live by Ajay Anubolu

## Table of Contents
1. [Description](#description)
2. [Usage](#usage)
3. [Documentation](#documentation)
4. [Acknowledgment](#acknowledgment)

## Description
**JavaCheckmate Live** is a live chess board that enables two players to play a game of chess against each other. This project implements a standard 8x8 chessboard, following the [standard rule set](https://www.fide.com/FIDE/handbook/LawsOfChess.pdf). Users can click the piece they want to move and select the end square to move a piece, and the board will update with each entry.

## Usage

To edit, clone the repository:
```bash
git clone https://github.com/AjAnubolu/DSA_Final.git
```
Open the code with the IDE of your choice. Add JavaFX as an [external library](https://openjfx.io/).

To play a basic chess game player vs player, follow these steps:
1. Run the program.
2. Type an opening move, like e4.
3. Switch turns with your friend to input their moves.
4. Continue alternating turns until the game is complete.

To play against the bot, delete the comments around the if statement at the end of the 'movePiece' method in the 'Board' class:
<pre>
if(!isWhiteTurn) {
    botMove();
}
</pre>
Change 'botMove' to 'botMove2' if you want to play against the stronger bot.


# Documentation
Structure will look like this: 
![Structure](./images/chessStructure.png)


## `ChessGame` Class

### Class Definition: `ChessGame`
Serves as the main for the chess game application

### Package
- **Package**: `ajay`

### Dependencies
- Utilizes **JavaFX application** framework to create and manage the graphical user interface.
- **JavaFX scene** and **stage** components are specifically imported to facilitate the GUI setup.

### Main Method
- **`main(String[] args)`**
  - The starting point of the application.
  - Invokes `launch(args)`, a static method from the `Application` class, to launch the JavaFX application. This method internally calls the `start` method, passing in the primary stage.

### `start(Stage primaryStage)`
- Initializes the game's primary UI components and displays the main game window.
- **Steps**:
  - **Board Initialization**: Creates an instance of the `Board` class to manage the game's logical state.
  - **GUI Setup**: Instantiates `ChessBoardGUI` with the `board` instance, setting up the graphical representation of the chess game.
  - **Scene Creation**: Constructs a `Scene` by calling `chessBoardGUI.createContent()`, which prepares the content for display.
  - **Stage Configuration**: Sets the window (stage) title to "Chess Game", assigns the created scene to the primary stage, and displays the stage.

### Additional Notes
-To run the chess game, execute the `main` method. The JavaFX framework handles window management and event loops
- **GUI-Centric Design**: `ChessGame` focuses on setting up and launching the graphical interface for the chess game, relying on JavaFX for window management and event handling. The game logic is handled separately within the `Board` and related classes. Separation of the graphical interface (`ChessBoardGUI`) from the game logic (`Board`) is emphasized
- **Extensibility**: The structure allows for easy extension or modification, such as adding menu options, implementing additional features like saving/loading games, or integrating network play.


## `ChessBoardGUI` Class

### Class Definition: `ChessBoardGUI`
- The `ChessBoardGUI` class is designed to bridge the game logic encapsulated by the `Board` class with a user-friendly graphical interface. Manages the display of the chessboard and pieces along with the interaction logic, enabling players to select and move pieces.
- 
### Package
- Package: `ajay`

### Imports
- Utilizes **JavaFX libraries** for GUI components, essential for creating and managing the graphical user interface.
- Uses **Java IO and Net** for handling images, enabling the chess pieces to be visually represented with icons.

### Fields
- `board`: A reference to the `Board` class instance, representing the game's logic and state.
- `imagePath`: String path to the directory containing image assets for the chess pieces.
- `gridPane`: A `GridPane` used to layout the chessboard's squares in a grid format.
- `selectedSquare`: Tracks the currently selected `Square` on the chessboard, if any.
- `selectedPiece`: Holds the currently selected `Piece`, facilitating move operations.

### Constructor
- **`ChessBoardGUI(board: Board)`**
  - Initializes the GUI with references to the game `board`, setting up initial values for `selectedPiece`, `selectedSquare`, and preparing the `gridPane`.
  - Optionally initializes other GUI components like `movesTable` and `movesList`, if tracking of moves is required.

### Methods

#### `createContent()`: `Parent`
- Constructs the primary content of the GUI.
- Initializes an `HBox` or similar container as the root.
- Clears any existing children from the `gridPane`.
- Populates the `gridPane` with buttons representing chessboard squares, dynamically setting their color and attaching event handlers for click actions.
  - Each square/button is assigned a color based on its position to achieve the checkered pattern.
  - If a square contains a piece, its corresponding image is set using `getImageForPiece`.
- Adds the `gridPane` to the root container, along with any other components like a `movesTable`, if utilized.
- Returns the root `Parent` object for display.

#### `getImageForPiece(piece: Piece)`: `Image`
- Determines the correct image URL for a given `Piece`, using its type and the `imagePath`.
- Attempts to load and return the `Image` from the URL, handling any potential IO exceptions.

#### `handleSquareClick(x: int, y: int)`: `void`
- Handles user interaction with the chessboard's squares.
- If no square is currently selected, selects the square and potentially the piece on that square.
- If a square is already selected, attempts to move the selected piece to the new square, according to chess rules.
- Updates the GUI to reflect any changes, such as moving a piece and capturing an opponent's piece.
- Resets `selectedSquare` and `selectedPiece` after the move attempt.

#### `updateGridPane()`: `void`
- Refreshes the `gridPane` to reflect the current state of the `board`.
- Similar to `createContent` but specifically focused on updating the grid's children to match any changes in piece positions or selections.

#### `refreshBoardGUI()`: `void`
- Invokes `updateGridPane`, along with any additional necessary updates to the GUI, ensuring the visual representation is in sync with the game's current state.

### Additional Notes
- 
- This pseudocode aims to provide a foundation for implementing a chess game GUI using JavaFX, with considerations for extensibility and maintainability.





## `Board Class`

### Class Definition: `Board`
- The `Board` class is central to managing the game logic of chess, handling move validation, game state updates, and bot decision-making. Manages the state of the chessboard, including piece positions, turn management, and game state conditions (check, checkmate, stalemate)

### Package
- Package: `ajay`

### Fields
- `board`: A 2D array of `Square`, representing the chessboard. Each `Square` may contain a `Piece`.
- `whiteCheckmate`: Boolean flag indicating if the white king is in checkmate.
- `blackCheckmate`: Boolean flag indicating if the black king is in checkmate.
- `stalemate`: Boolean flag indicating if the game is in a stalemate, where the current player has no legal move but is not in check.
- `isWhiteTurn`: Boolean indicating if it is White's turn (`true`) or Black's turn (`false`).

### Constructor
- `Board()`
  - Initializes the 8x8 `board` with `Square` objects, representing an empty chessboard.
  - Calls `setupPieces()` to place all chess pieces in their standard starting positions.

### Methods

#### `setupPieces()`
- Places chess pieces for both White and Black players in their standard initial positions on the `board`.

#### `movePiece(startX, startY, endX, endY)`: Boolean
- Attempts to move a piece from `(startX, startY)` to `(endX, endY)`.
- Validates the move based on chess rules, including checking if the path is valid for the specific piece and if the destination is not occupied by a friendly piece.
- Handles special moves like castling and pawn promotion when conditions are met.
- Updates the piece's position on the `board` if the move is valid.
- After the move, checks for checks, checkmates, or stalemate conditions and updates relevant flags.
- Toggles `isWhiteTurn` to switch turns between players.
- Returns `true` if the move was successfully executed, otherwise `false`.

#### `isWhiteCheck()`, `isBlackCheck()`: Boolean
- Determine if the white or black king is in check, respectively. This involves scanning the `board` for potential attacks on the king by opponent pieces.

#### `locateWhiteKing()`, `locateBlackKing()`: int[]
- Finds and returns the coordinates `(x, y)` of the white or black king on the `board`.

#### `getSquare(x, y)`: Square
- Returns the `Square` at the specified coordinates, facilitating access to the piece (if any) located there.

#### AI and Move Evaluation Methods

##### `botMove()`, `botMove2()`
- Simulate AI move decisions. `botMove()` implements a basic greedy algorithm, selecting moves based on piece value and a degree of randomness to avoid predictable behavior.
- `botMove2()` enhances the decision-making process by also considering positional values, mobility (the number of legal moves a piece has), and potential checks/checkmates, aiming for more strategic gameplay.

##### `evaluateMove(piece, move, x, y)`, `evaluateMove2(piece, move, x, y)`: int
- Calculate and return a score representing the value or quality of a given move, considering factors like captured piece value, strategic positioning, and game state implications (e.g., escaping check, putting the opponent in check).
- `evaluateMove2` provides a more complex evaluation, factoring in additional considerations like piece mobility and board control.

##### `getPieceValue(piece)`, `getPieceValue2(piece)`: int
- Return a numeric score representing the value of a piece, with `getPieceValue2` potentially applying different weights or considerations than `getPieceValue`.

##### `getPositionalValue(piece, x, y)`, `getMobilityValue(piece, board, x, y)`: int
- `getPositionalValue` computes a score based on a piece's strategic position on the board.
- `getMobilityValue` calculates a score based on the number of legal moves available for the piece, indicating its mobility.

##### `resultsInCheck(board, x, y, newX, newY, piece)`, `resultsInCheckmate(board, x, y, newX, newY, piece)`: Boolean
- Assess if executing a specific move results in placing the opponent's king in check (`resultsInCheck`) or checkmate (`resultsInCheckmate`).
- These methods simulate the move on a copy of the `board` to evaluate the resulting game state without affecting the actual game.


## `Piece` Enum 

### Enum Definition: `Piece`
Enumerates the different types of chess pieces, centralizing their identification within the game logic. Each enum value represents a unique chess piece, characterized by distinct movement capabilities and roles within the game. This design simplifies the process of implementing game rules and managing piece interactions.

### Package
- Package: `ajay`

### Imports
- Utilize `ArrayList` for dynamic arrays.
- Utilize `Arrays` for array manipulations.

#### Enum Values
Defines specific chess pieces with unique behaviors, particularly for pawns which have custom `getValidMovesIgnoringCheck` implementations:
- `WHITE_KING`, `WHITE_QUEEN`, etc., through `BLACK_PAWN`
  - Each piece is initialized without specific moves (null) and a flag indicating if it is a long-range mover (true for queen, rook, bishop).

### Fields
- `moves`: 2D array storing the potential moves a piece can make, represented as coordinate changes.
- `isLongRange`: Boolean flag indicating if a piece can move over multiple squares in a single move (e.g., rook, bishop, queen).

### Constructor
- `Piece(moves, isLongRange)`: Initializes the piece with its possible moves and range behavior.

### Methods

#### `copyBoard(board)`: Square[][]
- Creates a deep copy of the chess board, allowing simulation of moves for check detection.

#### `allowsWhiteCheck(board)`: Boolean
- Iterates over the entire board to determine if any black pieces move can capture the white king, indicating a check.

#### `allowsBlackCheck(board)`: Boolean
- Iterates over the entire board to determine if any white pieces move can capture the black king, indicating a check.

#### `getValidMovesWithCheck(board, rank, file, isWhiteTurn)`: int[][]
- Generates valid moves for a piece considering the current state of the board and filtering out moves that would leave or put the player's king in check using 'getvalidMovesIgnoringCheck' and 'copyBoard'.

#### `getValidMovesIgnoringCheck(board, rank, file)`: int[][]
- Calculates valid moves for a piece without considering whether such moves would result in a check using 'getValidMoves'.

##### Overridden Implementation for Pawns (`WHITE_PAWN`, `BLACK_PAWN`)
- `WHITE_PAWN`: Calculates forward movement and captures. Includes special moves like moving two squares forward from the starting position and capturing diagonally.
- `BLACK_PAWN`: Similar logic applied for black pawns but in the opposite direction.

#### `getValidMoves()`: int[][]
- Switch case logic to return an array of valid moves based on the piece type. Each piece type calls its respective method to generate its moves.

#### Detailed Move Generation Methods
- `getWhitePawnMoves`, `getBlackPawnMoves`: Returns moves for pawns, considering their unique forward movement and capture rules.
- `getKnightMoves`: Calculates L-shaped moves.
- `getBishopMoves`: Generates diagonal moves across the board.
- `getRookMoves`: Generates horizontal and vertical moves.
- `getQueenMoves`: Combines the moves of rook and bishop.
- `getKingMoves`: Generates one-square moves in all directions and includes castling under specific conditions.

#### Utility Methods
- `toString()`: Converts the enum name into a formatted string representing the piece's type.
- `isKing()`: Checks if the piece is a king.
- `isWhite()`: Determines if the piece is white based on its enum name.
- `isLongRange()`: Returns true if the piece is capable of long-range movement.

## `Square` Class 

### Package
- Package: `ajay`

### Class Definition: `Square`
Represents a single square on the chessboard. Holds information about any chess piece that occupies it ('null' if none) and its coordinates on the board.

### Fields
- `piece`: A `Piece` object that resides on this square. It's `null` if the square is empty.
- `x`: Integer representing the x-coordinate of the square on the chessboard.
- `y`: Integer representing the y-coordinate of the square on the chessboard.

### Constructor
- `Square(int x, int y)`
  - Initializes a `Square` instance with specified coordinates. Initially, no piece occupies the square (`piece` is set to `null`).

### Methods

#### `getPiece()`: `Piece`
- Returns the `Piece` currently occupying the square, or `null` if the square is empty.

#### `setPiece(Piece piece)`: `void`
- Assigns a `Piece` to the square. Use `null` to indicate the square is being emptied.

#### `isEmpty()`: `boolean`
- Checks if the square is empty (i.e., does not contain a piece).
- Returns `true` if there's no piece on the square, otherwise `false`.

#### `getX()`: `int`
- Returns the x-coordinate of the square, indicating its position on the chessboard horizontally.

#### `getY()`: `int`
- Returns the y-coordinate of the square, indicating its position on the chessboard vertically.

#### `clone()`: `Square`
- Creates and returns a deep copy of the `Square` instance.
- The cloned `Square` has the same coordinates and, if present, a reference to the same `Piece` object (note: this does not clone the `Piece` object itself, just copies the reference).

## Future Improvements

- Bot enhancements(addition of depth with treeset, more holistic view of board)
- moveList in GUI(in progres with chessMove class converting moves to [notation](https://www.chess.com/terms/chess-notation) and tableView in GUI class)

## Acknowledgment

Thank you to <ins>Ms. Shahin</ins> for teaching me how to code throughout high school!
  
### Libraries

- `JDK >= 17`
- `JavaFX`
