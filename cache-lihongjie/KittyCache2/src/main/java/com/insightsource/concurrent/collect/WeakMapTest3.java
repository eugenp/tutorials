package com.insightsource.concurrent.collect;

import java.util.UUID;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.google.common.collect.MapMaker;

/*
 * weak value ---> MapMaker
 */
public class WeakMapTest3 {
    public static ConcurrentMap<UUID, ReentrantReadWriteLock> map = null;
    public static Thread a = null, b = null;
    public static UUID testUUID = null;

    public static void main(String[] args) {
        map = new MapMaker().weakValues().makeMap();

        a = new Thread(new ThreadA());
        b = new Thread(new ThreadB());

        a.start();
        b.start();
    }

    private static class ThreadA implements Runnable {
        @Override
        public void run() {
            System.out.println("A: Start.");
            try {
                Thread.currentThread().sleep(10000);
            } catch (Exception e) {
                System.out.println("A: Start checking key.");
            }

            for (int i = 0; i < 1000000; i++) {
                if (map.get(testUUID) != null) {
                    System.out.println("A: At iteration " + i + " the map still holds the reference on key. Size: " + map.size());
                } else {
                    System.out.println("A: Key has finally been garbage collected at iteration " + i + ", hence the map is now empty. Size: " + map.size());
                    break;
                }
            }
            System.out.println("A: End.");
        }
    }


    private static class ThreadB implements Runnable {
        @Override
        public void run() {
            System.out.println("B: Start.");
            UUID id = UUID.randomUUID();
            testUUID = id;

            map.put(id, new ReentrantReadWriteLock());
            System.out.println("B: Put into map with key " + id + "\t" + map.get(id));
            System.out.println("B: Map size is " + map.size());

            Thread tc = new Thread(new ThreadC());
            tc.start();

            try {
                Thread.currentThread().sleep(1000);
            } catch (Exception e) {
            }

            a.interrupt();        //week up Thread A

            System.out.println("B: End.");
        }
    }

    private static class ThreadC implements Runnable {
        @Override
        public void run() {
            System.err.println("C: start.");
            System.err.println("C: Get key " + testUUID.toString());
            ReentrantReadWriteLock lock = map.get(testUUID);

            System.err.println("C: Get lock " + lock);
            try {
                Thread.currentThread().sleep(10000);
            } catch (Exception e) {
            }

            System.err.println("C end.");
        }
    }
}
