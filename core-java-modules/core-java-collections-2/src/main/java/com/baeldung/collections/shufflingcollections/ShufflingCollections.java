package com.baeldung.collections.shufflingcollections;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ShufflingCollections {
    public static List<String> shuffleList(List<String> list) {
        return list.stream()
                .collect(Collectors.collectingAndThen(Collectors.toList(), collected -> {
                    Collections.shuffle(collected);
                    return collected;
                }));
    }
}
