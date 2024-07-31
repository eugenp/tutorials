package com.baeldung.commonissues;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CollectionsConcurrencyIssues {

    private void putIfAbsentList_NonAtomicOperation_ProneToConcurrencyIssues() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        if (!list.contains("foo")) {
            list.add("foo");
        }
    }

    private void putIfAbsentList_AtomicOperation_ThreadSafe() {
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        synchronized (list) {
            if (!list.contains("foo")) {
                list.add("foo");
            }
        }
    }

    private void putIfAbsentMap_NonAtomicOperation_ProneToConcurrencyIssues() {
        Map<String, String> map = new ConcurrentHashMap<>();
        if (!map.containsKey("foo")) {
            map.put("foo", "bar");
        }
    }

    private void putIfAbsentMap_AtomicOperation_BetterApproach() {
        Map<String, String> map = new ConcurrentHashMap<>();
        synchronized (map) {
            if (!map.containsKey("foo")) {
                map.put("foo", "bar");
            }
        }
    }

    private void putIfAbsentMap_AtomicOperation_BestApproach() {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.putIfAbsent("foo", "bar");
    }

    private void computeIfAbsentMap_AtomicOperation() {
        Map<String, String> map = new ConcurrentHashMap<>();
        map.computeIfAbsent("foo", key -> key + "bar");
    }

}
