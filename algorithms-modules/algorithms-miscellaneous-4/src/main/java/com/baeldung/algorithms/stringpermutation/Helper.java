package com.baeldung.algorithms.stringpermutation;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    private Helper() {
    }

    static List<Character> toCharacterList(final String string) {
        return string.chars().mapToObj(s -> ((char) s)).collect(Collectors.toList());
    }

    static String toString(Collection<Character> collection) {
        return collection.stream().map(Object::toString).collect(Collectors.joining());
    }

}
