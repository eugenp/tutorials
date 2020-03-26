package com.baeldung.map;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.junit.Test;
import org.springframework.util.LinkedCaseInsensitiveMap;
import static org.junit.Assert.*;

import java.util.TreeMap;

public class CaseInsensitiveMapUnitTest {
    @Test
    public void givenCaseInsensitiveTreeMap_whenTwoEntriesAdded_thenSizeIsOne(){
        TreeMap<String, Integer> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        treeMap.put("abc", 1);
        treeMap.put("ABC", 2);

        assertEquals(treeMap.size(), 1);
    }

    @Test
    public void givenCommonsCaseInsensitiveMap_whenSameEntryAdded_thenValueUpdated(){
        CaseInsensitiveMap<String, Integer> commonsHashMap = new CaseInsensitiveMap<>();
        commonsHashMap.put("abc", 1);
        commonsHashMap.put("ABC", 2);

        assertEquals(commonsHashMap.get("aBc"), (Integer)2);
    }

    @Test
    public void givenLinkedCaseInsensitiveMap_whenEntryRemoved_thenSizeIsZero(){
        LinkedCaseInsensitiveMap<Integer> linkedHashMap = new LinkedCaseInsensitiveMap<>();
        linkedHashMap.put("abc", 3);
        linkedHashMap.remove("aBC");

        assertEquals(linkedHashMap.size(), 0);
    }
}
