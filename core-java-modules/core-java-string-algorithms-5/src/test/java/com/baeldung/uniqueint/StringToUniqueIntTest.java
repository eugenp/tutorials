package com.baeldung.uniqueint;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Named;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import net.jqwik.api.Arbitraries;

class StringToUniqueIntTest {

    @ParameterizedTest
    @MethodSource("implementations")
    public void shouldEnsureLowLevelUniqueness_1k_elements(Function<String, Integer> implementation) {
        Stream<String> strings = uniqueStringsOfSize(1_000);

        List<Integer> integers = strings.map(implementation)
            .toList();

        assertThat(integers).doesNotHaveDuplicates();
    }

    @ParameterizedTest
    @MethodSource("implementations")
    public void shouldEnsureMidLevelUniqueness_10k_elements(Function<String, Integer> implementation) {
        Stream<String> strings = uniqueStringsOfSize(50_000);

        List<Integer> integers = strings.map(implementation)
            .toList();

        assertThat(integers).doesNotHaveDuplicates();
    }

    @ParameterizedTest
    @MethodSource("implementations")
    @Disabled
    public void shouldEnsureHighLevelUniqueness_500k_elements(Function<String, Integer> implementation) {
        Stream<String> strings = uniqueStringsOfSize(500_000);

        List<Integer> integers = strings.map(implementation)
            .toList();

        assertThat(integers).doesNotHaveDuplicates();
    }

    private static Stream<Arguments> implementations() {
        return Stream.of(Arguments.of(Named.<Function<String, Integer>> of("toIntByHashCode", StringToUniqueInt::toIntByHashCode)),
            Arguments.of(Named.<Function<String, Integer>> of("toIntByCR32", StringToUniqueInt::toIntByCR32)),
            Arguments.of(Named.<Function<String, Integer>> of("toIntByCharFormula", StringToUniqueInt::toIntByCharFormula)),
            Arguments.of(Named.<Function<String, Integer>> of("toIntByMD5", StringToUniqueInt::toIntByMD5)),
            Arguments.of(Named.<Function<String, Integer>> of("toIntByLookup", StringToUniqueInt::toIntByLookup))
        );
    }

    private static Stream<String> uniqueStringsOfSize(int size) {
        return Arbitraries.strings()
            .filter(it -> !it.isBlank())
            .stream()
            .ofMinSize(size)
            .ofMaxSize(size)
            .uniqueElements()
            .sample();
    }
}