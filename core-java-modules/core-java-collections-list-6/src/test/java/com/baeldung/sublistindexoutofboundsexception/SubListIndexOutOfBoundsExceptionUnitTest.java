package com.baeldung.sublistindexoutofboundsexception;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;

import org.junit.jupiter.api.Test;

class SubListIndexOutOfBoundsExceptionUnitTest {

    @Test
    void whenCallingSuListWithIncorrectIndexes_thenThrowIndexOutOfBoundsException() {
        List<String> cities = List.of("Tokyo", "Tamassint", "Paris", "Madrid", "London");

        assertThatThrownBy(() -> cities.subList(6, 10)).isInstanceOf(IndexOutOfBoundsException.class);
    }

    @Test
    void whenCallingSafeSuList_thenReturnData() {
        List<String> cities = List.of("Amsterdam", "Buenos Aires", "Dublin", "Brussels", "Prague");

        assertThat(SubListUtils.safeSubList(cities, 6, 10)).isEmpty();
        assertThat(SubListUtils.safeSubList(cities, -2, 3)).containsExactly("Amsterdam", "Buenos Aires", "Dublin");
        assertThat(SubListUtils.safeSubList(cities, 3, 20)).containsExactly("Brussels", "Prague");
    }

}
