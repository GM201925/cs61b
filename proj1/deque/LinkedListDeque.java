package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    private Node sentinel;
    private int size;
    /** Node. */
    private class Node {
        private T item;
        private Node prev;
        private Node next;

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
    @Override
    public void addFirst(T item) {
        Node addNode = new Node(item, sentinel, sentinel.next);
        sentinel.next.prev = addNode;
        sentinel.next = addNode;
        size += 1;
    }

    /** Adds an item to the back of the deque. */
    @Override
    public void addLast(T item) {
        Node addNode = new Node(item, sentinel.prev, sentinel);
        sentinel.prev.next = addNode;
        sentinel.prev = addNode;
        size += 1;
    }

    /** Return the number of items in the deque. */
    @Override
    public int size() {
        return size;
    }

    /**
     * Prints the items in the deque from first to last, separated by a space.
     * Once all the items have been printed, print out a new line.
     */
    @Override
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
    @Override
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
    @Override
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
    @Override
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

    private class LinkedListDequeIterator implements Iterator<T> {
        private Node current;
        LinkedListDequeIterator() {
            current = sentinel.next;
        }

        @Override
        public boolean hasNext() {
            return current != sentinel;
        }

        @Override
        public T next() {
            T returnItem = current.item;
            current = current.next;
            return returnItem;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new LinkedListDequeIterator();
    }

    /**
     * Returns whether or not the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the same
     * contents (as goverened by the generic T’s equals method) in the same order.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Deque<?>) {
            Deque<?> deque = (Deque<?>) o;
            if (this.size() != deque.size()) {
                return false;
            }

            Iterator<T> mine = iterator();
            int i = 0;
            while (mine.hasNext()) {
                if (!java.util.Objects.equals(mine.next(), deque.get(i))) {
                    return false;
                }
                i += 1;
            }
            return true;
        }
        return false;
    }

}
