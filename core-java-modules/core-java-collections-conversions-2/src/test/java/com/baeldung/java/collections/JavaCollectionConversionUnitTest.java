package com.baeldung.java.collections;

import com.google.common.collect.Sets;
import com.google.common.primitives.Ints;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.*;

@SuppressWarnings("unused")
public class JavaCollectionConversionUnitTest {


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
        final Integer[] targetArray = sourceSet.toArray(new Integer[0]);
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
    public final void givenUsingCommonsCollections_whenSetConvertedToArrayOfPrimitives_thenCorrect() {
        final Set<Integer> sourceSet = Sets.newHashSet(0, 1, 2, 3, 4, 5);
        final Integer[] targetArray = sourceSet.toArray(new Integer[0]);
        final int[] primitiveTargetArray = ArrayUtils.toPrimitive(targetArray);
    }

}
