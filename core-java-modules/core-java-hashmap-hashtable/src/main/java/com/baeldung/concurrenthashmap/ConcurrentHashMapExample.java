package com.baeldung.concurrenthashmap;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapExample {
    public static void simpleConcurrentHashMap(){
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("Key1", "1");
        concurrentHashMap.put("Key2", "2");
        concurrentHashMap.putIfAbsent("Key3", "3");
        String value = concurrentHashMap.get("Key2");
        System.out.println("Value for Key2: " + value);
    }

    public static void concurrentHashMapThreadSafety(){
        // ConcurrentHashMap
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        long startTime = System.currentTimeMillis();
        startTime = System.currentTimeMillis();

        Thread t3 = new Thread(() -> {
            for (int i = 0; i < 10000; i++) {
                concurrentHashMap.put(String.valueOf(i), i);
            }
        });

        Thread t4 = new Thread(() -> {
            for (int i = 10000; i < 20000; i++) {
                concurrentHashMap.put(String.valueOf(i), i);
            }
        });

        t3.start();
        t4.start();

        try {
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Time taken with ConcurrentHashMap: " + (endTime - startTime) + " ms");
    }

    public static void  concurrentHashMapWeaklyConsistent(){
        ConcurrentHashMap<String, Integer> concurrentHashMap = new ConcurrentHashMap<>();
        concurrentHashMap.put("Key1", 1);
        concurrentHashMap.put("Key2", 2);

        Thread iteratorThread = new Thread(() -> {
            Iterator<String> it = concurrentHashMap.keySet().iterator();
            try {
                while (it.hasNext()) {
                    System.out.println(it.next());
                    Thread.sleep(100);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        Thread modifierThread = new Thread(() -> {
            try {
                Thread.sleep(150);
                concurrentHashMap.put("Key3", 3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        iteratorThread.start();
        modifierThread.start();
    }
}
