package com.baeldung.equilibriumindex;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class EquilibriumIndexFinderUnitTest {
    
    @Test
    void givenArrayHasEquilibriumIndexes_whenFindEquilibriumIndexes_thenListAllEquilibriumIndexes() {
        int[] array = {1, -3, 0, 4, -5, 4, 0, 1, -2, -1};
        assertThat(new EquilibriumIndexFinder().findEquilibriumIndexes(array)).containsExactly(1, 4, 9);
    }
   
    @Test
    void givenArrayWithoutEquilibriumIndexes_whenFindEquilibriumIndexes_thenEmptyList() {
        int[] array = {1, 2, 3};
        assertThat(new EquilibriumIndexFinder().findEquilibriumIndexes(array)).isEmpty();
    }
   
    @Test
    void givenArrayWithOneElement_whenFindEquilibriumIndexes_thenListFirstIndex() {
        int[] array = {5};
        assertThat(new EquilibriumIndexFinder().findEquilibriumIndexes(array)).containsExactly(0);
    }

}
