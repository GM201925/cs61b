package deque;

public class ArrayDeque<T> {
    private T[] array;
    private int size;
    private int nextFirst;
    private int nextLast;
    /** Creates an empty array deque. */
    public ArrayDeque() {
        array = (T[]) new Object[8];
        size = 0;
        nextFirst = array.length;
        nextLast = 0;
    }

    /** Resize the array. */
    private void resize(int capacity) {
        T[] tmp = (T[]) new Object[capacity];
        int first = (nextFirst + 1 + array.length) % array.length;
        for (int i = 0; i < size; ++i) {
            int index = (first + i) % array.length;
            tmp[i] = array[index];
        }
        array = tmp;
        nextFirst = array.length - 1;
        nextLast = size;
    }

    /** Adds an item of type T to the front of the deque. */
    public void addFirst(T item) {
        if (size == array.length) {
            resize(size * 2);
        }
        array[nextFirst] = item;
        size += 1;
        nextFirst = (nextFirst - 1 + array.length) % array.length;
    }

    /** Adds an item to the back of the deque. */
    public void addLast(T item) {
        if (size == array.length) {
            resize(size * 2);
        }
        array[nextLast] = item;
        size += 1;
        nextLast = (nextLast + 1) % array.length;
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
        int first = (nextFirst + 1) % array.length;
        for (int i = 0; i < size; ++i) {
            int index = (first + i) % array.length;
            System.out.print(array[index] + " ");
        }
        System.out.println();
    }

    /**
     * Removes and returns the item at the front of the deque.
     * If no such item exists, returns null.
     */
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int first = (nextFirst + 1) % array.length;
        nextFirst = first;
        size -= 1;
        T removeItem = array[first];
        array[first] = null;

        double usageFactor = size * 1.0 / array.length;
        if (array.length >= 16 && usageFactor < 0.25) {
            resize(array.length / 2);
        }
        return removeItem;
    }

    /**
     * Removes and returns the item at the back of the deque.
     * If no such item exists, returns null.
     */
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int last = (nextLast - 1 + array.length) % array.length;
        nextLast = last;
        size -= 1;
        T removeItem = array[last];
        array[last] = null;

        double usageFactor = size * 1.0 / array.length;
        if (array.length >= 16 && usageFactor < 0.25) {
            resize(array.length / 2);
        }
        return removeItem;
    }

    /**
     * Gets the item at the given index, where 0 is the front, 1 is the next item, and so forth.
     * If no such item exists, returns null.
     */
    public T get(int index) {
        if (index >= size || index < 0) {
            // No such item exists.
            return null;
        }
        int first = (nextFirst + 1) % array.length;
        index = (first + index) % array.length;
        return array[index];
    }
}
