package com.baeldung.arrayconcat;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class CombiningByteArraysUnitTest {

    CombiningByteArrays combiningByteArrays;

    @Before
    public void init() {
        combiningByteArrays = new CombiningByteArrays();
    }

    @Test
    public void givenTwoArrays_whenCombinedUsingArraysCopy_thenArrayContainingAllElementsMustBeReturned() {
        byte[] first = {69, 121, 101, 45, 62, 118, 114};
        byte[] second = {58, 120, 100, 46, 64, 114, 103, 117};

        byte[] combined = combiningByteArrays.combineArraysUsingArrayCopy(first, second);

        byte[] expectedArray = {69, 121, 101, 45, 62, 118, 114, 58, 120, 100, 46, 64, 114, 103, 117};
        assertArrayEquals(expectedArray, combined);
    }

    @Test
    public void givenTwoArrays_whenCombinedUsingByteBuffer_thenArrayContainingAllElementsMustBeReturned() {
        byte[] first = {69, 121, 101, 45};
        byte[] second = {64, 114, 103, 117};
        byte[] third = {11, 22, 33};

        byte[] combined = combiningByteArrays.combineArraysUsingByteBuffer(first, second, third);

        byte[] expectedArray = {69, 121, 101, 45, 64, 114, 103, 117, 11, 22, 33};
        assertArrayEquals(expectedArray, combined);
    }

    @Test
    public void givenTwoArrays_whenCombinedUsingCustomMethod_thenArrayContainingAllElementsMustBeReturned() {
        byte[] first = {101, 45, 62 };
        byte[] second = {58, 120, 100, 46, 64, 114};

        byte[] combined = combiningByteArrays.combineArraysUsingCustomMethod(first, second);

        byte[] expectedArray = {101, 45, 62, 58, 120, 100, 46, 64, 114};
        assertArrayEquals(expectedArray, combined);
    }

    @Test
    public void givenTwoArrays_whenCombinedUsingGuava_thenArrayContainingAllElementsMustBeReturned() {
        byte[] first = {69, 121, 101, 118, 114};
        byte[] second = {58, 120, 114, 103, 117};
        byte[] third = {11, 22, 33};

        byte[] combined = combiningByteArrays.combineArraysUsingGuava(first, second, third);

        byte[] expectedArray = {69, 121, 101, 118, 114, 58, 120, 114, 103, 117, 11, 22, 33};
        assertArrayEquals(expectedArray, combined);
    }

    @Test
    public void givenTwoArrays_whenCombinedUsingApacheCommons_thenArrayContainingAllElementsMustBeReturned() {
        byte[] first = {45, 62, 114};
        byte[] second = {58, 120, 100};

        byte[] combined = combiningByteArrays.combineArraysUsingApacheCommons(first, second);

        byte[] expectedArray = {45, 62, 114, 58, 120, 100};
        assertArrayEquals(expectedArray, combined);
    }
}
