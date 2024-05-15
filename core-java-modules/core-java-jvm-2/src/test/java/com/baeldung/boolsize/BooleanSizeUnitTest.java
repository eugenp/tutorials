package com.baeldung.boolsize;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

public class BooleanSizeUnitTest {

    @Test
    public void printingTheVMDetails() {
        System.out.println(VM.current().details());
    }

    @Test
    public void printingTheBoolWrapper() {
        System.out.println(ClassLayout.parseClass(BooleanWrapper.class).toPrintable());
    }

    @Test
    public void printingTheBoolArray() {
        boolean[] value = new boolean[3];

        System.out.println(ClassLayout.parseInstance(value).toPrintable());
    }
}
