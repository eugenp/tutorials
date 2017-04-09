import com.google.common.collect.Streams;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class GauavaStreamsTests {

    List<Integer> numbers;

    @Before
    public void setUp() {
        numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);
    }

    @Test
    public void createStreamsWithCollection() {
        //Deprecated API to create stream from collection
        Stream streamFromCollection = Streams.stream(numbers);

        //Assert.assertNotNull(streamFromCollection);
        StreamUtility.assertStreamEquals(streamFromCollection, numbers.stream());
    }

    @Test
    public void createStreamsWithIterable() {
        Iterable<Integer> numbersIterable = (Iterable<Integer>) numbers;

        Stream streamFromIterable = Streams.stream(numbersIterable);

        Assert.assertNotNull(streamFromIterable);
        StreamUtility.assertStreamEquals(streamFromIterable, numbers.stream());
    }

    @Test
    public void createStreamsWithIterator() {
        Iterator<Integer> numbersIterator = numbers.iterator();

        Stream streamFromIterator = Streams.stream(numbersIterator);

        Assert.assertNotNull(streamFromIterator);
        StreamUtility.assertStreamEquals(streamFromIterator, numbers.stream());
    }

    @Test
    public void createStreamsWithOptional() {

        Stream streamFromOptional = Streams.stream(Optional.of(1));

        Assert.assertNotNull(streamFromOptional);
        Assert.assertEquals(streamFromOptional.count(), 1);
    }

    @Test
    public void createStreamsWithOptionalLong() {

        LongStream streamFromOptionalLong = Streams.stream(OptionalLong.of(1));

        Assert.assertNotNull(streamFromOptionalLong);
        Assert.assertEquals(streamFromOptionalLong.count(), 1);
    }

    @Test
    public void createStreamsWithOptionalInt() {

        IntStream streamFromOptionalInt = Streams.stream(OptionalInt.of(1));

        //Assert.assertNotNull(streamFromOptionalInt);
        Assert.assertEquals(streamFromOptionalInt.count(), 1);
    }

    @Test
    public void createStreamsWithOptionalDouble() {

        DoubleStream streamFromOptionalDouble = Streams.stream(OptionalDouble.of(1.0));

        //Assert.assertNotNull(streamFromOptionalDouble);
        Assert.assertEquals(streamFromOptionalDouble.count(), 1);

    }

    @Test
    public void concatStreamsOfSameType() {
        Stream oddNumbers = Arrays
          .asList(1, 3, 5, 7, 9, 11, 13, 15, 17, 19)
          .stream();
        Stream evenNumbers = Arrays
          .asList(2, 4, 6, 8, 10, 12, 14, 16, 18, 20)
          .stream();

        Stream combinedStreams = Streams.concat(oddNumbers, evenNumbers);

        //Assert.assertNotNull(combinedStreams);
        StreamUtility.assertStreamEquals(combinedStreams, Stream.concat(oddNumbers, evenNumbers));
    }

    @Test
    public void concatStreamsOfTypeLongStream() {
        LongStream firstTwenty = LongStream.range(1, 20);
        LongStream nextTwenty = LongStream.range(21, 40);

        LongStream combinedStreams = Streams.concat(firstTwenty, nextTwenty);

        Assert.assertNotNull(combinedStreams);
        StreamUtility.assertStreamEquals(combinedStreams, LongStream.concat(firstTwenty, nextTwenty));
    }

    @Test
    public void concatStreamsOfTypeIntStream() {
        IntStream firstTwenty = IntStream.range(1, 20);
        IntStream nextTwenty = IntStream.range(21, 40);

        IntStream combinedStreams = Streams.concat(firstTwenty, nextTwenty);

        Assert.assertNotNull(combinedStreams);
        StreamUtility.assertStreamEquals(combinedStreams, IntStream.concat(firstTwenty, nextTwenty));
    }

    @Test
    public void findLastOfStream() {
        Optional<Integer> lastElement = Streams.findLast(numbers.stream());

        Assert.assertNotNull(lastElement.get());
        Assert.assertEquals(lastElement.get(), numbers.get(20));
    }

    @Test
    public void mapWithIndexTest() {
        Stream stringSream = Stream.of("a", "b", "c");

        Stream<String> mappedStream = Streams.mapWithIndex(stringSream, (str, index) -> str + ":" + index);

        //Assert.assertNotNull(mappedStream);
        Assert.assertEquals(mappedStream
          .findFirst()
          .get(), "a:0");

    }

    @Test
    public void streamsZipTest() {
        Stream stringSream = Stream.of("a", "b", "c");
        Stream intStream = Stream.of(1, 2, 3);
        Stream<String> mappedStream = Streams.zip(stringSream, intStream, (str, index) -> str + ":" + index);

        //Assert.assertNotNull(mappedStream);
        Assert.assertEquals(mappedStream
          .findFirst()
          .get(), "a:1");

    }

}
