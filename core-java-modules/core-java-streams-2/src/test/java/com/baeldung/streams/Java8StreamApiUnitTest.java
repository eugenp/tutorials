package com.baeldung.streams;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

import static org.junit.Assert.*;

public class Java8StreamApiUnitTest {

    private long counter;

    private static Logger log = LoggerFactory.getLogger(Java8StreamApiUnitTest.class);

    private List<Product> productList;

    @Before
    public void init() {
        productList = Arrays.asList(new Product(23, "potatoes"), new Product(14, "orange"), new Product(13, "lemon"), new Product(23, "bread"), new Product(13, "sugar"));
    }

    @Test
    public void checkPipeline_whenStreamOneElementShorter_thenCorrect() {

        List<String> list = Arrays.asList("abc1", "abc2", "abc3");
        long size = list.stream().skip(1).map(element -> element.substring(0, 3)).count();
        assertEquals(list.size() - 1, size);
    }

    @Test
    public void checkOrder_whenChangeQuantityOfMethodCalls_thenCorrect() {

        List<String> list = Arrays.asList("abc1", "abc2", "abc3");

        counter = 0;
        long sizeFirst = list.stream().skip(2).map(element -> {
            wasCalled();
            return element.substring(0, 3);
        }).count();
        assertEquals(1, counter);

        counter = 0;
        long sizeSecond = list.stream().map(element -> {
            wasCalled();
            return element.substring(0, 3);
        }).skip(2).count();
        assertEquals(3, counter);
    }

    @Test
    public void createEmptyStream_whenEmpty_thenCorrect() {

        Stream<String> streamEmpty = Stream.empty();
        assertEquals(0, streamEmpty.count());

        List<String> names = Collections.emptyList();
        Stream<String> streamOf = Product.streamOf(names);
        assertTrue(streamOf.count() == 0);
    }

