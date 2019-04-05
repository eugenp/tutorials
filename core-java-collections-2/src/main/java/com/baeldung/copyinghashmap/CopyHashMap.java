package com.baeldung.copyinghashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;

public class CopyHashMap {
    
    public static HashMap copyUsingConstructor(HashMap originalMap) {
        return new HashMap(originalMap);
    }
    
    public static HashMap copyUsingClone(HashMap originalMap) {
        return (HashMap) originalMap.clone();
    }

    public static HashMap copyUsingPut(HashMap originalMap) {
        HashMap copyMap = new HashMap();
        Set<Map.Entry> entries = originalMap.entrySet();
        for(Map.Entry mapEntry: entries) {
            copyMap.put(mapEntry.getKey(), mapEntry.getValue());
        }
        
        return copyMap;
    }
    
    public static Map copyUsingPutAll(Map originalMap) {
        HashMap copyMap = new HashMap();
        copyMap.putAll(originalMap);
        
        return copyMap;
    }
    
    public static HashMap copyUsingJava8Stream(HashMap originalMap) {
        Set<Map.Entry> entries = originalMap.entrySet();
        HashMap copyMap = (HashMap) entries
            .stream()
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        
        return copyMap;
    }
    
    public static HashMap copyMapAndDivideValuesBy2(HashMap originalMap) {
        Set<Map.Entry> entries = originalMap.entrySet();
        HashMap copyMap = (HashMap) entries
            .stream()
            .collect(Collectors.toMap(mapEntry -> mapEntry.getKey(), mapEntry -> (int)mapEntry.getValue()/2));
        
        return copyMap;
    }
    
    public static HashMap shallowCopy(HashMap originalMap) {
        return (HashMap) originalMap.clone();
    }
    
    public static HashMap deepCopy(HashMap originalMap) {
        return SerializationUtils.clone(originalMap);
    }
    
}
