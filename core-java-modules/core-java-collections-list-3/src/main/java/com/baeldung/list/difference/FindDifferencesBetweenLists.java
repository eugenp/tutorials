package com.baeldung.list.difference;

import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

public class FindDifferencesBetweenLists {

    public static <T> List<T> differencesUsingPlainJavaCollections(List<T> listOne, List<T> listTwo) {
        Set<T> differencesListOne = new HashSet<>(listOne);
        differencesListOne.removeAll(listTwo);
        Set<T> differencesListTwo = new HashSet<>(listTwo);
        differencesListTwo.removeAll(listOne);
        differencesListOne.addAll(differencesListTwo);
        return new ArrayList<>(differencesListOne);
    }

    public static <T> List<T> differencesUsingPlainJavaStream(List<T> listOne, List<T> listTwo) {
        List<T> differencesListOne = listOne.stream()
                .filter(element -> !listTwo.contains(element))
                .distinct()
                .collect(Collectors.toList());
        List<T> differencesListTwo = listTwo.stream()
                .filter(element -> !listOne.contains(element))
                .distinct()
                .collect(Collectors.toList());
        differencesListOne.addAll(differencesListTwo);
        return differencesListOne;
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
