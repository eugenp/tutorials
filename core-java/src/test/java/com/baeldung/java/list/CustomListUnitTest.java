package com.baeldung.java.list;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

public class CustomListUnitTest {
    @Test
    public void givenAddToTheEndAndGetImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();
        boolean succeeded = list.add("baeldung");
        Object element = list.get(0);

        assertTrue(succeeded);
        assertEquals("baeldung", element);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenAddToSpecifiedIndexImpl_whenCycle1_thenPasses() {
        new CustomList<>().add(0, null);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenAddAllToTheEndImpl_whenCycle1_thenPasses() {
        Collection<Object> collection = new ArrayList<>();
        List<Object> list = new CustomList<>();
        list.addAll(collection);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenAddAllToSpecifiedIndexImpl_whenCycle1_thenPasses() {
        Collection<Object> collection = new ArrayList<>();
        List<Object> list = new CustomList<>();
        list.addAll(0, collection);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenRemoveAtSpecifiedIndexImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        list.remove(0);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenRemoveSpecifiedElementImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        list.remove("baeldung");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenRemoveAllImpl_whenCycle1_thenPasses() {
        Collection<Object> collection = new ArrayList<>();
        collection.add("baeldung");
        List<Object> list = new CustomList<>();
        list.removeAll(collection);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenRetainAllImpl_whenCycle1_thenPasses() {
        Collection<Object> collection = new ArrayList<>();
        collection.add("baeldung");
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        list.retainAll(collection);
    }

    @Test
    public void givenSizeImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();

        assertEquals(0, list.size());
    }

    @Test
    public void givenIsEmptyImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();

        assertTrue(list.isEmpty());
    }

    @Test
    public void givenContainsImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");

        assertTrue(list.contains("baeldung"));
    }

    @Test
    public void givenContainsAllImpl_whenCycle1_thenPasses() {
        Collection<Object> collection = new ArrayList<>();
        collection.add("baeldung");
        List<Object> list = new CustomList<>();
        list.add("baeldung");

        assertTrue(list.containsAll(collection));
    }

    @Test
    public void givenSetImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        Object element = list.set(0, null);

        assertEquals("baeldung", element);
        assertNull(list.get(0));
    }

    @Test
    public void givenClearImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        list.clear();

        assertTrue(list.isEmpty());
    }

    @Test
    public void givenIndexOfImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");

        assertEquals(0, list.indexOf("baeldung"));
    }

    @Test
    public void givenLastIndexOfImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");

        assertEquals(0, list.lastIndexOf("baeldung"));
    }

    @Test
    public void givenSubListImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        List<Object> subList = list.subList(0, 1);

        assertEquals("baeldung", subList.get(0));
    }

    @Test
    public void givenToNewArrayImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        Object[] array = list.toArray();

        assertEquals("baeldung", array[0]);
    }

    @Test
    public void givenToExistingArrayImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        String[] input = new String[1];
        String[] output = list.toArray(input);

        assertEquals("baeldung", output[0]);
    }

    @Test
    public void givenIteratorImpl_whenCycle1_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        Iterator<Object> iterator = list.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("baeldung", iterator.next());
    }

    @Test
    public void givenAddToTheEndAndGetImpl_whenCycle2_thenPasses() {
        List<Object> list = new CustomList<>();
        boolean succeeded1 = list.add("baeldung");
        boolean succeeded2 = list.add(".com");
        Object element1 = list.get(0);
        Object element2 = list.get(1);

        assertTrue(succeeded1);
        assertTrue(succeeded2);
        assertEquals("baeldung", element1);
        assertEquals(".com", element2);
    }

    @Test
    public void givenContainsImpl_whenCycle2_thenPasses() {
        List<Object> list = new CustomList<>();
        assertFalse(list.contains("baeldung"));

        list.add("baeldung");
        assertTrue(list.contains("baeldung"));
    }

    @Test
    public void givenContainsAllImpl_whenCycle2_thenPasses() {
        Collection<Object> collection1 = new ArrayList<>();
        collection1.add("baeldung");
        collection1.add(".com");
        Collection<Object> collection2 = new ArrayList<>();
        collection2.add("baeldung");

        List<Object> list = new CustomList<>();
        list.add("baeldung");

        assertFalse(list.containsAll(collection1));
        assertTrue(list.containsAll(collection2));
    }

    @Test
    public void givenIndexOfImpl_whenCycle2_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        list.add(".com");
        list.add(".com");

        assertEquals(1, list.indexOf(".com"));
        assertEquals(-1, list.indexOf("com"));
    }

    @Test
    public void givenLastIndexOfImpl_whenCycle2_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        list.add("baeldung");
        list.add(".com");

        assertEquals(1, list.lastIndexOf("baeldung"));
        assertEquals(-1, list.indexOf("com"));
    }

    @Test
    public void givenSubListImpl_whenCycle2_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        list.add(".");
        list.add("com");
        List<Object> subList = list.subList(1, 2);

        assertEquals(1, subList.size());
        assertEquals(".", subList.get(0));
    }

    @Test
    public void givenToExistingArrayImpl_whenCycle2_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        String[] input = {};
        String[] output = list.toArray(input);

        assertArrayEquals(new String[] { "baeldung" }, output);
    }

    @Test
    public void givenIteratorImpl_whenCycle2_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        Iterator<Object> iterator = list.iterator();

        assertTrue(iterator.hasNext());
        assertEquals("baeldung", iterator.next());
        assertFalse(iterator.hasNext());
    }

    @Test
    public void givenToExistingArrayImpl_whenCycle3_thenPasses() {
        List<Object> list = new CustomList<>();
        list.add("baeldung");
        String[] input = new String[2];
        String[] output = list.toArray(input);

        assertArrayEquals(new String[] { "baeldung", null }, output);
    }
}
