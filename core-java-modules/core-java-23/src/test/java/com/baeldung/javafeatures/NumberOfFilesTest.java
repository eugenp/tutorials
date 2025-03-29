package com.baeldung.javafeatures;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.Function;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class NumberOfFilesTest {

    private final String resourcePath = this.getClass()
        .getResource("/filesToBeFound")
        .getPath();

    @ParameterizedTest
    @MethodSource("functionsUnderTest")
    void shouldReturnNumberOfAllFilesInsidePath(Function<String, Long> functionUnderTest) {
        long expectedCount = 5;

        long result = functionUnderTest.apply(resourcePath);

        assertThat(result).isEqualTo(expectedCount);
    }

    private static Stream<Arguments> functionsUnderTest() {
        return Stream.<Function<String, Long>> of(
            FindFolder::numberOfFilesIn_Walk,
                FindFolder::numberOfFilesIn_classic,
                FindFolder::numberOfFilesIn_Stream,
                FindFolder::numberOfFilesIn_NIO
            )
            .map(Arguments::of);
    }
}
