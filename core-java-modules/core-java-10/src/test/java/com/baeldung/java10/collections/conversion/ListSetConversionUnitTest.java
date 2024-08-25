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
    public final void givenUsingCoreJava_whenListConvertedToSet_thenCorrect() {
        final List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        final Set<Integer> targetSet = new HashSet<>(sourceList);
    }

    @Test
    public final void givenUsingCoreJava_whenUsingLoopConvertToSet_thenCorrect() {
        final List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        final Set<Integer> targetSet = new HashSet<>();
        for (final Integer element : sourceList) {
            targetSet.add(element);
        }
    }

    @Test
    public final void givenUsingCoreJava_whenUsingAddAllToSet_thenCorrect() {
        final List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        final Set<Integer> targetSet = new HashSet<>();
        targetSet.addAll(sourceList);
    }

    @Test
    public final void givenUsingCoreJava_whenUsingStreamToSet_thenCorrect() {
        final List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        final Set<Integer> targetSet = sourceList.stream().collect(Collectors.toSet());
    }

    @Test
    public final void givenUsingGuava_whenListConvertedToSet_thenCorrect() {
        final List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        final Set<Integer> targetSet = Sets.newHashSet(sourceList);
    }

    @Test
    public void givenUsingJava10_whenListConvertedToSet_thenCorrect() {
        final List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        final Set<Integer> targetSet = Set.copyOf(sourceList);
    }

    @Test
    public final void givenUsingCommonsCollections_whenListConvertedToSet_thenCorrect() {
        final List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);

        final Set<Integer> targetSet = new HashSet<>(6);
        CollectionUtils.addAll(targetSet, sourceList);
    }

    // Set -> List
    @Test
    public final void givenUsingCoreJava_whenSetConvertedToList_thenCorrect() {
        final Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        final List<Integer> targetList = new ArrayList<>(sourceSet);
    }

    @Test
    public final void givenUsingCoreJava_whenUsingLoop_thenCorrect() {
        final Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        final List<Integer> targetList = new ArrayList<>();
        for (final Integer element : sourceSet) {
            targetList.add(element);
        }
    }

    @Test
    public final void givenUsingCoreJava_whenUsingAddAll_thenCorrect() {
        final Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        final List<Integer> targetList = new ArrayList<>();
        targetList.addAll(sourceSet);
    }

    @Test
    public final void givenUsingCoreJava_whenUsingStream_thenCorrect() {
        final Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        final List<Integer> targetList = sourceSet.stream().collect(Collectors.toList());
    }

    @Test
    public void givenUsingJava10_whenSetConvertedToList_thenCorrect() {
        final Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        final List<Integer> targetList = List.copyOf(sourceSet);
    }

    @Test
    public final void givenUsingGuava_whenSetConvertedToList_thenCorrect() {
        final Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        final List<Integer> targetList = Lists.newArrayList(sourceSet);
    }

    @Test
    public final void givenUsingCommonsCollections_whenSetConvertedToList_thenCorrect() {
        final Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);

        final List<Integer> targetList = new ArrayList<>(6);
        CollectionUtils.addAll(targetList, sourceSet);
    }
}