package test.java.com.baeldung.algorithms.kary_tree;

import org.junit.Test;
import main.java.com.baeldung.algorithms.kary_tree.NodeCounter;

public class NodeCounterUnitTest {

    @Test(expected = IllegalArgumentException.class)
    public void givenUnaryTree_whenCountNodeRuns_thenThrowsIllegalArgumentException() {
        int k = 1;
        int leaves = 8;
        NodeCounter nc = new NodeCounter();
        int total = nc.countTotalNodes(leaves, k);
    }

}
