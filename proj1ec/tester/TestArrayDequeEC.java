package tester;

import static org.junit.Assert.*;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import student.StudentArrayDeque;

public class TestArrayDequeEC {
    @Test
    public void randomizedTest() {
        StudentArrayDeque<Integer> studentArrayDeque = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> arrayDequeSolution = new ArrayDequeSolution<>();
        int N = 5000;
        int size = 0;
        StringBuilder operations = new StringBuilder();
        for (int i = 0; i < N; ++i) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(2019);
                studentArrayDeque.addLast(randVal);
                arrayDequeSolution.addLast(randVal);
                size += 1;
                operations.append("addLast(").append(randVal).append(")\n");
            } else if (operationNumber == 1 && size > 0) {
                // removeLast
                Integer x1 = studentArrayDeque.removeLast();
                Integer x2 = arrayDequeSolution.removeLast();
                size -= 1;
                operations.append("removeLast()\n");
                assertEquals(operations.toString(), x2, x1);
            } else if (operationNumber == 2 && size > 0) {
                // removeFirst
                Integer x1 = studentArrayDeque.removeFirst();
                Integer x2 = arrayDequeSolution.removeFirst();
                size -= 1;
                operations.append("removeFirst()\n");
                assertEquals(operations.toString(), x2, x1);
            } else if (operationNumber == 3) {
                // addFirst
                int randVal = StdRandom.uniform(2019);
                studentArrayDeque.addFirst(randVal);
                arrayDequeSolution.addFirst(randVal);
                size += 1;
                operations.append("addFirst(");
                operations.append(randVal);
                operations.append(")\n");
            }
        }
    }
}
