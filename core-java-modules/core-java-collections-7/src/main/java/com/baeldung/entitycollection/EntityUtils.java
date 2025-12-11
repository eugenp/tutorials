package com.baeldung.entitycollection;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EntityUtils {

    public static <T, R> List<R> extractField(Collection<T> entities, Function<T, R> extractor) {
        return entities.stream()
                .map(extractor)
                .collect(Collectors.toList());
    }

    public static <T, R> Set<R> extractFieldAsSet(Collection<T> entities, Function<T, R> extractor) {
        return entities.stream()
                .map(extractor)
                .collect(Collectors.toSet());
    }
}