    @Test
    public void createStream_whenCreated_thenCorrect() {

        Collection<String> collection = Arrays.asList("a", "b", "c");
        Stream<String> streamOfCollection = collection.stream();
        assertEquals(3, streamOfCollection.count());

        Stream<String> streamOfArray = Stream.of("a", "b", "c");
        assertEquals(3, streamOfArray.count());

        String[] arr = new String[] { "a", "b", "c" };
        Stream<String> streamOfArrayPart = Arrays.stream(arr, 1, 3);
        assertEquals(2, streamOfArrayPart.count());

        IntStream intStream = IntStream.range(1, 3);
        LongStream longStream = LongStream.rangeClosed(1, 3);
        Random random = new Random();
        DoubleStream doubleStream = random.doubles(3);
        assertEquals(2, intStream.count());
        assertEquals(3, longStream.count());
        assertEquals(3, doubleStream.count());

        IntStream streamOfChars = "abc".chars();
        IntStream str = "".chars();
        assertEquals(3, streamOfChars.count());

        Stream<String> streamOfString = Pattern.compile(", ").splitAsStream("a, b, c");
        assertEquals("a", streamOfString.findFirst().get());

        Path path = getPath();
        Stream<String> streamOfStrings = null;
        try {
            streamOfStrings = Files.lines(path, Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.error("Error creating streams from paths {}", path, e.getMessage(), e);
        }
        assertEquals("a", streamOfStrings.findFirst().get());

        Stream<String> streamBuilder = Stream.<String> builder().add("a").add("b").add("c").build();
        assertEquals(3, streamBuilder.count());

        Stream<String> streamGenerated = Stream.generate(() -> "element").limit(10);
        assertEquals(10, streamGenerated.count());

        Stream<Integer> streamIterated = Stream.iterate(40, n -> n + 2).limit(20);
        assertTrue(40 <= streamIterated.findAny().get());
    }

    @Test
    public void runStreamPipeline_whenOrderIsRight_thenCorrect() {

        List<String> list = Arrays.asList("abc1", "abc2", "abc3");
        Optional<String> stream = list.stream().filter(element -> {
            log.info("filter() was called");
            return element.contains("2");
        }).map(element -> {
            log.info("map() was called");
            return element.toUpperCase();
        }).findFirst();
    }

    @Test
    public void reduce_whenExpected_thenCorrect() {

        OptionalInt reduced = IntStream.range(1, 4).reduce((a, b) -> a + b);
        assertEquals(6, reduced.getAsInt());

        int reducedTwoParams = IntStream.range(1, 4).reduce(10, (a, b) -> a + b);
        assertEquals(16, reducedTwoParams);

        int reducedThreeParams = Stream.of(1, 2, 3).reduce(10, (a, b) -> a + b, (a, b) -> {
            log.info("combiner was called");
            return a + b;
        });
        assertEquals(16, reducedThreeParams);

        int reducedThreeParamsParallel = Arrays.asList(1, 2, 3).parallelStream().reduce(10, (a, b) -> a + b, (a, b) -> {
            log.info("combiner was called");
            return a + b;
        });
        assertEquals(36, reducedThreeParamsParallel);
    }

    @Test
    public void collecting_whenAsExpected_thenCorrect() {

        List<String> collectorCollection = productList.stream().map(Product::getName).collect(Collectors.toList());

        assertTrue(collectorCollection instanceof List);
        assertEquals(5, collectorCollection.size());

        String listToString = productList.stream().map(Product::getName).collect(Collectors.joining(", ", "[", "]"));

        assertTrue(listToString.contains(",") && listToString.contains("[") && listToString.contains("]"));

        double averagePrice = productList.stream().collect(Collectors.averagingInt(Product::getPrice));
        assertTrue(17.2 == averagePrice);

        int summingPrice = productList.stream().collect(Collectors.summingInt(Product::getPrice));
        assertEquals(86, summingPrice);

        IntSummaryStatistics statistics = productList.stream().collect(Collectors.summarizingInt(Product::getPrice));
        assertEquals(23, statistics.getMax());

        Map<Integer, List<Product>> collectorMapOfLists = productList.stream().collect(Collectors.groupingBy(Product::getPrice));
        assertEquals(3, collectorMapOfLists.keySet().size());

        Map<Boolean, List<Product>> mapPartioned = productList.stream().collect(Collectors.partitioningBy(element -> element.getPrice() > 15));
        assertEquals(2, mapPartioned.keySet().size());

    }

    @Test(expected = UnsupportedOperationException.class)
    public void collect_whenThrows_thenCorrect() {
        Set<Product> unmodifiableSet = productList.stream().collect(Collectors.collectingAndThen(Collectors.toSet(), Collections::unmodifiableSet));
        unmodifiableSet.add(new Product(4, "tea"));
    }

    @Test
    public void customCollector_whenResultContainsAllElementsFrSource_thenCorrect() {
        Collector<Product, ?, LinkedList<Product>> toLinkedList = Collector.of(LinkedList::new, LinkedList::add, (first, second) -> {
            first.addAll(second);
            return first;
        });

        LinkedList<Product> linkedListOfPersons = productList.stream().collect(toLinkedList);
        assertTrue(linkedListOfPersons.containsAll(productList));
    }

    @Test
    public void parallelStream_whenWorks_thenCorrect() {
        Stream<Product> streamOfCollection = productList.parallelStream();
        boolean isParallel = streamOfCollection.isParallel();
        boolean haveBigPrice = streamOfCollection.map(product -> product.getPrice() * 12).anyMatch(price -> price > 200);
        assertTrue(isParallel && haveBigPrice);
    }

    @Test
    public void parallel_whenIsParallel_thenCorrect() {
        IntStream intStreamParallel = IntStream.range(1, 150).parallel().map(element -> element * 34);
        boolean isParallel = intStreamParallel.isParallel();
        assertTrue(isParallel);
    }

    @Test
    public void parallel_whenIsSequential_thenCorrect() {
        IntStream intStreamParallel = IntStream.range(1, 150).parallel().map(element -> element * 34);
        IntStream intStreamSequential = intStreamParallel.sequential();
        boolean isParallel = intStreamParallel.isParallel();
        assertFalse(isParallel);
    }

    private Path getPath() {
        Path path = null;
        try {
            path = Files.createTempFile(null, ".txt");
        } catch (IOException e) {
            log.error(e.getMessage());
        }

        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("a\nb\nc");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return path;
    }

    private void wasCalled() {
        counter++;
    }
}
