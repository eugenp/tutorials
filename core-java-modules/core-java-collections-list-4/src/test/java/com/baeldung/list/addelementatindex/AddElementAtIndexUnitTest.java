package com.baeldung.list.addelementatindex;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


class AddElementAtIndexUnitTest {

    @Test
    void givenArrayList_whenAddElementAtIndex_thenGetExpectedElementAtIndex() {
        List<Integer> integers = new ArrayList<>();
        // add some elements
        integers.add(5);
        integers.add(6);
        integers.add(7);
        integers.add(8);
        assertThat(integers.get(0)).isEqualTo(5);
        assertThat(integers.get(1)).isEqualTo(6);
        assertThat(integers.get(2)).isEqualTo(7);
        assertThat(integers.get(3)).isEqualTo(8);
        // add an element at a specific index
        integers.add(1, 9);
        assertThat(integers.get(0)).isEqualTo(5);
        assertThat(integers.get(1)).isEqualTo(9);
        assertThat(integers.get(2)).isEqualTo(6);
        assertThat(integers.get(3)).isEqualTo(7);
        assertThat(integers.get(4)).isEqualTo(8);
        // sort the array list
        integers.sort(Integer::compareTo);
        assertThat(integers.get(0)).isEqualTo(5);
        assertThat(integers.get(1)).isEqualTo(6);
        assertThat(integers.get(2)).isEqualTo(7);
        assertThat(integers.get(3)).isEqualTo(8);
        assertThat(integers.get(4)).isEqualTo(9);
    }
}