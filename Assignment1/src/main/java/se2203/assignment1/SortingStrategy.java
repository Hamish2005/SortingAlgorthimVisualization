package se2203.assignment1;

import javafx.application.Platform;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Abstract base class for sorting algorithms.
 * Implements Runnable to allow sorting execution in a separate thread.
 */
public abstract class SortingStrategy implements Runnable {

    protected int[] list; // Reference to the array being sorted
    protected SortingHubController controller; // Reference to the UI controller

    /**
     * Constructor to initialize the sorting strategy with a controller.
     * @param controller The main UI controller to handle visualization updates.
     */
    public SortingStrategy(SortingHubController controller) {
        this.controller = controller;
    }

    /**
     * Abstract method to be implemented by subclasses for sorting logic.
     * @param numbers The array to be sorted.
     */
    public abstract void sort(int[] numbers);

    /**
     * Sets the array to be sorted.
     * @param numbers The array to set.
     */
    public void setList(int[] numbers) {
        this.list = numbers;
    }

    /**
     * Shuffles the array randomly using the Fisher-Yates shuffle algorithm.
     * Updates the visualization after each swap.
     */
    public void shuffle() {
        if (list == null) return;

        Random random = new Random();
        for (int i = list.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            swap(list, i, j);
            updateGraph(list); // Update visualization after each shuffle step
        }
    }

    /**
     * Reverses the order of elements in the array.
     * Updates the visualization after each swap.
     */
    public void reverse() {
        if (list == null) return;

        int left = 0, right = list.length - 1;
        while (left < right) {
            swap(list, left, right);
            updateGraph(list); // Update visualization after each swap
            left++;
            right--;
        }
    }

    /**
     * Swaps two elements in the given array.
     * @param numbers The array in which elements will be swapped.
     * @param i The first index.
     * @param j The second index.
     */
    protected void swap(int[] numbers, int i, int j) {
        int temp = numbers[i];
        numbers[i] = numbers[j];
        numbers[j] = temp;
    }

    /**
     * Updates the sorting visualization by calling the UI controller.
     * Ensures thread safety by using Platform.runLater().
     * @param numbers The current state of the array.
     */
    protected void updateGraph(int[] numbers) {
        Platform.runLater(() -> controller.updateGraph(numbers));

        // Introduce a delay only if delay > 0
        if (controller.getDelay() > 0) {
            delay();
        }
    }

    /**
     * Introduces a delay in the sorting process to control visualization speed.
     * Prevents UI freezing by handling interruptions.
     */
    protected void delay() {
        try {
            long delay = controller.getDelay();
            if (delay > 0) {
                TimeUnit.MILLISECONDS.sleep(delay);
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restore the thread's interrupted status
        }
    }

    /**
     * Runs the sorting algorithm in a separate thread.
     * Ensures the visualization updates after sorting is completed.
     */
    @Override
    public void run() {
        if (list != null) {
            sort(list); // Execute the sorting algorithm
            updateGraph(list); // Final visualization update after sorting
        }
    }
}
