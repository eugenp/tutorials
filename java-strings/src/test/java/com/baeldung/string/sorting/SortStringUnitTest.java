package com.baeldung.string.sorting;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SortStringUnitTest {

    @Test
    void givenString_whenSort_thenSorted() {
        String abcd = "bdca";
        char[] chars = abcd.toCharArray();

        Arrays.sort(chars);
        String sorted = new String(chars);

        assertThat(sorted).isEqualTo("abcd");
    }

    @Test
    void givenString_whenSortJava8_thenSorted() {
        String sorted = "bdca".chars()
            .sorted()
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();

        assertThat(sorted).isEqualTo("abcd");
    }
}
