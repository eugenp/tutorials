package com.baeldung.objectsize;

import java.lang.instrument.Instrumentation;

public class ObjectSizeCalculator {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long sizeOf(Object o) {
        return instrumentation.getObjectSize(o);
    }
}
