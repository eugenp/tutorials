package com.baeldung.tapeequilibrium;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TapeEquilibriumSolverUnitTest {
    
    @Test
    void whenCalculateTapeEquilibrium_thenReturnMinimalDifference() {
        int[] array = {1, 2, 3, 4, 5};
        assertEquals(3, new TapeEquilibriumSolver().calculateTapeEquilibrium(array));
        int[] arrayOnly2Elements = {0, 12};
        assertEquals(12, new TapeEquilibriumSolver().calculateTapeEquilibrium(arrayOnly2Elements));
        int[] arrayWithNegativeValues = {-1, 6, 8, -4, 7};
        assertEquals(2, new TapeEquilibriumSolver().calculateTapeEquilibrium(arrayWithNegativeValues));
    }

}
