/**
 * Package to host code for Frog River One coding problem
 */

package com.baeldung.algorithms.frogriverone;

import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;

public class FrogRiverOne {
    /*
     * HashSet and Boolean array based solutions for Frog River One problem
     * @param m Final destination
     * @param leaves Integer array to hold leave positions
     *
     * @return status Integer to denote total time steps
     * taken to reach destination
     */
    public int HashSetSolution(int m, @Nonnull int[] leaves) {
        Set<Integer> leavesCovered = new HashSet<>();
        int status = -1;
        for (int k = 0; k < leaves.length; k++) {
            int position = leaves[k];
            leavesCovered.add(position);

            if (leavesCovered.size() == m) {
                    status = k + 1;
                    return status;
            }
        }

        return status;
    }

    public int BooleanArraySolution(int m, @Nonnull int[] leaves) {
        boolean[] leavesCovered = new boolean[m + 1];
        int leavesUncovered = m;
        for (int k = 0; k< leaves.length; k++) {
            int position = leaves[k];
            if (!leavesCovered[position]) {
                leavesCovered[position] = true;
                leavesUncovered--;
                if (leavesUncovered == 0) {
                    return k + 1;
                }
            }
        }
        return -1;
    }
}
