package com.baeldung.array.operations;

import org.junit.jupiter.api.Test;

import static com.baeldung.array.operations.ArrayOperations.intersectionMultiSet;
import static com.baeldung.array.operations.ArrayOperations.intersectionSet;
import static com.baeldung.array.operations.ArrayOperations.intersectionSimple;
import static org.assertj.core.api.Assertions.assertThat;

class IntersectionUnitTest {
    private static final Integer[] a = { 1, 3, 2 };
    private static final Integer[] b = { 4, 3, 2, 4, 2, 3, 4, 4, 3 };
    private static final Integer[] c = { 1, 3, 2, 3, 3, 2 };

    @Test
    void whenIntersectionSimpleIsUsed_thenCommonEntriesAreInTheResult() {
        assertThat(intersectionSimple(a, b)).isEqualTo(new Integer[] { 3, 2 });
        assertThat(intersectionSimple(b, a)).isEqualTo(new Integer[] { 3, 2, 2, 3, 3 });
    }

    @Test
    void whenIntersectionSimpleIsUsedWithAnArrayAndItself_thenTheResultIsTheIdentity() {
        assertThat(intersectionSimple(b, b)).isEqualTo(b);
        assertThat(intersectionSimple(a, a)).isEqualTo(a);
    }

    @Test
    void whenIntersectionSetIsUsed_thenCommonEntriesAreInTheResult() {
        assertThat(intersectionSet(b, a)).isEqualTo(new Integer[] { 3, 2 });
    }

    @Test
    void whenIntersectionSetIsUsed_thenTheNumberOfEntriesDoesNotChangeWithTheParameterOrder() {
        assertThat(intersectionSet(a, b)).isEqualTo(new Integer[] { 3, 2 });
        assertThat(intersectionSet(b, a)).isEqualTo(new Integer[] { 3, 2 });
    }

    @Test
    void whenIntersectionSetIsUsedWithAnArrayAndWithItself_andTheInputHasNoDuplicateEntries_ThenTheResultIsTheIdentity() {
        assertThat(intersectionSet(a, a)).isEqualTo(a);
    }

    @Test
    void whenIntersectionSetIsUsedWithAnArrayAndWithItself_andTheInputHasDuplicateEntries_ThenTheResultIsNotTheIdentity() {
        assertThat(intersectionSet(b, b)).isNotEqualTo(b);
    }

    @Test
    void whenMultiSetIsUsed_thenCommonEntriesAreInTheResult() {
        assertThat(intersectionMultiSet(b, a)).isEqualTo(new Integer[] { 3, 2 });
    }

    @Test
    void whenIntersectionMultiSetIsUsed_thenTheNumberOfEntriesDoesNotChangeWithTheParameterOrder() {
        assertThat(intersectionMultiSet(a, b)).isEqualTo(new Integer[] { 3, 2 });
        assertThat(intersectionMultiSet(b, a)).isEqualTo(new Integer[] { 3, 2 });
        assertThat(intersectionMultiSet(b, c)).isEqualTo(new Integer[] { 3, 2, 2, 3, 3 });
        assertThat(intersectionMultiSet(c, b)).isEqualTo(new Integer[] { 3, 2, 3, 3, 2 });
    }

    @Test
    void whenIntersectionMultiSetIsUsedWithAnArrayAndWithItself_ThenTheResultIsTheIdentity() {
        assertThat(intersectionMultiSet(b, b)).isEqualTo(b);
        assertThat(intersectionMultiSet(a, a)).isEqualTo(a);
    }
}