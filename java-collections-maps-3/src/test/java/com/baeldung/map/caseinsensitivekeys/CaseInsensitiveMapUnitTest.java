package com.baeldung.map.caseinsensitivekeys;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.junit.Test;
import org.springframework.util.LinkedCaseInsensitiveMap;
import java.util.Map;
import java.util.TreeMap;
import static org.junit.Assert.*;

public class CaseInsensitiveMapUnitTest {
    @Test
    public void givenCaseInsensitiveTreeMap_whenTwoEntriesAdded_thenSizeIsOne(){
        Map<String, Integer> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        treeMap.put("abc", 1);
        treeMap.put("ABC", 2);

        assertEquals(1, treeMap.size());

    }

    @Test
    public void givenCommonsCaseInsensitiveMap_whenTwoEntriesAdded_thenSizeIsOne(){
        Map<String, Integer> commonsHashMap = new CaseInsensitiveMap<>();
        commonsHashMap.put("abc", 1);
        commonsHashMap.put("ABC", 2);

        assertEquals(1, commonsHashMap.size());
    }

    @Test
    public void givenLinkedCaseInsensitiveMap_whenTwoEntriesAdded_thenSizeIsOne(){
        Map<String, Integer> linkedHashMap = new LinkedCaseInsensitiveMap<>();
        linkedHashMap.put("abc", 1);
        linkedHashMap.put("ABC", 2);

        assertEquals(1, linkedHashMap.size());
    }

    @Test
    public void givenCaseInsensitiveTreeMap_whenSameEntryAdded_thenValueUpdated(){
        Map<String, Integer> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        treeMap.put("abc", 1);
        treeMap.put("ABC", 2);

        assertEquals(2, treeMap.get("aBc").intValue());
        assertEquals(2, treeMap.get("ABc").intValue());
    }

    @Test
    public void givenCommonsCaseInsensitiveMap_whenSameEntryAdded_thenValueUpdated(){
        Map<String, Integer> commonsHashMap = new CaseInsensitiveMap<>();
        commonsHashMap.put("abc", 1);
        commonsHashMap.put("ABC", 2);

        assertEquals(2, commonsHashMap.get("aBc").intValue());
        assertEquals(2, commonsHashMap.get("ABc").intValue());
    }

    @Test
    public void givenLinkedCaseInsensitiveMap_whenSameEntryAdded_thenValueUpdated(){
        Map<String, Integer> linkedHashMap = new LinkedCaseInsensitiveMap<>();
        linkedHashMap.put("abc", 1);
        linkedHashMap.put("ABC", 2);

        assertEquals(2, linkedHashMap.get("aBc").intValue());
        assertEquals(2, linkedHashMap.get("ABc").intValue());
    }

    @Test
    public void givenCaseInsensitiveTreeMap_whenEntryRemoved_thenSizeIsZero(){
        Map<String, Integer> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        treeMap.put("abc", 3);
        treeMap.remove("aBC");

        assertEquals(0, treeMap.size());
    }

    @Test
    public void givenCommonsCaseInsensitiveMap_whenEntryRemoved_thenSizeIsZero(){
        Map<String, Integer> commonsHashMap = new CaseInsensitiveMap<>();
        commonsHashMap.put("abc", 3);
        commonsHashMap.remove("aBC");

        assertEquals(0, commonsHashMap.size());
    }

    @Test
    public void givenLinkedCaseInsensitiveMap_whenEntryRemoved_thenSizeIsZero(){
        Map<String, Integer> linkedHashMap = new LinkedCaseInsensitiveMap<>();
        linkedHashMap.put("abc", 3);
        linkedHashMap.remove("aBC");

        assertEquals(0, linkedHashMap.size());
    }
}
