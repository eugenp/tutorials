package com.baeldung.pipeline;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.Function;
import org.junit.jupiter.api.Test;

class FunctionPipelineUnitTest {

    @Test
    void whenCombiningThreeFunctions_andInitializingPipeline_thenResultIsCorrect() {
        Function<Integer, Integer> square = s -> s * s;
        Function<Integer, Integer> half = s -> s / 2;
        Function<Integer, String> toString = Object::toString;
        Function<Integer, String> pipeline = square.andThen(half)
            .andThen(toString);
        String result = pipeline.apply(5);
        String expected = "12";
        assertEquals(expected, result);
    }
}