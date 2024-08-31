package com.baeldung.guava.multimap;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

public class MultimapIteration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MultimapIteration.class);

    static void iterateUsingEntries(Multimap<String, String> multiMap) {
        multiMap.entries()
          .forEach(entry -> LOGGER.info("{} => {}", entry.getKey(), entry.getValue()));
    }

    static void iterateUsingAsMap(Multimap<String, String> multiMap) {
        multiMap.asMap()
          .entrySet()
          .forEach(entry -> LOGGER.info("{} => {}", entry.getKey(), entry.getValue()));
    }

    static void iterateUsingKeySet(Multimap<String, String> multiMap) {
        multiMap.keySet()
          .forEach(LOGGER::info);
    }

    static void iterateUsingKeys(Multimap<String, String> multiMap) {
        multiMap.keys()
          .forEach(LOGGER::info);
    }

    static void iterateUsingValues(Multimap<String, String> multiMap) {
        multiMap.values()
          .forEach(LOGGER::info);
    }

    public static void main(String[] args) {

        Multimap<String, String> multiMap = ArrayListMultimap.create();
        multiMap.putAll("key1", List.of("value1", "value11", "value111"));
        multiMap.putAll("key2", List.of("value2", "value22", "value222"));
        multiMap.putAll("key3", List.of("value3", "value33", "value333"));

        iterateUsingEntries(multiMap);
        iterateUsingAsMap(multiMap);
        iterateUsingKeys(multiMap);
        iterateUsingKeySet(multiMap);
        iterateUsingValues(multiMap);
    }

}