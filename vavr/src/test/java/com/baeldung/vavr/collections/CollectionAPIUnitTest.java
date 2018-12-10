package com.baeldung.vavr.collections;

import io.vavr.Tuple;
import io.vavr.Tuple2;
import io.vavr.collection.*;
import io.vavr.collection.BitSet;
import io.vavr.collection.HashMap;
import io.vavr.collection.HashSet;
import io.vavr.collection.Iterator;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import io.vavr.collection.Queue;
import io.vavr.collection.Set;
import io.vavr.collection.SortedMap;
import io.vavr.collection.SortedSet;
import io.vavr.collection.TreeMap;
import io.vavr.collection.TreeSet;
import io.vavr.collection.Vector;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

public class CollectionAPIUnitTest {


    @Test
    public void givenParams_whenListAPI_thenCorrect() {

        List<String> list
          = List.of("Java", "PHP", "Jquery", "JavaScript", "JShell", "JAVA");

        List list1 = list.drop(2);
        assertFalse(list1.contains("Java") && list1.contains("PHP"));

        List list2 = list.dropRight(2);
        assertFalse(list2.contains("JAVA") && list2.contains("JShell"));

        List list3 = list.dropUntil(s -> s.contains("Shell"));
        assertEquals(list3.size(), 2);
        
        List list4 = list.dropWhile(s -> s.length() > 0);
        assertTrue(list4.isEmpty());

        List list5 = list.take(1);
        assertEquals(list5.single(), "Java");

        List list6 = list.takeRight(1);
        assertEquals(list6.single(), "JAVA");

        List list7 = list.takeUntil(s -> s.length() > 6);
        assertEquals(list7.size(), 3);

        List list8
          = list.distinctBy( (s1, s2) -> s1.startsWith(s2.charAt(0)+"") ? 0 : 1);
        assertEquals(list8.size(), 2);

        Iterator<List<String>> iterator = list.grouped(2);
        assertEquals(iterator.head().size(), 2);

        Map<Boolean, List<String>> map = list.groupBy(e -> e.startsWith("J"));
        assertEquals(map.size(), 2);
        assertEquals(map.get(false).get().size(), 1);
        assertEquals(map.get(true).get().size(), 5);

        String words = List.of("Boys", "Girls")
                         .intersperse("and")
                         .reduce((s1, s2) -> s1.concat( " " + s2 ))
                         .trim();
        
        assertEquals(words, "Boys and Girls");

    }

    @Test
    public void givenEmptyList_whenStackAPI_thenCorrect() {

        List<Integer> intList = List.empty();

        List<Integer> intList1 = intList.pushAll(List.rangeClosed(5,10));

        assertEquals(intList1.peek(), Integer.valueOf(10));

        List intList2 = intList1.pop();
        assertEquals(intList2.size(), (intList1.size() - 1) );

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
        Queue<Integer> secondQueue = queue.enqueueAll(List.of(4,5));

        assertEquals(3, queue.size());
        assertEquals(5, secondQueue.size());

        Tuple2<Integer, Queue<Integer>> result = secondQueue.dequeue();
        assertEquals(Integer.valueOf(1), result._1);

        Queue<Integer> tailQueue = result._2;
        assertFalse(tailQueue.contains(secondQueue.get(0)));

        Queue<Queue<Integer>> queue1 = queue.combinations(2);
        assertEquals(queue1.get(2).toCharSeq(), CharSeq.of("23"));
    }

    @Test
    public void givenStream_whenProcessed_thenCorrect() {

        Stream<Integer> s1 = Stream.tabulate(5, (i)-> i + 1);
        assertEquals(s1.get(2).intValue(), 3);

        Stream<Integer> s = Stream.of(2,1,3,4);

        Stream<Tuple2<Integer, Integer>> s2 = s.zip(List.of(7,8,9));
        Tuple2<Integer, Integer> t1 = s2.get(0);
        assertEquals(t1._1().intValue(), 2);
        assertEquals(t1._2().intValue(), 7);

        Stream<Integer> intStream = Stream.iterate(0, i -> i + 1)
          .take(10);

        assertEquals(10, intStream.size());

        long evenSum = intStream.filter(i -> i % 2 == 0)
          .sum()
          .longValue();

        assertEquals(20, evenSum);
    }

    @Test
    public void givenArray_whenQueried_thenCorrect() {

        Array<Integer> rArray = Array.range(1, 5);
        assertFalse(rArray.contains(5));

        Array<Integer> rArray2 = Array.rangeClosed(1, 5);
        assertTrue(rArray2.contains(5));

        Array<Integer> rArray3 = Array.rangeClosedBy(1,6,2);
        assertEquals(rArray3.size(), 3);

        Array<Integer> intArray = Array.of(1, 2, 3);
        Array<Integer> newArray = intArray.removeAt(1);
        assertEquals(2, newArray.size());
        assertEquals(3, newArray.get(1).intValue());

        Array<Integer> array2 = intArray.replace(1, 5);
        assertEquals(array2.get(0).intValue(), 5);
    }

