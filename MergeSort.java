import java.util.Arrays;

public class MergeSort {
    /*
     * Step 1: Entry method
     * I create method sort with one parameter: the array arr.
     */
    public static void sort(int[] arr) {
        /*
         * Step 2: Base case
         * I check if (arr.length <= 1) — then just return.
         * An array with 0 or 1 element is already sorted.
         * his stops the recursion
         */
        if (arr.length <= 1) return;
        /*
         * Step 3: Find the middle
         * I calculate mid = arr.length /
         * 2. This splits the array into two equal halves.
         */
        int mid = arr.length / 2;
        /*
         * Step 4: Create two sub-arrays
         * I create left array — from index 0 to mid (not including mid).
         * I create right array — from index mid to the end.
         * I use Arrays.copyOfRange() to copy the elements.
         */
        int[] left  = Arrays.copyOfRange(arr, 0, mid);
        int[] right = Arrays.copyOfRange(arr, mid, arr.length);
        /*
         * Step 5: Recursive calls
         * I call sort(left) — this sorts the left half.
         * I call sort(right) — this sorts the right half.
         * Each call splits the array again and again 
         * until every piece has 1 element.
         */
        sort(left);
        sort(right);
        /*
         * Step 6: Merge method
         * I create method merge with three parameters: 
         * the original array arr, sorted
         * left, and sorted right.
         * I create three pointers:
         * 
         * i — current position in left
         * j — current position in right
         * k — current position in arr (where I write the result)
         */
        merge(arr, left, right);
        /*
         * Step 9: Array is now sorted
         * The merge method writes directly into the original arr. So after merge
         * returns, arr contains the fully sorted result.
         */
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        /*
         * Step 7: Compare and fill
         * I run a while loop — as long as both left and right still have elements:
         */
        while (i < left.length && j < right.length) {
            /*
             * If left[i] <= right[j] → I write left[i] into arr[k], 
             * then increment i and k
             */
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                /*Otherwise → I write right[j] into arr[k], then increment j and k */
                arr[k++] = right[j++];
            }
        }
        /*
         * Step 8: Copy leftovers
         * After the main loop, one side may still have elements.
         * I run two more while loops:
         * 
         * First copies remaining elements from left into arr
         */
        while (i < left.length)  arr[k++] = left[i++];
        /*Second copies remaining elements from right into arr */
        while (j < right.length) arr[k++] = right[j++];
    }

    public static void main(String[] args) {
        int[] arr = {5, 2, 8, 1, 9, 3};
        sort(arr);
        System.out.println(Arrays.toString(arr));
        // [1, 2, 3, 5, 8, 9]
    }
}

/*
 * Visual example — what happens with {5, 2, 8, 1}:
 * {5, 2, 8, 1} ← sort() called
 * / \
 * {5, 2} {8, 1} ← split
 * / \ / \
 * {5} {2} {8} {1} ← base case (length 1)
 * \ / \ /
 * {2, 5} {1, 8} ← merge()
 * \ /
 * {1, 2, 5, 8} ← merge()
 * 
 * What to say on interview:
 * 
 * "Merge Sort always has O(n log n) time complexity — in best, average, and worst case. The log n comes from splitting the array in half each time. The n comes from the merge step. Space complexity is O(n) because we create new left and right arrays. Unlike QuickSort, Merge Sort is stable — equal elements keep their original order. It's a good choice when stability matters or when worst-case performance must be guaranteed."
 */