package se2203.assignment1;
public class SelectionSort extends SortingStrategy {

    public SelectionSort(SortingHubController controller) {
        super(controller);
    }

    @Override
    public void sort(int[] numbers) {
        this.list = numbers; // Set the list

        for (int i = 0; i < list.length - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.length; j++) {
                if (list[j] < list[minIndex]) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                swap(list, i, minIndex);
                updateGraph(list); // Update the graph after each swap
            }
        }
    }
}
