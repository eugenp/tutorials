package com.insightsource.concurrent.collect;

import java.util.UUID;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/*
 * weak key
 */
public class WeakMapTest2 {
    public static WeakHashMap<UUID, ReentrantReadWriteLock> map = null;
    public static Thread a = null, b = null;

    public static void main(String[] args) {
        map = new WeakHashMap<UUID, ReentrantReadWriteLock>();

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
                if (map.size() != 0) {
                    System.out.println("A: At iteration " + i + " the map still holds the reference on key");
                } else {
                    System.out.println("A: Key has finally been garbage collected at iteration " + i + ", hence the map is now empty");
                    break;
                }
            }
        }
    }

    private static class ThreadB implements Runnable {
        @Override
        public void run() {
            System.out.println("B: Start.");
            System.out.println("B: Put key into map.");
            UUID id = UUID.randomUUID();
            String randUUID = id.toString();
            map.put(id, new ReentrantReadWriteLock());
            System.out.println("B: Map size is " + map.size());

            a.interrupt();
        }
    }
}
