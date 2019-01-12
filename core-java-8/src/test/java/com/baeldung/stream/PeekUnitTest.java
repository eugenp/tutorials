package com.baeldung.stream;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.StringWriter;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PeekUnitTest {

    private StringWriter out;

    @BeforeEach
    void setup() {
        out = new StringWriter();
    }

    @Test
    void givenStringStream_whenCallingPeekOnly_thenNoElementProcessed() {
        // given
        Stream<String> nameStream = Stream.of("Alice", "Bob", "Chuck");

        // when
        nameStream.peek(out::append);

        // then
        assertThat(out.toString()).isEmpty();
    }

    @Test
    void givenStringStream_whenCallingForEachOnly_thenElementsProcessed() {
        // given
        Stream<String> nameStream = Stream.of("Alice", "Bob", "Chuck");

        // when
        nameStream.forEach(out::append);

        // then
        assertThat(out.toString()).isEqualTo("AliceBobChuck");
    }

    @Test
    void givenStringStream_whenCallingPeekAndNoopForEach_thenElementsProcessed() {
        // given
        Stream<String> nameStream = Stream.of("Alice", "Bob", "Chuck");

        // when
        nameStream.peek(out::append)
            .forEach(this::noop);

        // then
        assertThat(out.toString()).isEqualTo("AliceBobChuck");
    }

    @Test
    void givenStringStream_whenCallingPeekAndCollect_thenElementsProcessed() {
        // given
        Stream<String> nameStream = Stream.of("Alice", "Bob", "Chuck");

        // when
        nameStream.peek(out::append)
            .collect(Collectors.toList());

        // then
        assertThat(out.toString()).isEqualTo("AliceBobChuck");
    }

    @Test
    void givenStringStream_whenCallingPeekAndForEach_thenElementsProcessedTwice() {
        // given
        Stream<String> nameStream = Stream.of("Alice", "Bob", "Chuck");

        // when
        nameStream.peek(out::append)
            .forEach(out::append);

        // then
        assertThat(out.toString()).isEqualTo("AliceAliceBobBobChuckChuck");
    }

    private void noop(String s) {
    }

}
