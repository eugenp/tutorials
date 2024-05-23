package com.baeldung.streams.mapnotnull;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

public class ReturnNotNullElementsFromStreamMapUnitTest {

    private static final List<String> INPUT = List.of("f o o", "a,b,c,d", "b a r", "w,x,y,z", "w,o,w");
    private static final List<String> EXPECTED = List.of("a b c d", "w x y z", "w o w");

    private String commaToSpace(String input) {
        if (input.contains(",")) {
            return input.replaceAll(",", " ");
        } else {
            return null;
        }
    }

    @Test
    void whenUseMapAndFilter_thenGetTheExpectedResult() {
        List<String> result = INPUT.stream()
            .map(str -> commaToSpace(str))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        assertEquals(EXPECTED, result);
    }

    @Test
    void whenUseOptional_thenGetTheExpectedResult() {
        //unnecessarily wrapping in Optional, not recommended
        List<String> result = INPUT.stream()
            .map(str -> Optional.ofNullable(commaToSpace(str)))
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toList());
        assertEquals(EXPECTED, result);
    }

    private Optional<String> commaToSpaceOptional(String input) {
        if (input.contains(",")) {
            return Optional.of(input.replaceAll(",", " "));
        } else {
            return Optional.empty();
        }
    }

    @Test
    void whenUseMapAndFilterWithOptionalReturnValues_thenGetTheExpectedResult() {
        List<String> result = INPUT.stream()
            .map(str -> commaToSpaceOptional(str).orElse(null))
            .filter(Objects::nonNull)
            .collect(Collectors.toList());
        assertEquals(EXPECTED, result);
    }
}