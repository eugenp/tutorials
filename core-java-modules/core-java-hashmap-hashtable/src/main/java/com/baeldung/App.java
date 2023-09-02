package com.baeldung;

import com.baeldung.concurrenthashmap.ConcurrentHashMapExample;
import com.baeldung.hashtable.HashTableExample;

public class App {
    public static void main(String[] args) {
        HashTableExample.simpleHashTable();
        ConcurrentHashMapExample.simpleConcurrentHashMap();

        HashTableExample.hashTableThreadSafety();
        ConcurrentHashMapExample.concurrentHashMapThreadSafety();

        HashTableExample.hashTableFailFast();
        ConcurrentHashMapExample.concurrentHashMapWeaklyConsistent();
    }
}
