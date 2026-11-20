package com.baeldung.resolvingjunitconstructorerror;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResolvingJUnitConstructorErrorJUnit5UnitTest {

    private final ResolvingJUnitConstructorError service = new ResolvingJUnitConstructorError();

    @ParameterizedTest
    @ValueSource(ints = {2, 3, 4})
    void givenNumber_whenSquare_thenReturnsCorrectResult(int input) {
        assertEquals(input * input, service.square(input));
    }
}
