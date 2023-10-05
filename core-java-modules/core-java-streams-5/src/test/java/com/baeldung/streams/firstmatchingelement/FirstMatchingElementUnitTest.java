package com.baeldung.streams.firstmatchingelement;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.IntStream;

import org.assertj.core.util.Lists;
import org.junit.Test;

import com.codepoetics.protonpack.StreamUtils;

public class FirstMatchingElementUnitTest {

    @Test
    public void testWithForLoop() {
        List<Object> dataList = Lists.newArrayList("String", 1, Boolean.TRUE);
        int index = -1;
        for (int i = 0; i < dataList.size(); i++) {
            if (dataList.get(i) instanceof Boolean) {
                index = i;
            }
        }
        assertEquals(2, index);
    }

    @Test
    public void testWithIndexOf() {
        List<Object> dataList = Lists.newArrayList("String", 1, Boolean.TRUE);
        Optional<Object> booleanData = dataList.stream()
            .filter(data -> data instanceof Boolean)
            .findFirst();
        int index = booleanData.isPresent() ? dataList.indexOf(booleanData.get()) : -1;
        assertEquals(2, index);
    }

    @Test
    public void testWithIterator() {
        List<Object> dataList = Lists.newArrayList("String", 1, Boolean.TRUE);
        int index = -1;
        Iterator<Object> iterator = dataList.iterator();
        while (iterator.hasNext()) {
            Object data = iterator.next();
            if (data instanceof Boolean) {
                index = dataList.indexOf(data);
                break;
            }
        }
        assertEquals(2, index);
    }

    @Test
    public void testWithListIterator() {
        List<Object> dataList = Lists.newArrayList("String", 1, Boolean.TRUE);
        int index = -1;
        ListIterator<Object> listIterator = dataList.listIterator();
        while (listIterator.hasNext()) {
            if (listIterator.next() instanceof Boolean) {
                index = listIterator.previousIndex();
                break;
            }
        }
        assertEquals(2, index);
    }

    @Test
    public void testWithTakeAWhile() {
        List<Object> dataList = Lists.newArrayList("String", 1, Boolean.TRUE, 2, 3);
        OptionalInt booleanIndex = dataList.stream()
            .takeWhile(data -> !(data instanceof Boolean))
            .mapToInt(dataList::indexOf)
            .max();

        if (booleanIndex.isPresent() && dataList.get(booleanIndex.getAsInt()) instanceof Boolean) {
            assertEquals(2, booleanIndex.getAsInt());
        }
    }

    @Test
    public void testWithIntStream() {
        List<Object> dataList = Lists.newArrayList("String", 1, Boolean.TRUE);
        int index = IntStream.of(0, dataList.size() - 1)
            .filter(streamIndex -> dataList.get(streamIndex) instanceof Boolean)
            .findFirst()
            .orElse(-1);
        assertEquals(2, index);
    }

    @Test
    public void testWithZipWithIndex() {
        List<Object> dataList = Lists.newArrayList("String", 1, Boolean.TRUE, 2, 3);
        int index = StreamUtils.zipWithIndex(dataList.stream())
            .filter(i -> i.getValue() instanceof Boolean)
            .mapToInt(i -> Long.valueOf(i.getIndex())
                .intValue())
            .findFirst()
            .orElse(-1);
        assertEquals(2, index);
    }
}
