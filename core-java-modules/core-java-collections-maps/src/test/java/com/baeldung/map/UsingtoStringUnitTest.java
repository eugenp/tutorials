package com.baeldung.map;

import org.junit.Test;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;

public class UsingtoStringUnitTest {

    @Test
    public void given_HashMapString_whenUsingtoString_thenConvertToHashMapObject() {
        // Example string representation of a HashMap
        String hashMapString = "{key1=value1, key2=value2, key3=value3}";

        // Remove unnecessary characters
        String keyValuePairs = hashMapString.replaceAll("[{}\\s]", "");

        // Split into individual key-value pairs
        String[] pairs = keyValuePairs.split(",");

        // Create a new HashMap
        HashMap<String, String> expectedHashMap = new HashMap<>();
        expectedHashMap.put("key1", "value1");
        expectedHashMap.put("key2", "value2");
        expectedHashMap.put("key3", "value3");

        HashMap<String, String> actualHashMap = new HashMap<>();

        // Parse and populate the HashMap
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");
            if (keyValue.length == 2) {
                actualHashMap.put(keyValue[0], keyValue[1]);
            }
        }

        // Assert that the converted HashMap matches the expected one
        assertEquals(expectedHashMap, actualHashMap);
    }

}
