package com.baeldung.java9.language.stream;

import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Integer.*;
import static org.junit.Assert.assertEquals;

public class StreamFeaturesUnitTest {

    public static class TakeAndDropWhileTest {

        public Stream<String> getStreamAfterTakeWhileOperation() {
            return Stream.iterate("", s -> s + "s").takeWhile(s -> s.length() < 10);
        }

        public Stream<String> getStreamAfterDropWhileOperation() {
            return Stream.iterate("", s -> s + "s").takeWhile(s -> s.length() < 10).dropWhile(s -> !s.contains("sssss"));
        }

        @Test
        public void testTakeWhileOperation() {
            List<String> list = getStreamAfterTakeWhileOperation().collect(Collectors.toList());

            assertEquals(10, list.size());

            assertEquals("", list.get(0));
            assertEquals("ss", list.get(2));
            assertEquals("sssssssss", list.get(list.size() - 1));
        }

        @Test
        public void testDropWhileOperation() {
            List<String> list = getStreamAfterDropWhileOperation().collect(Collectors.toList());

            assertEquals(5, list.size());

            assertEquals("sssss", list.get(0));
            assertEquals("sssssss", list.get(2));
            assertEquals("sssssssss", list.get(list.size() - 1));
        }

    }

    public static class IterateTest {

        private Stream<Integer> getStream() {
            return Stream.iterate(0, i -> i < 10, i -> i + 1);
        }

        @Test
        public void testIterateOperation() {
            List<Integer> list = getStream().collect(Collectors.toList());

            assertEquals(10, list.size());

            assertEquals(valueOf(0), list.get(0));
            assertEquals(valueOf(5), list.get(5));
            assertEquals(valueOf(9), list.get(list.size() - 1));
        }

    }

    public static class OfNullableTest {

        private List<String> collection = Arrays.asList("A", "B", "C");
        private Map<String, Integer> map = new HashMap<>() {
            {
                put("A", 10);
                put("C", 30);
            }
        };

        private Stream<Integer> getStreamWithOfNullable() {
            return collection.stream().flatMap(s -> Stream.ofNullable(map.get(s)));
        }

        private Stream<Integer> getStream() {
            return collection.stream().flatMap(s -> {
                Integer temp = map.get(s);
                return temp != null ? Stream.of(temp) : Stream.empty();
            });
        }

        private List<Integer> testOfNullableFrom(Stream<Integer> stream) {
            List<Integer> list = stream.collect(Collectors.toList());

            assertEquals(2, list.size());

            assertEquals(valueOf(10), list.get(0));
            assertEquals(valueOf(30), list.get(list.size() - 1));

            return list;
        }

        @Test
        public void testOfNullable() {

            assertEquals(testOfNullableFrom(getStream()), testOfNullableFrom(getStreamWithOfNullable()));

        }

    }

}
