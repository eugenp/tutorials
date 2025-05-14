package com.baeldung.multivaluedmap;

import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

// Unit test for MultivaluedMap.
public class MultivaluedMapUnitTest {
    
    // Testing MultivaluedHashMap.
    @Test
    public void givenMultivaluedHashMap_whenEquals_thenTrue() {
        jakarta.ws.rs.core.MultivaluedMap<String, Integer> mulmap = new jakarta.ws.rs.core.MultivaluedHashMap<>();

        // Mapping keys to values.
        mulmap.addAll("first", 1, 2, 3);
        mulmap.add(null, null);

        assertNotNull(mulmap, "The MultivaluedHashMap is null!");
        assertEquals(1, mulmap.getFirst("first"), "The key isn't mapped to the right values!");
        assertEquals(null, mulmap.getFirst(null), "MultivaluedHashMap didn't accept null!");
    }

    // Testing HashMap.
    @Test
    public void givenHashMap_whenEquals_thenTrue() {
        Map<String, Integer> map = new HashMap<>();

        // Putting key-value pairs into our map.
        map.put("first", 1);
        map.put(null, 2);
        map.put("third", null);

        assertNotNull(map, "The HashMap is null!");
        assertEquals(1, map.get("first"), "The key isn't mapped to the right value!");
        assertEquals(2, map.get(null), "HashMap didn't accept null as key!");
        assertEquals(null, map.get("third"), "HashMap didn't accept null value!");
    }

    @org.junit.Test
    public void givenMultiValuesMap_whenPuttingMultipleValuesUsingPutMethod_thenReturningAllValues() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();

        map.put("fruits", "apple");
        map.put("fruits", "orange");

        assertThat((Collection<String>) map.get("fruits")).containsExactly("apple", "orange");

    }

    @org.junit.Test
    public void givenMultiValuesMap_whenPuttingMultipleValuesUsingPutAllMethod_thenReturningAllValues() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();

        map.putAll("vehicles", Arrays.asList("car", "bike"));

        assertThat((Collection<String>) map.get("vehicles")).containsExactly("car", "bike");

    }

    @org.junit.Test
    public void givenMultiValuesMap_whenGettingValueUsingGetMethod_thenReturningValue() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();

        map.put("fruits", "apple");

        assertThat((Collection<String>) map.get("fruits")).containsExactly("apple");
    }

    @org.junit.Test
    public void givenMultiValuesMap_whenUsingEntriesMethod_thenReturningMappings() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");

        Collection<Map.Entry<String, String>> entries = (Collection<Map.Entry<String, String>>) map.entries();

        for(Map.Entry<String,String> entry : entries) {
            assertThat(entry.getKey()).contains("fruits");
            assertTrue(entry.getValue().equals("apple") || entry.getValue().equals("orange") );
        }
    }

    @org.junit.Test
    public void givenMultiValuesMap_whenUsingKeysMethod_thenReturningAllKeys() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");

        MultiSet<String> keys = map.keys();

        assertThat((keys)).contains("fruits", "vehicles");

    }

    @org.junit.Test
    public void givenMultiValuesMap_whenUsingKeySetMethod_thenReturningAllKeys() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");

        Set<String> keys = map.keySet();

        assertThat(keys).contains("fruits", "vehicles");

    }

    @org.junit.Test
    public void givenMultiValuesMap_whenUsingValuesMethod_thenReturningAllValues() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();

        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");

        assertThat(((Collection<String>) map.values())).contains("apple", "orange", "car", "bike");
    }

    @org.junit.Test
    public void givenMultiValuesMap_whenUsingRemoveMethod_thenReturningUpdatedMap() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();

        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");
        assertThat(((Collection<String>) map.values())).contains("apple", "orange", "car", "bike");

        map.remove("fruits");

        assertThat(((Collection<String>) map.values())).contains("car", "bike");

    }

    @org.junit.Test
    public void givenMultiValuesMap_whenUsingRemoveMappingMethod_thenReturningUpdatedMapAfterMappingRemoved() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();

        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");
        assertThat(((Collection<String>) map.values())).contains("apple", "orange", "car", "bike");

        map.removeMapping("fruits", "apple");

        assertThat(((Collection<String>) map.values())).contains("orange", "car", "bike");
    }

    @org.junit.Test
    public void givenMultiValuesMap_whenUsingClearMethod_thenReturningEmptyMap() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");
        assertThat(((Collection<String>) map.values())).contains("apple", "orange", "car", "bike");

        map.clear();

        assertTrue(map.isEmpty());
    }

    @org.junit.Test
    public void givenMultiValuesMap_whenUsingContainsKeyMethod_thenReturningTrue() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");

        assertTrue(map.containsKey("fruits"));
    }

    @org.junit.Test
    public void givenMultiValuesMap_whenUsingContainsValueMethod_thenReturningTrue() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");

        assertTrue(map.containsValue("orange"));
    }

    @org.junit.Test
    public void givenMultiValuesMap_whenUsingIsEmptyMethod_thenReturningFalse() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");

        assertFalse(map.isEmpty());
    }

    @org.junit.Test
    public void givenMultiValuesMap_whenUsingSizeMethod_thenReturningElementCount() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");

        assertEquals(4, map.size());
    }

    @org.junit.Test
    public void givenArrayListValuedHashMap_whenPuttingDoubleValues_thenReturningAllValues() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("fruits", "orange");

        assertThat((Collection<String>) map.get("fruits")).containsExactly("apple", "orange", "orange");
    }

    @org.junit.Test
    public void givenHashSetValuedHashMap_whenPuttingTwiceTheSame_thenReturningOneValue() {
        MultiValuedMap<String, String> map = new HashSetValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "apple");

        assertThat((Collection<String>) map.get("fruits")).containsExactly("apple");
    }

    @org.junit.Test(expected = UnsupportedOperationException.class)
    public void givenUnmodifiableMultiValuedMap_whenInserting_thenThrowingException() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        MultiValuedMap<String, String> immutableMap = MultiMapUtils.unmodifiableMultiValuedMap(map);

        immutableMap.put("fruits", "banana");

    }
}
