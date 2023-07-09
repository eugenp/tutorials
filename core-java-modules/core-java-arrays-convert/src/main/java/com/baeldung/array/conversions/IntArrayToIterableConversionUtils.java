package com.baeldung.array.conversions;


import com.google.common.primitives.Ints;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Arrays;
import java.util.stream.Collectors;

public class IntArrayToIterableConversionUtils {

    public static Iterable<Integer> convertWithStreamToList(int[] array) {
        return Arrays.stream(array).boxed().collect(Collectors.toList());
    }

    public static Iterable<Integer> convertWithStreamIterator(int[] array) {
        return () -> Arrays.stream(array).iterator();
    }

    public static Iterable<Integer> convertWithApacheCommonsAndJavaAsList(int[] array) {
        return Arrays.asList(ArrayUtils.toObject(array));
    }

    public static Iterable<Integer> convertWithGuava(int[] array) {
        return Ints.asList(array);
    }

}
