package com.baeldung.pipeline;

import static org.assertj.core.api.Assertions.assertThat;

import com.baeldung.pipeline.pipes.Pipe;
import org.junit.jupiter.api.Test;

class PipeUnitTest {

    @Test
    void whenCombiningThreePipes_andInitializingPipeline_thenResultIsCorrect() {
        Pipe<Integer, Integer> square = s -> s * s;
        Pipe<Integer, Integer> half = s -> s / 2;
        Pipe<Integer, String> toString = Object::toString;
        Pipe<Integer, String> pipeline = square.add(half).add(toString);
        String result = pipeline.process(5);
        String expected = "12";
        assertThat(result).isEqualTo(expected);
    }
}