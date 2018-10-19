package com.baeldung.java9.language.collections;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class SetFactoryMethodsUnitTest {

    @Test
    public void whenSetCreated_thenSuccess() {
        Set<String> traditionlSet = new HashSet<String>();
        traditionlSet.add("foo");
        traditionlSet.add("bar");
        traditionlSet.add("baz");
        Set<String> factoryCreatedSet = Set.of("foo", "bar", "baz");
        assertEquals(traditionlSet, factoryCreatedSet);
    }

    @Test(expected = IllegalArgumentException.class)
    public void onDuplicateElem_IfIllegalArgExp_thenSuccess() {
        Set.of("foo", "bar", "baz", "foo");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void onElemAdd_ifUnSupportedOpExpnThrown_thenSuccess() {
        Set<String> set = Set.of("foo", "bar");
        set.add("baz");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void onElemRemove_ifUnSupportedOpExpnThrown_thenSuccess() {
        Set<String> set = Set.of("foo", "bar", "baz");
        set.remove("foo");
    }

    @Test(expected = NullPointerException.class)
    public void onNullElem_ifNullPtrExpnThrown_thenSuccess() {
        Set.of("foo", "bar", null);
    }

    @Test
    public void ifNotHashSet_thenSuccess() {
        Set<String> list = Set.of("foo", "bar");
        assertFalse(list instanceof HashSet);
    }

    @Test
    public void ifSetSizeIsOne_thenSuccess() {
        int[] arr = { 1, 2, 3, 4 };
        Set<int[]> set = Set.of(arr);
        assertEquals(1, set.size());
        assertArrayEquals(arr, set.iterator().next());
    }

}
