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
        final BiMap<String, String> personCountryHeadBiMap = HashBiMap.create();
        personCountryHeadBiMap.put("Modi", "India");
        personCountryHeadBiMap.put("Obama", "USA");
        personCountryHeadBiMap.put("Putin", "USSR");

        final String countryHeadName = personCountryHeadBiMap.inverse().get("India");

        assertEquals("Modi", countryHeadName);
    }

    @Test
    public void whenCreateBiMapFromExistingMap_shouldReturnKey() {
        final Map<String, String> personCountryHeadMap = new HashMap<>();
        personCountryHeadMap.put("Modi", "India");
        personCountryHeadMap.put("Obama", "USA");
        personCountryHeadMap.put("Putin", "USSR");
        final BiMap<String, String> personCountryHeadBiMap = HashBiMap.create(personCountryHeadMap);

        final String countryHeadName = personCountryHeadBiMap.inverse().get("India");

        assertEquals("Modi", countryHeadName);
    }

    @Test
    public void whenQueryByKey_shouldReturnValue() {
        final BiMap<String, String> personCountryHeadBiMap = HashBiMap.create();

        personCountryHeadBiMap.put("Modi", "India");
        personCountryHeadBiMap.put("Obama", "USA");
        personCountryHeadBiMap.put("Putin", "USSR");

        assertEquals("USA", personCountryHeadBiMap.get("Obama"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenSameValueIsBeingPresent_shouldThrowException() {
        final BiMap<String, String> personCountryHeadBiMap = HashBiMap.create();

        personCountryHeadBiMap.put("Modi", "India");
        personCountryHeadBiMap.put("Obama", "USA");
        personCountryHeadBiMap.put("Putin", "USSR");
        personCountryHeadBiMap.put("Trump", "USA");
    }

    @Test
    public void givenSameValueIsBeingPresent_whenForcePutIsUsed_shouldCompleteSuccessfully() {
        final BiMap<String, String> personCountryHeadBiMap = HashBiMap.create();

        personCountryHeadBiMap.put("Modi", "India");
        personCountryHeadBiMap.put("Obama", "USA");
        personCountryHeadBiMap.put("Putin", "USSR");
        personCountryHeadBiMap.forcePut("Trump", "USA");

        assertEquals("USA", personCountryHeadBiMap.get("Trump"));
        assertEquals("Trump", personCountryHeadBiMap.inverse().get("USA"));
    }

    @Test
    public void whenSameKeyIsBeingPresent_shouldReplaceAlreadyPresent() {
        final BiMap<String, String> personCountryHeadBiMap = HashBiMap.create();

        personCountryHeadBiMap.put("Modi", "India");
        personCountryHeadBiMap.put("Obama", "USA");
        personCountryHeadBiMap.put("Putin", "USSR");
        personCountryHeadBiMap.put("Obama", "HongKong");

        assertEquals("HongKong", personCountryHeadBiMap.get("Obama"));
    }

    @Test
    public void whenUsingImmutableBiMap_shouldAllowPutSuccessfully() {
        final BiMap<String, String> personCountryHeadBiMap = new ImmutableBiMap.Builder<String, String>().put("Modi", "India").put("Obama", "USA").put("Putin", "USSR").build();

        assertEquals("USA", personCountryHeadBiMap.get("Obama"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenUsingImmutableBiMap_shouldNotAllowRemove() {
        final BiMap<String, String> personCountryHeadBiMap = new ImmutableBiMap.Builder<String, String>().put("Modi", "India").put("Obama", "USA").put("Putin", "USSR").build();

        personCountryHeadBiMap.remove("Modi");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void whenUsingImmutableBiMap_shouldNotAllowPut() {
        final BiMap<String, String> personCountryHeadBiMap = new ImmutableBiMap.Builder<String, String>().put("Modi", "India").put("Obama", "USA").put("Putin", "USSR").build();

        personCountryHeadBiMap.put("Trump", "USA");
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
