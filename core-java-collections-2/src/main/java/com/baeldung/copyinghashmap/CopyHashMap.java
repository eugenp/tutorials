package com.baeldung.copyinghashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;

public class CopyHashMap {
    
    public static <K, V> HashMap<K, V> copyUsingConstructor(HashMap<K, V> originalMap) {
        return new HashMap<K, V>(originalMap);
    }
    
    public static <K, V> HashMap<K, V> copyUsingClone(HashMap<K, V> originalMap) {
        return (HashMap<K, V>) originalMap.clone();
    }

    public static <K, V> HashMap<K, V> copyUsingPut(HashMap<K, V> originalMap) {
        HashMap<K, V> copyMap = new HashMap<K, V>();
        Set<Entry<K, V>> entries = originalMap.entrySet();
        for(Map.Entry<K, V> mapEntry: entries) {
            copyMap.put((K)mapEntry.getKey(), (V)mapEntry.getValue());
        }
        
        return copyMap;
    }
    
    public static <K, V> HashMap<K, V> copyUsingPutAll(HashMap<K, V> originalMap) {
        HashMap<K, V> copyMap = new HashMap<K, V>();
        copyMap.putAll(originalMap);
        
        return copyMap;
    }
    
    public static <K, V> HashMap<K, V> copyUsingJava8Stream(HashMap<K, V> originalMap) {
        Set<Entry<K, V>> entries = originalMap.entrySet();
        HashMap<K, V> copyMap = (HashMap<K, V>) entries
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        return copyMap;
    }
    
    public static <K, V> HashMap<K, V> shallowCopy(HashMap<K, V> originalMap) {
        return (HashMap<K, V>) originalMap.clone();
    }
    
    public static <K, V> HashMap<K, V> deepCopy(HashMap<K, V> originalMap) {
        return SerializationUtils.clone(originalMap);
    }
    
}
