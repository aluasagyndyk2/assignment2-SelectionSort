package algorithms;

import metrics.PerformanceTracker;

public class SelectionSort {
    public static void sort(int[] arr, PerformanceTracker tracker) {
        int n = arr.length;

        for (int i = 0; i < n - 1; i++) {
            int minIndex = i;

            for (int j = i + 1; j < n; j++) {

                tracker.incrementAccesses(2);
                tracker.incrementComparisons();

                if (arr[j] < arr[minIndex]) {
                    minIndex = j;
                }
            }

            if (minIndex != i) {
                swap(arr, i, minIndex, tracker);
            }
        }
    }

    private static void swap(int[] arr, int index1, int index2, PerformanceTracker tracker) {

        tracker.incrementAccesses(1);
        int temp = arr[index1];

        tracker.incrementAccesses(1);
        tracker.incrementAccesses(1);
        arr[index1] = arr[index2];

        tracker.incrementAccesses(1);
        arr[index2] = temp;

        tracker.incrementSwaps();
    }
}