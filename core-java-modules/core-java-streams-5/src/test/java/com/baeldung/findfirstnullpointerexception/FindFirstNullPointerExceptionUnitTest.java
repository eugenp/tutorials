package com.baeldung.findfirstnullpointerexception;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.junit.Test;

public class FindFirstNullPointerExceptionUnitTest {

    private final List<String> inputs = Arrays.asList(null, "foo", "bar");

    @Test(expected = NullPointerException.class)
    public void givenStream_whenCallingFindFirst_thenThrowNullPointerException() {
        Optional<String> firstElement = inputs.stream()
          .findFirst();
    }

    @Test
    public void givenStream_whenUsingOfNullableBeforeFindFirst_thenCorrect() {
        Optional<String> firstElement = inputs.stream()
          .map(Optional::ofNullable)
          .findFirst()
          .flatMap(Function.identity());

        assertTrue(firstElement.isEmpty());
    }

    @Test
    public void givenStream_whenUsingFilterBeforeFindFirst_thenCorrect() {
        Optional<String> firstNonNullElement = inputs.stream()
          .filter(Objects::nonNull)
          .findFirst();

        assertTrue(firstNonNullElement.isPresent());
    }

}
