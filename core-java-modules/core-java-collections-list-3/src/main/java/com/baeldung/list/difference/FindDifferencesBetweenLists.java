package com.baeldung.list.difference;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class FindDifferencesBetweenLists {

    public static <T> List<T> differencesUsingPlainJavaCollections(List<T> listOne, List<T> listTwo) {
        Set<T> differences = new HashSet<>(listOne);
        differences.removeAll(listTwo);
        return new ArrayList<>(differences);
    }

    public static <T> List<T> differencesUsingPlainJavaStream(List<T> listOne, List<T> listTwo) {
        return listOne.stream()
                .filter(element -> !listTwo.contains(element))
                .distinct()
                .collect(Collectors.toList());
    }

    public static <T> List<T> differencesUsingApacheCommonsCollections(List<T> listOne, List<T> listTwo) {
        return new ArrayList<>(new HashSet<>(CollectionUtils.removeAll(listOne, listTwo)));
    }

    public static <T> List<T> differencesUsingGoogleGuava(List<T> listOne, List<T> listTwo) {
        return new ArrayList<>(Sets.difference(Sets.newHashSet(listOne), Sets.newHashSet(listTwo)));
    }

}
