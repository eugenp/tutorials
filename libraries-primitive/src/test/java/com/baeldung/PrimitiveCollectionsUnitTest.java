package com.baeldung;

import org.eclipse.collections.api.list.primitive.ImmutableIntList;
import org.eclipse.collections.api.list.primitive.MutableLongList;
import org.eclipse.collections.api.map.primitive.MutableIntIntMap;
import org.eclipse.collections.api.set.primitive.MutableIntSet;
import org.eclipse.collections.impl.factory.primitive.*;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.map.mutable.primitive.IntIntHashMap;
import org.junit.Test;

import java.util.stream.DoubleStream;

import static org.junit.Assert.assertEquals;


public class PrimitiveCollectionsUnitTest {

    @Test
    public void whenListOfLongHasOneTwoThree_thenSumIsSix() {
        MutableLongList longList = LongLists.mutable.of(1L, 2L, 3L);
        assertEquals(6, longList.sum());
    }

    @Test
    public void whenListOfIntHasOneTwoThree_thenMaxIsThree() {
        ImmutableIntList intList = IntLists.immutable.of(1, 2, 3);
        assertEquals(3, intList.max());
    }

    @Test
    public void whenConvertFromIterableToPrimitive_thenValuesAreEquals() {
        Iterable<Integer> iterable = Interval.oneTo(3);
        MutableIntSet intSet = IntSets.mutable.withAll(iterable);
        IntInterval intInterval = IntInterval.oneTo(3);
        assertEquals(intInterval.toSet(), intSet);
    }

    @Test
    public void testOperationsOnIntIntMap() {
        MutableIntIntMap map = new IntIntHashMap();
        assertEquals(5, map.addToValue(0, 5));
        assertEquals(5, map.get(0));
        assertEquals(3, map.getIfAbsentPut(1, 3));
    }

    @Test
    public void whenCreateDoubleStream_thenAverageIsThree() {
        DoubleStream doubleStream = DoubleLists
                .mutable.with(1.0, 2.0, 3.0, 4.0, 5.0)
                .primitiveStream();
        assertEquals(3, doubleStream.average().getAsDouble(), 0.001);
    }

    @Test
    public void whenCreateMapFromStream_thenValuesMustMatch() {
        Iterable<Integer> integers = Interval.oneTo(3);
        MutableIntIntMap map =
                IntIntMaps.mutable.from(
                        integers,
                        key -> key,
                        value -> value * value);
        MutableIntIntMap expected = IntIntMaps.mutable.empty()
                .withKeyValue(1, 1)
                .withKeyValue(2, 4)
                .withKeyValue(3, 9);
        assertEquals(expected, map);
    }
}