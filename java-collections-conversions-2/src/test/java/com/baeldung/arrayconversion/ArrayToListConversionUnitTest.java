package com.baeldung.arrayconversion;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayToListConversionUnitTest {

    @Test(expected = UnsupportedOperationException.class)
    public void givenAnArray_whenConvertingToList_returnUnmodifiableListUnitTest() {
        String[] stringArray = new String[] { "A", "B", "C", "D" };
        List<String> stringList = Arrays.asList(stringArray);
        System.out.println(stringList);
        stringList.set(0, "E");
        System.out.println(stringList);
        System.out.println(Arrays.toString(stringArray));
        stringList.add("F");
    }

    @Test
    public void givenAnArray_whenConvertingToList_returnModifiableListUnitTest() {
        String[] stringArray = new String[] { "A", "B", "C", "D" };
        List<String> stringList = new ArrayList<>(Arrays.asList(stringArray));
        System.out.println(stringList);
        stringList.set(0, "E");
        System.out.println(stringList);
        System.out.println(Arrays.toString(stringArray));
        stringList.add("F");
    }
}
