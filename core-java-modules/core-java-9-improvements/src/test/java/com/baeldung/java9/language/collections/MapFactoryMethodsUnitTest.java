package com.baeldung.java9.language.collections;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class MapFactoryMethodsUnitTest {

    @Test
    public void whenMapCreated_thenSuccess() {
        Map<String, String> traditionlMap = new HashMap<String, String>();
        traditionlMap.put("foo", "a");
        traditionlMap.put("bar", "b");
        traditionlMap.put("baz", "c");
        Map<String, String> factoryCreatedMap = Map.of("foo", "a", "bar", "b", "baz", "c");
        assertEquals(traditionlMap, factoryCreatedMap);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void onElemAdd_ifUnSupportedOpExpnThrown_thenSuccess() {
        Map<String, String> map = Map.of("foo", "a", "bar", "b");
        map.put("baz", "c");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void onElemModify_ifUnSupportedOpExpnThrown_thenSuccess() {
        Map<String, String> map = Map.of("foo", "a", "bar", "b");
        map.put("foo", "c");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void onElemRemove_ifUnSupportedOpExpnThrown_thenSuccess() {
        Map<String, String> map = Map.of("foo", "a", "bar", "b");
        map.remove("foo");
    }

    @Test(expected = IllegalArgumentException.class)
    public void givenDuplicateKeys_ifIllegalArgExp_thenSuccess() {
        Map.of("foo", "a", "foo", "b");
    }

    @Test(expected = NullPointerException.class)
    public void onNullKey_ifNullPtrExp_thenSuccess() {
        Map.of("foo", "a", null, "b");
    }

    @Test(expected = NullPointerException.class)
    public void onNullValue_ifNullPtrExp_thenSuccess() {
        Map.of("foo", "a", "bar", null);
    }

    @Test
    public void ifNotHashMap_thenSuccess() {
        Map<String, String> map = Map.of("foo", "a", "bar", "b");
        assertFalse(map instanceof HashMap);
    }

}
