package com.baeldung.java.listwithdefault;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.function.Supplier;

import org.junit.jupiter.api.Test;

import com.google.common.collect.Lists;

public class ListWithDefaultValuesUnitTest {
    private static final List<String> EXPECTED_LIST = Lists.newArrayList("new", "new", "new", "new", "new");
    private static final Date DATE_EPOCH = Date.from(Instant.EPOCH);
    private static final Date DATE_NOW = new Date();

    static <T> List<T> newListWithDefault(T value, int size) {
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(value);
        }
        return list;
    }

    static <T> List<T> newListWithDefault2(Supplier<T> supplier, int size) {
        List<T> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    @Test
    void whenUsingArraysFill_thenGetExpectedList() {
        String[] strings = new String[5];
        Arrays.fill(strings, "new");
        List<String> result = Arrays.asList(strings);
        assertEquals(EXPECTED_LIST, result);

        //result is a fixed size list
        assertThrows(UnsupportedOperationException.class, () -> result.add("a new string"));
        assertThrows(UnsupportedOperationException.class, () -> result.remove(0));

        //result's element can be "set"
        result.set(2, "a new value");
        assertEquals("a new value", result.get(2));

        Date[] dates = new Date[2];
        Arrays.fill(dates, Date.from(Instant.EPOCH));
        List<Date> dateList = Arrays.asList(dates);
        assertEquals(Lists.newArrayList(DATE_EPOCH, DATE_EPOCH), dateList);
        dateList.get(0)
          .setTime(DATE_NOW.getTime());
        assertEquals(Lists.newArrayList(DATE_NOW, DATE_NOW), dateList);

    }

    @Test
    void whenUsingNewListWithDefault_thenGetExpectedList() {
        List<String> result = newListWithDefault("new", 5);
        assertEquals(EXPECTED_LIST, result);

        List<Integer> intList = newListWithDefault(42, 3);
        assertEquals(Lists.newArrayList(42, 42, 42), intList);

        List<Date> dateList = newListWithDefault(Date.from(Instant.EPOCH), 2);
        assertEquals(Lists.newArrayList(DATE_EPOCH, DATE_EPOCH), dateList);
        dateList.get(0)
          .setTime(DATE_NOW.getTime());
        assertEquals(Lists.newArrayList(DATE_NOW, DATE_NOW), dateList);
    }

    @Test
    void whenUsingNewListWithDefault2_thenGetExpectedList() {
        List<String> result = newListWithDefault2(() -> "new", 5);
        assertEquals(EXPECTED_LIST, result);

        List<Integer> intList = newListWithDefault2(() -> 42, 3);
        assertEquals(Lists.newArrayList(42, 42, 42), intList);

        List<Date> dateList = newListWithDefault2(() -> Date.from(Instant.EPOCH), 2);
        assertEquals(Lists.newArrayList(DATE_EPOCH, DATE_EPOCH), dateList);
        dateList.get(0)
          .setTime(DATE_NOW.getTime());
        assertEquals(Lists.newArrayList(DATE_NOW, DATE_EPOCH), dateList);
    }
}