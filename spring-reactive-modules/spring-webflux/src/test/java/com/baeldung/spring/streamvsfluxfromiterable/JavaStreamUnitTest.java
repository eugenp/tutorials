package com.baeldung.spring.streamvsfluxfromiterable;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

@SuppressWarnings("unchecked")
public class JavaStreamUnitTest {

    @Test
    void givenList_whenProcessedWithStream_thenReturnDoubledEvenNumbers() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);

        List<Integer> doubledEvenNumbers = numbers.stream()
          .filter(n -> n % 2 == 0)
          .map(n -> n * n)
          .toList();
        assertEquals(List.of(4, 16), doubledEvenNumbers);
    }

    @Test
    void givenList_whenNoTerminalOperator_thenNoResponse() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        Function<Integer, Integer> mockMapper = mock(Function.class);
        Stream<Integer> streamPipeline = numbers.stream()
          .map(mockMapper);
        verifyNoInteractions(mockMapper);

        List<Integer> mappedList = streamPipeline.toList();
        verify(mockMapper, times(5));
    }

    @Test
    void givenList_whenDividedByZeroInStream_thenThrowException() {
        List<Integer> numbers = List.of(1, 2, 0, 4, 5);
        assertThrows(ArithmeticException.class, () -> numbers.stream()
          .map(n -> 10 / n)
          .toList());
    }

    @Test
    void givenStream_whenReused_thenThrowException() {
        List<Integer> numbers = List.of(1, 2, 3, 4, 5);
        Stream<Integer> doubleStream = numbers.stream()
          .map(n -> n * 2);

        assertEquals(List.of(2, 4, 6, 8, 10), doubleStream.toList());

        assertThrows(IllegalStateException.class, doubleStream::toList);
    }

}
