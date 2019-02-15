package com.baeldung.combiningcollections;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.SetUtils;

import com.google.common.collect.Sets;

public class CombiningSets {
    
    public static Set<Object> usingNativeJava(Set<Object> first, Set<Object> second) {
        Set<Object> combined = new HashSet<>();
        combined.addAll(first);
        combined.addAll(second);
        return combined;
    }
    
    public static Set<Object> usingJava8ObjectStream(Set<Object> first, Set<Object> second) {
        Set<Object> combined = Stream.concat(first.stream(), second.stream()).collect(Collectors.toSet());
        return combined;
    }

    public static Set<Object> usingJava8FlatMaps(Set<Object> first, Set<Object> second) {
        Set<Object> combined = Stream.of(first, second).flatMap(Collection::stream).collect(Collectors.toSet());
        return combined;
    }
    
    public static Set<Object> usingApacheCommons(Set<Object> first, Set<Object> second) {
        Set<Object> combined = SetUtils.union(first, second);
        return combined;
    }

    public static Set<Object> usingGuava(Set<Object> first, Set<Object> second) {
        Set<Object> combined = Sets.union(first, second);
        return combined;
    }
    
}
