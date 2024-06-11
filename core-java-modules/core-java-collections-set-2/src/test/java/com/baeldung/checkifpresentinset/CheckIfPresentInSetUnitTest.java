package com.baeldung.checkifpresentinset;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.SetUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class CheckIfPresentInSetUnitTest {

    private static final Set<String> CITIES = new HashSet<>();

    @BeforeAll
    static void setup() {
        CITIES.add("Paris");
        CITIES.add("London");
        CITIES.add("Tokyo");
        CITIES.add("Tamassint");
        CITIES.add("New york");
    }

    @Test
    void givenASet_whenUsingStreamAnyMatchMethod_thenCheck() {
        boolean isPresent = CITIES.stream()
            .anyMatch(city -> city.equals("London"));

        assertThat(isPresent).isTrue();
    }

    @Test
    void givenASet_whenUsingStreamFilterMethod_thenCheck() {
        long resultCount = CITIES.stream()
            .filter(city -> city.equals("Tamassint"))
            .count();

        assertThat(resultCount).isPositive();
    }

    @Test
    void givenASet_whenUsingContainsMethod_thenCheck() {
        assertThat(CITIES.contains("London")).isTrue();
        assertThat(CITIES.contains("Madrid")).isFalse();
    }

    @Test
    void givenASet_whenUsingCollectionsDisjointMethod_thenCheck() {
        boolean isPresent = !Collections.disjoint(CITIES, Collections.singleton("Paris"));

        assertThat(isPresent).isTrue();
    }

    @Test
    void givenASet_whenUsingCollectionUtilsContainsAnyMethod_thenCheck() {
        boolean isPresent = CollectionUtils.containsAny(CITIES, Collections.singleton("Paris"));

        assertThat(isPresent).isTrue();
    }

    @Test
    void givenASet_whenUsingSetUtilsIntersectionMethod_thenCheck() {
        Set<String> result = SetUtils.intersection(CITIES, Collections.singleton("Tamassint"));

        assertThat(result).isNotEmpty();
    }

}
