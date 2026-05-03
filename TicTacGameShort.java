import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicTacGameShort {
    public static void main(String[] args) {
        Scanner scann = new Scanner(System.in);

        System.out.println("=== Tic Tac Toe ===");

        List<Integer> nums1Player = new ArrayList<>();
        List<Integer> nums2Player = new ArrayList<>();
        printScore(nums1Player, nums2Player);

        // Play again loop
        while (true) {
            playGame(nums1Player, nums2Player, scann);

            System.out.print("Play again? (y/n): ");

            String answer = scann.next().trim().toLowerCase();
            if (!answer.equals("y")) {
                System.out.println("Thanks for playing!");
                break;
            }
            System.out.println();

            nums1Player.clear();
            nums2Player.clear();

        }
        scann.close();
    }

    static void playGame(List<Integer> nums1Player, List<Integer> nums2Player, Scanner scann) {
        // Game logic here
        boolean isWinner = false;

        for (int turn = 0; turn < 9; turn++) {
            int currentPlayer = (turn % 2) + 1;
            List<Integer> currentNums = (currentPlayer == 1) ? nums1Player : nums2Player;

            System.out.print("Player " + currentPlayer + ", enter your move (1-9): ");
            int move = scann.nextInt();

            // Validate move
            if (move < 1 || move > 9 || nums1Player.contains(move) || nums2Player.contains(move)) {
                System.out.println("Invalid move. Try again.");
                turn--; // Retry the same turn
                continue;
            }

            currentNums.add(move);
            printScore(nums1Player, nums2Player);

            // Check for win condition here (not implemented in this short version)
            isWinner = checkWiiner(nums1Player, nums2Player);
            if (isWinner) {
                System.out.println("Player " + currentPlayer + " wins!");
                break;
            }

        }
        
        if (!isWinner) {
            System.out.println("It's a draw!");
        }
    }

    static boolean checkWiiner(List<Integer> nums1Player, List<Integer> nums2Player) {
        // Win checking logic here (not implemented in this short version)
        // check different winning combinations
        // 1,2,3 - 4,5,6 - 7,8,9 - 1,4,7 - 2,5,8 - 3,6,9 - 1,5,9 - 3,5,7
        int[][] winningCombinations = {
                { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 9 }, // rows
                { 1, 4, 7 }, { 2, 5, 8 }, { 3, 6, 9 }, // columns
                { 1, 5, 9 }, { 3, 5, 7 } // diagonals
        };
        for (int[] combo : winningCombinations) {
            if (nums1Player.contains(combo[0]) && nums1Player.contains(combo[1]) && nums1Player.contains(combo[2])) {
                //System.err.println("Player 1 wins with combination: " + combo[0] + ", " + combo[1] + ", " + combo[2]);
                return true; // Player 1 wins
            }
            if (nums2Player.contains(combo[0]) && nums2Player.contains(combo[1]) && nums2Player.contains(combo[2])) {
                //System.err.println("Player 2 wins with combination: " + combo[0] + ", " + combo[1] + ", " + combo[2]);
                return true; // Player 2 wins
            }
        }

        return false; // Placeholder
    }

    static void printScore(List<Integer> nums1Player, List<Integer> nums2Player) {
        // Score printing logic h ere
        for (int i = 1; i <= 9; i++) {
            if (nums1Player.contains(i)) {
                System.out.print("X ");
            } else if (nums2Player.contains(i)) {
                System.out.print("O ");
            } else {
                System.out.print("- ");
            }
            if (i % 3 == 0) {
                System.out.println();
            }
        }
    }

}
