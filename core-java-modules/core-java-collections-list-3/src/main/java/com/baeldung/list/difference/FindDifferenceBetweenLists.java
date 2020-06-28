package com.baeldung.list.difference;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class FindDifferenceBetweenLists {

    public static <T> List<T> differenceUsingPlainJavaCollections(List<T> listOne, List<T> listTwo) {
        List<T> differences = differenceUsingCollections(listOne, listTwo);
        differences.addAll(differenceUsingCollections(listTwo, listOne));
        return differences;
    }

    private static <T> List<T> differenceUsingCollections(List<T> listOne, List<T> listTwo) {
        Set<T> differences = new HashSet<>(listOne);
        differences.removeAll(listTwo);
        return new ArrayList<>(differences);
    }

    public static <T> List<T> differenceUsingPlainJavaStream(List<T> listOne, List<T> listTwo) {
        List<T> differences = differenceUsingStream(listOne, listTwo);
        differences.addAll(differenceUsingStream(listTwo, listOne));
        return differences;
    }

    private static <T> List<T> differenceUsingStream(List<T> listOne, List<T> listTwo) {
        return listOne.stream()
                .filter(element -> !listTwo.contains(element))
                .distinct()
                .collect(Collectors.toList());
    }

    public static <T> List<T> differenceUsingApacheCommonsCollections(List<T> listOne, List<T> listTwo) {
        return new ArrayList<>(new HashSet<>(CollectionUtils.disjunction(listOne, listTwo)));
    }

    public static <T> List<T> differenceUsingGoogleGuava(List<T> listOne, List<T> listTwo) {
        List<T> differences = new ArrayList<>(Sets.difference(Sets.newHashSet(listOne), Sets.newHashSet(listTwo)));
        differences.addAll(new ArrayList<>(Sets.difference(Sets.newHashSet(listTwo), Sets.newHashSet(listOne))));
        return differences;
    }

}
