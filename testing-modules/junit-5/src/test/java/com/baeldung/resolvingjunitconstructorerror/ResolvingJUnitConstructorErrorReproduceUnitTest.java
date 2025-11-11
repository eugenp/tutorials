package com.baeldung.resolvingjunitconstructorerror;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ResolvingJUnitConstructorErrorReproduceUnitTest {

    private int input;

    public ResolvingJUnitConstructorErrorReproduceUnitTest(int input) {
        this.input = input;
    }

    @Test
    public void givenNumber_whenSquare_thenReturnsCorrectResult() {
        ResolvingJUnitConstructorError service = new ResolvingJUnitConstructorError();
        assertEquals(input * input, service.square(input));
    }
}
