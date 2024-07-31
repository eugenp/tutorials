package com.baeldung.set;

import java.util.Set;

public class CopySets {

    // Using Java 10
    public static <T> Set<T> copyBySetCopyOf(Set<T> original) {
        Set<T> copy = Set.copyOf(original);
        return copy;
    }

}
