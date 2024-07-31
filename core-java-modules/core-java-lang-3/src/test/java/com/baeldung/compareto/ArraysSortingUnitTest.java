package com.baeldung.compareto;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class ArraysSortingUnitTest {

    @Test
    public void givenArrayOfNumbers_whenSortingArray_thenNumbersAreSortedAscending() {
        int[] numbers = new int[] {5, 3, 9, 11, 1, 7};
        Arrays.sort(numbers);
        assertThat(numbers).containsExactly(1, 3, 5, 7, 9, 11);
    }

    @Test
    public void givenArrayOfStrings_whenSortingArray_thenStringsAreSortedAlphabetically() {
        String[] players = new String[] {"ronaldo",  "modric", "ramos", "messi"};
        Arrays.sort(players);
        assertThat(players).containsExactly("messi", "modric", "ramos", "ronaldo");
    }

}
