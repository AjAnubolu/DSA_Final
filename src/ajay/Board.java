/*
 * Name: Ajay Anubolu
 * Date: 2/14/2024
 * Data Structures and Algorithms Final Project
 */

package ajay;



public class Board {
    private Square[][] board; // Represents the chessboard as a 2D array of Squares
    private boolean whiteCheckmate; // Flag to indicate if white is in checkmate
    private boolean blackCheckmate; // Flag to indicate if black is in checkmate
    private boolean stalemate; // Flag to indicate if the game is in stalemate
    //private ArrayList<ChessMove> moveList = new ArrayList<ChessMove>(); // List to track all moves made (currently commented out)
    public boolean isWhiteTurn = true; // Flag to track whose turn it is, true for white, false for black

    // Constructor to initialize the board
    public Board() {
        board = new Square[8][8]; // Initialize the board with 8x8 squares
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = new Square(i, j); // Initialize each square with its coordinates
            }
        }
        setupPieces(); // Set up pieces on the board
    }

    // Checks if white king is in check
    public boolean isWhiteCheck() {
        return Piece.allowsWhiteCheck(board);
    }

    // Checks if black king is in check
    public boolean isBlackCheck() {
        return Piece.allowsBlackCheck(board);
    }

    // Generates a notation for a move
    public String notation(Piece piece, int endX, int endY, boolean capture, boolean check, boolean checkmate) {
        ChessMove move = new ChessMove(piece, new int[]{endX, endY}, capture, check, checkmate);
        //moveList.add(move); // Add move to the move list (commented out)
        return move.toString(); // Return the move as a string
    }

    // Moves a piece from one square to another
   public boolean movePiece(int startX, int startY, int endX, int endY) {
       if (startX < 0 || startX >= 8 || startY < 0 || startY >= 8 || endX < 0 || endX >= 8 || endY < 0 || endY >= 8) {
           return false; 
       }
       
       Square startSquare = board[startX][startY];
       Square endSquare = board[endX][endY];
       Piece movingPiece = startSquare.getPiece();
       if (movingPiece == null || (isWhiteTurn != movingPiece.toString().startsWith("white"))) {
           return false;
       }
       int[][] validMoves = movingPiece.getValidMovesWithCheck(board, startX, startY, isWhiteTurn);
      
       boolean isValidMove = false;
      
       for (int[] move : validMoves) {
           int targetX = startX + move[0];
           int targetY = startY + move[1];
           if (targetX == endX && targetY == endY) {
               isValidMove = true;
               break;
           }
       }
       System.out.println("Attempting to move piece from (" + startX + ", " + startY + ") to (" + endX + ", " + endY + ")");
       if (!isValidMove) {
           System.out.println("Move invalid: No valid moves match.");
          
           return false;
       }
       // Handle special case for castling
       if(board[startX][startY].getPiece() == Piece.WHITE_KING){
    	   if(startY == endY-2) {
    		   board[0][7].setPiece(null);
    		   board[0][5].setPiece(Piece.WHITE_ROOK);
    	   }
    	   else if(startY == endY+2) {
    		   board[0][0].setPiece(null);
    		   board[0][3].setPiece(Piece.WHITE_ROOK);
    	   }
       }
       if(board[startX][startY].getPiece() == Piece.BLACK_KING){
    	   if(startY == endY-2) {
    		   board[7][7].setPiece(null);
    		   board[7][5].setPiece(Piece.BLACK_ROOK);
    	   }
    	   else if(startY == endY+2) {
    		   board[7][0].setPiece(null);
    		   board[7][3].setPiece(Piece.BLACK_ROOK);
    	   }
       }
       
       if(isWhiteTurn) {
           //moveList.add(new ChessMove(startSquare.getPiece(), new int[] {endSquare.getX(), endSquare.getY()}, endSquare.getPiece() != null, isWhiteCheck(), whiteCheckmate));
       }
       if(!isWhiteTurn) {
           //moveList.add(new ChessMove(startSquare.getPiece(), new int[] {endSquare.getX(), endSquare.getY()}, endSquare.getPiece() != null, isBlackCheck(), blackCheckmate));
       }
       // Handle special case for pawn promotion
       endSquare.setPiece(startSquare.getPiece());
       startSquare.setPiece(null);
       if(endSquare.getPiece() == Piece.WHITE_PAWN && endSquare.getX()==7) {
    	   endSquare.setPiece(Piece.WHITE_QUEEN);
       }
       if(endSquare.getPiece() == Piece.BLACK_PAWN && endSquare.getX()==0) {
    	   endSquare.setPiece(Piece.BLACK_QUEEN);
       }
       // Updating game state
       if(isWhiteTurn) {
    	   if(isBlackCheck()) {
          		System.out.println("Check on Black!");

          		blackCheckmate = true;
          		for(int i = 0; i < 8; i++) {
          			for(int j = 0; j < 8; j++) {
          			   if(board[i][j].getPiece() != null && !board[i][j].getPiece().isWhite())
          				   if(!(board[i][j].getPiece().getValidMovesWithCheck(board, i, j, false).length == 0)) {
          				
          				   blackCheckmate = false;
          			   }
          			}
          		}
          		if(blackCheckmate) {
          			System.out.println("Checkmate! White wins!");
          		}
          		
          	}
       }
       if(!isWhiteTurn) {
       	if(isWhiteCheck()) {
       		System.out.println("Check on White!");
       		
       		whiteCheckmate = true;
       		for(int i = 0; i < 8; i++) {
       			for(int j = 0; j < 8; j++) {
       				if(board[i][j].getPiece() != null && board[i][j].getPiece().isWhite())
       				   if(!(board[i][j].getPiece().getValidMovesWithCheck(board, i, j, true).length == 0)) {
       					   System.out.println(board[i][j].getPiece().toString());
       					   
       				   whiteCheckmate = false;
       			   }
       			}
       		}
       		if(whiteCheckmate) {
       			System.out.println("Checkmate! Black wins!");
       		}
       		
       	}
       	if(isWhiteTurn && !isWhiteCheck()) {
       		stalemate = true;
       		for(int i = 0; i < 8; i++) {
       			for(int j = 0; j < 8; j++) {
       				if(board[i][j].getPiece() != null && board[i][j].getPiece().isWhite()) {
    				   if(!(board[i][j].getPiece().getValidMovesWithCheck(board, i, j, true).length == 0)) {
    					   stalemate = false;
   				}

       		}
       	}
       	}
       }
       	if(!isWhiteTurn && !isBlackCheck()) {
       		stalemate = true;
       		for(int i = 0; i < 8; i++) {
       			for(int j = 0; j < 8; j++) {
       				if(board[i][j].getPiece() != null && !board[i][j].getPiece().isWhite()) {
    				   if(!(board[i][j].getPiece().getValidMovesWithCheck(board, i, j, true).length == 0)) {
    					   stalemate = false;
   				}

       		}
       	}
       	}
       }
       }
       if(stalemate) {
    	   System.out.println("Stalemate!");
       }
       
       isWhiteTurn = !isWhiteTurn;   
       // Moving with bot
       if(!isWhiteTurn) {
    	   botMove2();
       }
       
      
       return true;
       
   }
   // Methods for finding king positions
   public int[] locateWhiteKing() {
	   for(int i = 0; i < 8; i++) {
		   for(int j = 0; j < 8; j++) {
			   if(board[i][j].getPiece()!=null && board[i][j].getPiece() == Piece.WHITE_KING) {
				   return new int[]{i,j};
			   }
		   }
	   }
	   return null;
   }
   public int[] locateBlackKing() {
	   for(int i = 0; i < 8; i++) {
		   for(int j = 0; j < 8; j++) {
			   if(board[i][j].getPiece()!=null && board[i][j].getPiece() == Piece.BLACK_KING) {
				   return new int[]{i,j};
			   }
		   }
	   }
	   return null;
   }
   private void setupPieces() {
   	//setting up white pieces
       board[0][0].setPiece(Piece.WHITE_ROOK);
       board[0][1].setPiece(Piece.WHITE_KNIGHT);
       board[0][2].setPiece(Piece.WHITE_BISHOP);
       board[0][3].setPiece(Piece.WHITE_QUEEN);
       board[0][4].setPiece(Piece.WHITE_KING);
       board[0][5].setPiece(Piece.WHITE_BISHOP);
       board[0][6].setPiece(Piece.WHITE_KNIGHT);
       board[0][7].setPiece(Piece.WHITE_ROOK);
      
       for (int i = 0; i < 8; i++) {
           board[1][i].setPiece(Piece.WHITE_PAWN);
       }
       //setting up black pieces
       board[7][0].setPiece(Piece.BLACK_ROOK);
       board[7][1].setPiece(Piece.BLACK_KNIGHT);
       {
    	   board[7][2].setPiece(Piece.BLACK_BISHOP);
       }
       board[7][3].setPiece(Piece.BLACK_QUEEN);
       board[7][4].setPiece(Piece.BLACK_KING);
       board[7][5].setPiece(Piece.BLACK_BISHOP);
       board[7][6].setPiece(Piece.BLACK_KNIGHT);
       board[7][7].setPiece(Piece.BLACK_ROOK);
       for (int i = 0; i < 8; i++) {
           board[6][i].setPiece(Piece.BLACK_PAWN);
       }
   }
   public Square getSquare(int x, int y) {
       if (x < 0 || x >= 8 || y < 0 || y >= 8) {
           return null; 
       }
       return board[x][y];
   }
   
   public void botMove() {
       int bestValue = Integer.MIN_VALUE;
       int[] bestMove = null;
       for (int i = 0; i < 8; i++) {
    	   for(int j = 0; j < 8; j++) {
    	   if(board[i][j].getPiece() != null && !board[i][j].getPiece().isWhite()) {
           int[][] moves = board[i][j].getPiece().getValidMovesWithCheck(board, i, j, !isWhiteTurn);
           for (int[] move : moves) {
               int value = evaluateMove(board[i][j].getPiece(), move, i, j);
               if (value > bestValue) {
                   bestValue = value;
                   bestMove = new int[] { i, j, i+move[0], j+move[1]};
               }
               if(value == bestValue && Math.random() < 0.78) {
            	   bestValue = value;
                   bestMove = new int[] { i, j, i+move[0], j+move[1]};
               }
           }
    	   }
    	   }
       }
       
       

       if (bestMove == null) {
    	   for(int i = (int)Math.random()*8; i < 8; i++) {
    		   for(int j = (int)Math.random()*8; j < 8; j++) {
    			   if(isWhiteTurn && board[i][j].getPiece() != null && board[i][j].getPiece().isWhite()) {
    				   bestMove = board[i][j].getPiece().getValidMovesWithCheck(board, j, i, isWhiteTurn)[1];
    				   movePiece(i, j, bestMove[0], bestMove[1]);
    		           System.out.println("Bot made move: ");
    			   }
    			   if(!isWhiteTurn && board[i][j].getPiece() != null && board[i][j].getPiece().getValidMovesWithCheck(board, j, i, !isWhiteTurn).length != 0 && !board[i][j].getPiece().isWhite()) {
    				   bestMove = board[i][j].getPiece().getValidMovesWithCheck(board, j, i, !isWhiteTurn)[0];
    				   movePiece(i, j, i+bestMove[0], j+bestMove[1]);
    		           System.out.println("Bot made move: " + bestMove);
    			   }
    		   }
    	   }
           
       }
       else {
           
           movePiece(bestMove[0], bestMove[1], bestMove[2], bestMove[3]);
           
       }
   }
   // Evaluation for bot 1
   private int evaluateMove(Piece piece, int[] move, int x, int y) {
       // Simplified evaluation: Consider the piece value at the destination
       Square endSquare = board[x + move[0]][y + move[1]];
       int value = 0;
       if (endSquare.getPiece() != null) {
           value += getPieceValue(endSquare.getPiece());
       }
       return value;
   }
   
   // Piece values for bot 1
   private int getPieceValue(Piece piece) {
       switch (piece) {
           case WHITE_PAWN:
           case BLACK_PAWN:
               return 1;
           case WHITE_KNIGHT:
           case BLACK_KNIGHT:
           case WHITE_BISHOP:
           case BLACK_BISHOP:
               return 3;
           case WHITE_ROOK:
           case BLACK_ROOK:
               return 5;
           case WHITE_QUEEN:
           case BLACK_QUEEN:
               return 9;
           default:
               return 0;
       }
   }
   
   public void botMove2() {
       int bestValue = Integer.MIN_VALUE;
       int[] bestMove = null;
       for (int i = 0; i < 8; i++) {
    	   for(int j = 0; j < 8; j++) {
    	   if(board[i][j].getPiece() != null && !board[i][j].getPiece().isWhite()) {
           int[][] moves = board[i][j].getPiece().getValidMovesWithCheck(board, i, j, !isWhiteTurn);
           for (int[] move : moves) {
               int value = evaluateMove2(board[i][j].getPiece(), move, i, j);
               if (value > bestValue) {
                   bestValue = value;
                   bestMove = new int[] { i, j, i+move[0], j+move[1]};
               }
               if(value == bestValue && Math.random() > 0.78) {
            	   bestValue = value;
                   bestMove = new int[] { i, j, i+move[0], j+move[1]};
               }
           }
    	   }
    	   }
       }
       
       

       if (bestMove == null) {
    	   for(int i = (int)Math.random()*8; i < 8; i++) {
    		   for(int j = (int)Math.random()*8; j < 8; j++) {
    			   if(isWhiteTurn && board[i][j].getPiece() != null && board[i][j].getPiece().isWhite()) {
    				   bestMove = board[i][j].getPiece().getValidMovesWithCheck(board, j, i, isWhiteTurn)[1];
    				   movePiece(i, j, bestMove[0], bestMove[1]);
    		           System.out.println("Bot made move: ");
    			   }
    			   if(!isWhiteTurn && board[i][j].getPiece() != null && board[i][j].getPiece().getValidMovesWithCheck(board, j, i, !isWhiteTurn).length != 0 && !board[i][j].getPiece().isWhite()) {
    				   bestMove = board[i][j].getPiece().getValidMovesWithCheck(board, j, i, !isWhiteTurn)[0];
    				   movePiece(i, j, i+bestMove[0], j+bestMove[1]);
    		           System.out.println("Bot made move: " + bestMove);
    			   }
    		   }
    	   }
           
       }
       else {
           
           movePiece(bestMove[0], bestMove[1], bestMove[2], bestMove[3]);
           
       }
   }
   // Evaluation for bot 2
   private int evaluateMove2(Piece piece, int[] move, int x, int y) {
	    Square endSquare = board[x + move[0]][y + move[1]];
	    int value = 0;

	    if (endSquare.getPiece() != null) {
	        value += getPieceValue2(endSquare.getPiece());
	    }

	    value += getPositionalValue(piece, x + move[0], y + move[1]);

	    value += getMobilityValue(piece, board, x + move[0], y + move[1]);

	    
	    if (resultsInCheck(board, x, y, x + move[0], y + move[1], piece)) {
	        value += 200; 
	    }
	    if (resultsInCheckmate(board, x, y, x + move[0], y + move[1], piece)) {
	        value += 1000; // High value for checkmate
	    }

	    return value;
	}
   
    // Piece values for bot 2
	private int getPieceValue2(Piece piece) {
		switch (piece) {
        case WHITE_PAWN:
        case BLACK_PAWN:
            return 10;
        case WHITE_KNIGHT:
        case BLACK_KNIGHT:
        case WHITE_BISHOP:
        case BLACK_BISHOP:
            return 30;
        case WHITE_ROOK:
        case BLACK_ROOK:
            return 50;
        case WHITE_QUEEN:
        case BLACK_QUEEN:
            return 90;
        default:
            return 0;
    }
	}
	
	// Position value for bot 2
	private int getPositionalValue(Piece piece, int x, int y) {
	    
	    if ((x == 3 || x == 4) && (y == 3 || y == 4)) {
	        return 10; 
	    }
	    return 0;
	}
	
	// Mobility value for bot 2
	private int getMobilityValue(Piece piece, Square[][] board, int x, int y) {
	    int mobility = piece.getValidMovesWithCheck(board, x, y, piece.isWhite()).length;
	    return mobility;
	}

	
	// Checking game state for bot 2
	private boolean resultsInCheck(Square[][] board, int x, int y, int newX, int newY, Piece piece) {
		Board copyBoard = new Board();
	    copyBoard.board = Piece.copyBoard(board);
	    copyBoard.board[newX][newY].setPiece(copyBoard.board[x][y].getPiece());
	    copyBoard.board[x][y].setPiece(null);
	    if(copyBoard.isWhiteCheck()) {
	    	return true;
	    }
	    return false;
	}

	private boolean resultsInCheckmate(Square[][] board, int x, int y, int newX, int newY, Piece piece) {
		Board copyBoard = new Board();
	    copyBoard.board = Piece.copyBoard(board);
	    copyBoard.board[newX][newY].setPiece(copyBoard.board[x][y].getPiece());
	    copyBoard.board[x][y].setPiece(null);
	    if(copyBoard.whiteCheckmate) {
	    	return true;
	    }
	    return false;
	}

   
}
