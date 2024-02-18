/*
 * Name: Ajay Anubolu
 * Date: 2/14/2024
 * Data Structures and Algorithms Final Project
 */

package ajay;

public class Square {
    private Piece piece;
    private int x; // Add x coordinate
    private int y; // Add y coordinate

    // Modified constructor to initialize coordinates
    public Square(int x, int y){
        this.piece = null;
        this.x = x;
        this.y = y;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public boolean isEmpty() {
        return piece == null;
    }

    // Method to get the x coordinate
    public int getX() {
        return x;
    }

    // Method to get the y coordinate
    public int getY() {
        return y;
    }
    public Square clone() {
    	Square n = new Square(this.getX(), this.getY());
    	n.setPiece(this.piece);
    	return n;
    }
}