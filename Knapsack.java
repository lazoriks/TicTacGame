public class Knapsack {

    // Task: maximum VALUE you can fit in a bag with weight limit W
    // Each item: either take it (1) or leave it (0) — can't split!
    //
    // items:   {name,  weight, value}
    //          {Book,    2,     6  }
    //          {Laptop,  3,     10 }
    //          {Camera,  4,     12 }
    //          {Phone,   1,     4  }
    // W = 5 (bag limit)
    // Answer: Laptop(3) + Phone(1) = weight 4, value 14 ✅
    
    /*
     * Step 1: Understand the problem
     * I have a bag with weight limit W = 5.
     * I have n items — each has a weight and a value.
     * I can either take an item or leave it — I cannot split it.
     * Goal: maximize total value without exceeding weight limit.
     * items: weight value
     * Book 2 6
     * Laptop 3 10
     * Camera 4 12
     * Phone 1 4
     * 
     * All items = weight 10 → too heavy!
     * Best choice: Laptop(3) + Phone(1) = weight 4, value 14 ✅
     */

    // ── SOLUTION 1: Naive Recursion (BAD) ──────────────────
    public static int knapsackRecursive(int[] weights, int[] values,
                                        int W, int n) {
        if (n == 0 || W == 0) return 0; // base case

        // item is too heavy — skip it
        if (weights[n-1] > W) {
            return knapsackRecursive(weights, values, W, n - 1);
        }

        // choice: take item OR skip item → pick the better one
        int take = values[n-1] +
                   knapsackRecursive(weights, values, W - weights[n-1], n-1);
        int skip = knapsackRecursive(weights, values, W, n - 1);

        return Math.max(take, skip);
    }
    // O(2^n) — exponential, unusable for large n

    // ── SOLUTION 2: Tabulation (Bottom-Up DP) ✅ BEST ──────
    public static int knapsackDP(int[] weights, int[] values,
                                 int W, int n) {
        // dp[i][w] = max value using first i items, with weight limit w
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {

                // option 1: skip current item
                dp[i][w] = dp[i-1][w];

                // option 2: take current item (if it fits)
                if (weights[i-1] <= w) {
                    int takeValue = values[i-1] + dp[i-1][w - weights[i-1]];
                    dp[i][w] = Math.max(dp[i][w], takeValue);
                }
            }
        }
        return dp[n][W];
    }

    // ── BONUS: Which items were taken? ─────────────────────
    public static void printItems(int[] weights, int[] values,
                                  int W, int n) {
        int[][] dp = new int[n + 1][W + 1];

        for (int i = 1; i <= n; i++) {
            for (int w = 0; w <= W; w++) {
                dp[i][w] = dp[i-1][w];
                if (weights[i-1] <= w) {
                    int takeValue = values[i-1] + dp[i-1][w - weights[i-1]];
                    dp[i][w] = Math.max(dp[i][w], takeValue);
                }
            }
        }

        // trace back which items were chosen
        System.out.print("Items taken: ");
        int w = W;
        for (int i = n; i > 0; i--) {
            if (dp[i][w] != dp[i-1][w]) {       // value changed → item taken
                System.out.print("item" + i + " ");
                w -= weights[i-1];               // reduce remaining capacity
            }
        }
    }

    public static void main(String[] args) {
        int[] weights = {2, 3, 4, 1};
        int[] values  = {6, 10, 12, 4};
        int W = 5;
        int n = weights.length;

        System.out.println(knapsackDP(weights, values, W, n)); // 14
        printItems(weights, values, W, n); // item4 item2 (Phone + Laptop)
    }
}

