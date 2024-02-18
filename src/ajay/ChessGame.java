/*
 * Name: Ajay A
 * Date: 2/14/2024
 * Data Structures and Algorithms Final Project
 */
package ajay;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ChessGame extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Initialize the chess board logic
        Board board = new Board();
        
        // Create the GUI representation of the chess board
        ChessBoardGUI chessBoardGUI = new ChessBoardGUI(board);
        
        // Create the scene with the chess board GUI content
        Scene scene = new Scene(chessBoardGUI.createContent());
      
        // Set the stage with title, scene, and show it
        primaryStage.setTitle("Chess Game");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
  
    public static void main(String[] args) {
        // Launch the JavaFX application
        launch(args);
        
        // Scanner code commented out, potentially for future user input handling
        //Scanner in = new Scanner(System.in);
        //in.close();
    }
}
