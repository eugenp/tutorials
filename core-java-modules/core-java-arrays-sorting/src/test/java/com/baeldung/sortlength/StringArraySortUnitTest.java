package com.baeldung.sortlength;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

class StringArraySortUnitTest {

    private static final String[] SORTED = new String[] {"I", "am", "too", "busy", "today"};

    private String[] inputArray;

    @BeforeEach
    void setup() {
        inputArray = new String[] {"am", "today", "too", "I", "busy"};
    }

    @Test
    void whenSortByCustomComparator_thenArrayIsSorted() {
        StringLengthComparator comparator = new StringLengthComparator();
        Arrays.sort(inputArray, comparator);
        assertThat(inputArray).isEqualTo(SORTED);
    }

    @Test
    void whenSortByAnonymousComparator_thenArrayIsSorted() {
        Arrays.sort(inputArray, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                return Integer.compare(s1.length(), s2.length());
            }

        });
        assertThat(inputArray).isEqualTo(SORTED);
    }

    @Test
    void whenSortByLambda_thenArrayIsSorted() {
        Arrays.sort(inputArray, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        assertThat(inputArray).isEqualTo(SORTED);
    }

    @Test
    void whenSortByComparingInt_thenArrayIsSorted() {
        Arrays.sort(inputArray, Comparator.comparingInt(String::length));
        assertThat(inputArray).isEqualTo(SORTED);
    }

    static class StringLengthComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            return Integer.compare(s1.length(), s2.length());
        }
    }

}