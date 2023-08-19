import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MultiValueHashMapTest {
    @Test
    public void given_MultiValueHashMap_whenPuttingAndGettingSingleValue_thenValueIsRetrieved() {
        MultiValueHashMap<String, Integer> map = new MultiValueHashMap<>();
        map.put("key1", 10);
        assertEquals(List.of(10), map.get("key1"));
    }

    @Test
    public void given_MultiValueHashMap_whenPuttingAndGettingMultipleValues_thenAllValuesAreRetrieved() {
        MultiValueHashMap<String, String> map = new MultiValueHashMap<>();
        map.put("key2", "value1");
        map.put("key2", "value2");
        map.put("key2", "value3");

        assertEquals(List.of("value1", "value2", "value3"), map.get("key2"));
    }

    @Test
    public void given_MultiValueHashMap_whenGettingNonExistentKey_thenEmptyListIsReturned() {
        MultiValueHashMap<String, Double> map = new MultiValueHashMap<>();
        assertTrue(map.get("nonexistent").isEmpty());
    }

    @Test
    public void given_MultiValueHashMap_whenRemovingValue_thenValueIsSuccessfullyRemoved() {
        MultiValueHashMap<Integer, String> map = new MultiValueHashMap<>();
        map.put(1, "one");
        map.put(1, "uno");
        map.put(1, "eins");

        map.remove(1, "uno");
        assertEquals(List.of("one", "eins"), map.get(1));
    }

    @Test
    public void testRemoveNonExistentValue() {
        MultiValueHashMap<Integer, String> map = new MultiValueHashMap<>();
        map.put(1, "one");
        map.remove(1, "nonexistent");
        assertEquals(List.of("one"), map.get(1));
    }

    public class MultiValueHashMap<K, V> {
        private HashMap<K, ArrayList<V>> map;

        // Constructor
        public MultiValueHashMap() {
            map = new HashMap<>();
        }

        public void put(K key, V value) {
            if (!map.containsKey(key)) {
                map.put(key, new ArrayList<>());
            }
            map.get(key).add(value);
        }

        public List<V> get(K key) {
            return map.getOrDefault(key, new ArrayList<>());
        }

        public void remove(K key, V value) {
            if (map.containsKey(key)) {
                map.get(key).remove(value);
            }
        }


    }
}
