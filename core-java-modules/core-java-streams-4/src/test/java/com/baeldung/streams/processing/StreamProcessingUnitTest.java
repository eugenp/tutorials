package com.baeldung.streams.processing;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.apache.commons.collections4.ListUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.Iterators;

import cyclops.data.LazySeq;
import cyclops.reactive.ReactiveSeq;
import io.reactivex.rxjava3.core.Observable;
import reactor.core.publisher.Flux;

public class StreamProcessingUnitTest {
    public final int BATCH_SIZE = 10;

    private final List<Integer> firstBatch = List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    private final List<Integer> secondBatch = List.of(10, 11, 12, 13, 14, 15, 16, 17, 18, 19);
    private final List<Integer> thirdBatch = List.of(20, 21, 22, 23, 24, 25, 26, 27, 28, 29);
    private final List<Integer> fourthBatch = List.of(30, 31, 32, 33);

    public Stream<Integer> data;

    @BeforeEach
    public void setUp() {
        data = IntStream.range(0, 34)
          .boxed();
    }

    @Test
    public void givenAStreamOfData_whenIsProcessingInBatchUsingSpliterator_thenFourBatchesAreObtained() {
        Collection<List<Integer>> result = new ArrayList<>();
        CustomBatchIterator.batchStreamOf(data, BATCH_SIZE)
          .forEach(result::add);
        assertTrue(result.contains(firstBatch));
        assertTrue(result.contains(secondBatch));
        assertTrue(result.contains(thirdBatch));
        assertTrue(result.contains(fourthBatch));
    }

    @Test
    public void givenAStreamOfData_whenIsProcessingInBatchUsingCollectionAPI_thenFourBatchesAreObtained() {
        Collection<List<Integer>> result = data.collect(Collectors.groupingBy(it -> it / BATCH_SIZE))
          .values();
        assertTrue(result.contains(firstBatch));
        assertTrue(result.contains(secondBatch));
        assertTrue(result.contains(thirdBatch));
        assertTrue(result.contains(fourthBatch));
    }

    @Test
    public void givenAStreamOfData_whenIsProcessingInBatchParallelUsingCollectionAPI_thenFourBatchesAreObtained() {
        Collection<List<Integer>> result = data.parallel()
          .collect(Collectors.groupingBy(it -> it / BATCH_SIZE))
          .values();
        assertTrue(result.contains(firstBatch));
        assertTrue(result.contains(secondBatch));
        assertTrue(result.contains(thirdBatch));
        assertTrue(result.contains(fourthBatch));
    }

    @Test
    public void givenAStreamOfData_whenIsProcessingInBatchUsingRxJavaV3_thenFourBatchesAreObtained() {
        // RxJava v3
        Collection<List<Integer>> result = new ArrayList<>();
        Observable.fromStream(data)
          .buffer(BATCH_SIZE)
          .subscribe(result::add);
        assertTrue(result.contains(firstBatch));
        assertTrue(result.contains(secondBatch));
        assertTrue(result.contains(thirdBatch));
        assertTrue(result.contains(fourthBatch));
    }

    @Test
    public void givenAStreamOfData_whenIsProcessingInBatchUsingReactor_thenFourBatchesAreObtained() {
        Collection<List<Integer>> result = new ArrayList<>();
        Flux.fromStream(data)
          .buffer(BATCH_SIZE)
          .subscribe(result::add);
        assertTrue(result.contains(firstBatch));
        assertTrue(result.contains(secondBatch));
        assertTrue(result.contains(thirdBatch));
        assertTrue(result.contains(fourthBatch));
    }

    @Test
    public void givenAStreamOfData_whenIsProcessingInBatchUsingApacheCommon_thenFourBatchesAreObtained() {
        Collection<List<Integer>> result = new ArrayList<>(ListUtils.partition(data.collect(Collectors.toList()), BATCH_SIZE));
        assertTrue(result.contains(firstBatch));
        assertTrue(result.contains(secondBatch));
        assertTrue(result.contains(thirdBatch));
        assertTrue(result.contains(fourthBatch));
    }

    @Test
    public void givenAStreamOfData_whenIsProcessingInBatchUsingGuava_thenFourBatchesAreObtained() {
        Collection<List<Integer>> result = new ArrayList<>();
        Iterators.partition(data.iterator(), BATCH_SIZE)
          .forEachRemaining(result::add);
        assertTrue(result.contains(firstBatch));
        assertTrue(result.contains(secondBatch));
        assertTrue(result.contains(thirdBatch));
        assertTrue(result.contains(fourthBatch));
    }

    @Test
    public void givenAStreamOfData_whenIsProcessingInBatchUsingCyclops_thenFourBatchesAreObtained() {
        Collection<List<Integer>> result = new ArrayList<>();
        ReactiveSeq.fromStream(data)
          .grouped(BATCH_SIZE)
          .toList()
          .forEach(value -> result.add(value.collect(Collectors.toList())));
        assertTrue(result.contains(firstBatch));
        assertTrue(result.contains(secondBatch));
        assertTrue(result.contains(thirdBatch));
        assertTrue(result.contains(fourthBatch));
    }

    @Test
    public void givenAStreamOfData_whenIsProcessingInBatchUsingCyclopsLazy_thenFourBatchesAreObtained() {
        Collection<List<Integer>> result = new ArrayList<>();
        LazySeq.fromStream(data)
          .grouped(BATCH_SIZE)
          .toList()
          .forEach(value -> result.add(value.collect(Collectors.toList())));
        assertTrue(result.contains(firstBatch));
        assertTrue(result.contains(secondBatch));
        assertTrue(result.contains(thirdBatch));
        assertTrue(result.contains(fourthBatch));
    }
}