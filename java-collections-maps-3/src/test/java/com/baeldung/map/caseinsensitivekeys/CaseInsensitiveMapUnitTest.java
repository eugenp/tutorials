package com.baeldung.map.caseinsensitivekeys;

import org.apache.commons.collections4.map.CaseInsensitiveMap;
import org.junit.Assert;
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

        assertEquals(1, treeMap.size());

    }

    @Test
    public void givenCommonsCaseInsensitiveMap_whenTwoEntriesAdded_thenSizeIsOne(){
        CaseInsensitiveMap<String, Integer> commonsHashMap = new CaseInsensitiveMap<>();
        commonsHashMap.put("abc", 1);
        commonsHashMap.put("ABC", 2);

        assertEquals(1, commonsHashMap.size());

    }

    @Test
    public void givenLinkedCaseInsensitiveMap_whenTwoEntriesAdded_thenSizeIsOne(){
        LinkedCaseInsensitiveMap<Integer> linkedHashMap = new LinkedCaseInsensitiveMap<>();
        linkedHashMap.put("abc", 1);
        linkedHashMap.put("ABC", 2);

        assertEquals(1, linkedHashMap.size());

    }

    @Test
    public void givenCaseInsensitiveTreeMap_whenSameEntryAdded_thenValueUpdated(){
        TreeMap<String, Integer> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        treeMap.put("abc", 1);
        treeMap.put("ABC", 2);

        Assert.assertEquals((Integer)2, treeMap.get("aBc"));
    }

    @Test
    public void givenCommonsCaseInsensitiveMap_whenSameEntryAdded_thenValueUpdated(){
        CaseInsensitiveMap<String, Integer> commonsHashMap = new CaseInsensitiveMap<>();
        commonsHashMap.put("abc", 1);
        commonsHashMap.put("ABC", 2);

        Assert.assertEquals((Integer)2, commonsHashMap.get("aBc"));
    }

    @Test
    public void givenLinkedCaseInsensitiveMap_whenSameEntryAdded_thenValueUpdated(){
        LinkedCaseInsensitiveMap<Integer> linkedHashMap = new LinkedCaseInsensitiveMap<>();
        linkedHashMap.put("abc", 1);
        linkedHashMap.put("ABC", 2);

        Assert.assertEquals((Integer)2, linkedHashMap.get("aBc"));
    }

    @Test
    public void givenCaseInsensitiveTreeMap_whenEntryRemoved_thenSizeIsZero(){
        TreeMap<String, Integer> treeMap = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        treeMap.put("abc", 3);
        treeMap.remove("aBC");

        Assert.assertEquals(0, treeMap.size());
    }

    @Test
    public void givenCommonsCaseInsensitiveMap_whenEntryRemoved_thenSizeIsZero(){
        CaseInsensitiveMap<String, Integer> commonsHashMap = new CaseInsensitiveMap<>();
        commonsHashMap.put("abc", 3);
        commonsHashMap.remove("aBC");

        Assert.assertEquals(0, commonsHashMap.size());
    }

    @Test
    public void givenLinkedCaseInsensitiveMap_whenEntryRemoved_thenSizeIsZero(){
        LinkedCaseInsensitiveMap<Integer> linkedHashMap = new LinkedCaseInsensitiveMap<>();
        linkedHashMap.put("abc", 3);
        linkedHashMap.remove("aBC");

        Assert.assertEquals(0, linkedHashMap.size());
    }
}
