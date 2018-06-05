package com.baeldung.protonpack;

import com.codepoetics.protonpack.Indexed;
import com.codepoetics.protonpack.StreamUtils;
import com.codepoetics.protonpack.selectors.Selectors;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.toList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class StreamUtilsTests {

    @Test
    public void givenStream_whenZipWithIndex_shouldReturnZippedStreamWithIndex() {
        Stream<String> source = Stream.of("Foo", "Bar", "Baz");

        List<Indexed<String>> zipped = StreamUtils.zipWithIndex(source).collect(Collectors.toList());

        assertThat(zipped, contains(Indexed.index(0, "Foo"), Indexed.index(1, "Bar"), Indexed.index(2, "Baz")));
    }

    @Test
    public void givenTwoStreams_whenZip_shouldReturnZippedStream() {
        Stream<String> streamA = Stream.of("A", "B", "C");
        Stream<String> streamB = Stream.of("Apple", "Banana", "Carrot");

        List<String> zipped = StreamUtils.zip(streamA, streamB, (a, b) -> a + " is for " + b).collect(Collectors.toList());

        assertThat(zipped, contains("A is for Apple", "B is for Banana", "C is for Carrot"));
    }

    @Test
    public void givenThreeStreams_whenZip_shouldReturnZippedStream() {
        Stream<String> streamA = Stream.of("A", "B", "C");
        Stream<String> streamB = Stream.of("aggravating", "banausic", "complaisant");
        Stream<String> streamC = Stream.of("Apple", "Banana", "Carrot");

        List<String> zipped = StreamUtils.zip(streamA, streamB, streamC, (a, b, c) -> a + " is for " + b + " " + c).collect(Collectors.toList());

        assertThat(zipped, contains("A is for aggravating Apple", "B is for banausic Banana", "C is for complaisant Carrot"));
    }

    @Test
    // givenThreeStreams_whenMerge_shouldReturnMergedStream
    public void givenThreeStreams_whenMerge_shouldReturnMergedStream() {
        Stream<String> streamA = Stream.of("A", "B", "C");
        Stream<String> streamB = Stream.of("apple", "banana", "carrot", "date");
        Stream<String> streamC = Stream.of("fritter", "split", "cake", "roll", "pastry");

        Stream<List<String>> merged = StreamUtils.mergeToList(streamA, streamB, streamC);

        assertThat(merged.collect(toList()), contains(asList("A", "apple", "fritter"), asList("B", "banana", "split"), asList("C", "carrot", "cake"), asList("date", "roll"), asList("pastry")));
    }

    @Test
    // givenThreeStreams_whenInterleave_shouldReturnRoundRobinInterleavingStream
    public void givenThreeStreams_whenInterleave_shouldReturnRoundRobinInterleavingStream() {
        Stream<String> streamA = Stream.of("Peter", "Paul", "Mary");
        Stream<String> streamB = Stream.of("A", "B", "C", "D", "E");
        Stream<String> streamC = Stream.of("foo", "bar", "baz", "xyzzy");

        Stream<String> interleaved = StreamUtils.interleave(Selectors.roundRobin(), streamA, streamB, streamC);

        assertThat(interleaved.collect(Collectors.toList()), contains("Peter", "A", "foo", "Paul", "B", "bar", "Mary", "C", "baz", "D", "xyzzy", "E"));
    }

    @Test
    // givenInfiniteStream_whenTakeWhile10_shouldReturnStreamOfSize10
    public void givenInfiniteStream_whenTakeWhile10_shouldReturnStream() {
        Stream<Integer> infiniteInts = Stream.iterate(0, i -> i + 1);
        Stream<Integer> finiteInts = StreamUtils.takeWhile(infiniteInts, i -> i < 10);

        assertThat(finiteInts.collect(Collectors.toList()), hasSize(10));
    }

    @Test
    public void givenInfiniteStream_whenTakeUntil10_shouldReturnStreamUpto10() {
        Stream<Integer> infiniteInts = Stream.iterate(0, i -> i + 1);
        Stream<Integer> finiteInts = StreamUtils.takeUntil(infiniteInts, i -> i > 10);

        assertThat(finiteInts.collect(Collectors.toList()), hasSize(11));
    }

    @Test
    public void givenIntegerStreamOfTen_whenSkipWhileLessThanFour_shouldReturnStreamFromFourToTen() {
        Stream<Integer> ints = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Stream<Integer> skipped = StreamUtils.skipWhile(ints, i -> i < 4);
        List<Integer> collected = skipped.collect(Collectors.toList());

        assertThat(collected, contains(4, 5, 6, 7, 8, 9, 10));
    }

    @Test
    public void givenIntegerStreamOfTen_whenSkipUntilFour_shouldReturnStreamFromFiveToTen() {
        Stream<Integer> ints = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Stream<Integer> skipped = StreamUtils.skipUntil(ints, i -> i > 4);
        List<Integer> collected = skipped.collect(Collectors.toList());

        assertThat(collected, contains(5, 6, 7, 8, 9, 10));
    }

    @Test
    public void givenSeedValue_withUnfold_shouldReturnStreamAccordingToGeneratorMethod() {
        Stream<Integer> unfolded = StreamUtils.unfold(1, i -> (i < 10) ? Optional.of(i + 1) : Optional.empty());

        assertThat(unfolded.collect(Collectors.toList()), contains(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Test
    public void giveIntegerStream_whenGroupRuns_shouldReturnListGroupItems() {
        Stream<Integer> integerStream = Stream.of(1, 1, 2, 2, 3, 4, 5);
        List<List<Integer>> runs = StreamUtils.groupRuns(integerStream).collect(toList());

        assertThat(runs, contains(asList(1, 1), asList(2, 2), asList(3), asList(4), asList(5)));
    }

    @Test
    public void givenAStream_whenAggregate_shouldReturnAggregatedStreamOnTheBasisOfBiFunction() {
        Stream<String> stream = Stream.of("a1", "b1", "b2", "c1");
        Stream<List<String>> aggregated = StreamUtils.aggregate(stream, (e1, e2) -> e1.charAt(0) == e2.charAt(0));
        assertThat(aggregated.collect(toList()), contains(asList("a1"), asList("b1", "b2"), asList("c1")));
    }

    @Test
    public void givenIntegerStream_whenWindowed_shouldReturnListOfListOfItemsOfWindowSize() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);

        List<List<Integer>> windows = StreamUtils.windowed(integerStream, 2).collect(toList());

        assertThat(windows, contains(asList(1, 2), asList(2, 3), asList(3, 4), asList(4, 5)));
    }

    @Test
    // givenIntegerStream_whenWindowedWithWindowSizeAndSkip_shouldReturnListOfListOfWindowSizeAddingASkip
    public void givenIntegerStream_whenWindowedWithWindowSizeAndSkip_shouldReturnListOfListOfWindowSizeAddingASkip() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);

        List<List<Integer>> windows = StreamUtils.windowed(integerStream, 3, 2).collect(toList());

        assertThat(windows, contains(asList(1, 2, 3), asList(3, 4, 5)));
    }

    @Test
    public void givenEmptyStream_whenWindowed_shouldReturnIterableWithSizeZero() {
        ArrayList<Integer> ints = new ArrayList<>();

        ints.stream().collect(maxBy((a, b) -> a.toString().compareTo(b.toString())));

        List<List<Integer>> windows = StreamUtils.windowed(ints.stream(), 2).collect(toList());

        assertThat(windows, iterableWithSize(0));
    }

    @Test
    public void givenIntegerStream_whenWindowedWithWindowSizeAndSkipAndAllowLesserSize_shouldReturnListOfListOfInteger() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);

        List<List<Integer>> windows = StreamUtils.windowed(integerStream, 2, 2, true).collect(toList());

        assertThat(windows, contains(asList(1, 2), asList(3, 4), asList(5)));
    }

    @Test
    public void givenLimit_withIndices_shouldReturnLongStreamUptoLimit() {
        LongStream indices = StreamUtils.indices().limit(500);

        assertThat(indices.count(), equalTo(500));
    }

}
