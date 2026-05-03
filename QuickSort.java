import java.util.Arrays;    

public class QuickSort {

    /*
     * Step 1: Entry method
     * I create method sort with three parameters:
     * the array, low (start index), high (end index).
     * I check if low < high.
     * If not — the array has 0 or 1 element, nothing to sort, just return.
     */
    public static void sort(int[] arr, int low, int high) {
        /*
         * Step 7: Base case stops recursion
         * When low >= high, the subarray has 1 or 0 elements
         * — it's already sorted. The recursion stops.
         */
        if (low < high) {
            int pivotIndex = partition(arr, low, high);
            /*
             * Step 6: Recursive calls
             * Back in sort, I call sort recursively 
             * on the left part (low to pivotIndex - 1)
             * and the right part (pivotIndex + 1 to high).
             */
            sort(arr, low, pivotIndex - 1);   // ліва частина
            sort(arr, pivotIndex + 1, high);  // права частина
        }
    }

    /*
     * Step 2: Choose a pivot
     * I create method partition. I take the last element as the pivot.
     * I create variable i = low - 1.
     * This pointer tracks the boundary between "smaller than pivot"
     * and "bigger than pivot."
     */
    private static int partition(int[] arr, int low, int high) {
        int pivot = arr[high]; // останній елемент — pivot
        int i = low - 1;

        /*
         * Step 3: Walk through the array
         * I loop j from low to high - 1. For each element:
         * 
         * If arr[j] <= pivot → increment i → swap arr[i] and arr[j]
         * This moves all small elements to the left side
         */
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                int temp = arr[i];  // swap
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        /*
         * Step 4: Place pivot in the correct position
         * After the loop, I swap arr[i+1] with arr[high] (the pivot).
         * Now pivot is exactly where it belongs —
         * all smaller elements are to its left,
         * all bigger elements are to its right.
         */
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;

        /*
         * Step 5: Return pivot index
         * I return i + 1 — the final position of the pivot.
         */
        return i + 1;
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 8, 1, 9, 3};
        sort(arr, 0, arr.length - 1);
        System.out.println(Arrays.toString(arr));
        // [1, 2, 3, 5, 8, 9]
    }
}

/*
 * What to say on interview:
 * "Average time complexity — O(n log n). Worst case — O(n²) when array is
 * already sorted
 * and we always pick the last element as pivot. Space complexity — O(log n) for
 * recursion stack.
 * Unlike Merge Sort, QuickSort sorts in-place — no extra array needed."
 */