    @Test
    public void givenVector_whenQueried_thenCorrect() {
        Vector<Integer> intVector = Vector.range(1, 5);
        Vector<Integer> newVector = intVector.replace(2, 6);

        assertEquals(4, intVector.size());
        assertEquals(4, newVector.size());

        assertEquals(2, intVector.get(1).intValue());
        assertEquals(6, newVector.get(1).intValue());
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
        assertTrue(newSet.contains("Yellow"));

        HashSet<Integer> set0 = HashSet.rangeClosed(1,5);
        HashSet<Integer> set1 = HashSet.rangeClosed(3, 6);

        assertEquals(set0.union(set1), HashSet.rangeClosed(1,6));
        assertEquals(set0.diff(set1), HashSet.rangeClosed(1,2));
        assertEquals(set0.intersect(set1), HashSet.rangeClosed(3,5));


    }

    @Test
    public void givenSortedSet_whenIterated_thenCorrect() {
        SortedSet<String> set = TreeSet.of("Red", "Green", "Blue");
        assertEquals("Blue", set.head());

        SortedSet<Integer> intSet = TreeSet.of(1,2,3);
        assertEquals(2, intSet.average().get().intValue());
        
    }

    @Test
    public void givenSortedSet_whenReversed_thenCorrect() {
        SortedSet<String> reversedSet
          = TreeSet.of(Comparator.reverseOrder(), "Green", "Red", "Blue");
        assertEquals("Red", reversedSet.head());

        String str = reversedSet.mkString(" and ");
        assertEquals("Red and Green and Blue", str);
    }

    @Test
    public void giveBitSet_whenApiMethods_thenCorrect() {

        BitSet<Integer> bitSet = BitSet.of(1,2,3,4,5,6,7,8);

        BitSet<Integer> bitSet1 = bitSet.takeUntil(i -> i > 4);
        assertEquals(bitSet1.size(), 4);

    }

    @Test
    public void givenMap_whenMapApi_thenCorrect() {
        Map<Integer, List<Integer>> map = List.rangeClosed(0, 10)
          .groupBy(i -> i % 2);
        
        assertEquals(2, map.size());
        assertEquals(6, map.get(0).get().size());
        assertEquals(5, map.get(1).get().size());

        Map<String, String> map1
          = HashMap.of("key1", "val1", "key2", "val2", "key3", "val3");

        Map<String, String> fMap
          = map1.filterKeys(k -> k.contains("1") || k.contains("2"));
        assertFalse(fMap.containsKey("key3"));

        Map<String, String> fMap2
          = map1.filterValues(v -> v.contains("3"));
        assertEquals(fMap2.size(), 1);
        assertTrue(fMap2.containsValue("val3"));

        Map<String, Integer> map2 = map1.map(
          (k, v) -> Tuple.of(k, Integer.valueOf(v.charAt(v.length() - 1) + "") ));
        assertEquals(map2.get("key1").get().intValue(), 1);
    }

    @Test
    public void givenTreeMap_whenIterated_thenCorrect() {
        SortedMap<Integer, String> map
          = TreeMap.of(3, "Three", 2, "Two", 4, "Four", 1, "One");

        assertEquals(1, map.keySet().toJavaArray()[0]);
        assertEquals("Four", map.get(4).get());

        TreeMap<Integer, String> treeMap2 =
          TreeMap.of(Comparator.reverseOrder(), 3,"three", 6, "six", 1, "one");
        assertEquals(treeMap2.keySet().mkString(), "631");

    }

    @Test
    public void givenJavaList_whenConverted_thenCorrect() {
        java.util.List<Integer> javaList = java.util.Arrays.asList(1, 2, 3, 4);
        List<Integer> vavrList = List.ofAll(javaList);

        assertEquals(4, vavrList.size());
        assertEquals(1, vavrList.head().intValue());

        java.util.stream.Stream<Integer> javaStream = javaList.stream();
        Set<Integer> vavrSet = HashSet.ofAll(javaStream);

        assertEquals(4, vavrSet.size());
        assertEquals(2, vavrSet.tail().head().intValue());
    }

    @Test
    public void givenJavaStream_whenCollected_thenCorrect() {
        List<Integer> vavrList = IntStream.range(1, 10)
          .boxed()
          .filter(i -> i % 2 == 0)
          .collect(List.collector());

        assertEquals(4, vavrList.size());
        assertEquals(2, vavrList.head().intValue());
    }

    @Test
    public void givenVavrList_whenConverted_thenCorrect() {
        Integer[] array = List.of(1, 2, 3)
          .toJavaArray(Integer.class);
        assertEquals(3, array.length);

        java.util.Map<String, Integer> map = List.of("1", "2", "3")
          .toJavaMap(i -> Tuple.of(i, Integer.valueOf(i)));
        assertEquals(2, map.get("2").intValue());
    }

    @Test
    public void givenVavrList_whenCollected_thenCorrect() {
        java.util.Set<Integer> javaSet = List.of(1, 2, 3)
          .collect(Collectors.toSet());

        assertEquals(3, javaSet.size());
        assertEquals(1, javaSet.toArray()[0]);
    }

    @Test
    public void givenVavrList_whenConvertedView_thenCorrect() {
        java.util.List<Integer> javaList = List.of(1, 2, 3)
          .asJavaMutable();
        javaList.add(4);

        assertEquals(4, javaList.get(3).intValue());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenVavrList_whenConvertedView_thenException() {
        java.util.List<Integer> javaList = List.of(1, 2, 3)
          .asJava();

        assertEquals(3, javaList.get(2).intValue());
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
