package com.baeldung.architecture.hexagonal.common.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CollectionUtils {

    public static <T> List<List<T>> zip(List<T> list1, List<T> list2) {
        int size = list1.size();
        List<List<T>> result = new ArrayList<>(size);
        for (int i = 0; i < size; ++i) {
            result.add(Arrays.asList(list1.get(i), list2.get(i)));
        }
        return result;
    }
}
