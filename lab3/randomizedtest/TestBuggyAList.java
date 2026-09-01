package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {
  // YOUR TESTS HERE
    @Test
    public void testThreeAddThreeRemove() {
        AListNoResizing<Integer> listNoResizing = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        listNoResizing.addLast(4);
        buggyAList.addLast(4);
        listNoResizing.addLast(5);
        buggyAList.addLast(5);
        listNoResizing.addLast(6);
        buggyAList.addLast(6);

        assertEquals(listNoResizing.removeLast(), buggyAList.removeLast());
        assertEquals(listNoResizing.removeLast(), buggyAList.removeLast());
        assertEquals(listNoResizing.removeLast(), buggyAList.removeLast());
    }

    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> buggyAList = new BuggyAList<>();
        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggyAList.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int buggyAListSize = buggyAList.size();
                assertEquals(size, buggyAListSize);

            } else if (operationNumber == 2 && L.size() > 0) {
                // removeLast
                int last = L.removeLast();
                int buggyAListLast = buggyAList.removeLast();
                assertEquals(last, buggyAListLast);
            } else if (operationNumber == 3 && L.size() > 0) {
                // getLast
                int last = L.getLast();
                int buggyAListLast = buggyAList.getLast();
                assertEquals(last, buggyAListLast);
            }
        }
    }
}
