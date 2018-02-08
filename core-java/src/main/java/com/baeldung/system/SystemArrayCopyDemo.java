package com.baeldung.system;

import java.util.Arrays;

public class SystemArrayCopyDemo {
    public static void main(String[] args) {
        int[] a = {34, 22, 44, 2, 55, 3};
        int[] b = new int[a.length];

        System.out.println("array a: " + Arrays.toString(a));
        System.out.println("array b: " + Arrays.toString(b));
        System.arraycopy(a, 0, b, 0, a.length);
        System.out.println("after copying a to b, b: " + Arrays.toString(b));
    }
}
