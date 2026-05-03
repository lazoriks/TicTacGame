public class ReverseLinkedList {

    // Node definition
    static class Node {
        int val;
        Node next;
        Node(int val) { this.val = val; }
    }

    // ── SOLUTION 1: Iterative ──────────────────────────────
    public static Node reverseIterative(Node head) {
        /*
         * Step 1: Create two pointers
         * I create prev = null — this will be the new next for each node.
         * I create current = head — this is the node I'm currently processing.
         */
        Node prev    = null;
        Node current = head;
        /*
         * Step 2: Start the loop
         * I run while (current != null) — process every node until the end.
         */
        while (current != null) {
            /*
             * Step 3: Save the next node
             * I create nextTemp = current.next.
             * If I don't save it now — after reversing the pointer I lose access to the
             * rest of the list.
             */
            Node nextTemp = current.next; // save next node
            /*
             * Step 4: Reverse the pointer
             * I set current.next = prev.
             * Now the current node points backwards instead of forwards.
             */
            current.next = prev; // reverse the pointer
            /*
             * Step 5: Move both pointers forward
             * I set prev = current — prev moves one step forward.
             */
            prev = current; // move prev forward
            /*
             * I set current = nextTemp — current moves one step forward using the saved
             * reference.
             */
            current = nextTemp; // move current forward
        }
        /*
         * Step 6: Return new head
         * When current == null — I've processed all nodes.
         * prev now points to the last node — which is the new head. I return prev.
         */
        return prev; // prev is the new head
    }

    // ── SOLUTION 2: Recursive ─────────────────────────────
    public static Node reverseRecursive(Node head) {
        if (head == null || head.next == null) return head;

        Node newHead = reverseRecursive(head.next); // go to the end
        head.next.next = head;  // reverse the pointer
        head.next = null;       // remove old pointer
        return newHead;
    }

    // ── Helper: build list ────────────────────────────────
    public static void main(String[] args) {
        // Build: 1 → 2 → 3 → 4 → 5
        Node head = new Node(1);
        head.next = new Node(2);
        head.next.next = new Node(3);
        head.next.next.next = new Node(4);
        head.next.next.next.next = new Node(5);

        Node reversed = reverseIterative(head);

        // Print: 5 → 4 → 3 → 2 → 1
        while (reversed != null) {
            System.out.print(reversed.val + " ");
            reversed = reversed.next;
        }
    }
}

/*
 * Visual example — 1 → 2 → 3 → null:
 * Start: prev=null current=1
 * 
 * Step 1: nextTemp=2
 * 1.next = null (reversed!)
 * prev=1, current=2
 * 
 * Step 2: nextTemp=3
 * 2.next = 1 (reversed!)
 * prev=2, current=3
 * 
 * Step 3: nextTemp=null
 * 3.next = 2 (reversed!)
 * prev=3, current=null
 * 
 * End: return prev=3
 * Result: 3 → 2 → 1 → null ✅
 * 
 * Recursive Solution
 * Step 1: Base case
 * If head == null or head.next == null — single node or empty list, already
 * reversed. Return head.
 * Step 2: Recurse to the end
 * I call reverseRecursive(head.next).
 * This goes deeper and deeper until it reaches the last node.
 * The last node becomes newHead — the new head of the reversed list.
 * Step 3: Reverse the pointer
 * head.next.next = head — the next node now points back to the current node.
 * Step 4: Remove the old pointer
 * head.next = null — cut the old forward pointer to avoid a cycle.
 * Step 5: Pass the new head up
 * I return newHead — it bubbles up through every recursive call unchanged.
 * 
 * Visual — recursive call stack for 1 → 2 → 3:
 * reverseRecursive(1)
 * reverseRecursive(2)
 * reverseRecursive(3)
 * return 3 ← base case, newHead=3
 * 3.next.next = 2 → 3.next = null ✗ wait:
 * 2.next.next = 2 → 3.next = 2
 * 2.next = null
 * return 3
 * 1.next.next = 1 → 2.next = 1
 * 1.next = null
 * return 3
 * 
 * Result: 3 → 2 → 1 → null ✅
 * 
 * "Iterative — O(n) time, O(1) space. Three pointers: prev, current, nextTemp. Recursive — O(n) time, O(n) space because of call stack. On interview I always offer both solutions and explain the trade-off. The most common mistake is forgetting to save nextTemp before reversing the pointer — then you lose the rest of the list."
 */