import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        // key = number, value = its index
        /*
         * Step 1: Create a HashMap
         * I create Map<Integer, Integer>.
         * The key stores the number from the array.
         * The value stores its index.
         * This lets me find any previous number in O(1) time.
         */
        Map<Integer, Integer> map = new HashMap<>();

        /*
         * Step 2: Loop through the array
         * I loop with index i from 0 to nums.length - 1.
         */
        for (int i = 0; i < nums.length; i++) {
            /*
             * Step 3: Calculate the complement
             * For each element nums[i], I calculate: complement = target - nums[i].
             * This is the number I need to find to make the pair.
             */
            int complement = target - nums[i]; // чого не вистачає?

            /*
             * Step 4: Check the HashMap
             * I check map.containsKey(complement).
             * If yes — I already saw this number earlier!
             * I return both indices: map.get(complement) (the old index)
             * and i (the current index).
             */
            if (map.containsKey(complement)) {
                // знайшли пару!
                return new int[] { map.get(complement), i };
            }
            /*
             * Step 5: Store current element
             * If the complement is not found,
             * I put the current element into the map: map.put(nums[i], i).
             * Now future elements can find it.
             */
            map.put(nums[i], i);
        }
        /*
         * Step 6: Return result
         * If we finish the loop with no pair found
         * * — return an empty array.
         */
        return new int[] {}; // пара не знайдена
    }

    public static void main(String[] args) {
        int[] nums = { 2, 7, 11, 15 };
        int target = 9;
        System.out.println(Arrays.toString(twoSum(nums, target)));
        // [0, 1] → nums[0] + nums[1] = 2 + 7 = 9
    }
}

/*
 * What to say on interview:
 * 
 * "This is a classic O(n) time, O(n) space solution using HashMap.
 * The naive brute-force approach would be O(n²) with two nested loops.
 * The key insight is: instead of looking for the pair,
 * I look for what's missing — the complement.
 * HashMap gives O(1) lookup."
 */