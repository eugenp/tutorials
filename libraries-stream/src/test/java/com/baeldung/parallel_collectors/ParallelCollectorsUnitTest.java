package com.baeldung.parallel_collectors;

import com.pivovarit.collectors.ParallelCollectors;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.pivovarit.collectors.ParallelCollectors.parallel;
import static com.pivovarit.collectors.ParallelCollectors.parallelToOrderedStream;
import static com.pivovarit.collectors.ParallelCollectors.parallelToStream;
import static java.util.concurrent.CompletableFuture.completedFuture;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

public class ParallelCollectorsUnitTest {

    @Test
    public void shouldProcessInParallelWithStreams() {
        List<Integer> ids = Arrays.asList(1, 2, 3);

        List<String> results = ids.parallelStream()
          .map(i -> fetchById(i))
          .collect(toList());

        assertThat(results).containsExactly("user-1", "user-2", "user-3");
    }

    @Test
    public void shouldCollectInParallel() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        CompletableFuture<Stream<String>> results = ids.stream()
          .collect(parallel(ParallelCollectorsUnitTest::fetchById, executor, 4));

        assertThat(results.join()).containsExactly("user-1", "user-2", "user-3");
    }

    @Test
    public void shouldCollectToList() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        CompletableFuture<List<String>> results = ids.stream()
          .collect(parallel(ParallelCollectorsUnitTest::fetchById, toList(), executor, 4));

        assertThat(results.join()).containsExactly("user-1", "user-2", "user-3");
    }

    @Test
    public void shouldCollectToCollection() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        CompletableFuture<List<String>> results = ids.stream()
          .collect(parallel(i -> fetchById(i), toCollection(LinkedList::new), executor, 4));

        assertThat(results.join())
          .containsExactly("user-1", "user-2", "user-3");
    }

    @Test
    public void shouldCollectToStream() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        CompletableFuture<Map<Integer, List<String>>> results = ids.stream()
          .collect(parallel(i -> fetchById(i), executor, 4))
          .thenApply(stream -> stream.collect(Collectors.groupingBy(String::length)));

        assertThat(results.join())
          .hasSize(1)
          .containsEntry(6, Arrays.asList("user-1", "user-2", "user-3"));
    }

    @Test
    public void shouldStreamInCompletionOrder() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        Stream<String> result = ids.stream()
          .collect(parallelToStream(ParallelCollectorsUnitTest::fetchByIdWithRandomDelay, executor, 4));

        assertThat(result).contains("user-1", "user-2", "user-3");
    }

    @Test
    public void shouldStreamInOriginalOrder() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        Stream<String> result = ids.stream()
          .collect(parallelToOrderedStream(ParallelCollectorsUnitTest::fetchByIdWithRandomDelay, executor, 4));

        assertThat(result).containsExactly("user-1", "user-2", "user-3");
    }

    @Test
    public void shouldCollectToMap() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        CompletableFuture<Map<Integer, String>> results = ids.stream()
          .collect(parallel(i -> i, Collectors.toMap(i -> i, ParallelCollectorsUnitTest::fetchById), executor, 4));

        assertThat(results.join())
          .hasSize(3)
          .containsEntry(1, "user-1")
          .containsEntry(2, "user-2")
          .containsEntry(3, "user-3");
    }

    @Test
    public void shouldCollectToTreeMapAndResolveClashes() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        CompletableFuture<Map<Integer, String>> results = ids.stream()
          .collect(parallel(i -> i, Collectors.toMap(i -> i, ParallelCollectorsUnitTest::fetchById, (u1, u2) -> u1, TreeMap::new), executor, 4));

        assertThat(results.join())
          .hasSize(3)
          .containsEntry(1, "user-1")
          .containsEntry(2, "user-2")
          .containsEntry(3, "user-3");
    }

    @Test
    public void shouldCollectListOfFutures() {
        List<CompletableFuture<Integer>> futures = Arrays.asList(completedFuture(1), completedFuture(2), completedFuture(3));

        CompletableFuture<List<Integer>> result = futures.stream()
          .collect(ParallelCollectors.toFuture());

        assertThat(result.join()).containsExactly(1, 2, 3);
    }

    private static String fetchById(int id) {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            // ignore shamelessly
        }

        return "user-" + id;
    }

    private static String fetchByIdWithRandomDelay(int id) {
        try {
            Thread.sleep(ThreadLocalRandom.current().nextInt(1000));
        } catch (InterruptedException e) {
            // ignore shamelessly
        }

        return "user-" + id;
    }
}
