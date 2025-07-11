package com.baeldung.java.collections;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.*;

@SuppressWarnings("unused")
public class JavaCollectionConversionUnitTest {

    @Test
    public final void givenUsingCoreJava_whenMapValuesConvertedToArray_thenCorrect() {
        final Map<Integer, String> sourceMap = createMap();

        final Collection<String> values = sourceMap.values();
        final String[] targetArray = values.toArray(new String[0]);
    }

    @Test
    public final void givenUsingCoreJava_whenMapValuesConvertedToList_thenCorrect() {
        final Map<Integer, String> sourceMap = createMap();

        final List<String> targetList = new ArrayList<>(sourceMap.values());
    }

    @Test
    public final void givenUsingGuava_whenMapValuesConvertedToList_thenCorrect() {
        final Map<Integer, String> sourceMap = createMap();

        final List<String> targetList = Lists.newArrayList(sourceMap.values());
    }

    @Test
    public final void givenUsingCoreJava_whenMapValuesConvertedToSet_thenCorrect() {
        final Map<Integer, String> sourceMap = createMap();

        final Set<String> targetSet = new HashSet<>(sourceMap.values());
    }

    // UTIL

    private final Map<Integer, String> createMap() {
        final Map<Integer, String> sourceMap = new HashMap<>(3);
        sourceMap.put(0, "zero");
        sourceMap.put(1, "one");
        sourceMap.put(2, "two");
        return sourceMap;
    }

}
