import java.util.Arrays;

public class TwoPointers {

    // Task: find two numbers in SORTED array that sum to target
    // Example: {1, 2, 3, 5, 8, 9}, target = 10 → [1, 4] (indices of 2 and 8? No: 1+9=10 → [0,5])

    public static int[] twoSum(int[] arr, int target) {
        /*
         * Step 1: Create two pointers
         * I create variable left = 0 — it points to the first element of the array.
         * I create variable right = arr.length - 1 — it points to the last element.
         * Both pointers start at opposite ends of the array.
         */
        int left  = 0;
        int right = arr.length - 1;
        /*
         * Step 2: Start the loop
         * I run a while loop — as long as left < right.
         */
        while (left < right) {
            /*
             * Step 3: Calculate the sum
             * Inside the loop, I calculate sum = arr[left] + arr[right].
             * This is the sum of the two numbers that the pointers currently point to.
             */
            int sum = arr[left] + arr[right];

            /* Step 4: Three possible cases */
            if (sum == target) {
                /*
                 * Case 1 — sum equals target:
                 * I found the pair! I return new int[]{left, right} — the indices of both
                 * numbers.
                 */
                return new int[] { left, right }; // знайшли!
            } else if (sum < target) {
                /*
                 * Case 2 — sum is too small:
                 * sum < target → I need a bigger number.
                 * I move left++ — shift the left pointer one step to the right.
                 * Because the array is sorted, arr[left] is now bigger → the sum will increase.
                 */
                left++; // сума мала → рухаємо лівий вправо
            } else {
                /*
                 * Case 3 — sum is too big:
                 * sum > target → I need a smaller number.
                 * I move right-- — shift the right pointer one step to the left.
                 * Because the array is sorted, arr[right] is now smaller → the sum will
                 * decrease.
                 */
                right--; // сума велика → рухаємо правий вліво
            }
        }
        /*
         * Step 5: No pair found
         * If the loop ends without returning — no valid pair exists.
         * I return an empty array new int[]{}.
         */
        return new int[]{};  // пара не знайдена
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 5, 8, 9};
        int target = 10;
        System.out.println(Arrays.toString(twoSum(arr, target)));
        // [1, 4]  →  arr[1] + arr[4] = 2 + 8 = 10
    }
}

/* Visual example — {1, 2, 3, 5, 8, 9}, target = 10:
Step 1:  left=0, right=5  →  1 + 9 = 10  ✅  FOUND → return [0, 5]
Another example — {1, 2, 3, 5, 8, 9}, target = 13:
Step 1:  left=0, right=5  →  1 + 9 = 10  too small  → left++
Step 2:  left=1, right=5  →  2 + 9 = 11  too small  → left++
Step 3:  left=2, right=5  →  3 + 9 = 12  too small  → left++
Step 4:  left=3, right=5  →  5 + 9 = 14  too big    → right--
Step 5:  left=3, right=4  →  5 + 8 = 13  ✅  FOUND → return [3, 4]

Bonus — second classic problem: Remove Duplicates from sorted array
javapublic static int removeDuplicates(int[] arr) {
    if (arr.length == 0) return 0;

    int slow = 0; // pointer to last unique element

    for (int fast = 1; fast < arr.length; fast++) {
        if (arr[fast] != arr[slow]) { // found new unique element
            slow++;
            arr[slow] = arr[fast]; // write it after the last unique
        }
    }
    return slow + 1; // length of unique part
}

// {1, 1, 2, 3, 3, 4} → {1, 2, 3, 4, ...}  returns 4
Step 1: I create slow = 0 — tracks the last unique element position.
Step 2: I loop fast from 1 to the end.
Step 3: If arr[fast] != arr[slow] — found a new unique number. I increment slow and write arr[fast] into arr[slow].
Step 4: If arr[fast] == arr[slow] — it's a duplicate. I just move fast forward, do nothing.
Step 5: Return slow + 1 — the count of unique elements.

What to say on interview:

"Two Pointers works only on a sorted array. Time complexity is O(n) — each pointer moves at most n steps total. Space complexity is O(1) — no extra arrays, just two integer variables. Compare this to the HashMap approach for Two Sum — that's O(n) time but O(n) space. Two Pointers is better when the array is already sorted and we want to save memory. The key insight is: if the sum is too small, move left pointer right to get a bigger value. If the sum is too big, move right pointer left to get a smaller value." */