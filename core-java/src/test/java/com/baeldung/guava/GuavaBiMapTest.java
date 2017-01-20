package com.baeldung.guava;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import com.google.common.collect.BiMap;
import com.google.common.collect.EnumHashBiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableBiMap;

public class GuavaBiMapTest {
    @Test
    public void whenQueryByValue_shouldReturnKey() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();
        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");

        final String countryHeadName = capitalCountryBiMap.inverse().get("India");

        assertEquals("New Delhi", countryHeadName);
    }

    @Test
    public void whenCreateBiMapFromExistingMap_shouldReturnKey() {
        final Map<String, String> personCountryHeadMap = new HashMap<>();
        personCountryHeadMap.put("New Delhi", "India");
        personCountryHeadMap.put("Washingon, D.C.", "USA");
        personCountryHeadMap.put("Moscow", "Russia");
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create(personCountryHeadMap);

        final String countryHeadName = capitalCountryBiMap.inverse().get("India");

        assertEquals("New Delhi", countryHeadName);
    }

    @Test
    public void whenQueryByKey_shouldReturnValue() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");

        assertEquals("USA", capitalCountryBiMap.get("Washingon, D.C."));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSameValueIsBeingPresent_shouldThrowException() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");
        capitalCountryBiMap.put("Trump", "USA");
    }

    @Test
    public void givenSameValueIsBeingPresent_whenForcePutIsUsed_shouldCompleteSuccessfully() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");
        capitalCountryBiMap.forcePut("Trump", "USA");

        assertEquals("USA", capitalCountryBiMap.get("Trump"));
        assertEquals("Trump", capitalCountryBiMap.inverse().get("USA"));
    }

    @Test
    public void whenSameKeyIsBeingPresent_shouldReplaceAlreadyPresent() {
        final BiMap<String, String> capitalCountryBiMap = HashBiMap.create();

        capitalCountryBiMap.put("New Delhi", "India");
        capitalCountryBiMap.put("Washingon, D.C.", "USA");
        capitalCountryBiMap.put("Moscow", "Russia");
        capitalCountryBiMap.put("Washingon, D.C.", "HongKong");

        assertEquals("HongKong", capitalCountryBiMap.get("Washingon, D.C."));
    }

    @Test
    public void whenUsingImmutableBiMap_shouldAllowPutSuccessfully() {
        final BiMap<String, String> capitalCountryBiMap = new ImmutableBiMap.Builder<String, String>().put("New Delhi", "India").put("Washingon, D.C.", "USA").put("Moscow", "Russia").build();

        assertEquals("USA", capitalCountryBiMap.get("Washingon, D.C."));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenUsingImmutableBiMap_shouldNotAllowRemove() {
        final BiMap<String, String> capitalCountryBiMap = new ImmutableBiMap.Builder<String, String>().put("New Delhi", "India").put("Washingon, D.C.", "USA").put("Moscow", "Russia").build();

        capitalCountryBiMap.remove("New Delhi");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenUsingImmutableBiMap_shouldNotAllowPut() {
        final BiMap<String, String> capitalCountryBiMap = new ImmutableBiMap.Builder<String, String>().put("New Delhi", "India").put("Washingon, D.C.", "USA").put("Moscow", "Russia").build();

        capitalCountryBiMap.put("New York", "USA");
    }

    private enum Operation {
        ADD, SUBTRACT, MULTIPLY, DIVIDE
    }

    @Test
    public void whenUsingEnumAsKeyInMap_shouldReplaceAlreadyPresent() {
        final BiMap<Operation, String> operationStringBiMap = EnumHashBiMap.create(Operation.class);

        operationStringBiMap.put(Operation.ADD, "Add");
        operationStringBiMap.put(Operation.SUBTRACT, "Subtract");
        operationStringBiMap.put(Operation.MULTIPLY, "Multiply");
        operationStringBiMap.put(Operation.DIVIDE, "Divide");

        assertEquals("Divide", operationStringBiMap.get(Operation.DIVIDE));
    }
}
