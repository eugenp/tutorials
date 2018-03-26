package com.baeldung.protonpack;

import com.codepoetics.protonpack.Indexed;
import com.codepoetics.protonpack.StreamUtils;
import com.codepoetics.protonpack.selectors.Selectors;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StreamUtilsExample {

    public void createInfiniteIndex() {
        LongStream indices = StreamUtils.indices();
    }

    public void zipAStreamWithIndex() {
        Stream<String> source = Stream.of("Foo", "Bar", "Baz");
        List<Indexed<String>> zipped = StreamUtils.zipWithIndex(source).collect(Collectors.toList());
    }

    public void zipAPairOfStreams() {
        Stream<String> streamA = Stream.of("A", "B", "C");
        Stream<String> streamB = Stream.of("Apple", "Banana", "Carrot");

        List<String> zipped = StreamUtils.zip(streamA, streamB, (a, b) -> a + " is for " + b).collect(Collectors.toList());
    }

    public void zipThreeStreams() {
        Stream<String> streamA = Stream.of("A", "B", "C");
        Stream<String> streamB = Stream.of("aggravating", "banausic", "complaisant");
        Stream<String> streamC = Stream.of("Apple", "Banana", "Carrot");

        List<String> zipped = StreamUtils.zip(streamA, streamB, streamC, (a, b, c) -> a + " is for " + b + " " + c).collect(Collectors.toList());
    }

    public void mergeThreeStreams() {
        Stream<String> streamA = Stream.of("A", "B", "C");
        Stream<String> streamB = Stream.of("apple", "banana", "carrot", "date");
        Stream<String> streamC = Stream.of("fritter", "split", "cake", "roll", "pastry");

        Stream<List<String>> merged = StreamUtils.mergeToList(streamA, streamB, streamC);
    }

    public void interleavingStreamsUsingRoundRobin() {
        Stream<String> streamA = Stream.of("Peter", "Paul", "Mary");
        Stream<String> streamB = Stream.of("A", "B", "C", "D", "E");
        Stream<String> streamC = Stream.of("foo", "bar", "baz", "xyzzy");

        Stream<String> interleaved = StreamUtils.interleave(Selectors.roundRobin(), streamA, streamB, streamC);
    }

    public void takeWhileAndTakeUntilStream() {
        Stream<Integer> infiniteInts = Stream.iterate(0, i -> i + 1);
        Stream<Integer> finiteIntsWhileLessThan10 = StreamUtils.takeWhile(infiniteInts, i -> i < 10);
        Stream<Integer> finiteIntsUntilGreaterThan10 = StreamUtils.takeUntil(infiniteInts, i -> i > 10);
    }

    public void skipWhileAndSkipUntilStream() {
        Stream<Integer> ints = Stream.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Stream<Integer> skippedWhileConditionMet = StreamUtils.skipWhile(ints, i -> i < 4);
        Stream<Integer> skippedUntilConditionMet = StreamUtils.skipWhile(ints, i -> i > 4);
    }

    public void unfoldStream() {
        Stream<Integer> unfolded = StreamUtils.unfold(1, i -> (i < 10) ? Optional.of(i + 1) : Optional.empty());
    }

    public void windowedStream() {
        Stream<Integer> integerStream = Stream.of(1, 2, 3, 4, 5);

        List<List<Integer>> windows = StreamUtils.windowed(integerStream, 2).collect(toList());
        List<List<Integer>> windowsWithSkipIndex = StreamUtils.windowed(integerStream, 3, 2).collect(toList());
        List<List<Integer>> windowsWithSkipIndexAndAllowLowerSize = StreamUtils.windowed(integerStream, 2, 2, true).collect(toList());

    }

    public void groupRunsStreams() {
        Stream<Integer> integerStream = Stream.of(1, 1, 2, 2, 3, 4, 5);

        List<List<Integer>> runs = StreamUtils.groupRuns(integerStream).collect(toList());
    }

    public void aggreagateOnBiElementPredicate() {
        Stream<String> stream = Stream.of("a1", "b1", "b2", "c1");
        Stream<List<String>> aggregated = StreamUtils.aggregate(stream, (e1, e2) -> e1.charAt(0) == e2.charAt(0));
    }

}
