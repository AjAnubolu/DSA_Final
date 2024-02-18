/*
 * Name: Ajay A
 * Date: 2/14/2024
 * Data Structures and Algorithms Final Project
 */
package ajay;

import java.util.ArrayList;
import java.util.Arrays;

public enum Piece {
    // Enum constants representing chess pieces, each with custom behavior
    WHITE_KING(null, false),
    WHITE_QUEEN(null, true),
    WHITE_ROOK(null, true),
    WHITE_BISHOP(null, true),
    WHITE_KNIGHT(null, false),
    WHITE_PAWN(null, false) {
        // Overrides method for white pawn to calculate valid moves considering pawns' unique movement and capture rules
        @Override
        public int[][] getValidMovesIgnoringCheck(Square[][] board, int rank, int file) {
            ArrayList<int[]> moveList = new ArrayList<>();
            // Forward movement
            if (rank+1 < 8 && board[rank+1][file].getPiece() == null) {
                moveList.add(new int[]{1,0});
                // Initial double move
                if (rank == 1 && board[rank+2][file].getPiece() == null) {
                    moveList.add(new int[]{2,0});
                }
            }
            // Capture movements
            if(rank+1 < 8 && file+1 < 8 && board[rank+1][file+1].getPiece() != null && !board[rank+1][file+1].getPiece().isWhite()) {
                moveList.add(new int[] {1,1});
            }
            if(rank+1 < 8 && file-1 >= 0 && board[rank+1][file-1].getPiece() != null && !board[rank+1][file-1].getPiece().isWhite()) {
                moveList.add(new int[] {1,-1});
            }
            return moveList.toArray(new int[moveList.size()][]);
        }
    },
    BLACK_KING(null, false),
    BLACK_QUEEN(null, true),
    BLACK_ROOK(null, true),
    BLACK_BISHOP(null, true),
    BLACK_KNIGHT(null, false),
    BLACK_PAWN(null, false) {
        // Similar override for black pawn with adjustments for movement direction and initial position
        @Override
        public int[][] getValidMovesIgnoringCheck(Square[][] board, int rank, int file) {
            ArrayList<int[]> moveList = new ArrayList<>();
            // Forward movement
            if (rank-1 >= 0 && board[rank-1][file].getPiece() == null) {
                moveList.add(new int[]{-1,0});
                // Initial double move
                if (rank == 6 && board[rank-2][file].getPiece() == null) {
                    moveList.add(new int[]{-2,0});
                }
            }
            // Capture movements
            if(rank-1 >= 0 && file+1 < 8 && board[rank-1][file+1].getPiece() != null && board[rank-1][file+1].getPiece().isWhite()) {
                moveList.add(new int[] {-1,1});
            }
            if(rank-1 >= 0 && file-1 >= 0 && board[rank-1][file-1].getPiece() != null && board[rank-1][file-1].getPiece().isWhite()) {
                moveList.add(new int[] {-1,-1});
            }
            return moveList.toArray(new int[moveList.size()][]);
        }
    };

    private int[][] moves; // Array to store possible moves
    private boolean isLongRange; // Flag for pieces that can move over long distances (Queen, Rook, Bishop)

    // Constructor for enum constants
    Piece(int[][] moves, boolean isLongRange) {
        this.moves = moves;
        this.isLongRange = isLongRange;
    }

    // Check if the piece can move over long distances
    public boolean isLongRange() {
        return isLongRange;
    }

