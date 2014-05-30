package org.baeldung.java.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;

@SuppressWarnings("unused")
public class JavaCollectionConversionUnitTest {

    @Test
    public final void givenUsingCoreJava_whenArrayConvertedToList_thenCorrect() {
        final Integer[] sourceArray = { 0, 1, 2, 3, 4, 5 };
        final List<Integer> targetList = Arrays.asList(sourceArray);
    }

    @Test
    public void givenUsingCoreJava_whenListConvertedToArray_thenCorrect() {
        final List<Integer> sourceList = Lists.<Integer> newArrayList(0, 1, 2, 3, 4, 5);
        final Integer[] targetArray = sourceList.toArray(new Integer[sourceList.size()]);
    }

    @Test
    public final void givenUsingGuava_whenArrayConvertedToList_thenCorrect() {
        final Integer[] sourceArray = { 0, 1, 2, 3, 4, 5 };
        final List<Integer> targetList = Lists.newArrayList(sourceArray);
    }

    @Test
    public void givenUsingGuava_whenLIistConvertedToArray_thenCorrect() {
        final List<Integer> sourceList = Lists.newArrayList(0, 1, 2, 3, 4, 5);
        final int[] targetArray = Ints.toArray(sourceList);
    }

    @Test
    public void givenUsingCommonsCollections_whenArrayConvertedToList_thenCorrect() {
        final Integer[] sourceArray = { 0, 1, 2, 3, 4, 5 };
        final List<Integer> targetList = new ArrayList<>(6);
        CollectionUtils.addAll(targetList, sourceArray);
    }

}
