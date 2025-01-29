package se2203.assignment1;

import java.util.Arrays;

public class MergeSort extends SortingStrategy {

    public MergeSort(SortingHubController controller) {
        super(controller);
    }

    @Override
    public void sort(int[] numbers) {
        this.list = numbers; // Set the list
        int[] aux = Arrays.copyOf(numbers, numbers.length); // Use a single auxiliary array
        mergeSort(numbers, aux, 0, numbers.length - 1);
    }

    private void mergeSort(int[] numbers, int[] aux, int left, int right) {
        if (left < right) {
            int mid = left + (right - left) / 2;
            mergeSort(aux, numbers, left, mid);
            mergeSort(aux, numbers, mid + 1, right);
            merge(aux, numbers, left, mid, right);
        }
    }

    private void merge(int[] src, int[] dest, int left, int mid, int right) {
        int i = left, j = mid + 1, k = left;

        while (i <= mid && j <= right) {
            if (src[i] <= src[j]) {
                dest[k++] = src[i++];
            } else {
                dest[k++] = src[j++];
            }
        }

        while (i <= mid) {
            dest[k++] = src[i++];
        }

        while (j <= right) {
            dest[k++] = src[j++];
        }

        // update graph after merging an entire section
        updateGraph(dest);
    }
}
