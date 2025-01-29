package se2203.assignment1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.InputStream;

/**
 * Main entry point for the Sorting Hub JavaFX application.
 */
public class SortingHubApplication extends Application {

    /**
     * Starts the JavaFX application and sets up the primary stage.
     * @param stage The main window of the application.
     */
    @Override
    public void start(Stage stage) {
        try {
            // Load the FXML layout file for the GUI
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("SortingHub-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load()); // Create the scene

            // Set the window title
            stage.setTitle("Sorting Hub");

            // Set the window icon using an image file
            stage.getIcons().add(new Image(getClass().getResourceAsStream("WesternLogo.png")));

            // Set the scene to the stage and display the window
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace(); // Print any errors if the FXML file fails to load
        }
    }

    /**
     * Main method to launch the JavaFX application.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        launch(); // Launch the JavaFX application
    }
}
