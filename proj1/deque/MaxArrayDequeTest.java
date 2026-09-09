package deque;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

public class MaxArrayDequeTest {
    private static class AlphabetComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return a.compareTo(b);
        }
    }

    private static class LengthComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return Integer.compare(a.length(), b.length());
        }
    }

    private static class NumberComparator implements Comparator<Integer> {
        @Override
        public int compare(Integer a, Integer b) {
            return a.compareTo(b);
        }
    }

    @Test
    public void normalTest() {
        MaxArrayDeque<String> deque =  new MaxArrayDeque<>(new AlphabetComparator());

        deque.addLast("zoo");
        deque.addLast("elephant");
        deque.addLast("apple");

        assertEquals("elephant", deque.max(new LengthComparator()));

        assertEquals("zoo", deque.max());
    }

    @Test
    public void nullTest() {
        MaxArrayDeque<Integer> deque =  new MaxArrayDeque<>(new NumberComparator());
        assertEquals(null, deque.max());
        assertEquals(null, deque.max(new NumberComparator()));
    }

    @Test
    public void equalTest() {
        MaxArrayDeque<Integer> deque = new MaxArrayDeque<>(new NumberComparator());
        deque.addLast(5);
        assertEquals(Integer.valueOf(5), deque.max());
        deque.addLast(5);
        assertEquals(Integer.valueOf(5), deque.max());
    }
}
