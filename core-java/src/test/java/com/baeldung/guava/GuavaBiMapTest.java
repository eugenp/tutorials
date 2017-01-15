package com.baeldung.guava;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

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
    public void givenSameValueIsBeingPresent_whenForPutIsUsed_shouldThrowException() {
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

}
