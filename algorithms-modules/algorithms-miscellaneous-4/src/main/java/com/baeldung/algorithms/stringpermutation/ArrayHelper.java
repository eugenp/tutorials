package com.baeldung.algorithms.stringpermutation;

import java.util.List;
import java.util.stream.Collectors;

public class ArrayHelper {

    private ArrayHelper() {
    }

    static List<Character> toCharacterList(final String string) {
        return string.chars().mapToObj(s -> ((char) s)).collect(Collectors.toList());
    }
}
