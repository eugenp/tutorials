package com.baeldung.streams.filteronlyoneelement;

import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterUtils {

    public static <T> Optional<T> findUniqueElementMatchingPredicate_WithReduction(Stream<T> elements, Predicate<T> predicate) {
        return elements.filter(predicate)
            .collect(Collectors.reducing((a, b) -> null));
    }

    public static <T> T getUniqueElementMatchingPredicate_WithReduction(Stream<T> elements, Predicate<T> predicate) {
        return elements.filter(predicate)
            .reduce((a, b) -> {
                throw new IllegalStateException("Too many elements match the predicate");
            })
            .orElseThrow(() -> new IllegalStateException("No element matches the predicate"));
    }

    public static <T> Optional<T> findUniqueElementMatchingPredicate_WithCollectingAndThen(Stream<T> elements, Predicate<T> predicate) {
        return elements.filter(predicate)
            .collect(Collectors.collectingAndThen(Collectors.toList(), list -> Optional.ofNullable(findUniqueElement(list))));
    }

    private static <T> T findUniqueElement(List<T> elements) {
        if (elements.size() == 1) {
            return elements.get(0);
        }
        return null;
    }

    public static <T> T getUniqueElementMatchingPredicate_WithCollectingAndThen(Stream<T> elements, Predicate<T> predicate) {
        return elements.filter(predicate)
            .collect(Collectors.collectingAndThen(Collectors.toList(), FilterUtils::getUniqueElement));
    }

    private static <T> T getUniqueElement(List<T> elements) {
        if (elements.size() > 1) {
            throw new IllegalStateException("Too many elements match the predicate");
        } else if (elements.size() == 0) {
            throw new IllegalStateException("No element matches the predicate");
        }
        return elements.get(0);
    }

}
