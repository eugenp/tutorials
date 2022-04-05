package com.baeldung.array;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.commons.lang3.ArrayUtils;

import com.google.common.collect.Lists;

public class ArrayInverter {

    public void invertUsingFor(Object[] array) {
        for (int i = 0; i < array.length / 2; i++) {
            Object temp = array[i];
            array[i] = array[array.length - 1 - i];
            array[array.length - 1 - i] = temp;
        }
    }

    public void invertUsingCollectionsReverse(Object[] array) {
        List<Object> list = Arrays.asList(array);
        Collections.reverse(list);
    }

    public Object[] invertUsingStreams(final Object[] array) {
        return IntStream.rangeClosed(1, array.length)
            .mapToObj(i -> array[array.length - i])
            .toArray();
    }

    public void invertUsingCommonsLang(Object[] array) {
        ArrayUtils.reverse(array);
    }

    public Object[] invertUsingGuava(Object[] array) {
        List<Object> list = Arrays.asList(array);
        List<Object> reverted = Lists.reverse(list);
        return reverted.toArray();
    }

}
