package com.baeldung.guava.tutorial;

import com.google.common.util.concurrent.AtomicLongMap;

public class AtomicLongMapTutorials {

    private AtomicLongMap<String> atomicLongMap;

    public AtomicLongMapTutorials() {
        atomicLongMap = AtomicLongMap.create();
    }

    public void addKeys() {
        atomicLongMap.addAndGet("apple", 250);
        atomicLongMap.addAndGet("bat", 350);
        atomicLongMap.addAndGet("cat", 450);
        atomicLongMap.addAndGet("dog", 550);
    }

    public static void main(String[] args) {
        AtomicLongMapTutorials atomicLongMapTutorials = new AtomicLongMapTutorials();
        atomicLongMapTutorials.addKeys();

        System.out.println(atomicLongMapTutorials.atomicLongMap.get("2"));
    }

}
