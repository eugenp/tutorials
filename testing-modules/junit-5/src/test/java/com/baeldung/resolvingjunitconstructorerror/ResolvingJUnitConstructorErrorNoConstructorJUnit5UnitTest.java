package com.baeldung.resolvingjunitconstructorerror;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResolvingJUnitConstructorErrorNoConstructorJUnit5UnitTest {

    private ResolvingJUnitConstructorError service;
    private int input;

    @BeforeEach
    void setUp() {
        service = new ResolvingJUnitConstructorError();
        input = 2;
    }

    @Test
    void givenNumber_whenSquare_thenReturnsCorrectResult() {
        assertEquals(input * input, service.square(input));
    }
}
