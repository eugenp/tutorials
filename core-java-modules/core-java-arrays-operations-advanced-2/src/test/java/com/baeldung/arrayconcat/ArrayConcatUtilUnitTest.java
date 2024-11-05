package com.baeldung.arrayconcat;


import com.google.common.collect.ObjectArrays;
import com.google.common.primitives.Ints;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

public class ArrayConcatUtilUnitTest {
    private final String[] strArray1 = { "element 1", "element 2", "element 3" };
    private final String[] strArray2 = { "element 4", "element 5" };
    private final String[] expectedStringArray = { "element 1", "element 2", "element 3", "element 4", "element 5" };

    private final int[] intArray1 = { 0, 1, 2, 3 };
    private final int[] intArray2 = { 4, 5, 6, 7 };
    private final int[] expectedIntArray = { 0, 1, 2, 3, 4, 5, 6, 7 };

    @Test
    public void givenTwoStringArrays_whenConcatWithList_thenGetExpectedResult() {
        String[] result = ArrayConcatUtil.concatWithCollection(strArray1, strArray2);
        assertThat(result).isEqualTo(expectedStringArray);
    }

    @Test
    public void givenTwoStringArrays_whenConcatWithCopy_thenGetExpectedResult() {
        String[] result = ArrayConcatUtil.concatWithArrayCopy(strArray1, strArray2);
        assertThat(result).isEqualTo(expectedStringArray);
    }

    @Test
    public void givenTwoStrings_whenConcatWithCopy2_thenGetException() {
        String exMsg = "Only arrays are accepted.";
        try {
            ArrayConcatUtil.concatWithCopy2("String Nr. 1", "String Nr. 2");
            fail(String.format("IllegalArgumentException with message:'%s' should be thrown. But it didn't", exMsg));
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(exMsg);
        }
    }

    @Test
    public void givenTwoArraysInDifferentTypes_whenConcatWithCopy2_thenGetException() {
        String[] strArray = new String[] { "test 1", "test 2" };
        Integer[] integerArray = new Integer[] { 7, 11 };
        String exMsg = "Two arrays have different types.";

        try {
            ArrayConcatUtil.concatWithCopy2(strArray, integerArray);
            fail(String.format("IllegalArgumentException with message:'%s' should be thrown. But it didn't", exMsg));
        } catch (IllegalArgumentException e) {
            assertThat(e).hasMessage(exMsg);
        }
    }

    @Test
    public void givenTwoArrays_whenConcatWithCopy2_thenGetExpectedResult() {
        String[] result = ArrayConcatUtil.concatWithCopy2(strArray1, strArray2);
        assertThat(result).isEqualTo(expectedStringArray);

        int[] intResult = ArrayConcatUtil.concatWithCopy2(intArray1, intArray2);
        assertThat(intResult).isEqualTo(expectedIntArray);
    }

    @Test
    public void givenTwoStringArrays_whenConcatWithStream_thenGetExpectedResult() {
        String[] result = ArrayConcatUtil.concatWithStream(strArray1, strArray2);
        assertThat(result).isEqualTo(expectedStringArray);
    }

    @Test
    public void givenTwoIntArrays_whenConcatWithIntStream_thenGetExpectedResult() {
        int[] intResult = ArrayConcatUtil.concatIntArraysWithIntStream(intArray1, intArray2);
        assertThat(intResult).isEqualTo(expectedIntArray);
    }

    @Test
    public void givenTwoArrays_whenConcatWithCommonsLang_thenGetExpectedResult() {
        String[] result = ArrayUtils.addAll(strArray1, strArray2);
        assertThat(result).isEqualTo(expectedStringArray);

        int[] intResult = ArrayUtils.addAll(intArray1, intArray2);
        assertThat(intResult).isEqualTo(expectedIntArray);
    }

    @Test
    public void givenTwoStringArrays_whenConcatWithGuava_thenGetExpectedResult() {
        String[] result = ObjectArrays.concat(strArray1, strArray2, String.class);
        assertThat(result).isEqualTo(expectedStringArray);
    }

    @Test
    public void givenTwoIntArrays_whenConcatWithGuava_thenGetExpectedResult() {
        int[] intResult = Ints.concat(intArray1, intArray2);
        assertThat(intResult).isEqualTo(expectedIntArray);
    }
}
