package com.baeldung.hashtable;

import java.util.ConcurrentModificationException;
import java.util.Hashtable;
import java.util.Iterator;

public class HashTableExample {
    public static void simpleHashTable(){
        Hashtable<String, String> hashtable = new Hashtable<>();
        hashtable.put("Key1", "1");
        hashtable.put("Key2", "2");
        hashtable.putIfAbsent("Key3", "3");
        String value = hashtable.get("Key2");
        System.out.println("Value for Key2: " + value);
    }

    public static void hashTableThreadSafety(){
        // Hashtable
        Hashtable<String, Integer> hashTable = new Hashtable<>();
        long startTime = System.currentTimeMillis();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                hashTable.put(String.valueOf(i), i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 10000; i < 20000; i++) {
                hashTable.put(String.valueOf(i), i);
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken with Hashtable: " + (endTime - startTime) + " ms");
    }

    public static void hashTableFailFast(){
        Hashtable<String, Integer> hashtable = new Hashtable<>();
        hashtable.put("Key1", 1);
        hashtable.put("Key2", 2);
        hashtable.put("Key3", 3);
        Thread iteratorThread = new Thread(() -> {
            Iterator<String> it = hashtable.keySet().iterator();
            try {
                while (it.hasNext()) {
                    System.out.println(it.next());
                    Thread.sleep(100);
                }
            } catch (ConcurrentModificationException | InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread modifierThread = new Thread(() -> {
            try {
                Thread.sleep(150);
                hashtable.put("Key4", 4);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        iteratorThread.start();
        modifierThread.start();
    }

}