    // Utility method to deep copy a chessboard's state
    public static Square[][] copyBoard(Square[][] board) {
        Square[][] hypothetical = new Square[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                hypothetical[i][j] = board[i][j].clone();
            }
        }
        return hypothetical;
    }

    // Check if any move by black pieces puts the white king in check
    public static boolean allowsWhiteCheck(Square[][] board) {
        // Iterate through all squares
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getPiece() != null && !board[i][j].getPiece().isWhite()) {
                    // Check if any move of the current black piece can capture the white king
                    for (int[] move : board[i][j].getPiece().getValidMovesIgnoringCheck(board, i, j)) {
                        if (board[i + move[0]][j + move[1]].getPiece() == Piece.WHITE_KING) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Similar to allowsWhiteCheck but checks for black king
    public static boolean allowsBlackCheck(Square[][] board) {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].getPiece() != null && board[i][j].getPiece().isWhite()) {
                    for (int[] move : board[i][j].getPiece().getValidMovesIgnoringCheck(board, i, j)) {
                        if (board[i + move[0]][j + move[1]].getPiece() == Piece.BLACK_KING) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    // Method to get valid moves considering check constraints
    public int[][] getValidMovesWithCheck(Square[][] board, int rank, int file, boolean isWhiteTurn) {
        ArrayList<int[]> moveList = new ArrayList<>();
        Square[][] hypothetical = copyBoard(board);
        moves = getValidMovesIgnoringCheck(board, rank, file);
        // Filter moves that do not result in self-check
        for (int[] move : moves) {
            hypothetical[rank + move[0]][file + move[1]].setPiece(this);
            hypothetical[rank][file].setPiece(null);
            if (isWhiteTurn && !allowsWhiteCheck(hypothetical)) {
                moveList.add(move);
            } else if (!isWhiteTurn && !allowsBlackCheck(hypothetical)) {
                moveList.add(move);
            }
        }
        return moveList.toArray(new int[moveList.size()][]);
    }
	public int[][] getValidMovesIgnoringCheck(Square[][] board, int rank, int file) {
		ArrayList<int[]> moveList = new ArrayList<>();
		moves = getValidMoves();
		if(isLongRange() && isWhite()) {
			moveLoop: for(int[] move : moves) {
				
				for(int i = 1; i < 8; i++) {
					if(rank+move[0]*i < 8 && rank+move[0]*i > -1 && file+move[1]*i < 8 && file+move[1]*i > -1) {
						if(board[rank+move[0]*i][file+move[1]*i].getPiece() == null ) {
							moveList.add(new int[] {move[0]*i, move[1]* i});
						}
						else if(board[rank+move[0]*i][file+move[1]*i].getPiece().isWhite() == false) {
							moveList.add(new int[] {move[0]*i, move[1]* i});
							continue moveLoop;
						}
						
						else {
							continue moveLoop;
						}
					}
					
				}
			}
			return moveList.toArray(new int[moveList.size()][]);
		}
		
		if(isLongRange() && !isWhite()) {
			moveLoop: for(int[] move : moves) {
				for(int i = 1; i < 8; i++) {
					if(rank+move[0]*i < 8 && rank+move[0]*i > -1 && file+move[1]*i < 8 && file+move[1]*i > -1) {
						if(board[rank+move[0]*i][file+move[1]*i].getPiece() == null ) {
							moveList.add(new int[] {move[0]*i, move[1]* i});
						}
						else if(board[rank+move[0]*i][file+move[1]*i].getPiece().isWhite()) {
							moveList.add(new int[] {move[0]*i, move[1]* i});
							continue moveLoop;
						}
						
						else {
							continue moveLoop;
						}
					}
					
				}
			}
			return moveList.toArray(new int[moveList.size()][]);
		}
		if(this == Piece.WHITE_KNIGHT || this == Piece.WHITE_KING) {
			for(int[] move : moves) {
				//checking bounds
				if(rank+move[0] < 8 && rank+move[0] > -1 && file+move[1] < 8 && file+move[1] > -1) {
					if(board[rank+move[0]][file+move[1]].getPiece() == null || !board[rank+move[0]][file+move[1]].getPiece().isWhite()) {
						moveList.add(new int[] {move[0], move[1]});
					}
				}
				
			
			}
			if(this == Piece.WHITE_KING && board[0][4].getPiece() == Piece.WHITE_KING && board[0][5].getPiece() == null && board[0][6].getPiece() == null && board[0][7].getPiece() == Piece.WHITE_ROOK) {
				moveList.add(new int[] {0,2});
			}
			if(this == Piece.WHITE_KING && board[0][4].getPiece() == Piece.WHITE_KING && board[0][3].getPiece() == null && board[0][2].getPiece() == null && board[0][1].getPiece() == null && board[0][0].getPiece() == Piece.WHITE_ROOK) {
				moveList.add(new int[] {0,-2});
			}
			return moveList.toArray(new int[moveList.size()][]);
		}
		if(this == Piece.BLACK_KNIGHT || this == Piece.BLACK_KING) {
			for(int[] move : moves) {
				if(rank+move[0] < 8 && rank+move[0] > -1 && file+move[1] < 8 && file+move[1] > -1) {
					if(board[rank+move[0]][file+move[1]].getPiece() == null || board[rank+move[0]][file+move[1]].getPiece().isWhite()  ) {
						moveList.add(new int[] {move[0], move[1]});
					}
				}
				
			}
			if(this == Piece.BLACK_KING && board[7][4].getPiece() == Piece.BLACK_KING && board[7][5].getPiece() == null && board[7][6].getPiece() == null && board[7][7].getPiece() == Piece.BLACK_ROOK) {
				moveList.add(new int[] {0,2});
			}
			if(this == Piece.BLACK_KING && board[7][4].getPiece() == Piece.BLACK_KING && board[7][3].getPiece() == null && board[7][2].getPiece() == null && board[7][1].getPiece() == null && board[7][0].getPiece() == Piece.BLACK_ROOK) {
				moveList.add(new int[] {0,-2});
			}
			return moveList.toArray(new int[moveList.size()][]);
		}
		return null;
	}
	
	
	public int[][] getValidMoves() {
	    switch (this) {
	    	case WHITE_PAWN:
	    		return getWhitePawnMoves();
	        case BLACK_PAWN:
	            return getBlackPawnMoves();
	        case WHITE_KNIGHT:
	        case BLACK_KNIGHT:
	            return getKnightMoves();
	        case WHITE_BISHOP:
	        case BLACK_BISHOP:
	            return getBishopMoves();
	        case WHITE_ROOK:
	        case BLACK_ROOK:
	            return getRookMoves();
	        case WHITE_QUEEN:
	        case BLACK_QUEEN:
	            return getQueenMoves();
	        case WHITE_KING:
	        case BLACK_KING:
	            return getKingMoves();
	        default:
	            return new int[0][0];
	    }
	}

	private int[][] getWhitePawnMoves() {
		// Pawn movement(no capture, no en passant)
	    return new int[][]{{1, 0}};
	}
    private int[][] getBlackPawnMoves() {
    	// Pawn movement(no capture, no en passant)
	    return new int[][]{{-1, 0}};
    }

	private int[][] getKnightMoves() {
        // Knight movement
	    return new int[][]{{1, 2}, {2, 1}, {-1, 2}, {2, -1}, {-1, -2}, {-2, -1}, {1, -2}, {-2, 1}};
	}

    private int[][] getBishopMoves() {
        // Bishop movement
        return new int[][]{{1, 1}, {1, -1}, {-1, 1}, {-1, -1}}; 
    }

    private int[][] getRookMoves() {
        // Rook movement
        return new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; 
    }

    private int[][] getQueenMoves() {
        // Queen movement
        return new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}}; //up to the board limit
    }

    private int[][] getKingMoves() {
        // King movement
        return new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};
    }
   
    public String toString() {
        return name().substring(0,5).toLowerCase() + name().substring(6).toLowerCase();
    }
	

	public boolean isKing() {
		// TODO Auto-generated method stub
		return this == Piece.WHITE_KING || this == Piece.BLACK_KING;
	}

	boolean isWhite() {
		// TODO Auto-generated method stub
		return this == Piece.WHITE_KING || this == Piece.WHITE_QUEEN || this == Piece.WHITE_ROOK || this == Piece.WHITE_BISHOP || this == Piece.WHITE_KNIGHT || this == Piece.WHITE_PAWN
				;
	}

}
