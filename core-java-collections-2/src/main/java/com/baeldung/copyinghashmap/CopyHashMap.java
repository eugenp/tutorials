package com.baeldung.copyinghashmap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.SerializationUtils;

public class CopyHashMap {

    public static HashMap basicCopy(HashMap originalMap, HashMap copyMap) {
        Set<Map.Entry> entries = originalMap.entrySet();
        for(Map.Entry mapEntry: entries) {
            copyMap.put(mapEntry.getKey(), mapEntry.getValue());
        }
        
        return copyMap;
    }
    
    
    public static Map copyUsingPutAll(Map originalMap, Map copyMap) {
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
    
    public static HashMap shallowCopy(HashMap originalMap) {
        //return new HashMap(originalMap);
        return (HashMap) originalMap.clone();
    }
    
    public static HashMap deepCopy(HashMap originalMap) {
        return SerializationUtils.clone(originalMap);
    }
    
}
