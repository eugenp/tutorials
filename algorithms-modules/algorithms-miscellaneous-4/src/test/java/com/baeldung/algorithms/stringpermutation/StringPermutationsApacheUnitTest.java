package com.baeldung.algorithms.stringpermutation;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StringPermutationsApacheUnitTest {

    @ParameterizedTest
    @CsvSource({"abc, 6",
                "hello, 120",
                "aaaaaa, 720"})
    void testPermutationsWithRepetitions(String string, int numberOfPermutations) {
        StringPermutationsApache permutationGenerator = new StringPermutationsApache();
        final List<String> permutations = permutationGenerator.eagerPermutationWithRepetitions(string);
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
        final List<String> permutations = permutationGenerator.lazyPermutationWithoutRepetitions(string);
        int size = permutations.size();
        assertThat(size)
            .as("\"%s\" should have %d permutation, but had %d", string, numberOfPermutations, size)
            .isEqualTo(numberOfPermutations);
    }
}