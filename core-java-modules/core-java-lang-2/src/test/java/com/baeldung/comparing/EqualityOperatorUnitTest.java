package com.baeldung.comparing;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class EqualityOperatorUnitTest {

    @Test
    void givenTwoIntsWithSameValues_whenEqualityOperators_thenConsideredSame() {
        int a = 1;
        int b = 1;

        assertThat(a == b).isTrue();
        assertThat(a != b).isFalse();
    }

    @Test
    void givenTwoIntsWithDifferentValues_whenEqualityOperators_thenNotConsideredSame() {
        int a = 1;
        int b = 2;

        assertThat(a == b).isFalse();
        assertThat(a != b).isTrue();
    }

    @Test
    void givenTwoIntsWithSameValuesOneWrapped_whenEqualityOperators_thenConsideredSame() {
        int a = 1;
        Integer b = new Integer(1);

        assertThat(a == b).isTrue();
        assertThat(a != b).isFalse();
    }

    @Test
    void givenTwoIntsWithDifferentValuesOneWrapped_whenEqualityOperators_thenNotConsideredSame() {
        int a = 1;
        Integer b = new Integer(2);

        assertThat(a == b).isFalse();
        assertThat(a != b).isTrue();
    }

    @Test
    void givenTwoIntegersWithSameValues_whenEqualityOperators_thenNotConsideredSame() {
        Integer a = new Integer(1);
        Integer b = new Integer(1);

        assertThat(a == b).isFalse();
        assertThat(a != b).isTrue();
    }

    @Test
    void givenTwoIntegersWithDifferentValues_whenEqualityOperators_thenNotConsideredSame() {
        Integer a = new Integer(1);
        Integer b = new Integer(2);

        assertThat(a == b).isFalse();
        assertThat(a != b).isTrue();
    }

    @Test
    void givenTwoIntegersWithSameReference_whenEqualityOperators_thenConsideredSame() {
        Integer a = new Integer(1);
        Integer b = a;

        assertThat(a == b).isTrue();
        assertThat(a != b).isFalse();
    }

    @Test
    void givenTwoIntegersFromValueOfWithSameValues_whenEqualityOperators_thenConsideredSame() {
        Integer a = Integer.valueOf(1);
        Integer b = Integer.valueOf(1);

        assertThat(a == b).isTrue();
        assertThat(a != b).isFalse();
    }

    @Test
    void givenTwoStringsWithSameValues_whenEqualityOperators_thenNotConsideredSame() {
        String a = new String("Hello!");
        String b = new String("Hello!");

        assertThat(a == b).isFalse();
        assertThat(a != b).isTrue();
    }

    @Test
    void givenTwoStringsFromLiteralsWithSameValues_whenEqualityOperators_thenConsideredSame() {
        String a = "Hello!";
        String b = "Hello!";

        assertThat(a == b).isTrue();
        assertThat(a != b).isFalse();
    }

    @Test
    void givenTwoNullObjects_whenEqualityOperators_thenConsideredSame() {
        Object a = null;
        Object b = null;

        assertThat(a == b).isTrue();
        assertThat(a != b).isFalse();
    }

    @Test
    void givenTwoObjectsOneNull_whenEqualityOperators_thenNotConsideredSame() {
        Object a = null;
        Object b = "Hello!";

        assertThat(a == b).isFalse();
        assertThat(a != b).isTrue();
    }
}
