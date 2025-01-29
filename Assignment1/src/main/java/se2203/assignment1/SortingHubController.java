package se2203.assignment1;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import java.util.Random;

/**
 * Controller class for the Sorting Hub application.
 * Manages user interactions, sorting visualizations, and UI updates.
 */
public class SortingHubController {

    private int[] intArray; // Array to be sorted
    private SortingStrategy sortingMethod; // Active sorting strategy

    // UI components (linked to FXML)
    @FXML
    private ComboBox<String> algorithm_Combo;
    @FXML
    private Slider size_Slider, delay_Slider;
    @FXML
    private AnchorPane bar_Anchor;
    @FXML
    private Button sort_Button, shuffle_Button, reverse_Button;
    @FXML
    private Label size_Label, delay_Label;

    /**
     * Default constructor required for JavaFX.
     */
    public SortingHubController() {}

    /**
     * Initializes the controller.
     * Sets up UI components, listeners, and generates the initial array.
     */
    public void initialize() {
        // Populate the algorithm dropdown
        algorithm_Combo.getItems().addAll("Selection Sort", "Merge Sort", "Quick Sort");
        algorithm_Combo.setValue("Selection Sort");
        setSortStrategy(algorithm_Combo.getValue());

        // Listen for slider value changes and update UI accordingly
        size_Slider.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal.intValue() != oldVal.intValue()) {
                sizeDragged();
            }
        });

        delay_Slider.valueProperty().addListener((obs, oldVal, newVal) -> delayDragged());

        // Generate initial array
        generateArray((int) size_Slider.getValue());
    }

    /**
     * Sets the sorting strategy based on the selected algorithm.
     * @param algorithm The sorting algorithm name.
     */
    public void setSortStrategy(String algorithm) {
        switch (algorithm) {
            case "Selection Sort": sortingMethod = new SelectionSort(this); break;
            case "Merge Sort": sortingMethod = new MergeSort(this); break;
            case "Quick Sort": sortingMethod = new QuickSort(this); break;
            default: sortingMethod = null;
        }
    }

    /**
     * Generates a random array of the given size and updates the visualization.
     * @param size The size of the array.
     */
    public void generateArray(int size) {
        intArray = new int[size];
        for (int i = 0; i < size; i++) intArray[i] = i + 1;
        shuffleArray(intArray);
        updateGraph(intArray);
    }

    /**
     * Updates the graphical representation of the array in the UI.
     * @param data The array to visualize.
     */
    public void updateGraph(int[] data) {
        Platform.runLater(() -> {
            bar_Anchor.getChildren().clear();
            double paneWidth = bar_Anchor.getPrefWidth();
            double paneHeight = bar_Anchor.getPrefHeight();
            double barWidth = paneWidth / data.length;

            // Create bars for visualization
            for (int i = 0; i < data.length; i++) {
                double barHeight = (paneHeight / data.length) * data[i];
                double x = i * barWidth;
                double y = paneHeight - barHeight;

                Rectangle bar = new Rectangle(x+4, y+4, barWidth, barHeight);
                bar.setFill(Color.RED);
                bar.setStroke(Color.WHITE);
                bar_Anchor.getChildren().add(bar);
            }
        });
    }

    /**
     * Starts sorting the array using the selected algorithm.
     */
    @FXML
    void sortPressed() {
        if (sortingMethod != null) {
            sortingMethod.setList(intArray);
            new Thread(() -> sortingMethod.run()).start();
        }
    }

    /**
     * Shuffles the array randomly and updates the visualization.
     */
    @FXML
    void shufflePressed() {
        if (intArray == null) return; // Prevents errors when no array exists

        new Thread(() -> {
            shuffleArray(intArray);
            Platform.runLater(() -> updateGraph(intArray));
        }).start();
    }

    /**
     * Reverses the array and updates the visualization.
     */
    @FXML
    void reversePressed() {
        if (intArray == null || intArray.length == 0) return; // Prevents errors

        new Thread(() -> {
            reverseArray(intArray);
            Platform.runLater(() -> updateGraph(intArray));
        }).start();
    }

    /**
     * Handles changes in the size slider.
     * Generates a new array and updates the visualization.
     */
    public void sizeDragged() {
        int newSize = (int) size_Slider.getValue();
        size_Label.setText(String.valueOf(newSize));

        generateArray(newSize);
        shuffleArray(intArray);
        updateGraph(intArray);
    }

    /**
     * Updates the displayed delay value when the slider is adjusted.
     */
    public void delayDragged() {
        delay_Label.setText(String.valueOf((int) delay_Slider.getValue()));
    }

    /**
     * Retrieves the delay time for visualization.
     * @return The delay value in milliseconds.
     */
    public long getDelay() {
        return (long) delay_Slider.getValue();
    }

    /**
     * Implements the Fisher-Yates shuffle algorithm.
     * @param array The array to shuffle.
     */
    private void shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    /**
     * Reverses the order of elements in the array.
     * @param array The array to reverse.
     */
    private void reverseArray(int[] array) {
        int left = 0, right = array.length - 1;
        while (left < right) {
            int temp = array[left];
            array[left] = array[right];
            array[right] = temp;
            left++;
            right--;
        }
    }

    /**
     * Handles algorithm selection change from the dropdown.
     */
    @FXML
    void comboPressed(){
        setSortStrategy(algorithm_Combo.getValue());
    }
}
