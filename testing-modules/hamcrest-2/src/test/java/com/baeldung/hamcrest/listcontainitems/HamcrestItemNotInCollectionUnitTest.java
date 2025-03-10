package com.baeldung.hamcrest.listcontainitems;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasItemInArray;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class HamcrestItemNotInCollectionUnitTest {

    private static final List<String> LIST = List.of("a", "b", "c", "d", "e", "f");
    private static final String[] ARRAY = { "a", "b", "c", "d", "e", "f" };

    @Test
    void whenUsingNotAndHasItem_thenCorrect() {
        assertThat(LIST, hasItem("a"));
        assertThat(LIST, not(hasItem("x")));

        assertThat(ARRAY, hasItemInArray("a"));
        assertThat(ARRAY, not(hasItemInArray("x")));
    }

    @Test
    void whenUsingJUnitAssertTrue_thenCorrect() {
        assertTrue(LIST.contains("a"));
        assertFalse(LIST.contains("x"));

        assertTrue(Arrays.stream(ARRAY).anyMatch("a"::equals));
        assertFalse(Arrays.asList(ARRAY).contains("z"));
    }
}