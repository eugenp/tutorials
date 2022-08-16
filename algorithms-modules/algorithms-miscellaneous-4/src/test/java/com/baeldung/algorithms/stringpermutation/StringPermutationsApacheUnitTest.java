package com.baeldung.algorithms.stringpermutation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import java.util.List;
import org.apache.commons.collections4.iterators.PermutationIterator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StringPermutationsApacheUnitTest {

    @CsvSource({"abc, 6",
                "hello, 120",
                "aaaaaa, 720"})
    @DisplayName("Apache permutation for ")
    void testPermutationsWithRepetitions(String string, int numberOfPermutations) {
        StringPermutationsApache permutationGenerator = new StringPermutationsApache();
        final Collection<List<Character>> permutations = permutationGenerator.eagerPermutationWithRepetitions(string);
        final int size = permutations.size();
        assertThat(permutations)
            .as("\"%s\" should have %d permutation, but had %d", string, numberOfPermutations, size)
            .hasSize(numberOfPermutations);
    }

    @ParameterizedTest
    @CsvSource({"abc, 6",
        "hello, 120",
        "aaaaaa, 720"})
    void testPermutationsWithoutRepetitions(String string, int numberOfPermutations) {
        StringPermutationsApache permutationGenerator = new StringPermutationsApache();
        final PermutationIterator<Character> permutations = permutationGenerator.lazyPermutationWithoutRepetitions(string);
        int size = 0;
        while (permutations.hasNext()) {
            permutations.next();
            ++size;
        }
        assertThat(size)
            .as("\"%s\" should have %d permutation, but had %d", string, numberOfPermutations, size)
            .isEqualTo(numberOfPermutations);
    }
}