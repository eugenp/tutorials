package org.baeldung.java.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;

@SuppressWarnings("unused")
public class JavaCollectionConversionUnitTest {

    // List -> array; array -> List

    @Test
    public final void givenUsingCoreJava_whenArrayConvertedToList_thenCorrect() {
        final Integer[] sourceArray = { 0, 1, 2, 3, 4, 5 };
        final List<Integer> targetList = Arrays.asList(sourceArray);
    }

    @Test
    public final void givenUsingCoreJava_whenListConvertedToArray_thenCorrect() {
        final List<Integer> sourceList = Lists.<Integer> newArrayList(0, 1, 2, 3, 4, 5);
        final Integer[] targetArray = sourceList.toArray(new Integer[sourceList.size()]);
    }

    @Test
    public final void givenUsingGuava_whenArrayConvertedToList_thenCorrect() {
        final Integer[] sourceArray = { 0, 1, 2, 3, 4, 5 };
        final List<Integer> targetList = Lists.newArrayList(sourceArray);
    }

    @Test
    public final void givenUsingGuava_whenListConvertedToArray_thenCorrect() {
        final List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        final int[] targetArray = Ints.toArray(sourceList);
    }

    @Test
    public final void givenUsingCommonsCollections_whenArrayConvertedToList_thenCorrect() {
        final Integer[] sourceArray = { 0, 1, 2, 3, 4, 5 };
        final List<Integer> targetList = new ArrayList<>(6);
        CollectionUtils.addAll(targetList, sourceArray);
    }

    // Set -> array; array -> Set

    @Test
    public final void givenUsingCoreJavaV1_whenArrayConvertedToSet_thenCorrect() {
        final Integer[] sourceArray = { 0, 1, 2, 3, 4, 5 };
        final Set<Integer> targetSet = new HashSet<Integer>(Arrays.asList(sourceArray));
    }

    @Test
    public final void givenUsingCoreJavaV2_whenArrayConvertedToSet_thenCorrect() {
        final Integer[] sourceArray = { 0, 1, 2, 3, 4, 5 };
        final Set<Integer> targetSet = new HashSet<Integer>();
        Collections.addAll(targetSet, sourceArray);
    }

    @Test
    public final void givenUsingCoreJava_whenSetConvertedToArray_thenCorrect() {
        final Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        final Integer[] targetArray = sourceSet.toArray(new Integer[sourceSet.size()]);
    }

    @Test
    public final void givenUsingGuava_whenArrayConvertedToSet_thenCorrect() {
        final Integer[] sourceArray = { 0, 1, 2, 3, 4, 5 };
        final Set<Integer> targetSet = Sets.newHashSet(sourceArray);
    }

    @Test
    public final void givenUsingGuava_whenSetConvertedToArray_thenCorrect() {
        final Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        final int[] targetArray = Ints.toArray(sourceSet);
    }

    @Test
    public final void givenUsingCommonsCollections_whenArrayConvertedToSet_thenCorrect() {
        final Integer[] sourceArray = { 0, 1, 2, 3, 4, 5 };
        final Set<Integer> targetSet = new HashSet<>(6);
        CollectionUtils.addAll(targetSet, sourceArray);
    }

    @Test
    public final void givenUsingCommonsCollections_whenSetConvertedToArray_thenCorrect() {
        final Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        final Integer[] targetArray = sourceSet.toArray(new Integer[sourceSet.size()]);
    }

    @Test
    public final void givenUsingCommonsCollections_whenSetConvertedToArrayOfPrimitives_thenCorrect() {
        final Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        final Integer[] targetArray = sourceSet.toArray(new Integer[sourceSet.size()]);
        final int[] primitiveTargetArray = ArrayUtils.toPrimitive(targetArray);
    }

}