/*
 * Step 2: Why greedy fails
 * Sort by value/weight ratio:
 * Book = 6/2 = 3.0
 * Phone = 4/1 = 4.0 ← greedy picks first
 * Laptop = 10/3 = 3.3
 * Camera = 12/4 = 3.0
 * 
 * Greedy: Phone(1) + Book(2) = weight 3, value 10 ← only 10!
 * Optimal: Phone(1) + Laptop(3) = weight 4, value 14 ✅
 * Greedy picks the "best ratio" item but misses the true optimal combination.
 * That's why we need DP.
 * 
 * Step 3: Create the 2D dp table
 * I create int[][] dp = new int[n+1][W+1].
 * 
 * Rows = items (0 to n)
 * Columns = weight capacity (0 to W)
 * dp[i][w] = max value using first i items with weight limit w
 * 
 * w=0 w=1 w=2 w=3 w=4 w=5
 * i=0 0 0 0 0 0 0 ← no items
 * i=1 ? ? ? ? ? ? ← consider Book
 * i=2 ? ? ? ? ? ? ← consider Laptop
 * i=3 ? ? ? ? ? ? ← consider Camera
 * i=4 ? ? ? ? ? ? ← consider Phone
 * 
 * Step 4: Fill the table — two choices per cell
 * For every item i and every capacity w I have exactly two choices:
 * Choice 1 — Skip item i:
 * dp[i][w] = dp[i-1][w]
 * Same value as without this item.
 * Choice 2 — Take item i (only if it fits: weights[i-1] <= w):
 * dp[i][w] = values[i-1] + dp[i-1][w - weights[i-1]]
 * Current item's value + best value from remaining capacity using previous
 * items.
 * I always pick the maximum of both choices.
 * 
 * Step 5: Visual — filling the table
 * weights = {2, 3, 4, 1}
 * values = {6, 10, 12, 4}
 * W = 5
 * 
 * w=0 w=1 w=2 w=3 w=4 w=5
 * i=0 0 0 0 0 0 0
 * 
 * i=1 Book(w=2, v=6):
 * w=0,1: can't fit → copy row above → 0, 0
 * w=2: skip=0, take=6+dp[0][0]=6 → max=6
 * w=3: skip=0, take=6+dp[0][1]=6 → max=6
 * w=4: skip=0, take=6+dp[0][2]=6 → max=6
 * w=5: skip=0, take=6+dp[0][3]=6 → max=6
 * → row: [0, 0, 6, 6, 6, 6]
 * 
 * i=2 Laptop(w=3, v=10):
 * w=0,1,2: can't fit → copy [0, 0, 6]
 * w=3: skip=6, take=10+dp[1][0]=10 → max=10
 * w=4: skip=6, take=10+dp[1][1]=10 → max=10
 * w=5: skip=6, take=10+dp[1][2]=16 → max=16
 * → row: [0, 0, 6, 10, 10, 16]
 * 
 * i=3 Camera(w=4, v=12):
 * w=0,1,2,3: can't fit → copy [0, 0, 6, 10]
 * w=4: skip=10, take=12+dp[2][0]=12 → max=12
 * w=5: skip=16, take=12+dp[2][1]=12 → max=16
 * → row: [0, 0, 6, 10, 12, 16]
 * 
 * i=4 Phone(w=1, v=4):
 * w=0: can't fit → 0
 * w=1: skip=0, take=4+dp[3][0]=4 → max=4
 * w=2: skip=6, take=4+dp[3][1]=4 → max=6
 * w=3: skip=10, take=4+dp[3][2]=10 → max=10
 * w=4: skip=12, take=4+dp[3][3]=14 → max=14 ✅
 * w=5: skip=16, take=4+dp[3][4]=16 → max=16
 * → row: [0, 4, 6, 10, 14, 16]
 * 
 * Answer: dp[4][5] = 16
 * Wait — answer is 16! Let me check:
 * Book(2) + Laptop(3) = weight 5, value 16 ✅
 * Even better than 14!
 * 
 * Step 6: Traceback — which items were taken?
 * Start at dp[4][5] = 16
 * dp[4][5] != dp[3][5]? 16 != 16 → NO, Phone not taken, w stays 5
 * dp[3][5] != dp[2][5]? 16 != 16 → NO, Camera not taken
 * dp[2][5] != dp[1][5]? 16 != 6 → YES! Laptop taken, w = 5-3 = 2
 * dp[1][2] != dp[0][2]? 6 != 0 → YES! Book taken, w = 2-2 = 0
 * 
 * Items taken: Book + Laptop = value 16 ✅
 * 
 * What to say on interview:
 * 
 * "0/1 Knapsack is the classic DP problem. The key insight is: for each item I have exactly two choices — take it or skip it. I build a 2D table where dp[i][w] stores the maximum value using the first i items with capacity w. Each cell is max(skip, take). Skip copies the row above. Take adds the current item's value plus the best solution for the remaining capacity. Time complexity O(n × W), space O(n × W). I can optimize space to O(W) using a single 1D array filled from right to left — this avoids using the same item twice."
 */