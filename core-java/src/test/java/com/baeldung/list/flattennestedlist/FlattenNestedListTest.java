package com.baeldung.list.flattennestedlist;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FlattenNestedListTest {
    private List<List<String>> lol = new ArrayList<>();
    List<String> ls1 = Arrays.asList("one:one", "one:two", "one:three");
    List<String> ls2 = Arrays.asList("two:one", "two:two", "two:three");
    List<String> ls3 = Arrays.asList("three:one", "three:two", "three:three");

    @Before
    public void setup() {
        lol.addAll(Arrays.asList(ls1, ls2, ls3));
    }

    @After
    public void tearDown() {
        lol = null;
    }

    @Test
    public void givenString_flattenNestedList1() {
        List<String> ls = flattenListOfListsImperatively(lol);

        assertNotNull(ls);
        assertTrue(ls.size() == 9);
        // assert content
        assertEquals(ls.get(0), "one:one");
        assertEquals(ls.get(1), "one:two");
        assertEquals(ls.get(2), "one:three");
        assertEquals(ls.get(3), "two:one");
        assertEquals(ls.get(4), "two:two");
        assertEquals(ls.get(5), "two:three");
        assertEquals(ls.get(6), "three:one");
        assertEquals(ls.get(7), "three:two");
        assertEquals(ls.get(8), "three:three");
    }

    @Test
    public void givenString_flattenNestedList2() {
        List<String> ls = flattenListOfListsStream(lol);

        assertNotNull(ls);
        assertTrue(ls.size() == 9);
        // assert content
        assertEquals(ls.get(0), "one:one");
        assertEquals(ls.get(1), "one:two");
        assertEquals(ls.get(2), "one:three");
        assertEquals(ls.get(3), "two:one");
        assertEquals(ls.get(4), "two:two");
        assertEquals(ls.get(5), "two:three");
        assertEquals(ls.get(6), "three:one");
        assertEquals(ls.get(7), "three:two");
        assertEquals(ls.get(8), "three:three");
    }

    public <T> List<T> flattenListOfListsImperatively(List<List<T>> list) {
        List<T> ls = new ArrayList<>();
        list.forEach(ls::addAll);
        return ls;
    }

    public <T> List<T> flattenListOfListsStream(List<List<T>> list) {
        return list.stream().flatMap(Collection::stream).collect(Collectors.toList());
    }
}
