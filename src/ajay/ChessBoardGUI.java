/*
 * Name: Ajay Anubolu
 * Date: 2/14/2024
 * Data Structures and Algorithms Final Project
 */

package ajay;

import java.io.IOException;
import java.net.URL;
import javafx.collections.ObservableList;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
public class ChessBoardGUI {
    private Board board; // The chessboard state
    private final String imagePath = "assets/"; // Path to images
    private GridPane gridPane; // Grid to hold chess squares
    private Square selectedSquare; // Currently selected square
    private Piece selectedPiece; // Currently selected piece
    private TableView<ChessMove> movesTable; // Table to display moves
    private ObservableList<ChessMove> movesList; // List of chess moves

   public ChessBoardGUI(Board board) {
       this.board = board;
       this.selectedPiece = null;
       this.selectedSquare = null;
       this.gridPane = new GridPane();
       this.movesTable = new TableView<>();
       this.movesList = FXCollections.observableArrayList();
       //initializeMovesTable();
   }
   /*
   private void initializeMovesTable() {
       TableColumn<ChessMove, String> whiteColumn = new TableColumn<>("Move Table");
       whiteColumn.setCellValueFactory(new PropertyValueFactory<>("notation"));

       TableColumn<ChessMove, String> blackColumn = new TableColumn<>("Black");
       blackColumn.setCellValueFactory(new PropertyValueFactory<>("notation"));

       movesTable.getColumns().add(whiteColumn);
       movesTable.getColumns().add(blackColumn);

       movesTable.setItems(movesList);

       whiteColumn.setPrefWidth(120); // Adjust as needed
       movesTable.setPrefWidth(120); // Adjust width as needed
       movesTable.setPrefHeight(400); // Adjust height as needed
       blackColumn.setPrefWidth(120); // Adjust as needed
       movesTable.setPrefWidth(120); // Adjust width as needed
       movesTable.setPrefHeight(400); // Adjust height as needed
   }
   */
// Method to create the GUI content
   public Parent createContent() {
       HBox root = null;
       gridPane.getChildren().clear(); // Clear previous state
       for (int i = 0; i < 8; i++) { // Iterate over rows
           for (int j = 0; j < 8; j++) { // Iterate over columns
               final int x = i;
               final int y = j;
               Button square = new Button();
               square.setPrefSize(50, 50);

               // Set square color based on position to create a checkerboard pattern
               Color squareColor = (i + j) % 2 == 0 ? Color.BEIGE : Color.GRAY;
               square.setBackground(new Background(new BackgroundFill(squareColor, null, null)));

               // If there's a piece on this square, get and display its image
               Piece piece = board.getSquare(i, j).getPiece();
               if (piece != null) {
                   Image image = getImageForPiece(piece);
                   ImageView imageView = new ImageView(image);
                   imageView.setFitWidth(30);
                   imageView.setFitHeight(30);
                   square.setGraphic(imageView);
               }

               // Set action on square click
               square.setOnAction(e -> handleSquareClick(x, y));

               // Add square to grid
               gridPane.add(square, j, i);
           }
       }
       // Initialize root HBox if it hasn't been already
       if (root == null) {
           root = new HBox(10);
           root.getChildren().addAll(gridPane);
           //root.getChildren().addAll(gridPane, movesTable); // Add moves table if needed
       }

       return root; // Return the root node containing the grid
   }

   // Method to get an image for a chess piece
   private Image getImageForPiece(Piece piece) {
       URL url = ChessGame.class.getClassLoader().getResource(imagePath + piece.toString().toLowerCase() + ".png");
       try {
           return new Image(url.openStream());
       } catch (IOException e) {
           e.printStackTrace();
           return null;
       }
   }

   // Handle clicks on squares
   private void handleSquareClick(int x, int y) {
       if (selectedSquare == null) { // If no square is selected
           selectedSquare = board.getSquare(x, y); // Select the current square
           selectedPiece = selectedSquare.getPiece(); // Select the piece on this square
           if (selectedPiece != null) { // If there's a piece
               System.out.println("Piece selected: " + selectedPiece);
           }
       } else { // If a square is already selected
           // Attempt to move the selected piece to the new square
           if (board.movePiece(selectedSquare.getX(), selectedSquare.getY(), x, y)) {
               System.out.println("Moved piece");
               refreshBoardGUI(); // Refresh the GUI to reflect the move
           } else {
               System.out.println("Invalid move");
           }
           // Reset selection
           selectedSquare = null;
           selectedPiece = null;
       }
   }

   // Update the grid pane to reflect any changes (e.g., after a move)
   private void updateGridPane() {
       gridPane.getChildren().clear(); // Clear the grid
       // Similar logic to createContent() for setting up the board
       for (int i = 0; i < 8; i++) {
           for (int j = 0; j < 8; j++) {
               final int x = i;
               final int y = j;
               Button square = new Button();
               square.setPrefSize(50, 50);

               Color squareColor = (i + j) % 2 == 0 ? Color.BEIGE : Color.GREY;
               square.setBackground(new Background(new BackgroundFill(squareColor, null, null)));
               Piece piece = board.getSquare(i, j).getPiece();
               if (piece != null) {
                   Image image = getImageForPiece(piece);
                   ImageView imageView = new ImageView(image);
                   imageView.setFitWidth(30);
                   imageView.setFitHeight(30);
                   square.setGraphic(imageView);
               }
               square.setOnAction(e -> handleSquareClick(x, y));
               gridPane.add(square, j, i);
           }
       }
   }

   // Refresh the entire board GUI
   private void refreshBoardGUI() {
       updateGridPane();
   }
}