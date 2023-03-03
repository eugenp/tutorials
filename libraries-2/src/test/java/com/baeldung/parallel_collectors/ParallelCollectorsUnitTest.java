package com.baeldung.parallel_collectors;

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

import static com.pivovarit.collectors.ParallelCollectors.parallel;
import static com.pivovarit.collectors.ParallelCollectors.parallelOrdered;
import static com.pivovarit.collectors.ParallelCollectors.parallelToCollection;
import static com.pivovarit.collectors.ParallelCollectors.parallelToList;
import static com.pivovarit.collectors.ParallelCollectors.parallelToMap;
import static com.pivovarit.collectors.ParallelCollectors.parallelToStream;

public class ParallelCollectorsUnitTest {

    @Test
    public void shouldProcessInParallelWithStreams() {
        List<Integer> ids = Arrays.asList(1, 2, 3);

        List<String> results = ids.parallelStream()
          .map(i -> fetchById(i))
          .collect(Collectors.toList());

        System.out.println(results);
    }

    @Test
    public void shouldProcessInParallelWithParallelCollectors() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        CompletableFuture<List<String>> results = ids.stream()
          .collect(parallelToList(ParallelCollectorsUnitTest::fetchById, executor, 4));

        System.out.println(results.join());
    }

    @Test
    public void shouldCollectToList() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        List<String> results = ids.stream()
          .collect(parallelToList(ParallelCollectorsUnitTest::fetchById, executor, 4))
          .join();

        System.out.println(results); // [user-1, user-2, user-3]
    }

    @Test
    public void shouldCollectToCollection() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        List<String> results = ids.stream()
          .collect(parallelToCollection(i -> fetchById(i), LinkedList::new, executor, 4))
          .join();

        System.out.println(results); // [user-1, user-2, user-3]
    }

    @Test
    public void shouldCollectToStream() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        Map<Integer, List<String>> results = ids.stream()
          .collect(parallelToStream(i -> fetchById(i), executor, 4))
          .thenApply(stream -> stream.collect(Collectors.groupingBy(i -> i.length())))
          .join();

        System.out.println(results); // [user-1, user-2, user-3]
    }

    @Test
    public void shouldStreamInCompletionOrder() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        ids.stream()
          .collect(parallel(ParallelCollectorsUnitTest::fetchByIdWithRandomDelay, executor, 4))
          .forEach(System.out::println);
    }

    @Test
    public void shouldStreamInOriginalOrder() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        ids.stream()
          .collect(parallelOrdered(ParallelCollectorsUnitTest::fetchByIdWithRandomDelay, executor, 4))
          .forEach(System.out::println);
    }

    @Test
    public void shouldCollectToMap() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        Map<Integer, String> results = ids.stream()
          .collect(parallelToMap(i -> i, ParallelCollectorsUnitTest::fetchById, executor, 4))
          .join();

        System.out.println(results); // {1=user-1, 2=user-2, 3=user-3}
    }

    @Test
    public void shouldCollectToTreeMap() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        Map<Integer, String> results = ids.stream()
          .collect(parallelToMap(i -> i, ParallelCollectorsUnitTest::fetchById, TreeMap::new,  executor, 4))
          .join();

        System.out.println(results); // {1=user-1, 2=user-2, 3=user-3}
    }

    @Test
    public void shouldCollectToTreeMapAndResolveClashes() {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<Integer> ids = Arrays.asList(1, 2, 3);

        Map<Integer, String> results = ids.stream()
          .collect(parallelToMap(i -> i, ParallelCollectorsUnitTest::fetchById, TreeMap::new, (s1, s2) -> s1,   executor, 4))
          .join();

        System.out.println(results); // {1=user-1, 2=user-2, 3=user-3}
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
