package com.baeldung.collections.multiplecollections;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.IterableUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

public class CombineMultipleCollectionsUnitTest {

    @Test
    public void givenUsingJava8_whenConcatenatingUsingConcat_thenCorrect() {
        Collection<String> collectionA = asList("S", "T");
        Collection<String> collectionB = asList("U", "V");
        Collection<String> collectionC = asList("W", "X");

        Stream<String> combinedStream = Stream.concat(Stream.concat(collectionA.stream(), collectionB.stream()), collectionC.stream());
        Collection<String> collectionCombined = combinedStream.collect(Collectors.toList());

        Assert.assertEquals(asList("S", "T", "U", "V", "W", "X"), collectionCombined);
    }

    @Test
    public void givenUsingJava8_whenConcatenatingUsingflatMap_thenCorrect() {
        Collection<String> collectionA = asList("S", "T");
        Collection<String> collectionB = asList("U", "V");

        Stream<String> combinedStream = Stream.of(collectionA, collectionB).flatMap(Collection::stream);
        Collection<String> collectionCombined = combinedStream.collect(Collectors.toList());

        Assert.assertEquals(asList("S", "T", "U", "V"), collectionCombined);
    }

    @Test
    public void givenUsingGuava_whenConcatenatingUsingIterables_thenCorrect() {
        Collection<String> collectionA = asList("S", "T");
        Collection<String> collectionB = asList("U", "V");

        Iterable<String> combinedIterables = Iterables.unmodifiableIterable(Iterables.concat(collectionA, collectionB));
        Collection<String> collectionCombined = Lists.newArrayList(combinedIterables);

        Assert.assertEquals(asList("S", "T", "U", "V"), collectionCombined);
    }

    @Test
    public void givenUsingJava7_whenConcatenatingUsingIterables_thenCorrect() {
        Collection<String> collectionA = asList("S", "T");
        Collection<String> collectionB = asList("U", "V");

        Iterable<String> combinedIterables = concat(collectionA, collectionB);
        Collection<String> collectionCombined = makeListFromIterable(combinedIterables);
        Assert.assertEquals(Arrays.asList("S", "T", "U", "V"), collectionCombined);
    }

    public static <E> Iterable<E> concat(Iterable<? extends E> i1, Iterable<? extends E> i2) {
        return new Iterable<E>() {
            public Iterator<E> iterator() {
                return new Iterator<E>() {
                    Iterator<? extends E> listIterator = i1.iterator();
                    Boolean checkedHasNext;
                    E nextValue;
                    private boolean startTheSecond;

                    void theNext() {
                        if (listIterator.hasNext()) {
                            checkedHasNext = true;
                            nextValue = listIterator.next();
                        } else if (startTheSecond)
                            checkedHasNext = false;
                        else {
                            startTheSecond = true;
                            listIterator = i2.iterator();
                            theNext();
                        }
                    }

                    public boolean hasNext() {
                        if (checkedHasNext == null)
                            theNext();
                        return checkedHasNext;
                    }

                    public E next() {
                        if (!hasNext())
                            throw new NoSuchElementException();
                        checkedHasNext = null;
                        return nextValue;
                    }

                    public void remove() {
                        listIterator.remove();
                    }
                };
            }
        };
    }

    public static <E> List<E> makeListFromIterable(Iterable<E> iter) {
        List<E> list = new ArrayList<>();
        for (E item : iter) {
            list.add(item);
        }
        return list;
    }

    @Test
    public void givenUsingApacheCommons_whenConcatenatingUsingUnion_thenCorrect() {
        Collection<String> collectionA = asList("S", "T");
        Collection<String> collectionB = asList("U", "V");

        Iterable<String> combinedIterables = CollectionUtils.union(collectionA, collectionB);
        Collection<String> collectionCombined = Lists.newArrayList(combinedIterables);

        Assert.assertEquals(asList("S", "T", "U", "V"), collectionCombined);
    }

    @Test
    public void givenUsingApacheCommons_whenConcatenatingUsingChainedIterable_thenCorrect() {
        Collection<String> collectionA = asList("S", "T");
        Collection<String> collectionB = asList("U", "V");

        Iterable<String> combinedIterables = IterableUtils.chainedIterable(collectionA, collectionB);
        Collection<String> collectionCombined = Lists.newArrayList(combinedIterables);

        Assert.assertEquals(asList("S", "T", "U", "V"), collectionCombined);
    }
}
