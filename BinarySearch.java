public class BinarySearch {

    public static int search(int[] arr, int target) {
        /*
         * Step 1: Create two pointers
         * I create left = 0 — start of the array.
         * I create right = arr.length - 1 — end of the array.
         * These define the current search zone.
         */
        int left = 0;
        int right = arr.length - 1;
        /*
         * Step 2: Start the loop
         * I run while (left <= right).
         * When left > right — the search zone is empty, element not found.
         */
        while (left <= right) {
            /*
             * Step 3: Find the middle
             * I calculate mid = left + (right - left) / 2.
             * I never write (left + right) / 2 — that can cause integer overflow if both
             * values are very large.
             */
            int mid = left + (right - left) / 2; // уникаємо overflow
            /* Step 4: Three possible cases */
            if (arr[mid] == target) {
                /*
                 * Case 1 — found:
                 * arr[mid] == target → return mid (the index).
                 */
                return mid; // знайшли!
            } else if (arr[mid] < target) {
                /*
                 * Case 2 — too small:
                 * arr[mid] < target → target is in the right half.
                 * I move left = mid + 1 — cut off the left half completely.
                 */
                left = mid + 1; // target праворуч
            } else {
                /*
                 * Case 3 — too big:
                 * arr[mid] > target → target is in the left half.
                 * I move right = mid - 1 — cut off the right half completely.
                 */
                right = mid - 1; // target ліворуч
            }
        }
        /*
         * Step 5: Not found
         * Loop ends without returning → return -1.
         */
        return -1; // не знайдено
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 5, 8, 9, 12, 15};
        System.out.println(search(arr, 8));   // 4
        System.out.println(search(arr, 7));   // -1
    }
}

/*
 * Visual example — {1, 2, 3, 5, 8, 9, 12, 15}, target = 8:
 * Step 1: left=0, right=7 → mid=3 arr[3]=5 too small → left=4
 * Step 2: left=4, right=7 → mid=5 arr[5]=9 too big → right=4
 * Step 3: left=4, right=4 → mid=4 arr[4]=8 ✅ FOUND → return 4
 * 
 * "Binary Search cuts the search zone in half every step. So for 1,000,000 elements — maximum 20 steps. Time complexity O(log n), space O(1). It only works on a sorted array. The most common mistake is writing (left + right) / 2 — always use left + (right - left) / 2 to avoid overflow."
 */