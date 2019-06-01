package com.baeldung.set;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang.SerializationUtils;

import com.google.gson.Gson;

public class CopySets {

    public static <T> Set<T> copyByConstructor(Set<T> original) {
        Set<T> copy = new HashSet<>(original);
        return copy;
    }

    public static <T> Set<T> copyBySetAddAll(Set<T> original) {
        Set<T> copy = new HashSet<>();
        copy.addAll(original);
        return copy;
    }

    public static <T> Set<T> copyBySetClone(HashSet<T> original) {
        Set<T> copy = (Set<T>) original.clone();
        return copy;
    }

    public static <T> Set<T> copyByJson(Set<T> original) {
        Gson gson = new Gson();
        String jsonStr = gson.toJson(original);
        Set<T> copy = gson.fromJson(jsonStr, Set.class);

        return copy;
    }

    public static <T extends Serializable> Set<T> copyByApacheCommonsLang(Set<T> original) {
        Set<T> copy = new HashSet<>();
        for (T item : original) {
            copy.add((T) SerializationUtils.clone(item));
        }
        return copy;
    }

    public static <T> void copyByStreamsAPI(Set<T> original) {
        Set<T> copy1 = original.stream()
            .collect(Collectors.toSet());

        // Skip the first element
        Set<T> copy2 = original.stream()
            .skip(1)
            .collect(Collectors.toSet());

        // Filter by comparing the types and attributes
        Set<T> copy3 = original.stream()
            .filter(f -> f.getClass()
                .equals(Integer.class))
            .collect(Collectors.toSet());

        // Null check in case of expecting null values
        Set<T> copy4 = original.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toSet());

    }

    public static <T> Set<T> copyByJava8(Set<T> original) {
        Set<T> copy = Set.copyOf(original);
        return copy;
    }

}
