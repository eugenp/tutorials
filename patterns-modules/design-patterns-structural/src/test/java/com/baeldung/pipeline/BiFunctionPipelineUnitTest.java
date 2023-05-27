package com.baeldung.pipeline;

import static org.junit.jupiter.api.Assertions.*;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.junit.jupiter.api.Test;

class BiFunctionPipelineUnitTest {

    @Test
    void whenCombiningFunctionAndBiFunctions_andInitializingPipeline_thenResultIsCorrect() {
        BiFunction<Integer, Integer, Integer> add = Integer::sum;
        BiFunction<Integer, Integer, Integer> mul = (a, b) -> a * b;
        Function<Integer, String> toString = Object::toString;
        BiFunction<Integer, Integer, String> pipeline = add.andThen(a -> mul.apply(a, 2))
            .andThen(toString);
        String result = pipeline.apply(1, 2);
        String expected = "6";
        assertEquals(expected, result);
    }
}