package com.baeldung.java10.collections.conversion;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ListSetConversionUnitTest {
    // List -> Set
    @Test
    public void givenUsingCoreJava_whenListConvertedToSet_thenCorrect() {
        List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        Set<Integer> targetSet = new HashSet<>(sourceList);
    }

    @Test
    public void givenUsingCoreJava_whenUsingLoopConvertToSet_thenCorrect() {
        List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        Set<Integer> targetSet = new HashSet<>();
        for (Integer element : sourceList) {
            targetSet.add(element);
        }
    }

    @Test
    public void givenUsingCoreJava_whenUsingAddAllToSet_thenCorrect() {
        List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        Set<Integer> targetSet = new HashSet<>();
        targetSet.addAll(sourceList);
    }

    @Test
    public void givenUsingCoreJava_whenUsingStreamToSet_thenCorrect() {
        List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        Set<Integer> targetSet = sourceList.stream().collect(Collectors.toSet());
    }

    @Test
    public void givenUsingGuava_whenListConvertedToSet_thenCorrect() {
        List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        Set<Integer> targetSet = Sets.newHashSet(sourceList);
    }

    @Test
    public void givenUsingJava10_whenListConvertedToSet_thenCorrect() {
        List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        Set<Integer> targetSet = Set.copyOf(sourceList);
    }

    @Test
    public void givenUsingCommonsCollections_whenListConvertedToSet_thenCorrect() {
        List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);

        Set<Integer> targetSet = new HashSet<>(6);
        CollectionUtils.addAll(targetSet, sourceList);
    }

    // Set -> List
    @Test
    public void givenUsingCoreJava_whenSetConvertedToList_thenCorrect() {
        Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        List<Integer> targetList = new ArrayList<>(sourceSet);
    }

    @Test
    public void givenUsingCoreJava_whenUsingLoop_thenCorrect() {
        Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        List<Integer> targetList = new ArrayList<>();
        for (Integer element : sourceSet) {
            targetList.add(element);
        }
    }

    @Test
    public void givenUsingCoreJava_whenUsingAddAll_thenCorrect() {
        Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        List<Integer> targetList = new ArrayList<>();
        targetList.addAll(sourceSet);
    }

    @Test
    public void givenUsingCoreJava_whenUsingStream_thenCorrect() {
        Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        List<Integer> targetList = sourceSet.stream().collect(Collectors.toList());
    }

    @Test
    public void givenUsingJava10_whenSetConvertedToList_thenCorrect() {
        Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        List<Integer> targetList = List.copyOf(sourceSet);
    }

    @Test
    public void givenUsingGuava_whenSetConvertedToList_thenCorrect() {
        Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        List<Integer> targetList = Lists.newArrayList(sourceSet);
    }

    @Test
    public void givenUsingCommonsCollections_whenSetConvertedToList_thenCorrect() {
        Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);

        List<Integer> targetList = new ArrayList<>(6);
        CollectionUtils.addAll(targetList, sourceSet);
    }
}