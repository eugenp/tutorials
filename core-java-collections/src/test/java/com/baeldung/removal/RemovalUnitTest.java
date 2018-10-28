package com.baeldung.removal;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class RemovalUnitTest {

    Collection<String> names;
    Collection<String> expected;
    Collection<String> removed;

    @Before
    public void setupTestData() {
        names = new ArrayList<>();
        expected = new ArrayList<>();
        removed = new ArrayList<>();

        names.add("John");
        names.add("Ana");
        names.add("Mary");
        names.add("Anthony");
        names.add("Mark");

        expected.add("John");
        expected.add("Mary");
        expected.add("Mark");

        removed.add("Ana");
        removed.add("Anthony");
    }

    @Test
    public void givenCollectionOfNames_whenUsingIteratorToRemoveAllNamesStartingWithLetterA_finalListShouldContainNoNamesStartingWithLetterA() {
        Iterator<String> i = names.iterator();

        while (i.hasNext()) {
            String e = i.next();
            if (e.startsWith("A")) {
                i.remove();
            }
        }

        assertThat(names, is(expected));
    }

    @Test
    public void givenCollectionOfNames_whenUsingRemoveIfToRemoveAllNamesStartingWithLetterA_finalListShouldContainNoNamesStartingWithLetterA() {
        names.removeIf(e -> e.startsWith("A"));
        assertThat(names, is(expected));
    }

    @Test
    public void givenCollectionOfNames_whenUsingStreamToFilterAllNamesStartingWithLetterA_finalListShouldContainNoNamesStartingWithLetterA() {
        Collection<String> filteredCollection = names
                .stream()
                .filter(e -> !e.startsWith("A"))
                .collect(Collectors.toList());
        assertThat(filteredCollection, is(expected));
    }

    @Test
    public void givenCollectionOfNames_whenUsingStreamAndPartitioningByToFindNamesThatStartWithLetterA_shouldFind3MatchingAnd2NonMatching() {
        Map<Boolean, List<String>> classifiedElements = names
                .stream()
                .collect(Collectors.partitioningBy((String e) -> !e.startsWith("A")));

        assertThat(classifiedElements.get(Boolean.TRUE), is(expected));
        assertThat(classifiedElements.get(Boolean.FALSE), is(removed));
    }

}
