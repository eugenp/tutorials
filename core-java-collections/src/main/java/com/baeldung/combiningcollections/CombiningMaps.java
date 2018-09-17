package com.baeldung.combiningcollections;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.exec.util.MapUtils;

import com.google.common.collect.ImmutableMap;

public class CombiningMaps {
    
    public static Map<Object, Object> usingNativeJava(Map<Object, Object> first, Map<Object, Object> second) {
    	Map<Object, Object> combined = new HashMap<>();
    	first.putAll(second);
        return first;
    }
    
//    public static Map<Object, Object> usingJava8ObjectStream(Map<Object, Object> first, Map<Object, Object> second) {
//        Map<Object, Object> combined = first.merge(key, value, remappingFunction)
//    }
    
    public static Map<Object, Object> usingJava8ObjectStream(Map<Object, Object> first, Map<Object, Object> second) {
        Map<Object, Object> combined = list.stream().collect(HashMap::new,
                (a, b) -> b.forEach((k, v) -> a.merge(k, v, Integer::max)),
                Map::putAll);
    }

    public static Map<Object, Object> usingJava8FlatMaps(Map<Object, Object> first, Map<Object, Object> second) {
        Map<Object, Object> combined = Stream.of(first, second)
            .map(Map::entrySet)          // converts each map into an entry set
            .flatMap(Collection::stream) // converts each set into an entry stream, then
                                         // "concatenates" it in place of the original set
            .collect(
                Collectors.toMap(        // collects into a map
                    Map.Entry::getKey,   // where each entry is based
                    Map.Entry::getValue, // on the entries in the stream
                    Integer::max         // such that if a value already exist for
                                         // a given key, the max of the old
                                         // and new value is taken
                )
            )
        ;

    }
    
    public static Map<Object, Object> usingApacheCommons(Map<Object, Object> first, Map<Object, Object> second) {
        Map<Object, Object> combined = MapUtils.merge(first, second);
        return combined;
    }

    public static Map<Object, Object> usingGuava(Map<Object, Object> first, Map<Object, Object> second) {
        Map<Object, Object> combined = ImmutableMap.<Object, Object>builder()
        	      .putAll(first)
        	      .putAll(second)
        	      .build();
        return combined;
    }
    
}
