package com.baeldung.copyinghashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;

public class CopyHashMap {
    
    public static <String, Employee> HashMap<String, Employee> copyUsingConstructor(HashMap<String, Employee> originalMap) {
        return new HashMap<String, Employee>(originalMap);
    }
    
    public static <String, Employee> HashMap<String, Employee> copyUsingClone(HashMap<String, Employee> originalMap) {
        return (HashMap<String, Employee>) originalMap.clone();
    }

    public static <String, Employee> HashMap<String, Employee> copyUsingPut(HashMap<String, Employee> originalMap) {
        HashMap<String, Employee> copyMap = new HashMap<String, Employee>();
        Set<Entry<String, Employee>> entries = originalMap.entrySet();
        for(Map.Entry<String, Employee> mapEntry: entries) {
            copyMap.put(mapEntry.getKey(), mapEntry.getValue());
        }
        
        return copyMap;
    }
    
    public static <String, Employee> HashMap<String, Employee> copyUsingPutAll(HashMap<String, Employee> originalMap) {
        HashMap<String, Employee> copyMap = new HashMap<String, Employee>();
        copyMap.putAll(originalMap);
        
        return copyMap;
    }
    
    public static <String, Employee> HashMap<String, Employee> copyUsingJava8Stream(HashMap<String, Employee> originalMap) {
        Set<Entry<String, Employee>> entries = originalMap.entrySet();
        HashMap<String, Employee> copyMap = (HashMap<String, Employee>) entries
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        return copyMap;
    }
    
    public static <String, Employee> HashMap<String, Employee> shallowCopy(HashMap<String, Employee> originalMap) {
        return (HashMap<String, Employee>) originalMap.clone();
    }
    
    public static <String, Employee> HashMap<String, Employee> deepCopy(HashMap<String, Employee> originalMap) {
        return SerializationUtils.clone(originalMap);
    }
    
}
