package com.baeldung.streams.processing.vavr;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import com.baeldung.streams.processing.StreamProcessingUnitTest;

import io.vavr.collection.List;
import io.vavr.collection.Stream;

public class StreamProcessingWithVavrUnitTest extends StreamProcessingUnitTest {

    private final List<Integer> firstBatch = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    private final List<Integer> secondBatch = List.of(10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
    private final List<Integer> thirdBatch = List.of(20, 21, 22, 23, 24, 25, 26, 27, 28, 29);
    private final List<Integer> fourthBatch = List.of(30, 31, 32, 33);

    @Test
    public void givenAStreamOfData_whenIsProcessingInBatchUsingVavr_thenFourBatchesAreObtained() {
        List<List<Integer>> result = Stream.ofAll(data)
          .toList()
          .grouped(BATCH_SIZE)
          .toList();
        assertTrue(result.contains(firstBatch));
        assertTrue(result.contains(secondBatch));
        assertTrue(result.contains(thirdBatch));
        assertTrue(result.contains(fourthBatch));
    }
}
