public class SlidingWindow {

    // Task: find maximum sum of k consecutive elements
    // Example: {2, 1, 5, 1, 3, 2}, k=3 → 9 (5+1+3)

    public static int maxSum(int[] arr, int k) {
        /*
         * Step 1: Check edge case
         * If arr.length < k — not enough elements for one window. Return -1.
         */
        if (arr.length < k)
            return -1;

        // Step 1: sum of first window
        int windowSum = 0;
        /*
         * Step 2: Calculate the first window
         * I loop from i = 0 to i < k.
         * I add each element to windowSum.
         * After the loop — windowSum holds the sum of the first k elements.
         * I save this as maxSum.
         */
        for (int i = 0; i < k; i++) {
            windowSum += arr[i];
        }

        int maxSum = windowSum;
        /*
         * Step 3: Slide the window
         * I loop from i = k to the end of the array.
         * In each step the window moves one position to the right:
         */
        // Step 2: slide the window
        for (int i = k; i < arr.length; i++) {
            windowSum += arr[i]; /* I add arr[i] — the new element entering the window from the right */ 
            windowSum -= arr[i - k]; /* I subtract arr[i - k] — the old element leaving the window from the left */ 

            /*
             * Step 4: Update maximum
             * After each slide, I check Math.max(maxSum, windowSum).
             * I keep the biggest sum seen so far.
             */
            maxSum = Math.max(maxSum, windowSum);
        }
        /*
         * Step 5: Return result
         * After the loop — return maxSum.
         */
        return maxSum;
    }

    public static void main(String[] args) {
        int[] arr = {2, 1, 5, 1, 3, 2};
        System.out.println(maxSum(arr, 3)); // 9
    }
}

/*
 * Visual example — {2, 1, 5, 1, 3, 2}, k=3:
 * Window 1: [2, 1, 5] sum=8
 * Window 2: [1, 5, 1] sum=7 (+1, -2)
 * Window 3: [5, 1, 3] sum=9 (+3, -1) ← MAX
 * Window 4: [1, 3, 2] sum=6 (+2, -5)
 * 
 * Answer: 9
 * Bonus — Variable size window (longest substring without repeating
 * characters):
 * javapublic static int lengthOfLongestSubstring(String s) {
 * Map<Character, Integer> map = new HashMap<>();
 * int maxLen = 0;
 * int left = 0;
 * 
 * for (int right = 0; right < s.length(); right++) {
 * char c = s.charAt(right);
 * 
 * // if char already in window → shrink from left
 * if (map.containsKey(c)) {
 * left = Math.max(left, map.get(c) + 1);
 * }
 * 
 * map.put(c, right); // save latest index of char
 * maxLen = Math.max(maxLen, right - left + 1);
 * }
 * return maxLen;
 * }
 * // "abcabcbb" → 3 ("abc")
 * // "pwwkew" → 3 ("wke")
 * 
 * "Fixed window — O(n) time, O(1) space. Variable window — O(n) time, O(k) space where k is the window size. The key insight: instead of recalculating the sum from scratch every step, just add the new element and remove the old one. This turns an O(n×k) brute force into O(n)."
 */