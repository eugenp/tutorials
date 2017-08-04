package com.baeldung.vavr.collections;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.Array;
import io.vavr.collection.CharSeq;
import io.vavr.collection.HashSet;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Queue;
import io.vavr.collection.Set;
import io.vavr.collection.SortedMap;
import io.vavr.collection.SortedSet;
import io.vavr.collection.Stream;
import io.vavr.collection.TreeMap;
import io.vavr.collection.TreeSet;
import io.vavr.collection.Vector;
import org.junit.Test;

import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class CollectionAPIUnitTest {

    @Test
    public void givenEmptyList_whenStacked_thenCorrect() {
        List<Integer> intList = List.empty();

        List<Integer> anotherList = intList.push(4)
          .push(0);
        Iterator<Integer> iterator = anotherList.iterator();

        assertEquals(new Integer(0), iterator.next());
        assertEquals(new Integer(4), iterator.next());
    }

    @Test
    public void givenList_whenPrependTail_thenCorrect() {
        List<Integer> intList = List.of(1, 2, 3);

        List<Integer> newList = intList.tail()
          .prepend(0);

        assertEquals(new Integer(1), intList.get(0));
        assertEquals(new Integer(2), intList.get(1));
        assertEquals(new Integer(3), intList.get(2));

        assertNotSame(intList.get(0), newList.get(0));
        assertEquals(new Integer(0), newList.get(0));
        assertSame(intList.get(1), newList.get(1));
        assertSame(intList.get(2), newList.get(2));
    }

    @Test
    public void givenQueue_whenEnqueued_thenCorrect() {
        Queue<Integer> queue = Queue.of(1, 2, 3);
        Queue<Integer> secondQueue = queue.enqueue(4)
          .enqueue(5);

        assertEquals(3, queue.size());
        assertEquals(5, secondQueue.size());

        Tuple2<Integer, Queue<Integer>> result = secondQueue.dequeue();
        Integer headValue = result.apply((head, tail) -> head);
        assertEquals(new Integer(1), headValue);

        Iterator<Integer> iterator = result.apply((head, tail) -> tail.iterator());

        assertEquals(new Integer(2), iterator.next());
        assertEquals(new Integer(3), iterator.next());
        assertEquals(new Integer(4), iterator.next());
        assertEquals(new Integer(5), iterator.next());
    }

    @Test
    public void givenStream_whenProcessed_thenCorrect() {
        Stream<Integer> intStream = Stream.iterate(0, i -> i + 1)
          .take(10);

        assertEquals(10, intStream.size());

        long evenSum = intStream.filter(i -> i % 2 == 0)
          .sum()
          .longValue();

        assertEquals(20, evenSum);
        assertEquals(new Integer(5), intStream.get(5));
    }

    @Test
    public void givenArray_whenQueried_thenCorrect() {
        Array<Integer> intArray = Array.of(1, 2, 3);
        Array<Integer> newArray = intArray.removeAt(1);

        assertEquals(3, intArray.size());
        assertEquals(2, newArray.size());

        assertEquals(new Integer(1), intArray.get(0));
        assertEquals(new Integer(2), intArray.get(1));
        assertEquals(new Integer(3), intArray.get(2));
        assertEquals(new Integer(3), newArray.get(1));
    }

    @Test
    public void givenVector_whenQueried_thenCorrect() {
        Vector<Integer> intVector = Vector.range(1, 5);
        Vector<Integer> newVector = intVector.replace(2, 6);

        assertEquals(4, intVector.size());
        assertEquals(4, newVector.size());

        assertEquals(new Integer(1), intVector.get(0));
        assertEquals(new Integer(2), intVector.get(1));
        assertEquals(new Integer(3), intVector.get(2));
        assertEquals(new Integer(6), newVector.get(1));
    }

    @Test
    public void givenCharSeq_whenProcessed_thenCorrect() {
        CharSeq chars = CharSeq.of("vavr");
        CharSeq newChars = chars.replace('v', 'V');

        assertEquals(4, chars.size());
        assertEquals(4, newChars.size());

        assertEquals('v', chars.charAt(0));
        assertEquals('V', newChars.charAt(0));
        assertEquals("Vavr", newChars.mkString());
    }

    @Test
    public void givenHashSet_whenModified_thenCorrect() {
        HashSet<String> set = HashSet.of("Red", "Green", "Blue");
        HashSet<String> newSet = set.add("Yellow");

        assertEquals(3, set.size());
        assertEquals(4, newSet.size());
        assertFalse(set.contains("Yellow"));
        assertTrue(newSet.contains("Yellow"));
    }

    @Test
    public void givenSortedSet_whenIterated_thenCorrect() {
        SortedSet<String> set = TreeSet.of("Red", "Green", "Blue");

        Iterator<String> iterator = set.iterator();
        assertEquals("Blue", iterator.next());
        assertEquals("Green", iterator.next());
        assertEquals("Red", iterator.next());
    }

    @Test
    public void givenSortedSet_whenReversed_thenCorrect() {
        SortedSet<String> set = TreeSet.of(Comparator.reverseOrder(), "Green", "Red", "Blue");

        Iterator<String> iterator = set.iterator();
        assertEquals("Red", iterator.next());
        assertEquals("Green", iterator.next());
        assertEquals("Blue", iterator.next());
    }

    @Test
    public void givenMap_whenIterated_thenCorrect() {
        Map<Integer, List<Integer>> map = List.rangeClosed(0, 10)
          .groupBy(i -> i % 2);

        assertEquals(2, map.size());

        Iterator<Tuple2<Integer, List<Integer>>> iterator = map.iterator();
        assertEquals(6, iterator.next()
          ._2()
          .size());
        assertEquals(5, iterator.next()
          ._2()
          .size());
    }

    @Test
    public void givenTreeMap_whenIterated_thenCorrect() {
        SortedMap<Integer, String> map = TreeMap.of(3, "Three", 2, "Two", 4, "Four", 1, "One");

        Iterator<Tuple2<Integer, String>> iterator = map.iterator();
        assertEquals(new Integer(1), iterator.next()
          ._1());
        assertEquals(new Integer(2), iterator.next()
          ._1());
        assertEquals(new Integer(3), iterator.next()
          ._1());
    }

    @Test
    public void givenJavaList_whenConverted_thenCorrect() {
        java.util.List<Integer> javaList = java.util.Arrays.asList(1, 2, 3, 4);
        List<Integer> vavrList = List.ofAll(javaList);

        assertEquals(4, vavrList.size());
        assertEquals(new Integer(1), vavrList.head());

        java.util.stream.Stream<Integer> javaStream = javaList.stream();
        Set<Integer> vavrSet = HashSet.ofAll(javaStream);

        assertEquals(4, vavrSet.size());
        assertEquals(new Integer(2), vavrSet.tail()
          .head());
    }

    @Test
    public void givenJavaStream_whenCollected_thenCorrect() {
        List<Integer> vavrList = IntStream.range(1, 10)
          .boxed()
          .filter(i -> i % 2 == 0)
          .collect(List.collector());

        assertEquals(4, vavrList.size());
        assertEquals(new Integer(2), vavrList.head());
    }

    @Test
    public void givenVavrList_whenConverted_thenCorrect() {
        Integer[] array = List.of(1, 2, 3)
          .toJavaArray(Integer.class);
        assertEquals(3, array.length);

        java.util.Map<String, Integer> map = List.of("1", "2", "3")
          .toJavaMap(i -> Tuple.of(i, Integer.valueOf(i)));
        assertEquals(new Integer(2), map.get("2"));
    }

    @Test
    public void givenVavrList_whenCollected_thenCorrect() {
        java.util.Set<Integer> javaSet = List.of(1, 2, 3)
          .collect(Collectors.toSet());

        assertEquals(3, javaSet.size());
        assertEquals(new Integer(1), javaSet.iterator()
          .next());
    }

    @Test
    public void givenVavrList_whenConvertedView_thenCorrect() {
        java.util.List<Integer> javaList = List.of(1, 2, 3)
          .asJavaMutable();
        javaList.add(4);

        assertEquals(new Integer(4), javaList.get(3));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenVavrList_whenConvertedView_thenException() {
        java.util.List<Integer> javaList = List.of(1, 2, 3)
          .asJava();

        assertEquals(new Integer(3), javaList.get(2));
        javaList.add(4);
    }

    @Test
    public void givenList_whenSquared_thenCorrect() {
        List<Integer> vavrList = List.of(1, 2, 3);
        Number sum = vavrList.map(i -> i * i)
          .sum();

        assertEquals(14L, sum);
    }
}
