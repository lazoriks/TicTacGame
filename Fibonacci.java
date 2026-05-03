public class Fibonacci {

    // ── SOLUTION 1: Naive Recursion (BAD) ─────────────────
    public static int fibRecursive(int n) {
        /*
         * Step 1: Base case
         * If n == 0 → return 0.
         * If n == 1 → return 1.
         * These are the two starting values of Fibonacci.
         */
        if (n <= 1)
            return n;
        /*
         * Step 2: Recursive call
         * I return fibRecursive(n-1) + fibRecursive(n-2).
         * This looks simple but hides a big problem.
         */
        return fibRecursive(n - 1) + fibRecursive(n - 2);
    }
    // fib(5) → fib(4) + fib(3)
    // fib(3) + fib(2) fib(2) + fib(1)
    // ...
    // fib(3) is calculated TWICE — very wasteful!
    /*
     * Step 3: The problem — repeated work
     * fib(5)
     * ├── fib(4)
     * │ ├── fib(3)
     * │ │ ├── fib(2) ← calculated here
     * │ │ └── fib(1)
     * │ └── fib(2) ← calculated AGAIN!
     * └── fib(3) ← calculated AGAIN!
     * ├── fib(2) ← calculated AGAIN!
     * └── fib(1)
     * For fib(50) — over 1 billion redundant calls.
     * Time complexity: O(2ⁿ) — exponential, unusable for large n.
     */

    // ── SOLUTION 2: Memoization (Top-Down DP) ─────────────
    public static int fibMemo(int n, int[] memo) {
         /*
         * Step 1: Base case
         * If n == 0 → return 0.
         * If n == 1 → return 1.
         * These are the two starting values of Fibonacci.
         */
         if (n <= 1)
             return n;
         /*
          * Step 1: Create memo array
          * I create int[] memo = new int[n + 1].
          * All values are 0 by default — meaning "not calculated yet."
          */
         /*
          * Step 3: Check memo first
          * Before calculating, I check if (memo[n] != 0) return memo[n].
          * If I already calculated this value — return it immediately. No recursion
          * needed.
          */
         if (memo[n] != 0)
             return memo[n]; // already calculated!
         /*
          * Step 4: Calculate and save
          * If not in memo — I calculate fibMemo(n-1, memo) + fibMemo(n-2, memo).
          * I save the result: memo[n] = result.
          * Next time someone asks for fib(n) — it's instant.
          */
         memo[n] = fibMemo(n - 1, memo) + fibMemo(n - 2, memo);
         return memo[n];
     }

     /*
      * Step 5: Each value calculated only once
      * fib(5) with memo:
      * ├── fib(4)
      * │ ├── fib(3)
      * │ │ ├── fib(2) → saved in memo[2]
      * │ │ └── fib(1)
      * │ └── fib(2) → memo[2] exists! return instantly ✅
      * └── fib(3) → memo[3] exists! return instantly ✅
      * Time complexity: O(n).
      * Space complexity: O(n) for memo array + O(n) call stack.
      */
    // ── SOLUTION 3: Tabulation (Bottom-Up DP) ─────────────
    public static int fibTabulation(int n) {
        /*
         * Step 1: Handle base cases
         * If n <= 1 — return n immediately.
         */
        if (n <= 1)
            return n;
        /*
         * Step 2: Create dp table
         * I create int[] dp = new int[n + 1].
         * This array will store every Fibonacci number from 0 to n.
         */
        int[] dp = new int[n + 1];
        /*
         * Step 3: Fill starting values
         * I set dp[0] = 0 and dp[1] = 1.
         * These are the two known values — the foundation.
         */
        dp[0] = 0;
        dp[1] = 1;
        /*
         * Step 4: Fill the table bottom-up
         * I loop from i = 2 to n.
         * For each i: dp[i] = dp[i-1] + dp[i-2].
         * I build the answer from small to big — no recursion needed.
         */
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        /*
         * Step 5: Return the answer
         * I return dp[n] — the last value in the table.
         */
        return dp[n];
    }

    /*
     * Visual — dp table for n=7:
     * Index: 0 1 2 3 4 5 6 7
     * Value: 0 1 1 2 3 5 8 13
     * ↑ ↑
     * dp[0]+dp[1]=1
     * ↑ ↑
     * dp[1]+dp[2]=2 ... and so on
     * Time complexity: O(n).
     * Space complexity: O(n) for dp array. No call stack.
     */
    // ── SOLUTION 4: Optimized Space ───────────────────────
    public static int fibOptimized(int n) {
        /*
         * Step 1: Handle base case
         * If n <= 1 — return n.
         */
        if (n <= 1)
            return n;
        /*
         * Step 2: Create only two variables
         * I create prev2 = 0 — represents fib(i-2).
         * I create prev1 = 1 — represents fib(i-1).
         * I don't need the whole array — only the last two values.
         */
        int prev2 = 0; // fib(0)
        int prev1 = 1; // fib(1)
        /*
         * Step 3: Loop from 2 to n
         * For each step:
         * 
         * I calculate current = prev1 + prev2
         * I shift: prev2 = prev1
         * I shift: prev1 = current
         */
        for (int i = 2; i <= n; i++) {
            int current = prev1 + prev2;
            prev2 = prev1;
            prev1 = current;
        }
        /*
         * Step 4: Return prev1
         * After the loop — prev1 holds fib(n). Return it.
         */
        return prev1;
    }
    /*
     * Visual — variables for n=6:
     * Start: prev2=0, prev1=1
     * i=2: current=1, prev2=1, prev1=1
     * i=3: current=2, prev2=1, prev1=2
     * i=4: current=3, prev2=2, prev1=3
     * i=5: current=5, prev2=3, prev1=5
     * i=6: current=8, prev2=5, prev1=8
     * 
     * return 8 ✅
     * Time complexity: O(n).
     * Space complexity: O(1) — only two variables!
     */

    public static void main(String[] args) {
        int n = 10;
        int[] memo = new int[n + 1];

        System.out.println(fibRecursive(n));    // 55 — slow!
        System.out.println(fibMemo(n, memo));   // 55 — fast
        System.out.println(fibTabulation(n));   // 55 — fast
        System.out.println(fibOptimized(n));    // 55 — fastest
    }
}

/*
 * What is Dynamic Programming?
 * DP = Recursion + Memory
 * 
 * Use DP when:
 * 1. Problem has OVERLAPPING subproblems
 * (same smaller problems appear again and again)
 * 
 * 2. Problem has OPTIMAL SUBSTRUCTURE
 * (optimal answer built from optimal smaller answers)
 * 
 * Classic DP problems:
 * ├── Fibonacci ← we just solved this
 * ├── Knapsack ← fit max value in a bag
 * ├── Longest Common ← longest matching substring
 * │ Subsequence (LCS)
 * ├── Coin Change ← min coins to make amount
 * └── Climbing Stairs ← number of ways to reach top
 * 
 * What to say on interview:
 * 
 * "I always start with the naive recursive solution to explain the logic, then identify the problem — overlapping subproblems. Then I introduce memoization: same recursion but we cache results in a memo array. That brings it from O(2ⁿ) down to O(n). Then I go bottom-up with tabulation — no recursion, no call stack risk. Finally I optimize space: since each step only needs the previous two values, I replace the array with just two variables — O(n) time, O(1) space. This progression shows I understand not just the solution but the thinking behind Dynamic Programming."
 */