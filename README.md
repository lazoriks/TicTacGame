/*
Спочатку імпортую Сканнер для роботи з введденням від юзера.
Будемо мати мейн метод відповідно.
оголошуємо наш сканнер тут.
і його ж закриємо по закінченні мейн методу.
просте прінт про початок гри
створюємо 2 ліста для 2 гравців для збереження введених чисел від них.
і прінт борда нашого:
 - в метод передаємо 2 наших ліста гравців
- робимо фор для 9 чисел
- якщо лист 1 гравця містить інтератор – то прінт «Х»
- якщо лист 2 гравця містить інтератор – то прінт «0»
- якщо не то не то – то прінт «-»
- перевіряємо на кратність 3 і прінт нового рядка
створюємо цикл для гри
основний метод гри з параметрами листи гравців і скан
оскільки це в нас цикл по грі – то видаємо питання про продовження гри – “y/n”
скануємо відповідь і якщо не «y» - то перериваємо наш цикл.
новий рядок і очищаємо листи.

тепер основний метод гри:
створюємо цикл з 9 чисел(максимум щоб заповнити наш борд)
оголошуємо перемінну поточного гравця за допомогою інтератора
також створюємо перемінну лист для запису поточного листа поточного гравця
і скопіюємо лист поточного гравця в цей лист
прінт запит для вводу числа
скан працює
перевіряємо на валідацію вводу
обовязково декремент якщо не валідоване значення

додаємо до поточного листа поточного гравця
прінт борда з введеним числом

створюємо метод для перевірки переможця з параметрами листи гравців
отримане значення передаємо для перевірки переможця і при виході з цикла 9 чисел для перевірки на ничію

метод перевірки на виграш з параметрами листи гравців
створюємо масив з масивами комбінацій виграшу по 3 числа
проходимо по циклу виграшних комбінацій
по кожному масиву комбінацій і перевіряємо чи містить лист гравця число з комбінації
якщо 3 числа з виграшної комбінації є в листі то повертаємо істину
після закінчення циклу повертаємо хибність
*/

# My Tic-Tac-Toe Game Explanation (Simple English)
## Step 1: Preparation
First, I import Scanner to read user input.

Then I create the main method.

I create a Scanner object and close it at the end.

I print a simple message: "=== Tic Tac Toe ===".

## Step 2: Create two lists for players
I create two lists: one for Player 1 and one for Player 2.

These lists store the numbers (positions) that each player chooses.

## Step 3: Print the board
I create a method to print the board. I pass the two player lists to this method.

I make a loop from 1 to 9.

If Player 1's list contains the number → print "X"

If Player 2's list contains the number → print "O"

If no one has the number → print "-"

Every 3 numbers, I print a new line.

## Step 4: Game loop (play again)
I create a loop to play the game.

Inside the loop, I call the main game method.

After the game ends, I ask: "Play again? (y/n)"

I read the answer. If the answer is not "y", I break the loop.

If we play again, I clear both player lists.

## Step 5: Main game method
I create a loop for 9 turns (maximum moves).

I calculate the current player using the turn number:

Even turn → Player 1

Odd turn → Player 2

I create a variable currentList that points to the correct player's list.

I ask the player: "Enter your move (1-9)"

I read the number using Scanner.

## Step 6: Validation
I check if the move is between 1 and 9.

I check if the number is already in Player 1's list or Player 2's list.

If the move is invalid, I print "Invalid move. Try again."

I decrement turn so the same player can try again.

## Step 7: Save the move and print the board
If the move is valid, I add the number to the current player's list.

I call the printScore method to show the updated board.

## Step 8: Check for winner
I call a method checkWinner and pass both player lists.

If the method returns true, I print "Player X wins!" and exit the game loop.

## Step 9: Check for draw
After the loop (9 turns with no winner), I print "It's a draw!"

## Step 10: Winner check method
I create a method checkWinner with two parameters: nums1Player and nums2Player.

Inside, I create a 2D array of winning combinations:


{1,2,3}, {4,5,6}, {7,8,9},  // rows
{1,4,7}, {2,5,8}, {3,6,9},  // columns
{1,5,9}, {3,5,7}            // diagonals

I loop through each combination.

For each combination, I check if all three numbers are in Player 1's list.

If yes → return true (Player 1 wins).

Then I check if all three numbers are in Player 2's list.

If yes → return true (Player 2 wins).

After the loop, if no winner found → return false.

## Short summary (for speaking during interview)

1. Scanner for user input
2. Two lists for two players
3. Print board method: loop 1-9, print X/O/-
4. Main game loop: 9 turns max
5. Validate move: between 1-9, not taken
6. Add move to player's list
7. Print updated board
8. Check winner using combinations array
9. If no winner after 9 turns → draw
10. Ask to play again