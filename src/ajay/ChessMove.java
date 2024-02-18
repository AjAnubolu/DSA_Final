//WORK IN PROGRESS
package ajay;

public class ChessMove {
    private final Piece piece; // Chess piece involved in the move
    private final int[] end; // Destination square of the move, represented as an array [rank, file]
    private final boolean capture; // Indicates if the move captures an opponent's piece
    private final boolean check; // Indicates if the move results in a check
    private final boolean checkmate; // Indicates if the move results in checkmate

    // Constructor to initialize a chess move
    public ChessMove(Piece piece, int[] end, boolean capture, boolean check, boolean checkmate) {
        this.piece = piece;
        this.end = end;
        this.capture = capture;
        this.check = check;
        this.checkmate = checkmate;
    }

    // Override toString method to return a string representation of the move
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        // Append the appropriate piece abbreviation
        switch (piece) {
            case WHITE_KING:
            case BLACK_KING:
                str.append("K");
                break;
            case WHITE_QUEEN:
            case BLACK_QUEEN:
                str.append("Q");
                break;
            case WHITE_ROOK:
            case BLACK_ROOK:
                str.append("R");
                break;
            case WHITE_BISHOP:
            case BLACK_BISHOP:
                str.append("B");
                break;
            case WHITE_KNIGHT:
            case BLACK_KNIGHT:
                str.append("N");
                break;
            default:
                // Pawns do not have an abbreviation in algebraic notation
                break;
        }
        // If the move is a capture, append 'x'
        if (capture) {
            str.append("x");
        }
        // Append the file letter 
        char fileChar = (char) ('a' + end[1]); // Correctly calculate file letter from end[1]
        str.append(fileChar);
        // Append the rank number 
        str.append(end[0] + 1);
        // If the move results in checkmate, append '#', otherwise if check, append '+'
        if (checkmate) {
            str.append("#");
        } else if (check) {
            str.append("+");
        }
        return str.toString();
    }
}
