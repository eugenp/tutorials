package com.baeldung.combiningcollections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.collections4.ListUtils;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;

public class CombiningLists {
    
    public static List<Object> usingNativeJava(List<Object> first, List<Object> second) {
        List<Object> combined = new ArrayList<>();
        combined.addAll(first);
        combined.addAll(second);
        return combined;
    }
    
    public static List<Object> usingJava8ObjectStream(List<Object> first, List<Object> second) {
        List<Object> combined = Stream.concat(first.stream(), second.stream()).collect(Collectors.toList());
        return combined;
    }

    public static List<Object> usingJava8FlatMaps(List<Object> first, List<Object> second) {
        List<Object> combined = Stream.of(first, second).flatMap(Collection::stream).collect(Collectors.toList());
        return combined;
    }
    
    public static List<Object> usingApacheCommons(List<Object> first, List<Object> second) {
        List<Object> combined = ListUtils.union(first, second);
        return combined;
    }

    public static List<Object> usingGuava(List<Object> first, List<Object> second) {
        Iterable<Object> combinedIterables = Iterables.unmodifiableIterable(
        Iterables.concat(first, second));
        
        List<Object> combined = Lists.newArrayList(combinedIterables);
        return combined;
    }
    
}
