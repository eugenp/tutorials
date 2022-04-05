package com.baeldung.array;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;

public class MultiDimensionalArrayUnitTest {

    private MultiDimensionalArray obj = new MultiDimensionalArray();

    @Test
    public void whenInitializedUsingShortHandForm_thenCorrect() {
        assertArrayEquals(new int[][] { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 9 } }, obj.shortHandFormInitialization());
    }

    @Test
    public void whenInitializedWithDeclarationAndThenInitalization_thenCorrect() {
        assertArrayEquals(new int[][] { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 9 } }, obj.declarationAndThenInitialization());
    }

    @Test
    public void whenInitializedWithDeclarationAndThenInitalizationUsingUserInputs_thenCorrect() {
        InputStream is = new ByteArrayInputStream("1 2 3 4 5 6 7 8 9".getBytes());
        System.setIn(is);
        assertArrayEquals(new int[][] { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 9 } }, obj.declarationAndThenInitializationUsingUserInputs());
        System.setIn(System.in);
    }

    @Test
    public void givenMultiDimensionalArrayAndAnIndex_thenReturnArrayAtGivenIndex() {
        int[][] multiDimensionalArr = { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 9 } };
        assertArrayEquals(new int[] { 1, 2 }, obj.getElementAtGivenIndex(multiDimensionalArr, 0));
        assertArrayEquals(new int[] { 3, 4, 5 }, obj.getElementAtGivenIndex(multiDimensionalArr, 1));
        assertArrayEquals(new int[] { 6, 7, 8, 9 }, obj.getElementAtGivenIndex(multiDimensionalArr, 2));
    }

    @Test
    public void givenMultiDimensionalArray_whenUsingArraysAPI_thenVerifyPrintedElements() {
        int[][] multiDimensionalArr = { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 9 } };
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        obj.printElements(multiDimensionalArr);
        assertEquals("[1, 2][3, 4, 5][6, 7, 8, 9]", outContent.toString().replace("\r", "").replace("\n", ""));
        System.setOut(System.out);
    }

    @Test
    public void givenMultiDimensionalArray_whenUsingArraysFill_thenVerifyInitialize2DArray() {
        int[][] multiDimensionalArr = new int[3][];
        multiDimensionalArr[0] = new int[2];
        multiDimensionalArr[1] = new int[3];
        multiDimensionalArr[2] = new int[4];
        obj.initialize2DArray(multiDimensionalArr);
        assertArrayEquals(new int[][] {{7,7}, {7,7,7}, {7,7,7,7}}, multiDimensionalArr);
    }
    
    @Test
    public void givenMultiDimensionalArray_whenUsingIteration_thenVerifyFindLengthOfElements() {
        int[][] multiDimensionalArr = { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 9 } };
        assertArrayEquals(new int[]{2,3,4}, obj.findLengthOfElements(multiDimensionalArr));
    }
    
    @Test
    public void givenMultiDimensionalArray_whenUsingArraysStream_thenVerifyFindLengthOfElements() {
        Integer[][] multiDimensionalArr = { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 9 } };
        assertArrayEquals(new Integer[]{2,3,4}, obj.findLengthOfElements(multiDimensionalArr));
    }
    
    @Test
    public void givenMultiDimensionalArray_whenUsingArraysCopyOf_thenVerifyCopy2DArray() {
        int[][] multiDimensionalArr = { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 9 } };
        assertArrayEquals(multiDimensionalArr, obj.copy2DArray(multiDimensionalArr));
    }
    
    @Test
    public void givenMultiDimensionalArray_whenUsingArraysStream_thenVerifyCopy2DArray() {
        Integer[][] multiDimensionalArr = { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 9 } };
        assertArrayEquals(multiDimensionalArr, obj.copy2DArray(multiDimensionalArr));
    }
}
