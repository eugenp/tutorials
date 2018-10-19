package org.baeldung.guava;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class GuavaMultiMapUnitTest {

    @Test
    public void givenMap_whenAddTwoValuesForSameKey_shouldOverridePreviousKey() {
        //given
        String key = "a-key";
        Map<String, String> map = new LinkedHashMap<>();

        //when
        map.put(key, "firstValue");
        map.put(key, "secondValue");

        //then
        assertEquals(1, map.size());
    }

    @Test
    public void givenMultiMap_whenAddTwoValuesForSameKey_shouldHaveTwoEntriesInMap() {
        //given
        String key = "a-key";
        Multimap<String, String> map = ArrayListMultimap.create();

        //when
        map.put(key, "firstValue");
        map.put(key, "secondValue");

        //then
        assertEquals(2, map.size());
    }

    @Test
    public void givenMapOfListValues_whenAddTwoValuesForSameKey_shouldHaveTwoElementsInList() {
        //given
        String key = "a-key";
        Map<String, List<String>> map = new LinkedHashMap<>();

        //when
        List<String> values = map.get(key);
        if(values == null){
            values = new LinkedList<>();
            values.add("firstValue");
            values.add("secondValue");
        }
        map.put(key, values);

        //then
        assertEquals(1, map.size());
    }
}