package com.baeldung.resolvingjunitconstructorerror;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class ResolvingJUnitConstructorErrorJUnit4UnitTest {

    private final int input;
    private final ResolvingJUnitConstructorError service = new ResolvingJUnitConstructorError();

    public ResolvingJUnitConstructorErrorJUnit4UnitTest(int input) {
        this.input = input;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{{2}, {3}, {4}});
    }

    @Test
    public void givenNumber_whenSquare_thenReturnsCorrectResult() {
        assertEquals(input * input, service.square(input));
    }
}
