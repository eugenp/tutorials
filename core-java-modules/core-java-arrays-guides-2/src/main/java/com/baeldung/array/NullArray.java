package com.baeldung.array;

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
     * How to handle null arrays using Java 8.
     */
    public static class Java8 {

        public static List<String> getAsList(String[] possiblyNullArray) {
            return Optional.ofNullable(possiblyNullArray)
                .map(Arrays::stream)
                .orElseGet(Stream::empty)
                .collect(Collectors.toList());
        }
    }

    /**
     * How to handle null arrays using the ternary operator.
     */
    public static class TernaryOperator {

        public static List<String> getAsList(String[] possiblyNullArray) {
            return possiblyNullArray == null ? new ArrayList<>() : Arrays.asList(possiblyNullArray);
        }
    }

    /**
     * How to handle null arrays using Apache Commons Lang.
     */
    public static class ApacheCommons {

        public static List<String> getAsList(String[] possiblyNullArray) {
            String[] notNullArray = ArrayUtils.nullToEmpty(possiblyNullArray);
            return Arrays.asList(notNullArray);
        }
    }
}
