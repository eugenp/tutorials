package com.baeldung.hamcrest.listcontainitems;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.not;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

public class HamcrestMatchersUnitTest {

    @Test
    void givenAList_whenTestTheOrdering_thenReturnExpectedBehavior() {
        List<String> myList = Arrays.asList("apple", "banana", "cherry");

        assertThat(myList, hasItems("apple", "cherry", "banana"));
        assertThat(myList, hasItems("banana", "apple", "cherry"));

        assertThat(myList, containsInAnyOrder("apple", "cherry", "banana"));
        assertThat(myList, containsInAnyOrder("banana", "apple", "cherry"));

        assertThat(myList, contains("apple", "banana", "cherry"));
        assertThat(myList, not(contains("banana", "apple", "cherry")));
    }

    @Test
    void givenAList_whenTestTheExactCountElements_thenReturnExpectedBehavior() {
        List<String> myList = Arrays.asList("apple", "banana", "cherry");

        assertThat(myList, hasItems("apple", "banana"));
        assertThat(myList, hasItems("apple", "banana", "cherry"));
        assertThat(myList, not(hasItems("apple", "banana", "cherry", "date")));

        assertThat(myList, contains("apple", "banana", "cherry"));

        assertThat(myList, containsInAnyOrder("apple", "banana", "cherry"));

        assertThat(myList, not(containsInAnyOrder("apple", "banana")));
        assertThat(myList, not(contains("apple", "banana")));

    }

    @Test
    void givenAListWithDuplicate_whenTestTheElementsDuplicate_thenReturnExpectedBehavior() {
        List<String> myList = Arrays.asList("apple", "banana", "cherry", "apple");

        assertThat(myList, hasItems("apple", "banana", "cherry"));
        assertThat(myList, contains("apple", "banana", "cherry", "apple"));
        assertThat(myList, containsInAnyOrder("apple", "banana", "cherry", "apple"));

        assertThat(myList, not(contains("apple", "banana", "cherry")));
        assertThat(myList, not(containsInAnyOrder("apple", "banana", "cherry")));

    }
}
