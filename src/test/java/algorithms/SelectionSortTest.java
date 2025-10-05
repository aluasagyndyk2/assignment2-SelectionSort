package algorithms;

import metrics.PerformanceTracker;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SelectionSortTest {
    @Test
    public void testEmptyArray() {
        int[] arr = {};
        PerformanceTracker tracker = new PerformanceTracker();
        SelectionSort.sort(arr, tracker);
        assertTrue(isSorted(arr));
    }

    @Test
    public void testSingleElement() {
        int[] arr = {1};
        PerformanceTracker tracker = new PerformanceTracker();
        SelectionSort.sort(arr, tracker);
        assertTrue(isSorted(arr));
    }

    @Test
    public void testDuplicates() {
        int[] arr = {5, 5, 5};
        PerformanceTracker tracker = new PerformanceTracker();
        SelectionSort.sort(arr, tracker);
        assertTrue(isSorted(arr));
    }

    @Test
    public void testRandomArray() {
        int[] arr = {3, 1, 4, 2};
        PerformanceTracker tracker = new PerformanceTracker();
        SelectionSort.sort(arr, tracker);
        assertTrue(isSorted(arr));
    }

    private boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }
}
