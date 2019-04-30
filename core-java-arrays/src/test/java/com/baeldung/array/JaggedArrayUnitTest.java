package com.baeldung.array;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import org.junit.Test;

public class JaggedArrayUnitTest {

    private JaggedArray obj = new JaggedArray();

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
    public void givenJaggedArrayAndAnIndex_thenReturnArrayAtGivenIndex() {
        int[][] jaggedArr = { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 9 } };
        assertArrayEquals(new int[] { 1, 2 }, obj.getElementAtGivenIndex(jaggedArr, 0));
        assertArrayEquals(new int[] { 3, 4, 5 }, obj.getElementAtGivenIndex(jaggedArr, 1));
        assertArrayEquals(new int[] { 6, 7, 8, 9 }, obj.getElementAtGivenIndex(jaggedArr, 2));
    }

    @Test
    public void givenJaggedArray_whenUsingArraysAPI_thenVerifyPrintedElements() {
        int[][] jaggedArr = { { 1, 2 }, { 3, 4, 5 }, { 6, 7, 8, 9 } };
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        obj.printElements(jaggedArr);
        assertEquals("[1, 2][3, 4, 5][6, 7, 8, 9]", outContent.toString().replace("\r", "").replace("\n", ""));
        System.setOut(System.out);
    }

}
