/**
 * 
 */
package com.baeldung.map;

import com.google.common.collect.HashBiMap;
import org.apache.commons.collections4.BidiMap;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

/**
 * @author swpraman
 *
 */
public class MapUtilUnitTest {
    
    
    @Test
    public void whenUsingImperativeWayForSingleKey_shouldReturnSingleKey() {
        Map<String, String> capitalCountryMap = new HashMap<>();
        capitalCountryMap.put("Tokyo", "Japan");
        capitalCountryMap.put("New Delhi", "India");
        assertEquals("New Delhi", MapUtil.getKey(capitalCountryMap, "India"));
    }
    
    @Test
    public void whenUsingImperativeWayForAllKeys_shouldReturnAllKeys() {
        Map<String, String> capitalCountryMap = new HashMap<>();
        capitalCountryMap.put("Tokyo", "Japan");
        capitalCountryMap.put("Berlin", "Germany");
        capitalCountryMap.put("Cape Town", "South Africa");
        capitalCountryMap.put("Pretoria", "South Africa");
        capitalCountryMap.put("Bloemfontein", "South Africa");

        assertEquals(new HashSet<String>(Arrays.asList(
                new String[] {"Cape Town", "Pretoria", "Bloemfontein"})), 
                MapUtil.getKeys(capitalCountryMap, "South Africa"));
    }
    
    @Test
    public void whenUsingFunctionalWayForSingleKey_shouldReturnSingleKey() {
        Map<String, String> capitalCountryMap = new HashMap<>();
        capitalCountryMap.put("Tokyo", "Japan");
        capitalCountryMap.put("Berlin", "Germany");
        assertEquals("Berlin", MapUtil.keys(capitalCountryMap, "Germany").findFirst().get());
    }
    
    @Test
    public void whenUsingFunctionalWayForAllKeys_shouldReturnAllKeys() {
        Map<String, String> capitalCountryMap = new HashMap<>();
        capitalCountryMap.put("Tokyo", "Japan");
        capitalCountryMap.put("Berlin", "Germany");
        capitalCountryMap.put("Cape Town", "South Africa");
        capitalCountryMap.put("Pretoria", "South Africa");
        capitalCountryMap.put("Bloemfontein", "South Africa");
        assertEquals(new HashSet<String>(Arrays.asList(
                new String[] {"Cape Town", "Pretoria", "Bloemfontein"})), 
                MapUtil.keys(capitalCountryMap, "South Africa").collect(Collectors.toSet()));
    }
    
    @Test
    public void whenUsingBidiMap_shouldReturnKey() {
        BidiMap<String, String> capitalCountryMap = new DualHashBidiMap<String, String>();
        capitalCountryMap.put("Berlin", "Germany");
        capitalCountryMap.put("Cape Town", "South Africa");
        assertEquals("Berlin", capitalCountryMap.getKey("Germany"));
    }
    
    @Test
    public void whenUsingBidiMapAddDuplicateValue_shouldRemoveOldEntry() {
        BidiMap<String, String> capitalCountryMap = new DualHashBidiMap<String, String>();
        capitalCountryMap.put("Berlin", "Germany");
        capitalCountryMap.put("Cape Town", "South Africa");
        capitalCountryMap.put("Pretoria", "South Africa");
        assertEquals("Pretoria", capitalCountryMap.getKey("South Africa"));
    }
    
    @Test
    public void whenUsingBiMap_shouldReturnKey() {
        HashBiMap<String, String> capitalCountryMap = HashBiMap.create();
        capitalCountryMap.put("Berlin", "Germany");
        capitalCountryMap.put("Cape Town", "South Africa");
        assertEquals("Berlin", capitalCountryMap.inverse().get("Germany"));
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void whenUsingBiMapAddDuplicateValue_shouldThrowException() {
        HashBiMap<String, String> capitalCountryMap = HashBiMap.create();
        capitalCountryMap.put("Berlin", "Germany");
        capitalCountryMap.put("Cape Town", "South Africa");
        capitalCountryMap.put("Pretoria", "South Africa");
        assertEquals("Berlin", capitalCountryMap.inverse().get("Germany"));
    }

}
