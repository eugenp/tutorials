package com.baeldung.streams.firstmatchingelement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.apache.commons.collections4.IterableUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;

import com.codepoetics.protonpack.StreamUtils;
import com.google.common.collect.Iterables;

import io.vavr.collection.Stream;
import one.util.streamex.EntryStream;

public class FirstMatchingElementUnitTest {

    private final List<Object> dataList = Lists.newArrayList("String", Boolean.TRUE, Integer.valueOf(10), Boolean.FALSE, Double.valueOf(20.0));

    @Test
    public void whenCalled_thenFindIndexUsingStream() {
        int index = dataList.stream()
            .filter(data -> data instanceof Boolean)
            .mapToInt(data -> dataList.indexOf(data))
            .findFirst()
            .orElse(-1);
        assertEquals(1, index);
    }

    @Test
    public void whenCalled_thenFindIndexUsingStreamIterator() {
        int index = -1;
        Iterator<Object> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object data = iterator.next();
            if (data instanceof Boolean) {
                index = dataList.indexOf(data);
                break;
            }
        }
        assertEquals(1, index);
    }

    @Test
    public void whenCalled_thenFindIndexUsingStreamListIterator() {
        int index = -1;
        ListIterator<Object> listIterator = dataList.listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.next() instanceof Boolean) {
                index = listIterator.previousIndex();
                break;
            }
        }
        assertEquals(1, index);
    }

    @Test
    public void whenCalled_thenFindIndexUsingIntStream() {
        int index = IntStream.range(0, dataList.size() - 1)
            .filter(streamIndex -> dataList.get(streamIndex) instanceof Boolean)
            .findFirst()
            .orElse(-1);
        assertEquals(1, index);
    }

    @Test
    public void whenCalled_thenFindIndexUsingStreamTakeWhile() {
        OptionalInt booleanIndex = dataList.stream()
            .takeWhile(data -> !(data instanceof Boolean))
            .mapToInt(dataList::indexOf)
            .max();

        if (booleanIndex.isPresent() && dataList.get(booleanIndex.getAsInt()) instanceof Boolean) {
            assertEquals(1, booleanIndex.getAsInt());
        }
    }

    @Test
    public void whenCalled_thenFindIndexUsingZipWithIndex() {
        int index = StreamUtils.zipWithIndex(dataList.stream())
            .filter(i -> i.getValue() instanceof Boolean)
            .mapToInt(i -> Long.valueOf(i.getIndex())
                .intValue())
            .findFirst()
            .orElse(-1);
        assertEquals(1, index);
    }

    @Test
    public void whenCalled_thenFindIndexUsingGoogleGuava() {
        int index = Iterables.indexOf(dataList, data -> data instanceof Boolean);
        assertEquals(1, index);
    }

    @Test
    public void whenCalled_thenFindIndexUsingApacheCommons() {
        int index = IterableUtils.indexOf(dataList, data -> data instanceof Boolean);
        assertEquals(1, index);
    }

    @Test
    public void whenCalled_thenFindIndexUsingStreamEx() {
        Integer foundIndex = EntryStream.of(dataList)
            .filterKeyValue((index, data) -> data instanceof Boolean)
            .keys()
            .findFirst()
            .orElse(-1);
        assertEquals(1, foundIndex);
    }

    @Test
    public void whenCalled_thenFindIndexUsingVavrStream() {
        Integer foundIndex = Stream.of(dataList.toArray())
            .zipWithIndex()
            .find(tuple -> tuple._1() instanceof Boolean)
            .map(tuple -> tuple._2())
            .getOrElse(-1);
        assertEquals(1, foundIndex);
    }
}
