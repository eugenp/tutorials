package com.baeldung.hashtableandconcurrenthashmap;

import org.junit.Test;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
public class ConcurrentHashMapUnitTest {
    @Test
    public void givenEmptyConcurrentHashMap_whenValuesAreAdded_thenValuesCanBeRetrieved() {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("Key1", "1");
        concurrentHashMap.put("Key2", "2");
        concurrentHashMap.putIfAbsent("Key3", "3");
        String value = concurrentHashMap.get("Key2");

        assertEquals("1", concurrentHashMap.get("Key1"));
        assertEquals("2", value);
        assertEquals("3", concurrentHashMap.get("Key3"));
    }
    @Test
    public void givenPopulatedConcurrentHashMap_whenModifiedDuringIteration_thenShouldNotThrowConcurrentModificationException() throws InterruptedException {
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("Key1", 1);
        concurrentHashMap.put("Key2", 2);
        concurrentHashMap.put("Key3", 3);
        AtomicBoolean exceptionCaught = new AtomicBoolean(false);

        Thread iteratorThread = new Thread(() -> {
            Iterator<String> it = concurrentHashMap.keySet().iterator();
            try {
                while (it.hasNext()) {
                    it.next();
                    Thread.sleep(100);
                }
            } catch (ConcurrentModificationException e) {
                exceptionCaught.set(true);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread modifierThread = new Thread(() -> {
            try {
                Thread.sleep(50);
                concurrentHashMap.put("Key4", 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        iteratorThread.start();
        modifierThread.start();

        iteratorThread.join();
        modifierThread.join();

        assertFalse(exceptionCaught.get());
    }
}
