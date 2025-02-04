package com.baeldung.nullablearraytolist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;

/**
 * This class demonstrates different ways to handle null arrays in Java.
 */
public class NullArray {

    /**
     * Returns a not-null list from a nullable array by using Java 8.
     */
    public static List<String> getAsListJava8(String[] possiblyNullArray) {
        return Optional.ofNullable(possiblyNullArray)
            .map(Arrays::stream)
            .orElseGet(Stream::empty)
            .collect(Collectors.toList());
    }

    /**
     * Returns a not-null list from a nullable array by using a ternary operator.
     */
    public static List<String> getAsListTernary(String[] possiblyNullArray) {
        return possiblyNullArray == null ? new ArrayList<>() : Arrays.asList(possiblyNullArray);
    }

    /**
     * Returns a not-null list from a nullable array by using Apache Commons Lang.
     */
    public static List<String> getAsListApacheCommons(String[] possiblyNullArray) {
        String[] notNullArray = ArrayUtils.nullToEmpty(possiblyNullArray);
        return Arrays.asList(notNullArray);
    }
}
