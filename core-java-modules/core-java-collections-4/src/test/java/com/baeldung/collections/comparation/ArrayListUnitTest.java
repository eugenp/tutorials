package com.baeldung.collections.comparation;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayListUnitTest {

    @Test
    void givenArrayList_whenItemAddedToSpecificIndex_thenItCanBeRetrieved() {
        List<String> list = new ArrayList<>();
        list.add("Daniel");
        list.add(1, "Marko");
        assertThat(list).hasSize(2);
        assertThat(list.get(1)).isEqualTo("Marko");
    }

    @Test
    void givenArrayList_whenItemRemovedViaIndex_thenListSizeIsReduced() {
        List<String> list = new ArrayList<>(Arrays.asList("Daniel", "Marko"));
        list.remove(1);
        assertThat(list).hasSize(1);
        assertThat(list).doesNotContain("Marko");
    }

}
