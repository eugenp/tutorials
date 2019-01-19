package com.baeldung.list.check;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.apache.commons.collections4.IterableUtils;
import org.hamcrest.Matchers;
import org.junit.Test;

import com.google.common.collect.Iterables;

public class DistinctElementsCheckUnitTest {

    private List<String> notSameElements = Arrays.asList("AB", "XY", "AB", "XY");

    private List<String> sameElements = Arrays.asList("AB", "AB", "AB", "AB");

    @Test
    public void whenSameElements_thenAllMatchWithFirstElementsWillBeTrue() {

        assertTrue(sameElements.stream()
            .allMatch(e -> e.equals(sameElements.get(0))));
        assertTrue(sameElements.parallelStream()
            .allMatch(e -> e.equals(sameElements.get(0))));

        assertTrue(sameElements.stream()
            .allMatch(sameElements.get(0)::equals));
        assertTrue(sameElements.parallelStream()
            .allMatch(sameElements.get(0)::equals));
    }

    @Test
    public void whenSameElements_thenFrequencyOfFirstElementShouldBeEqualToSize() {

        assertTrue(Collections.frequency(sameElements, sameElements.get(0)) == sameElements.size());
    }

    @Test
    public void whenSameElements_thenSetShouldHaveSingleElement() {

        assertTrue(new HashSet<>(sameElements).size() == 1);
    }

    @Test
    public void whenSameElements_thenCountMatchesShouldBeEqualToSize() {

        assertTrue(IterableUtils.countMatches(sameElements, e -> e.equals(sameElements.get(0))) == sameElements.size());
    }

    @Test
    public void whenSameElements_thenIterableFrequencyShouldBeEqualToSize() {

        assertTrue(IterableUtils.frequency(sameElements, sameElements.get(0)) == sameElements.size());
    }

    @Test
    public void whenSameElements_thenMatchesAllShouldBeTrue() {

        assertTrue(IterableUtils.matchesAll(sameElements, e -> e.equals(sameElements.get(0))));
    }

    @Test
    public void whenSameElements_thenIterablesAllShouldBeEqualToFirstElement() {

        assertTrue(Iterables.all(sameElements, e -> e.equals(sameElements.get(0))));
    }

    @Test
    public void whenSameElements_thenIterablesFrequencyEqualsSize() {

        assertTrue(Iterables.frequency(sameElements, sameElements.get(0)) == sameElements.size());
    }

    @Test
    public void whenSameElements_thenEveryItemIsEqualToFirstElement() {

        assertThat(sameElements, Matchers.everyItem(Matchers.comparesEqualTo(sameElements.get(0))));
    }

    @Test
    public void whenNotSameElements_thenAllMatchWithFirstElementsWillBeTrue() {

        assertFalse(notSameElements.stream()
            .allMatch(e -> e.equals(notSameElements.get(0))));
        assertFalse(notSameElements.parallelStream()
            .allMatch(e -> e.equals(notSameElements.get(0))));

        assertFalse(notSameElements.stream()
            .allMatch(notSameElements.get(0)::equals));
        assertFalse(notSameElements.parallelStream()
            .allMatch(notSameElements.get(0)::equals));

    }

    @Test
    public void whenNotSameElements_thenFrequencyOfFirstElementShouldNotBeEqualToSize() {

        assertFalse(Collections.frequency(notSameElements, notSameElements.get(0)) == notSameElements.size());
    }

    @Test
    public void whenNotSameElements_thenSetShouldHaveMoreThanOneElement() {

        assertFalse(new HashSet<>(notSameElements).size() == 1);
    }

    @Test
    public void whenNotSameElements_thenCountMatchesShouldNotBeEqualToSize() {

        assertFalse(IterableUtils.countMatches(notSameElements, e -> e.equals(notSameElements.get(0))) == notSameElements.size());
    }

    @Test
    public void whenNotSameElements_thenIterableFrequencyShouldNotBeEqualToSize() {

        assertFalse(IterableUtils.frequency(notSameElements, notSameElements.get(0)) == notSameElements.size());
    }

    @Test
    public void whenNotSameElements_thenMatchesAllShouldNotBeTrue() {

        assertFalse(IterableUtils.matchesAll(notSameElements, e -> e.equals(notSameElements.get(0))));
    }

    @Test
    public void whenNotSameElements_thenIterablesAllShouldNotBeEqualToFirstElement() {

        assertFalse(Iterables.all(notSameElements, e -> e.equals(notSameElements.get(0))));
    }

    @Test
    public void whenNotSameElements_thenIterablesFrequencyIsNotEqualToSize() {

        assertFalse(Iterables.frequency(notSameElements, notSameElements.get(0)) == notSameElements.size());
    }

    @Test
    public void whenNotSameElements_thenEveryItemIsEqualToFirstElement() {

        assertThat(notSameElements, Matchers.not(Matchers.everyItem(Matchers.comparesEqualTo(notSameElements.get(0)))));
    }
}