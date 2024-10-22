package com.baeldung.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.ArrayUtils;
public class NullArray {

    public static class Java8 {

        public static List<String> getAsList(String[] possiblyNullArray) {
            return Optional.ofNullable(possiblyNullArray)
                .map(Arrays::stream)
                .orElseGet(Stream::empty)
                .collect(Collectors.toList());
        }
    }

    public static class TernaryOperator {

        public static List<String> getAsList(String[] possiblyNullArray) {
            return possiblyNullArray == null ? new ArrayList<>() : Arrays.asList(possiblyNullArray);
        }
    }

    public static class ApacheCommons {

        public static List<String> getAsList(String[] possiblyNullArray) {
            String[] notNullArray = ArrayUtils.nullToEmpty(possiblyNullArray);
            return Arrays.asList(notNullArray);
        }
    }
}
