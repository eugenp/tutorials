package com.baeldung.stringtomap;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class StringToMapUnitTest {

    public Map<String, String> convertStringToMap(String data) {
        Map<String, String> map = new HashMap<>();
        StringTokenizer tokenizer = new StringTokenizer(data, " ");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            String[] keyValue = token.split("=");
            map.put(keyValue[0], keyValue[1]);
        }

        return map;
    }

    @Test
    public void given_StringWithKeyValuePairs_whenUsing_convertStringToMap_thenMapCreated() {
        String data = "name=John age=30 city=NewYork";
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("name", "John");
        expectedMap.put("age", "30");
        expectedMap.put("city", "NewYork");
        Map<String, String> resultMap = convertStringToMap(data);
        assertEquals(expectedMap, resultMap);
    }

    public Map<String, String> convertStringArrayToMap(String[] data) {
        Map<String, String> map = new HashMap<>();

        for (String keyValue : data) {
            String[] parts = keyValue.split("=");
            map.put(parts[0], parts[1]);
        }

        return map;
    }

    @Test
    public void given_StringArrayWithKeyValuePairs_whenUsing_convertStringArrayToMap_thenMapCreated() {
        String[] data = {"name=John", "age=30", "city=NewYork"};
        Map<String, String> expectedMap = new HashMap<>();
        expectedMap.put("name", "John");
        expectedMap.put("age", "30");
        expectedMap.put("city", "NewYork");
        Map<String, String> resultMap = convertStringArrayToMap(data);
        assertEquals(expectedMap, resultMap);
    }

    public Map<String, List<String>> convertStringArrayToMapWithDuplicates(String[] data) {
        Map<String, List<String>> map = new HashMap<>();

        for (String keyValue : data) {
            String[] parts = keyValue.split("=");
            String key = parts[0];
            String value = parts[1];

            if (map.containsKey(key)) {
                List<String> valuesList = map.get(key);
                valuesList.add(value);
            } else {
                List<String> valuesList = new ArrayList<>();
                valuesList.add(value);
                map.put(key, valuesList);
            }
        }

        return map;

    }

    @Test
    public void given_StringArrayWithKeyValuePairsWithDuplicates_whenUsing_convertStringArrayToMapWithDuplicates_thenMapCreatedWithLists() {
        String[] data = {"name=John", "age=30", "city=NewYork", "age=31"};
        Map<String, List<String>> expectedMap = new HashMap<>();
        expectedMap.put("name", Collections.singletonList("John"));
        expectedMap.put("age", Arrays.asList("30", "31"));
        expectedMap.put("city", Collections.singletonList("NewYork"));
        Map<String, List<String>> resultMap = convertStringArrayToMapWithDuplicates(data);
        assertEquals(expectedMap, resultMap);
    }
}
