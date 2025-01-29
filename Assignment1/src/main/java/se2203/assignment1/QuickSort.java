package se2203.assignment1;
public class QuickSort extends SortingStrategy {

    public QuickSort(SortingHubController controller) {
        super(controller);
    }

    @Override
    public void sort(int[] numbers) {
        this.list = numbers; // Set the list
        quickSort(numbers, 0, numbers.length - 1);
    }

    private void quickSort(int[] numbers, int low, int high) {
        if (low < high) {
            int pivotIndex = partition(numbers, low, high);
            updateGraph(numbers); // Update graph after partitioning
            quickSort(numbers, low, pivotIndex - 1);
            quickSort(numbers, pivotIndex + 1, high);
        }
    }

    private int partition(int[] numbers, int low, int high) {
        int pivot = numbers[high];
        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (numbers[j] < pivot) {
                i++;
                swap(numbers, i, j);
                updateGraph(numbers); // Update graph after each swap
            }
        }
        swap(numbers, i + 1, high);
        updateGraph(numbers); // Final update for pivot placement
        return i + 1;
    }
}
