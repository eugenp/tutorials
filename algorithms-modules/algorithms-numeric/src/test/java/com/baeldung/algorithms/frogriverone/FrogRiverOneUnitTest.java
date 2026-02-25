/**
 * Package to host JUNIT5 Unit Test code for Frog River One coding problem
 */

package com.baeldung.algorithms.frogriverone;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FrogRiverOneUnitTest {

    private final FrogRiverOne frogRiverOne = new FrogRiverOne();

    @Test
    void whenLeavesCoverPath_thenReturnsEarliestTime() {
        int m = 7;
        int[] leaves = {1, 3, 6, 4, 2, 3, 7, 5, 4};

        // Expected: Time 8 (Value 5 falls at index 7, completing 1..7)
        assertEquals(8, frogRiverOne.HashSetSolution(m, leaves));

        // Boolean array based solution
        assertEquals(8, frogRiverOne.BooleanArraySolution(m, leaves));
    }

    @Test
    void whenLeavesAreMissing_thenReturnsMinusOne() {
        int m = 7;
        int[] leaves = {1, 3, 6, 4, 2, 3, 7, 4};  //missing 5

        // HashSet based Solution
        assertEquals(-1, frogRiverOne.HashSetSolution(m, leaves));

        // Boolean array based solution
        assertEquals(-1, frogRiverOne.BooleanArraySolution(m, leaves));
    }

}