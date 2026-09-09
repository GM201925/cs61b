package deque;

public interface Deque<Type> {
    public void addFirst(Type item);
    public void addLast(Type item);
    public int size();
    public void printDeque();
    public Type removeFirst();
    public Type removeLast();
    public Type get(int index);

    /** Returns true if the deque is empty, false otherwise. */
    default public boolean isEmpty() {
        return size() == 0;
    }

}
