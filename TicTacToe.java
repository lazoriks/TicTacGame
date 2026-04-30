import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TicTacToe {
    //static board
    static char[][] board = new char[3][3];
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    static int playerWins = 0;
    static int aiWins = 0;
    static int draws = 0;
    static char currentPlayer = 'X';

    public static void main(String[] args) {

        System.out.println("=== Tic Tac Toe ===");
        System.out.println("You are X | AI is O");
        System.out.println("Positions: 1-9 (left to right, top to bottom)\n");

        // Play again loop
        while (true) {
            playGame();
            printScore();

            System.out.print("Play again? (y/n): ");
            String answer = scanner.next().trim().toLowerCase();
            if (!answer.equals("y")) {
                System.out.println("Thanks for playing!");
                break;
            }
            System.out.println();
        }

        scanner.close();

    }

    // ---------------------------------------------------------
    // One full game
    // ---------------------------------------------------------
    static void playGame() {
        initBoard();
        char currentPlayer = 'X'; // X = human, O = AI

        while (true) {
            printBoard();

            if (currentPlayer == 'X') {
                humanMove();
            } else {
                aiMove();
            }

            if (checkWinner(currentPlayer)) {
                printBoard();
                if (currentPlayer == 'X') {
                    System.out.println("You win!");
                    playerWins++;
                } else {
                    System.out.println("AI wins!");
                    aiWins++;
                }
                break;
            }

            if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                draws++;
                break;
            }

            // Switch turn
            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    // ---------------------------------------------------------
    // Human move: ask for input, validate, place mark
    // ---------------------------------------------------------
    static void humanMove() {
        while (true) {
            System.out.print("Your move (1-9): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Enter a number!");
                scanner.next(); // discard invalid input
                continue;
            }

            int pos = scanner.nextInt();

            if (pos < 1 || pos > 9) {
                System.out.println("Position must be 1-9.");
                continue;
            }

            int row = (pos - 1) / 3;
            int col = (pos - 1) % 3;

            if (board[row][col] != ' ') {
                System.out.println("Cell already taken! Choose another.");
                continue;
            }

            board[row][col] = 'X';
            break;
        }
    }

    // ---------------------------------------------------------
    // AI move: pick a random empty cell
    // ---------------------------------------------------------
    /* static void aiMove() {
        // Collect all empty positions as flat indexes (0-8)
        List<Integer> emptyPositions = new ArrayList<>();
        for (int i = 0; i < 3; i++)
            for (int j = 0; j < 3; j++)
                if (board[i][j] == ' ')
                    emptyPositions.add(i * 3 + j);

        // Pick one randomly
        int flatIndex = emptyPositions.get(random.nextInt(emptyPositions.size()));
        int row = flatIndex / 3;
        int col = flatIndex % 3;

        board[row][col] = 'O';
        System.out.println("AI placed at position " + (flatIndex + 1));
    } */

    // ---------------------------------------------------------
    // AI move — 3-step priority algorithm:
    //   1. Win now  → take winning cell
    //   2. Block    → block player's winning cell
    //   3. Random   → pick a random empty cell
    // ---------------------------------------------------------
    static void aiMove() {
        int pos = -1;
 
        // Step 1: Can AI win in one move?
        pos = findWinningMove('O');
        if (pos != -1) {
            placeAt(pos, 'O');
            System.out.println("AI placed at position " + (pos + 1));
            return;
        }
 
        // Step 2: Can player win in one move? Block it.
        pos = findWinningMove('X');
        if (pos != -1) {
            placeAt(pos, 'O');
            System.out.println("AI placed at position " + (pos + 1));
            return;
        }
 
        // Step 3: No critical move — pick randomly
        List<Integer> emptyPositions = new ArrayList<>();
        for (int i = 0; i < 9; i++)
            if (board[i / 3][i % 3] == ' ')
                emptyPositions.add(i);
 
        int flatIndex = emptyPositions.get(random.nextInt(emptyPositions.size()));
        placeAt(flatIndex, 'O');
        System.out.println("AI placed at position " + (flatIndex + 1));
    }
 
    /**
     * Scans all empty cells to find a move that would make `player` win.
     * Returns the flat index (0-8) of that cell, or -1 if none found.
     */
    static int findWinningMove(char player) {
        for (int i = 0; i < 9; i++) {
            int row = i / 3;
            int col = i % 3;
 
            if (board[row][col] != ' ') continue; // skip occupied cells
 
            // Temporarily place the mark
            board[row][col] = player;
 
            boolean wins = checkWinner(player);
 
            // Undo the move5
            board[row][col] = ' ';
 
            if (wins) return i; // found a winning cell
        }
        return -1; // no winning move
    }
 
    // Place mark at flat index
    static void placeAt(int flatIndex, char mark) {
        board[flatIndex / 3][flatIndex % 3] = mark;
    }

    //fill board with empty slots
    static void initBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    static void printBoard() {
        System.out.println();
        for (int i = 0; i < 3; i++) {
            System.out.println(" " + board[i][0] + " | " + board[i][1] + " | " + board[i][2]);
            if (i < 2) System.out.println("---|---|---");
        }
        System.out.println();
    }
    

    static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    static boolean checkWinner(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) return true;
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) return true;
        }
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) return true;
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) return true;
        return false;
    }

    static void printScore() {
        System.out.println("--- Score ---");
        System.out.println("You: " + playerWins + " | AI: " + aiWins + " | Draws: " + draws);
        System.out.println("-------------");
    }
}