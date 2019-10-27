package com.baeldung.map;

import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiSet;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MultiValuedMapUnitTest {

    @Test
    public void givenMultiValuesMap_whenPuttingMultipleValuesUsingPutMethod_thenReturningAllValues() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        
        assertThat((Collection<String>) map.get("fruits")).containsExactly("apple", "orange");

    }
 
    @Test
    public void givenMultiValuesMap_whenPuttingMultipleValuesUsingPutAllMethod_thenReturningAllValues() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
       
        map.putAll("vehicles", Arrays.asList("car", "bike"));
        
        assertThat((Collection<String>) map.get("vehicles")).containsExactly("car", "bike");

    }

    @Test
    public void givenMultiValuesMap_whenGettingValueUsingGetMethod_thenReturningValue() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
      
        map.put("fruits", "apple");
        
        assertThat((Collection<String>) map.get("fruits")).containsExactly("apple");
    }

    @Test
    public void givenMultiValuesMap_whenUsingEntriesMethod_thenReturningMappings() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");

        Collection<Entry<String, String>> entries = (Collection<Entry<String, String>>) map.entries();
        
        for(Map.Entry<String,String> entry : entries) {
            assertThat(entry.getKey()).contains("fruits");
            assertTrue(entry.getValue().equals("apple") || entry.getValue().equals("orange") );
        }
    }

    @Test
    public void givenMultiValuesMap_whenUsingKeysMethod_thenReturningAllKeys() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");
        
        MultiSet<String> keys = map.keys();
        
        assertThat((keys)).contains("fruits", "vehicles");

    }

    @Test
    public void givenMultiValuesMap_whenUsingKeySetMethod_thenReturningAllKeys() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");
        
        Set<String> keys = map.keySet();
        
        assertThat(keys).contains("fruits", "vehicles");

    }

    @Test
    public void givenMultiValuesMap_whenUsingValuesMethod_thenReturningAllValues() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();

        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");
        
        assertThat(((Collection<String>) map.values())).contains("apple", "orange", "car", "bike");
    }

    @Test
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

    @Test
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

    @Test
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

    @Test
    public void givenMultiValuesMap_whenUsingContainsKeyMethod_thenReturningTrue() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");
        
        assertTrue(map.containsKey("fruits"));
    }

    @Test
    public void givenMultiValuesMap_whenUsingContainsValueMethod_thenReturningTrue() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");
        
        assertTrue(map.containsValue("orange"));
    }

    @Test
    public void givenMultiValuesMap_whenUsingIsEmptyMethod_thenReturningFalse() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");
        
        assertFalse(map.isEmpty());
    }

    @Test
    public void givenMultiValuesMap_whenUsingSizeMethod_thenReturningElementCount() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("vehicles", "car");
        map.put("vehicles", "bike");
        
        assertEquals(4, map.size());
    }

    @Test
    public void givenArrayListValuedHashMap_whenPuttingDoubleValues_thenReturningAllValues() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        map.put("fruits", "orange");
        
        assertThat((Collection<String>) map.get("fruits")).containsExactly("apple", "orange", "orange");
    }

    @Test
    public void givenHashSetValuedHashMap_whenPuttingTwiceTheSame_thenReturningOneValue() {
        MultiValuedMap<String, String> map = new HashSetValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "apple");
  
        assertThat((Collection<String>) map.get("fruits")).containsExactly("apple");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenUnmodifiableMultiValuedMap_whenInserting_thenThrowingException() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("fruits", "apple");
        map.put("fruits", "orange");
        MultiValuedMap<String, String> immutableMap = MultiMapUtils.unmodifiableMultiValuedMap(map);
        
        immutableMap.put("fruits", "banana");

    }
    

}
