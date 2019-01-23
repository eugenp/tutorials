package com.baeldung.java.map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.Test;

public class MultiValuedMapUnitTest {

    @Test
    public void givenMultiValuesMap_whenPuttingMultipleValuesUsingPutMethod_thenReturningAllValues() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        
        map.put("key", "value1");
        map.put("key", "value2");
        map.put("key", "value2");
        
        assertThat((Collection<String>) map.get("key")).containsExactly("value1", "value2", "value2");
    }
 
    @Test
    public void givenMultiValuesMap_whenPuttingMultipleValuesUsingPutAllMethod_thenReturningAllValues() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        
        map.putAll("key", Arrays.asList("value1", "value2", "value2"));
        
        assertThat((Collection<String>) map.get("key")).containsExactly("value1", "value2", "value2");
    }

    @Test
    public void givenMultiValuesMap_whenGettingValueUsingGetMethod_thenReturningValue() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key", "value");
        
        assertThat((Collection<String>) map.get("key")).containsExactly("value");
    }

    @Test
    public void givenMultiValuesMap_whenUsingEntriesMethod_thenReturningMappings() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key", "value1");
        map.put("key", "value2");
        
        Collection<Entry<String, String>> entries = (Collection<Entry<String, String>>) map.entries();
        
        for(Map.Entry<String,String> entry : entries) {
            assertThat(entry.getKey()).contains("key");
            assertTrue(entry.getValue().equals("value1") || entry.getValue().equals("value2") );
        }
    }

    @Test
    public void givenMultiValuesMap_whenUsingKeysMethod_thenReturningAllKeys() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        
        assertThat(((Collection<String>) map.keys())).contains("key", "key1", "key2");
    }

    @Test
    public void givenMultiValuesMap_whenUsingKeySetMethod_thenReturningAllKeys() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        
        assertThat((Collection<String>) map.keySet()).contains("key", "key1", "key2");
    }

    @Test
    public void givenMultiValuesMap_whenUsingValuesMethod_thenReturningAllValues() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        
        assertThat(((Collection<String>) map.values())).contains("value", "value1", "value2");
    }

    @Test
    public void givenMultiValuesMap_whenUsingRemoveMethod_thenReturningUpdatedMap() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertThat(((Collection<String>) map.values())).contains("value", "value1", "value2");
        
        map.remove("key");
        
        assertThat(((Collection<String>) map.values())).contains("value1", "value2");
    }

    @Test
    public void givenMultiValuesMap_whenUsingRemoveMappingMethod_thenReturningUpdatedMapAfterMappingRemoved() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertThat(((Collection<String>) map.values())).contains("value", "value1", "value2");
        
        map.removeMapping("key", "value");
        
        assertThat(((Collection<String>) map.values())).contains("value1", "value2");
    }

    @Test
    public void givenMultiValuesMap_whenUsingClearMethod_thenReturningEmptyMap() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        assertThat(((Collection<String>) map.values())).contains("value", "value1", "value2");
        
        map.clear();
        
        assertTrue(map.isEmpty());
    }

    @Test
    public void givenMultiValuesMap_whenUsingContainsKeyMethod_thenReturningTrue() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        
        assertTrue(map.containsKey("key"));
    }

    @Test
    public void givenMultiValuesMap_whenUsingContainsValueMethod_thenReturningTrue() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        
        assertTrue(map.containsValue("value"));
    }

    @Test
    public void givenMultiValuesMap_whenUsingIsEmptyMethod_thenReturningFalse() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        
        assertFalse(map.isEmpty());
    }

    @Test
    public void givenMultiValuesMap_whenUsingSizeMethod_thenReturningElementCount() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key", "value");
        map.put("key1", "value1");
        map.put("key2", "value2");
        
        assertEquals(3, map.size());
    }

    @Test
    public void givenArrayListValuedHashMap_whenPuttingDoubleValues_thenReturningAllValues() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        
        map.put("key", "value1");
        map.put("key", "value2");
        map.put("key", "value2");
        
        assertThat((Collection<String>) map.get("key")).containsExactly("value1", "value2", "value2");
    }

    @Test
    public void givenHashSetValuedHashMap_whenPuttingTwiceTheSame_thenReturningOneValue() {
        MultiValuedMap<String, String> map = new HashSetValuedHashMap<>();
        
        map.put("key1", "value1");
        map.put("key1", "value1");
        
        assertThat((Collection<String>) map.get("key1")).containsExactly("value1");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenUnmodifiableMultiValuedMap_whenInserting_thenThrowingException() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key", "value1");
        map.put("key", "value2");
        MultiValuedMap<String, String> immutableMap = MultiMapUtils.unmodifiableMultiValuedMap(map);
        
        immutableMap.put("key", "value3");
    }
    

}
