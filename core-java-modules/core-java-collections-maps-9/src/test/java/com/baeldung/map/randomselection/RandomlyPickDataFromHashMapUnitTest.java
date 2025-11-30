package com.baeldung.map.randomselection;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RandomlyPickDataFromHashMapUnitTest {

    private static final Logger LOG = LoggerFactory.getLogger(RandomlyPickDataFromHashMapUnitTest.class);
    private static final HashMap<String, String> dataMap = new HashMap<>();

    @BeforeAll
    public static void setupMap() {
        dataMap.put("Key-A", "Value: A");
        dataMap.put("Key-B", "Value: B");
        dataMap.put("Key-C", "Value: C");
        dataMap.put("Key-D", "Value: D");
        dataMap.put("Key-E", "Value: E");
        dataMap.put("Key-F", "Value: F");
        dataMap.put("Key-G", "Value: G");
        dataMap.put("Key-H", "Value: H");
        dataMap.put("Key-I", "Value: I");
    }

    <K, V> K randomKeyUsingArray(HashMap<K, V> map) {
        K[] keys = (K[]) map.keySet().toArray();
        Random random = new Random();
        return keys[random.nextInt(keys.length)];
    }

    <K, V> K randomKeyUsingIterator(HashMap<K, V> map) {
        Random random = new Random();
        int targetIndex = random.nextInt(map.size());

        Iterator<K> iterator = map.keySet()
            .iterator();
        K currentKey = null;

        for (int i = 0; i <= targetIndex; i++) {
            currentKey = iterator.next();
        }

        return currentKey;
    }

    <K, V> K randomKeyUsingStream(HashMap<K, V> map) {
        Random random = new Random();
        return map.keySet()
            .stream()
            .skip(random.nextInt(map.size()))
            .findFirst()
            .orElseThrow();
    }

    // random value
    <K, V> V randomValueUsingStream(HashMap<K, V> map) {
        Random random = new Random();
        return map.values()
            .stream()
            .skip(random.nextInt(map.size()))
            .findFirst()
            .orElseThrow();
    }

    //random entry
    <K, V> HashMap.Entry<K, V> randomEntryUsingStream(HashMap<K, V> map) {
        Random random = new Random();
        return map.entrySet()
            .stream()
            .skip(random.nextInt(map.size()))
            .findFirst()
            .orElseThrow();
    }

    @Test
    void whenGetRandomKeyUsingArray_thenCorrect() {
        for (int i = 0; i < 3; i++) {
            String key = randomKeyUsingArray(dataMap);
            LOG.info("Random Key (Array): {}", key);
        }
    }

    @Test
    void whenGetRandomKeyUsingIterator_thenCorrect() {
        for (int i = 0; i < 3; i++) {
            String key = randomKeyUsingIterator(dataMap);
            LOG.info("Random Key (Iterator): {}", key);
        }
    }

    @Test
    void whenGetRandomKeyUsingStream_thenCorrect() {
        for (int i = 0; i < 3; i++) {
            String key = randomKeyUsingStream(dataMap);
            LOG.info("Random Key (Stream): {}", key);
        }
    }

    @Test
    void whenGetRandomValueByARandomKey_thenCorrect() {
        for (int i = 0; i < 3; i++) {
            String key = randomKeyUsingStream(dataMap);
            String value = dataMap.get(key);
            LOG.info("Random Value (by a random key): {}", value);
        }
    }

    @Test
    void whenGetRandomValueUsingStream_thenCorrect() {
        for (int i = 0; i < 3; i++) {
            String value = randomValueUsingStream(dataMap);
            LOG.info("Random Value (Stream): {}", value);
        }
    }

    @Test
    void whenGetRandomKeyValueByARandomKey_thenCorrect() {
        for (int i = 0; i < 3; i++) {
            String key = randomKeyUsingStream(dataMap);
            String value = dataMap.get(key);
            LOG.info("Random Key-Value (by a random key): {} -> {}", key, value);
        }
    }

    @Test
    void whenGetRandomEntryUsingStream_thenCorrect() {
        for (int i = 0; i < 3; i++) {
            HashMap.Entry<String, String> entry = randomEntryUsingStream(dataMap);
            LOG.info("Random Entry (Stream): {} -> {}", entry.getKey(), entry.getValue());
        }
    }
}