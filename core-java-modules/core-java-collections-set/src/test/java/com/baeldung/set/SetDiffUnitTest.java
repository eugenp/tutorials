package com.baeldung.set;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SetDiffUnitTest {
    private final static Set<String> immutableSet1 = Set.of("Kotlin", "Java", "Rust", "Python", "C++");
    private final static Set<String> immutableSet2 = Set.of("Kotlin", "Java", "Rust", "Ruby", "C#");
    private final static Set<String> expectedOnlyInSet1 = Set.of("Python", "C++");
    private final static Set<String> expectedDiff = Set.of("Python", "C++", "Ruby", "C#");

    @Test
    public void givenAnotherSet_whenRemoveAll_shouldGetDiff() {
        Set<String> set1 = Stream.of("Kotlin", "Java", "Rust", "Python", "C++").collect(Collectors.toSet());
        Set<String> set2 = Stream.of("Kotlin", "Java", "Rust", "Ruby", "C#").collect(Collectors.toSet());
        Set<String> expectedOnlyInSet1 = Set.of("Python", "C++");
        set1.removeAll(set2);
        assertThat(set1).isEqualTo(expectedOnlyInSet1);
    }

    @Test
    public void givenAnotherSet_whenUsingStreamFilter_shouldGet() {
        Set<String> actualOnlyInSet1 = immutableSet1.stream().filter(e -> !immutableSet2.contains(e)).collect(Collectors.toSet());
        assertThat(actualOnlyInSet1).isEqualTo(expectedOnlyInSet1);
    }

    @Test
    public void givenAnotherSet_whenCallingGuavaMethod_shouldGetDiff() {
        Set<String> actualOnlyInSet1 = Sets.difference(immutableSet1, immutableSet2);
        assertThat(actualOnlyInSet1).isEqualTo(expectedOnlyInSet1);
    }

    @Test
    public void givenAnotherSet_whenCallingCommonsMethod_shouldGetDiff() {
        Set<String> actualOnlyInSet1 = new HashSet<>(CollectionUtils.removeAll(immutableSet1, immutableSet2));
        assertThat(actualOnlyInSet1).isEqualTo(expectedOnlyInSet1);
    }


    @Test
    public void givenTwoSets_whenCallingFindDisjunction_shouldGetDisjunction() {
        Set<String> actualDiff = SetDiff.findSymmetricDiff(immutableSet1, immutableSet2);
        assertThat(actualDiff).isEqualTo(expectedDiff);
    }

    @Test
    public void givenTwoSets_whenCallingCommonsMethod_shouldGetDisjunction() {
        Set<String> actualDiff = SetUtils.disjunction(immutableSet1, immutableSet2);
        assertThat(actualDiff).isEqualTo(expectedDiff);
    }

}
