package com.baeldung.comparing;

import com.google.common.base.Objects;
import com.google.common.collect.ComparisonChain;
import com.google.common.primitives.Ints;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class GuavaUnitTest {

    @Nested
    class ObjectsEqualMethod {
        @Test
        void givenTwoStringsWithSameValues_whenObjectsEqualMethods_thenTrue() {
            String a = new String("Hello!");
            String b = new String("Hello!");

            assertThat(Objects.equal(a, b)).isTrue();
        }

        @Test
        void givenTwoStringsWithDifferentValues_whenObjectsEqualMethods_thenFalse() {
            String a = new String("Hello!");
            String b = new String("Hello World!");

            assertThat(Objects.equal(a, b)).isFalse();
        }
    }

    @Nested
    class ComparisonMethods {
        @Test
        void givenTwoIntsWithConsecutiveValues_whenIntsCompareMethods_thenNegative() {
            int first = 1;
            int second = 2;
            assertThat(Ints.compare(first, second)).isNegative();
        }

        @Test
        void givenTwoIntsWithSameValues_whenIntsCompareMethods_thenZero() {
            int first = 1;
            int second = 1;

            assertThat(Ints.compare(first, second)).isZero();
        }

        @Test
        void givenTwoIntsWithConsecutiveValues_whenIntsCompareMethodsReversed_thenNegative() {
            int first = 1;
            int second = 2;

            assertThat(Ints.compare(second, first)).isPositive();
        }
    }

    @Nested
    class ComparisonChainClass {
        @Test
        void givenTwoPersonWithEquals_whenComparisonChainByLastNameThenFirstName_thenSortedJoeFirstAndNathalieSecond() {
            PersonWithEquals nathalie = new PersonWithEquals("Nathalie", "Portman");
            PersonWithEquals joe = new PersonWithEquals("Joe", "Portman");

            int comparisonResult = ComparisonChain.start()
              .compare(nathalie.lastName(), joe.lastName())
              .compare(nathalie.firstName(), joe.firstName())
              .result();

            assertThat(comparisonResult).isPositive();
        }
    }
}
