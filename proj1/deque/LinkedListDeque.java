package deque;

public class LinkedListDeque<T> {
    private Node sentinel;
    private int size;
    /** Node. */
    private class Node {
        public T item;
        public Node prev;
        public Node next;

        /** Constructor. */
        Node(T i, Node prev, Node next) {
            item = i;
            this.prev = prev;
            this.next = next;
        }
    }

    /**
     * Create an empty linked list deque.
     */
    public LinkedListDeque() {
        sentinel = new Node(null, null, null);
        sentinel.next = sentinel;
        sentinel.prev = sentinel;
        size = 0;
    }

    /** Adds an item to the front of the deque. */
    public void addFirst(T item) {
        Node addNode = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = addNode;
        sentinel.next = addNode;
        size += 1;
    }

    /** Adds an item to the back of the deque. */
    public void addLast(T item) {
        Node addNode = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = addNode;
        sentinel.prev = addNode;
        size += 1;
    }

    /** Returns true if the deque is empty, false otherwise. */
    public boolean isEmpty() {
        return size == 0;
    }

    /** Return the number of items in the deque. */
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    public void printDeque() {
        Node p = sentinel.next;
        while (p != sentinel) {
            System.out.print(p.item + " ");
            p = p.next;
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst() {
        Node firstNode = sentinel.next;
        if (firstNode == sentinel) {
            return null;
        } else {
            sentinel.next = firstNode.next;
            firstNode.next.prev = sentinel;
        }
        size -= 1;
        return firstNode.item;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        Node lastNode = sentinel.prev;
        if (lastNode == sentinel) {
            return null;
        } else {
            sentinel.prev = lastNode.prev;
            lastNode.prev.next = sentinel;
        }
        size -= 1;
        return lastNode.item;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     */
    public T get(int index) {
        Node p = sentinel.next;
        int i = 0;
        while (p != sentinel && i < index) {
            i += 1;
            p = p.next;
        }
        if (i < index) {
            return null;
        } else {
            return p.item;
        }
    }

    /**
     * If the index of p is i, return the item at index i + offset.
     * If no such item exists, return null.
     */
    private T getRecursive(Node p, int offset) {
        // Base case.
        if (p == sentinel) {
            return null;
        }
        if (offset == 0) {
            return p.item;
        }
        return getRecursive(p.next, offset - 1);
    }

    /** Same as get, but uses recursion. */
    public T getRecursive(int index) {
        return getRecursive(sentinel.next, index);
    }

}
