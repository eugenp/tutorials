package com.baeldung.map;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.collections4.MultiMap;
import org.apache.commons.collections4.MultiMapUtils;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.map.MultiValueMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.commons.collections4.multimap.HashSetValuedHashMap;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

public class MapMultipleValuesUnitTest {
    private static final Logger LOG = LoggerFactory.getLogger(MapMultipleValuesUnitTest.class);

    @Test
    public void givenHashMap_whenPuttingTwice_thenReturningFirstValue() {
        Map<String, String> map = new HashMap<>();
        assertThat(map.put("key1", "value1")).isEqualTo(null);
        assertThat(map.put("key1", "value2")).isEqualTo("value1");
        assertThat(map.get("key1")).isEqualTo("value2");
    }

    @Test
    public void givenCollectionAsValue_whenPuttingTwice_thenReturningCollection() {
        Map<String, List<String>> map = new HashMap<>();
        List<String> list = new ArrayList<>();
        map.put("key1", list);
        map.get("key1").add("value1");
        map.get("key1").add("value2");
        assertThat(map.get("key1").get(0)).isEqualTo("value1");
        assertThat(map.get("key1").get(1)).isEqualTo("value2");
    }

    @Test
    public void givenCollectionAsValueAndJava8_whenPuttingTwice_thenReturningCollection() {
        Map<String, List<String>> map = new HashMap<>();
        map.computeIfAbsent("key1", k -> new ArrayList<>()).add("value1");
        map.computeIfAbsent("key1", k -> new ArrayList<>()).add("value2");
        assertThat(map.get("key1").get(0)).isEqualTo("value1");
        assertThat(map.get("key1").get(1)).isEqualTo("value2");
    }

    @Test
    public void givenMultiValueMap_whenPuttingTwice_thenReturningValues() {
        MultiMap<String, String> map = new MultiValueMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        assertThat((Collection<String>) map.get("key1"))
          .contains("value1", "value2");
    }

    @Test
    public void givenArrayListValuedHashMap_whenPuttingDoubleValues_thenReturningAllValues() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        map.put("key1", "value2");
        assertThat((Collection<String>) map.get("key1"))
          .containsExactly("value1", "value2", "value2");
    }
    
    @Test
    public void givenHashSetValuedHashMap_whenPuttingTwiceTheSame_thenReturningOneValue() {
        MultiValuedMap<String, String> map = new HashSetValuedHashMap<>();
        map.put("key1", "value1");
        map.put("key1", "value1");
        assertThat((Collection<String>) map.get("key1"))
          .containsExactly("value1");
    }

    @Test(expected = UnsupportedOperationException.class)
    public void givenUnmodifiableMultiValuedMap_whenInserting_thenThrowingException() {
        MultiValuedMap<String, String> map = new ArrayListValuedHashMap<>();
        map.put("key1", "value1");
        map.put("key1", "value2");
        MultiValuedMap<String, String> immutableMap =
          MultiMapUtils.unmodifiableMultiValuedMap(map);
        immutableMap.put("key1", "value3");
    }
    
    @Test
    public void givenArrayListMultiMap_whenInserting_thenCorrectOutput() {
        Multimap<String, String> map = ArrayListMultimap.create();
        map.put("key1", "value2");
        map.put("key1", "value1");
        assertThat((Collection<String>) map.get("key1"))
          .containsExactly("value2", "value1");
    }   
    
    @Test
    public void givenLinkedHashMultiMap_whenInserting_thenReturningValuesInInsertionOrder() {
        Multimap<String, String> map = LinkedHashMultimap.create();
        map.put("key1", "value3");
        map.put("key1", "value1");
        map.put("key1", "value2");
        assertThat((Collection<String>) map.get("key1"))
          .containsExactly("value3", "value1", "value2");
    }

    @Test
    public void givenTreeMultimap_whenInserting_thenReturningValuesInNaturalOrder() {
        Multimap<String, String> map = TreeMultimap.create();
        map.put("key1", "value3");
        map.put("key1", "value1");
        map.put("key1", "value2");
        assertThat((Collection<String>) map.get("key1"))
          .containsExactly("value1", "value2", "value3");
    }
    
}