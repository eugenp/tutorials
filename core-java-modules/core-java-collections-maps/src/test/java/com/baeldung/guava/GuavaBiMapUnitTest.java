package com.baeldung.guava;

import static org.junit.Assert.*;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;

public class GuavaBiMapUnitTest {
    @Test
    public void whenQueryByValue_returnsKey() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();
        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");

        final String countryCapitalName = capitalCountryBiMap.inverse().get("India");

        assertEquals("New Delhi", countryCapitalName);
    }

    @Test
    public void whenCreateBiMapFromExistingMap_returnsKey() {
        final Map<String, String> capitalCountryMap = new HashMap<>();
        capitalCountryMap.put("New Delhi", "India");
        capitalCountryMap.put("Washingon, D.C.", "USA");
        capitalCountryMap.put("Moscow", "Russia");
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create(capitalCountryMap);

        final String countryCapitalName = capitalCountryBiMap.inverse().get("India");

        assertEquals("New Delhi", countryCapitalName);
    }

    @Test
    public void whenQueryByKey_returnsValue() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");

        assertEquals("USA", capitalCountryBiMap.get("Washingon, D.C."));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSameValueIsPresent_throwsException() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");
        capitalCountryBiMap.put("Trump", "USA");
    }

    @Test
    public void givenSameValueIsPresent_whenForcePut_completesSuccessfully() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");
        capitalCountryBiMap.forcePut("Trump", "USA");

        assertEquals("USA", capitalCountryBiMap.get("Trump"));
        assertEquals("Trump", capitalCountryBiMap.inverse().get("USA"));
    }

    @Test
    public void whenSameKeyIsPresent_replacesAlreadyPresent() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");
        capitalCountryBiMap.put("Washingon, D.C.", "HongKong");

        assertEquals("HongKong", capitalCountryBiMap.get("Washingon, D.C."));
    }

    @Test
    public void whenUsingImmutableBiMap_allowsPutSuccessfully() {
        final BiMap<String, String> capitalCountryBiMap = new ImmutableBiMap.Builder<String, String>().put("New Delhi", "India").put("Washingon, D.C.", "USA").put("Moscow", "Russia").build();

        assertEquals("USA", capitalCountryBiMap.get("Washingon, D.C."));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenUsingImmutableBiMap_doesntAllowRemove() {
        final BiMap<String, String> capitalCountryBiMap = new ImmutableBiMap.Builder<String, String>().put("New Delhi", "India").put("Washingon, D.C.", "USA").put("Moscow", "Russia").build();

        capitalCountryBiMap.remove("New Delhi");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenUsingImmutableBiMap_doesntAllowPut() {
        final BiMap<String, String> capitalCountryBiMap = new ImmutableBiMap.Builder<String, String>().put("New Delhi", "India").put("Washingon, D.C.", "USA").put("Moscow", "Russia").build();

        capitalCountryBiMap.put("New York", "USA");
    }

    private enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    @Test
    public void whenUsingEnumAsKeyInMap_replacesAlreadyPresent() {
        final BiMap<Operation, String> operationStringBiMap = EnumHashBiMap.create(Operation.class);

        operationStringBiMap.put(Operation.ADD, "Add");
        operationStringBiMap.put(Operation.SUBTRACT, "Subtract");
        operationStringBiMap.put(Operation.MULTIPLY, "Multiply");
        operationStringBiMap.put(Operation.DIVIDE, "Divide");

        assertEquals("Divide", operationStringBiMap.get(Operation.DIVIDE));
    }
}