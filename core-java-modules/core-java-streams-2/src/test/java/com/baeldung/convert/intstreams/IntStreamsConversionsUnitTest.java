package com.baeldung.convert.intstreams;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.assertj.core.api.Assertions.assertThat;

public class IntStreamsConversionsUnitTest {

    @Test
    public void intStreamToArray() {
        int[] first50EvenNumbers = IntStream.iterate(0, i -> i + 2)
                .limit(50)
                .toArray();

        assertThat(first50EvenNumbers).hasSize(50);
        assertThat(first50EvenNumbers[2]).isEqualTo(4);
    }

    @Test
    public void intStreamToList() {
        List<Integer> first50IntegerNumbers = IntStream.range(0, 50)
                .boxed()
                .collect(Collectors.toList());

        assertThat(first50IntegerNumbers).hasSize(50);
        assertThat(first50IntegerNumbers.get(2)).isEqualTo(2);
    }

    @Test
    public void intStreamToString() {
        String first3numbers = IntStream.of(0, 1, 2)
                .mapToObj(String::valueOf)
                .collect(Collectors.joining(", ", "[", "]"));

        assertThat(first3numbers).isEqualTo("[0, 1, 2]");
    }
}
