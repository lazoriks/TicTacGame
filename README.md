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

### Merge Sort
Що це?
Алгоритм сортування масиву чисел.
Аналогія з реального життя
Уяви стопку перемішаних карток. Ділиш навпіл, потім ще навпіл, поки не залишиться по 1 картці. Потім зливаєш пари: меншу вперед. Потім четвірки, вісімки — і так до кінця.
Кроки
1. Розділи масив навпіл
2. Рекурсивно сортуй кожну половину
3. Злий дві відсортовані половини в одну
Де використовується
Сортування великих даних. Гарантований O(n log n) навіть у найгіршому випадку.

### Quick Sort
Що це?
Алгоритм сортування, що використовує 'опорний елемент' (pivot).
Аналогія з реального життя
Сортуєш людей по зросту. Вибираєш одну людину як 'мірило'. Всіх нижчих — ліворуч, вищих — праворуч. Потім те саме для кожної групи.
Кроки
1. Вибери pivot (останній елемент)
2. Перемісти менші ліворуч, більші праворуч
3. Рекурсивно повтори для обох частин
Де використовується
Найшвидший на практиці для середніх випадків. Використовується в Arrays.sort() для примітивів.

### Binary Search
Що це?
Алгоритм пошуку числа у відсортованому масиві.
Аналогія з реального життя
Відкриваєш словник на середині. Слово раніше — гортаєш першу половину. Пізніше — другу. 1000 сторінок = максимум 10 кроків.
Кроки
1. Дивись на середній елемент
2. Якщо менший — шукай у правій половині
3. Якщо більший — шукай у лівій половині
4. Повторюй поки не знайдеш
Де використовується
Пошук у базах даних, автодоповнення, перевірка чи існує елемент.

### Two Pointers
Що це?
Техніка з двома вказівниками, що рухаються назустріч один одному.
Аналогія з реального життя
Ти на одному кінці коридору, друг — на іншому. Йдете назустріч. Сума мала → лівий крок вправо. Велика → правий крок вліво.
Кроки
1. Постав left=0, right=кінець масиву
2. Рахуй суму arr[left]+arr[right]
3. Мала сума → left++. Велика → right--
4. Рівна target → знайшли!
Де використовується
Пошук пари з заданою сумою, видалення дублікатів, перевірка паліндрому.

### Two Sum (HashMap)
Що це?
Знайти два числа у масиві, що дають задану суму. Масив не відсортований.
Аналогія з реального життя
Потрібна сума 10, маєш 3 — шукаєш 7. Замість ходити по всьому магазину — записуєш у блокнот кожне бачене число. Перевіряєш: 'чи є тут 7?'
Кроки
1. Для кожного числа рахуй complement = target - число
2. Перевір чи є complement у HashMap
3. Якщо є → знайшли пару!
4. Якщо ні → запиши поточне число у HashMap
Де використовується
Класична задача на співбесіді. Основа для складніших задач на хешування.

### Sliding Window
Що це?
Техніка 'вікна' що ковзає по масиву.
Аналогія з реального життя
Потяг із 3 вагонами. Кожен метр — новий вагон спереду, старий відчіпляється ззаду. Не рахуєш вагу знову — просто додаєш нову та відіймаєш стару.
Кроки
1. Рахуй суму першого вікна розміром k
2. Зсувай: додай правий елемент, відніми лівий
3. Оновлюй максимум після кожного зсуву
Де використовується
Максимальна сума k елементів, найдовша підрядок без повторів, усереднення.

### Reverse Linked List
Що це?
Розвернути однозв'язний список у зворотному напрямку.
Аналогія з реального життя
Ланцюжок людей — кожен тримає наступного. Змусити кожного тримати попереднього. Запам'ятовуєш наступного, розвертаєш руку назад, переходиш далі.
Кроки
1. prev=null, current=head
2. Збережи nextTemp=current.next
3. Розверни: current.next=prev
4. Рухайся: prev=current, current=nextTemp
5. Повертай prev — новий head
Де використовується
Базова задача на Linked List. Часта на будь-якому рівні співбесіди.

### Dynamic Programming (Fibonacci)
Що це?
Запам'ятовувати вже пораховані результати щоб не рахувати знову.
Аналогія з реального життя
Тебе питають '2+2?' — ти кажеш '4'. Через хвилину питають знову. Наївний — рахує знову. DP — запам'ятав і відразу відповідає. Для fib(50): різниця між 1 секундою і 1000 роками.
Кроки
1. Наївна рекурсія: fib(n)=fib(n-1)+fib(n-2) — дуже повільно
2. Memoization: зберігай у memo[], перевіряй перед рекурсією
3. Tabulation: заповнюй dp[] від 0 до n без рекурсії
4. Оптимізація: лише 2 змінні prev1 і prev2
Де використовується
Найпростіший приклад DP. Основа для Coin Change, Knapsack, LCS.

### Coin Change
Що це?
Знайти мінімальну кількість монет щоб скласти задану суму.
Аналогія з реального життя
Автомат з кавою. Треба решту 11 центів монетами {1,5,6,9}. Жадібний касир дасть 9+1+1=3 монети. Але 5+6=2 монети — краще! DP перебирає всі варіанти від 0 до 11 і запам'ятовує найкращий для кожної суми.
Кроки
1. Створи dp[] і заповни значенням 'нескінченність'
2. dp[0]=0 (для суми 0 потрібно 0 монет)
3. Для кожної суми від 1 до amount: спробуй кожну монету
4. dp[i] = min(dp[i], dp[i-coin]+1)
5. Якщо dp[amount]>amount → -1 (неможливо)
Де використовується
Класика DP на співбесідах. Банкомати, решта, задачі на оптимізацію.

### Knapsack (0/1)
Що це?
Вибрати предмети з максимальною цінністю, що вміщуються у рюкзак за вагою.
Аналогія з реального життя
Збираєшся у похід. Рюкзак витримує 5 кг. Маєш: книгу (2кг, цінність 6), ноутбук (3кг, цінність 10), камеру (4кг, цінність 12), телефон (1кг, цінність 4). Жадібний візьме найдорожче/легше — але DP знайде ідеальну комбінацію: ноутбук+книга = 5кг, цінність 16!
Кроки
1. Створи 2D таблицю dp[n+1][W+1]
2. dp[i][w] = максимальна цінність з перших i предметів при вазі w
3. Для кожного предмета: вибір — взяти або пропустити
4. Пропустити: dp[i][w] = dp[i-1][w]
5. Взяти: dp[i][w] = values[i-1] + dp[i-1][w-weights[i-1]]
6. Обираєш максимум з двох варіантів
Де використовується
Вибір акцій/інвестицій, планування ресурсів, задачі на оптимізацію вибору.

