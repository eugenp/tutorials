package com.baeldung.list.replace;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ReplaceUsingIndexInArrayListUnitTest {

    private static final List<Integer> EXPECTED = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

    @Test void givenArrayList_updateUsingSet() {
        List<Integer> aList = new ArrayList<>(Arrays.asList(1, 2, 7, 4, 5));
        aList.set(2, 3);
        assertThat(aList).isEqualTo(EXPECTED);
    }
}