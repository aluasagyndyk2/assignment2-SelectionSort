package cli;

import algorithms.SelectionSort;
import metrics.PerformanceTracker;
import java.util.Random;
import java.util.Arrays;
import java.io.FileWriter;
import java.io.IOException;

public class BenchmarkRunner {

    private static final int[] SIZES = {100, 1000, 10000, 100000};
    private static final String CSV_FILE = "performance_results_selection_sort.csv";

    public static void main(String[] args) {
        System.out.println("--- Запуск бенчмарков Selection Sort ---");
        try (FileWriter writer = new FileWriter(CSV_FILE)) {
            writer.append("N,InputType,Time_ns,Comparisons,Swaps,ArrayAccesses\n");

            for (int n : SIZES) {
                System.out.println("\nТестирование N=" + n);

                int[] randomArr = generateRandomArray(n);
                runBenchmark(n, "Random", randomArr, writer);

                int[] sortedArr = Arrays.copyOf(randomArr, n);
                Arrays.sort(sortedArr);
                runBenchmark(n, "Sorted", sortedArr, writer);

                int[] reverseArr = generateReverseSortedArray(n);
                runBenchmark(n, "ReverseSorted", reverseArr, writer);

                int[] nearlySortedArr = generateNearlySortedArray(n, 0.05);
                runBenchmark(n, "NearlySorted", nearlySortedArr, writer);
            }


        } catch (IOException e) {
            System.err.println("Ошибка при записи в CSV файл: " + e.getMessage());
        }
    }

    private static void runBenchmark(int n, String type, int[] arr, FileWriter writer) throws IOException {
        int[] arrCopy = Arrays.copyOf(arr, n);
        PerformanceTracker tracker = new PerformanceTracker();

        long start = System.nanoTime();
        SelectionSort.sort(arrCopy, tracker);
        long end = System.nanoTime();

        long timeNs = (end - start);

        System.out.printf("  %s: Время=%.3fмс, Сравнений=%d, Обменов=%d, Обращений=%d (Sorted: %b)%n",
                type, timeNs / 1_000_000.0, tracker.getComparisons(), tracker.getSwaps(), tracker.getArrayAccesses(), isSorted(arrCopy));

        writer.append(String.format("%d,%s,%d,%d,%d,%d\n",
                n, type, timeNs, tracker.getComparisons(), tracker.getSwaps(), tracker.getArrayAccesses()));

        if (!isSorted(arrCopy)) {
            System.err.println("  *** ОШИБКА: Массив не отсортирован для N=" + n + " и типа: " + type + " ***");
        }
    }

    private static int[] generateRandomArray(int n) {
        return new Random().ints(n, 0, 10000).toArray();
    }

    private static int[] generateReverseSortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = n - i;
        }
        return arr;
    }

    private static int[] generateNearlySortedArray(int n, double noiseFactor) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }

        Random rand = new Random();
        int swaps = (int) (n * noiseFactor);
        for (int i = 0; i < swaps; i++) {
            int idx1 = rand.nextInt(n);
            int idx2 = rand.nextInt(n);
            int temp = arr[idx1];
            arr[idx1] = arr[idx2];
            arr[idx2] = temp;
        }
        return arr;
    }

    private static boolean isSorted(int[] arr) {
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] < arr[i - 1]) return false;
        }
        return true;
    }
}