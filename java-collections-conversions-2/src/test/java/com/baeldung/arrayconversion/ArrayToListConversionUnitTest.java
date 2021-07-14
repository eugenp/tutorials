package com.baeldung.arrayconversion;

import org.assertj.core.api.ListAssert;
import org.hamcrest.CoreMatchers;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class ArrayToListConversionUnitTest {

    @Test(expected = UnsupportedOperationException.class)
    public void givenAnArray_whenConvertingToList_returnUnmodifiableListUnitTest() {
        String[] stringArray = new String[] { "A", "B", "C", "D" };
        List<String> stringList = Arrays.asList(stringArray);
        stringList.set(0, "E");
        assertThat(stringList).containsExactly("E", "B", "C", "D");
        assertThat(stringArray).containsExactly("E", "B", "C", "D");
        stringList.add("F");
    }

    @Test
    public void givenAnArray_whenConvertingToList_returnModifiableListUnitTest() {
        String[] stringArray = new String[] { "A", "B", "C", "D" };
        List<String> stringList = new ArrayList<>(Arrays.asList(stringArray));
        stringList.set(0, "E");
        assertThat(stringList).containsExactly("E", "B", "C", "D");
        assertThat(stringArray).containsExactly("A", "B", "C", "D");
        stringList.add("F");
        assertThat(stringList).containsExactly("E", "B", "C", "D", "F");
    }
}
