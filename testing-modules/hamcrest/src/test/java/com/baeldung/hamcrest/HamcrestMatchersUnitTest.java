package com.baeldung.hamcrest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.contains;

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

        // This assertion will PASS because the order matches exactly
        assertThat(myList, contains("apple", "banana", "cherry"));
        try {
            // This assertion will FAIL because the order is different
            assertThat(myList, contains("banana", "apple", "cherry"));
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void givenAList_whenTestTheExactCountElements_thenReturnExpectedBehavior() {
        List<String> myList = Arrays.asList("apple", "banana", "cherry");

        assertThat(myList, hasItems("apple", "banana"));
        assertThat(myList, contains("apple", "banana", "cherry"));
        assertThat(myList, containsInAnyOrder("apple", "banana", "cherry"));

        try {
            // This assertion will FAIL because a required element is missing
            assertThat(myList, containsInAnyOrder("apple", "banana"));
            assertThat(myList, contains("apple", "banana"));
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    void givenAListWithDuplicate_whenTestTheElementsDuplicate_thenReturnExpectedBehavior() {
        List<String> myList = Arrays.asList("apple", "banana", "cherry", "apple");

        assertThat(myList, hasItems("apple", "banana", "cherry"));
        assertThat(myList, contains("apple", "banana", "cherry", "apple"));
        assertThat(myList, containsInAnyOrder("apple", "banana", "cherry", "apple"));

        try {
            // This assertion will FAIL because a required element is missing
            assertThat(myList, contains("apple", "banana", "cherry"));
            assertThat(myList, containsInAnyOrder("apple", "banana", "cherry"));
        } catch (AssertionError e) {
            System.out.println(e.getMessage());
        }
    }
}
