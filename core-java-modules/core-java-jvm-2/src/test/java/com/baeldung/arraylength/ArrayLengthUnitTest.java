package com.baeldung.arraylength;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;

public class ArrayLengthUnitTest {

    @Test
    public void printingTheArrayLength() {
        int[] ints = new int[42];
        System.out.println(ClassLayout.parseInstance(ints).toPrintable());
    }
}
