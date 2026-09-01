package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimes2() {
        /**
         * To test it can judge 1 to be composite.
         */
        IntList lst = IntList.of(1, 1, 1);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("1 -> 1 -> 1", lst.toString());
        assertFalse(changed);
    }

    @Test
    public void testAllPrimes() {
        /**
         * Only square the first prime ?
         */
        IntList lst = IntList.of(7, 11, 29, 13);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("49 -> 121 -> 841 -> 169", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testOnlyOneElement() {
        IntList lst = IntList.of(7);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("49", lst.toString());
        assertTrue(changed);
    }
}
