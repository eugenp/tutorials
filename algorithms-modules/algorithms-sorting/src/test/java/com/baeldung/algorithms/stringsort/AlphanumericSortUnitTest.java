package com.baeldung.algorithms.stringsort;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class AlphanumericSortUnitTest {

    @Test
    void givenAlphanumericString_whenLexicographicSort_thenSortLettersFirst() {
        String stringToSort = "C4B3A21";
        String sorted = AlphanumericSort.lexicographicSort(stringToSort);
        assertThat(sorted).isEqualTo("1234ABC");
    }

    @Test
    void givenAlphanumericArrayOfStrings_whenAlphanumericSort_thenSortNaturalOrder() {
        String[] arrayToSort = {"file2", "file10", "file0", "file1", "file20"};
        String[] sorted = AlphanumericSort.naturalAlphanumericSort(arrayToSort);
        assertThat(Arrays.toString(sorted)).isEqualTo("[file0, file1, file2, file10, file20]");
    }

    @Test
    void givenAlphanumericArrayOfStrings_whenAlphanumericCaseInsensitveSort_thenSortNaturalOrder() {
        String[] arrayToSort = {"a2", "A10", "b1", "B3", "A2"};
        String[] sorted = AlphanumericSort.naturalAlphanumericCaseInsensitiveSort(arrayToSort);
        assertThat(Arrays.toString(sorted)).isEqualTo("[A2, a2, A10, b1, B3]");
    }
}
