package com.baeldung.list.reverse;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

class ReverseArrayListUnitTest {

    private static final List<Integer> EXPECTED = new ArrayList<>(Arrays.asList(7, 6, 5, 4, 3, 2, 1));

    @Test
    void givenArrayList_whenCallReverseMethod_thenListReversedInPlace() {
        List<Integer> aList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        Collections.reverse(aList);
        assertThat(aList).isEqualTo(EXPECTED);
    }

    @Test
    void givenArrayList_whenCallReverseMethod_thenListReversedAsaNewList() {
        List<Integer> originalList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        List<Integer> aNewList = new ArrayList<>(originalList);
        Collections.reverse(aNewList);

        assertThat(aNewList).isNotEqualTo(originalList)
            .isEqualTo(EXPECTED);
    }

    @Test
    void givenArrayList_whenCallReverseWithRecur_thenListReversedInPlace() {
        List<Integer> aList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        ReverseArrayList.reverseWithRecursion(aList);
        assertThat(aList).isEqualTo(EXPECTED);
    }

    @Test
    void givenArrayList_whenCallReverseWithLoop_thenListReversedInPlace() {
        List<Integer> aList = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7));
        ReverseArrayList.reverseWithLoop(aList);
        assertThat(aList).isEqualTo(EXPECTED);
    }
}