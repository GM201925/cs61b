package deque;

public interface Deque<Type> {
    void addFirst(Type item);
    void addLast(Type item);
    int size();
    void printDeque();
    Type removeFirst();
    Type removeLast();
    Type get(int index);

    /** Returns true if the deque is empty, false otherwise. */
    default boolean isEmpty() {
        return size() == 0;
    }

}
