public class RunLengthEncoding {
    // Task: encode repeating characters with a count prefix
    // "aaabbc"   →  "3a2bc"
    // "aabbccdd" →  "2a2b2c2d"
    // "abcdddd"  →  "abc4d"
    // "aaaaa"    →  "5a"
    // Rule: if count == 1 → no number, just the letter

    /* Step 1: Understand the task
    I read the string character by character.
    If a character repeats — I count how many times, then write: count + letter.
    If a character appears only once — I write just the letter (no number).
    "aaabbc"
        ^^^ → 3 × 'a'  → "3a"
        ^^ → 2 × 'b'  → "2b"
        ^ → 1 × 'c'  → "c"
    Result: "3a2bc" */

    public static String encode(String s) {

        if (s == null || s.isEmpty()) return "";

        StringBuilder result = new StringBuilder();

        int i = 0;

        /* Step 2: Create outer loop
I create variable i = 0. This is my position in the string.
I run while (i < s.length()) — process every character. */

        while (i < s.length()) {

            /* Step 3: Get the current character
I read current = s.charAt(i).
I create count = 1 — at least 1 occurrence. */
            char current = s.charAt(i);
            int count = 1;

            // count how many times this char repeats
            /* Step 4: Inner loop — count repeats
I run inner while:
condition: next index is valid AND next char equals current
i + count < s.length() — don't go out of bounds
s.charAt(i + count) == current — still the same letter?
If yes → count++ */
            while (i + count < s.length()
                   && s.charAt(i + count) == current) {
                count++;
            }

            // if count > 1 → add number before letter
            /* Step 5: Write to result
If count > 1 → append the number first: result.append(count).
Always append the letter: result.append(current). */
            if (count > 1) {
                result.append(count);
            }
            result.append(current);

            /* Step 6: Jump forward
i += count — skip all repeated characters at once.
Now i points to the next different character. */
            i += count; // jump past all repeated chars
        }
        /* Step 7: Return result
After the loop — return result.toString(). */
        return result.toString();

    }

    public static void main(String[] args) {
        // Encode tests
        System.out.println(encode("aaabbc"));    // "3a2bc"
        System.out.println(encode("aabbccdd"));  // "2a2b2c2d"
        System.out.println(encode("abcdddd"));   // "abc4d"
        System.out.println(encode("aaaaa"));     // "5a"
        System.out.println(encode("a"));         // "a"
        System.out.println(encode("aab"));       // "2ab"
    }

}
/* What to say on interview:
"This is Run-Length Encoding — a simple lossless compression algorithm. Time complexity is O(n) — I visit each character exactly once. Space complexity is O(n) for the result StringBuilder. The key detail is: when count equals 1, I skip the number — that's what makes the encoding compact. The inner while loop doesn't make it O(n²) — both pointers together move at most n steps total. I also handle edge cases: null, empty string, single character, no repeats at all. The decode is the reverse: collect digits first, then read the letter, then repeat it count times." */