package com.baeldung.hashtableandconcurrenthashmap;

import org.junit.Test;
import java.util.ConcurrentModificationException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
public class HashtableUnitTest {
    @Test
    public void givenEmptyHashtable_whenValuesAreAdded_thenValuesCanBeRetrieved() {
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("Key1", "1");
        hashtable.put("Key2", "2");
        hashtable.putIfAbsent("Key3", "3");
        String value = hashtable.get("Key2");

        assertEquals("1", hashtable.get("Key1"));
        assertEquals("2", value);
        assertEquals("3", hashtable.get("Key3"));
    }
    @Test
    public void givenPopulatedHashtable_whenModifiedDuringIteration_thenShouldThrowConcurrentModificationException() throws InterruptedException {
        Hashtable<String, Integer> hashtable = new Hashtable<>();
        hashtable.put("Key1", 1);
        hashtable.put("Key2", 2);
        hashtable.put("Key3", 3);
        AtomicBoolean exceptionCaught = new AtomicBoolean(false);

        Thread iteratorThread = new Thread(() -> {
            Iterator<String> it = hashtable.keySet().iterator();
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
                hashtable.put("Key4", 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        iteratorThread.start();
        modifierThread.start();

        iteratorThread.join();
        modifierThread.join();

        assertTrue(exceptionCaught.get());
    }
}