package com.baeldung.arrayconversion;

import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertArrayEquals;

public class ArrayToListConversionUnitTest {

    @Test(expected = UnsupportedOperationException.class)
    public void givenAnArray_whenConvertingToList_returnUnmodifiableListUnitTest() {
        String[] stringArray = new String[] { "A", "B", "C", "D" };
        List<String> stringList = Arrays.asList(stringArray);
        stringList.set(0, "E");
        assertThat(stringList, CoreMatchers.hasItems("E", "B", "C", "D"));
        assertArrayEquals(stringArray, new String[] { "E", "B", "C", "D" });
        stringList.add("F");
    }

    @Test
    public void givenAnArray_whenConvertingToList_returnModifiableListUnitTest() {
        String[] stringArray = new String[] { "A", "B", "C", "D" };
        List<String> stringList = new ArrayList<>(Arrays.asList(stringArray));
        stringList.set(0, "E");
        assertThat(stringList, CoreMatchers.hasItems("E", "B", "C", "D"));
        assertArrayEquals(stringArray, new String[] { "A", "B", "C", "D" });
        stringList.add("F");
        assertThat(stringList, CoreMatchers.hasItems("E", "B", "C", "D", "F"));
    }
}
