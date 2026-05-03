import java.util.Arrays;

public class CoinChange {

    // Task: find MINIMUM number of coins to make the amount
    // Example: coins = {1, 5, 6, 9}, amount = 11
    // Answer: 2  →  (9 + 2? No! 6 + 5 = 11) → 2 coins

    // ── SOLUTION 1: Naive Recursion (BAD) ──────────────────
    public static int coinChangeRecursive(int[] coins, int amount) {
        if (amount == 0) return 0;
        if (amount < 0)  return -1;

        int minCoins = Integer.MAX_VALUE;

        for (int coin : coins) {
            int result = coinChangeRecursive(coins, amount - coin);
            if (result >= 0) {
                minCoins = Math.min(minCoins, result + 1);
            }
        }
        return minCoins == Integer.MAX_VALUE ? -1 : minCoins;
    }
    // Exponential O(amount^coins) — too slow!

    // ── SOLUTION 2: Memoization (Top-Down DP) ──────────────
    public static int coinChangeMemo(int[] coins, int amount, int[] memo) {
        if (amount == 0) return 0;
        if (amount < 0)  return -1;
        if (memo[amount] != 0) return memo[amount]; // cached!

        int minCoins = Integer.MAX_VALUE;

        for (int coin : coins) {
            int result = coinChangeMemo(coins, amount - coin, memo);
            if (result >= 0) {
                minCoins = Math.min(minCoins, result + 1);
            }
        }

        memo[amount] = (minCoins == Integer.MAX_VALUE) ? -1 : minCoins;
        return memo[amount];
    }

    // ── SOLUTION 3: Tabulation (Bottom-Up DP) ✅ BEST ──────
    public static int coinChangeDP(int[] coins, int amount) {
        // dp[i] = minimum coins needed to make amount i
        /*
         * Step 2: Create the dp array
         * I create int[] dp = new int[amount + 1].
         * dp[i] means: "minimum coins needed to make amount i
         */
        int[] dp = new int[amount + 1];
        /*
         * I fill all values with amount + 1 — this acts as "infinity" (impossible).
         */
        // fill with "impossible" value
        Arrays.fill(dp, amount + 1);
        /*
        * Why amount + 1? Because you can never need more coins than the amount itself
        * (minimum coin = 1).
        */
        // base case: 0 coins needed for amount 0
        /*
         * Step 3: Set the base case
         * I set dp[0] = 0.
         * To make amount 0 — I need 0 coins. This is the foundation.
         */
        dp[0] = 0;
        /*
         * Step 4: Fill the table bottom-up
         * I loop i from 1 to amount.
         * For each amount i, I try every coin:
         * if coin <= i:
         * dp[i] = min(dp[i], dp[i - coin] + 1)
         * ↑ ↑ ↑
         * current best best way +1 for
         * to make this coin
         * (i - coin)
         */
        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    // can we do better using this coin?
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }
        /*
         * Meaning:
         * "If I use this coin, the remaining amount is i - coin. How many coins did THAT need? Add 1 for the current coin."
         */
        /*
         * Meaning:
         * "If I use this coin, the remaining amount is i - coin. How many coins did THAT need? Add 1 for the current coin."
         * Step 5: Visual — coins = {1, 5, 6}, amount = 11:
         * dp[0] = 0 → 0 coins (base)
         * dp[1] = 1 → 1 (1)
         * dp[2] = 2 → 1+1
         * dp[3] = 3 → 1+1+1
         * dp[4] = 4 → 1+1+1+1
         * dp[5] = 1 → 5
         * dp[6] = 1 → 6
         * dp[7] = 2 → 1+6
         * dp[8] = 2 → 1+1+6? No: 5+... wait:
         * min(dp[7]+1, dp[3]+1, dp[2]+1)
         * = min(3, 4, 3) = 3? Actually:
         * dp[8] = dp[7]+1 = 3 using coin 1
         * dp[8] = dp[3]+1 = 4 using coin 5
         * dp[8] = dp[2]+1 = 3 using coin 6 → 2+1=3
         * dp[9] = 2 → dp[3]+1=4, dp[4]+1=5, dp[3]+1=4
         * actually dp[8]+1=4, dp[4]+1=5, dp[3]+1=4
         * wait: coin=9 → dp[0]+1=1 ✅ → dp[9]=1!
         * dp[10] = 2 → 5+5
         * dp[11] = 2 → 5+6 ✅
         * Step 6: Return result
         * If dp[amount] > amount — it's still "infinity" — return -1 (impossible).
         * Otherwise return dp[amount].
         */
        return dp[amount] > amount ? -1 : dp[amount];
    }

    public static void main(String[] args) {
        int[] coins = {1, 5, 6, 9};

        System.out.println(coinChangeDP(coins, 11));  // 2  (5+6)
        System.out.println(coinChangeDP(coins, 3));   // 3  (1+1+1)
        System.out.println(coinChangeDP(coins, 0));   // 0
        System.out.println(coinChangeDP(new int[]{2}, 3)); // -1 (impossible)
    }
}

/* * "Coin Change is a classic DP problem. The greedy approach fails because locally optimal choices don't always lead to a globally optimal solution — for example coins {1,5,6,9} and amount 11: greedy picks 9 first, leaving 1+1 = 3 coins total. But 5+6 = 2 coins is better. DP builds the answer bottom-up: for each amount from 1 to target, we try every coin and ask: 'if I use this coin, what's the best solution for the remaining amount?' We already know that answer because we built it earlier. Time complexity O(amount × coins), space O(amount)."
*/