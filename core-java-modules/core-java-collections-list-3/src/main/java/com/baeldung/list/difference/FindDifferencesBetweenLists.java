package com.baeldung.list.difference;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class FindDifferencesBetweenLists {

    public static <T> List<T> differencesUsingPlainJavaCollections(List<T> listOne, List<T> listTwo) {
        List<T> differences = differencesUsingCollections(listOne, listTwo);
        differences.addAll(differencesUsingCollections(listTwo, listOne));
        return differences;
    }

    private static <T> List<T> differencesUsingCollections(List<T> listOne, List<T> listTwo) {
        Set<T> differences = new HashSet<>(listOne);
        differences.removeAll(listTwo);
        return new ArrayList<>(differences);
    }

    public static <T> List<T> differencesUsingPlainJavaStream(List<T> listOne, List<T> listTwo) {
        List<T> differences = differencesUsingStream(listOne, listTwo);
        differences.addAll(differencesUsingStream(listTwo, listOne));
        return differences;
    }

    private static <T> List<T> differencesUsingStream(List<T> listOne, List<T> listTwo) {
        return listOne.stream()
            .filter(element -> !listTwo.contains(element))
            .distinct()
            .collect(Collectors.toList());
    }

    public static <T> List<T> differencesUsingApacheCommonsCollections(List<T> listOne, List<T> listTwo) {
        return new ArrayList<>(new HashSet<>(CollectionUtils.disjunction(listOne, listTwo)));
    }

    public static <T> List<T> differencesUsingGoogleGuava(List<T> listOne, List<T> listTwo) {
        List<T> differences = new ArrayList<>(Sets.difference(Sets.newHashSet(listOne), Sets.newHashSet(listTwo)));
        differences.addAll(new ArrayList<>(Sets.difference(Sets.newHashSet(listTwo), Sets.newHashSet(listOne))));
        return differences;
    }

}
