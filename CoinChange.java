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
        int[] dp = new int[amount + 1];

        // fill with "impossible" value
        Arrays.fill(dp, amount + 1);

        // base case: 0 coins needed for amount 0
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            for (int coin : coins) {
                if (coin <= i) {
                    // can we do better using this coin?
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

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