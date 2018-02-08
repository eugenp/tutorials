package com.baeldung.system;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;

public class SystemArrayCopyTest {

    @Test
    public void givenTwoArraysAB_whenUseArrayCopy_thenArrayCopiedFromAToBInResult() {
        int[] a = {34, 22, 44, 2, 55, 3};
        int[] b = new int[a.length];

        System.out.println("array a: " + Arrays.toString(a));
        System.out.println("array b: " + Arrays.toString(b));
        System.arraycopy(a, 0, b, 0, a.length);
        System.out.println("after copying a to b, b: " + Arrays.toString(b));
        Assert.assertArrayEquals(a, b);
    }
}
