package com.baeldung.streams.debug;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.stream.IntStream;

import org.junit.Test;

public class Example1 {
    @Test
    public void whenDebugging_thenInformationIsShown() {
        int[] listOutputSorted = IntStream.of(-3, 10, -4, 1, 3)
            .sorted()
            .toArray();

        assertThat(listOutputSorted).isSorted();
    }
}
