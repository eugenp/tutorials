package com.baeldung.guava.tutorial;

import org.junit.Assert;

import java.util.Iterator;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamUtility {

    public static <T> boolean assertStreamEquals(Stream<T> stream1, Stream<T> stream2) {

        Iterator<T> iterator1 = stream1.iterator();
        Iterator<T> iterator2 = stream2.iterator();

        while (iterator1.hasNext()) {
            Assert.assertEquals(iterator1.next(), iterator2.next());
        }

        Assert.assertFalse(iterator2.hasNext());

        return true;
    }

    public static boolean assertStreamEquals(LongStream stream1, LongStream stream2) {

        Iterator iterator1 = stream1.iterator();
        Iterator iterator2 = stream2.iterator();

        while (iterator1.hasNext()) {
            Assert.assertEquals(iterator1.next(), iterator2.next());
        }

        Assert.assertFalse(iterator2.hasNext());

        return true;
    }

    public static boolean assertStreamEquals(DoubleStream stream1, DoubleStream stream2) {

        Iterator iterator1 = stream1.iterator();
        Iterator iterator2 = stream2.iterator();

        while (iterator1.hasNext()) {
            Assert.assertEquals(iterator1.next(), iterator2.next());
        }

        Assert.assertFalse(iterator2.hasNext());

        return true;
    }

    public static boolean assertStreamEquals(IntStream stream1, IntStream stream2) {

        Iterator iterator1 = stream1.iterator();
        Iterator iterator2 = stream2.iterator();

        while (iterator1.hasNext()) {
            Assert.assertEquals(iterator1.next(), iterator2.next());
        }

        Assert.assertFalse(iterator2.hasNext());

        return true;
    }
}
