package com.baeldung.memlayout;

import org.junit.Test;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import jdk.internal.vm.annotation.Contended;

public class MemoryLayoutUnitTest {

    private volatile Object consumer;

    @Test
    public void printingTheVMDetails() {
        System.out.println(VM.current().details());
    }

    @Test
    public void simpleMemoryLayout() {
        System.out.println(ClassLayout.parseClass(SimpleInt.class).toPrintable());
    }

    @Test
    public void identityHashCodeMemoryLayout() {
        SimpleInt instance = new SimpleInt();
        System.out.println(ClassLayout.parseInstance(instance).toPrintable());

        System.out.println("The identity hash code is " + System.identityHashCode(instance));
        System.out.println(ClassLayout.parseInstance(instance).toPrintable());
    }

    @Test
    public void alignmentMemoryLayout() {
        System.out.println(ClassLayout.parseClass(SimpleLong.class).toPrintable());
    }

    @Test
    public void fieldPackingMemoryLayout() {
        System.out.println(ClassLayout.parseClass(FieldsArrangement.class).toPrintable());
    }

    @Test
    public void monitorLockMemoryLayout() {
        Lock lock = new Lock();
        System.out.println(ClassLayout.parseInstance(lock).toPrintable());

        synchronized (lock) {
            System.out.println(ClassLayout.parseInstance(lock).toPrintable());
        }
    }

    @Test
    public void ageAndTenuringMemoryLayout() {
        Object instance = new Object();
        long lastAddr = VM.current().addressOf(instance);
        ClassLayout layout = ClassLayout.parseInstance(instance);

        for (int i = 0; i < 10_000; i++) {
            long currentAddr = VM.current().addressOf(instance);
            if (currentAddr != lastAddr) {
                System.out.println(layout.toPrintable());
            }

            for (int j = 0; j < 10_000; j++) {
                consumer = new Object();
            }

            lastAddr = currentAddr;
        }
    }

    @Test
    public void contendedMemoryLayout() {
        System.out.println(ClassLayout.parseClass(Isolated.class).toPrintable());
    }

    @Test
    public void arrayMemoryLayout() {
        boolean[] booleans = new boolean[3];
        System.out.println(ClassLayout.parseInstance(booleans).toPrintable());
    }

    private static class SimpleInt {
        private int state;
    }

    private static class SimpleLong {
        private long state;
    }

    private static class FieldsArrangement {
        private boolean first;
        private char second;
        private double third;
        private int fourth;
        private boolean fifth;
    }

    private static class Lock {}

    private static class Isolated {

        @Contended
        private int i;

        @Contended
        private long l;
    }
}
