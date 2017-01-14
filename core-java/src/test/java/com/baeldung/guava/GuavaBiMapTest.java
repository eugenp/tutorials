package com.baeldung.guava;

import static org.junit.Assert.*;
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

        assertEquals("Modi", personCountryHeadBiMap.inverse().get("India"));
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
    public void whenSameKeyIsBeingPresent_shouldReplaceAlreadyPresent() {
        final BiMap<String, String> personCountryHeadBiMap = HashBiMap.create();

        personCountryHeadBiMap.put("Modi", "India");
        personCountryHeadBiMap.put("Obama", "USA");
        personCountryHeadBiMap.put("Putin", "USSR");
        personCountryHeadBiMap.put("Obama", "HongKong");
        
        assertEquals("HongKong", personCountryHeadBiMap.get("Obama"));
    }

}
