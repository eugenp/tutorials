package com.baeldung.java.panama.core;

import static java.lang.foreign.MemoryLayout.sequenceLayout;
import static java.lang.foreign.MemoryLayout.structLayout;
import static java.lang.foreign.ValueLayout.JAVA_DOUBLE;
import static java.lang.foreign.ValueLayout.JAVA_FLOAT;
import static java.lang.foreign.ValueLayout.PathElement;

import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.SequenceLayout;
import java.lang.invoke.VarHandle;

public class MemoryLayout {

    public static void main(String[] args) {

        GroupLayout pointLayout = structLayout(JAVA_DOUBLE.withName("x"), JAVA_DOUBLE.withName("y"));

        SequenceLayout ptsLayout = sequenceLayout(10, pointLayout);

        VarHandle xvarHandle = pointLayout.varHandle(PathElement.groupElement("x"));
        VarHandle yvarHandle = pointLayout.varHandle(PathElement.groupElement("y"));

        try (MemorySession memorySession = MemorySession.openConfined()) {

            MemorySegment pointSegment = memorySession.allocate(pointLayout);
            xvarHandle.set(pointSegment, 3d);
            yvarHandle.set(pointSegment, 4d);

            System.out.println(pointSegment.toString());

        }

    }

    static class ValueLayout {

        public static void main(String[] args) {

            try (MemorySession memorySession = MemorySession.openConfined()) {
                int byteSize = 5;
                int index = 3;
                float value = 6;
                MemorySegment segment = MemorySegment.allocateNative(byteSize, memorySession);
                segment.setAtIndex(JAVA_FLOAT, index, value);
                float result = segment.getAtIndex(JAVA_FLOAT, index);
                System.out.println("Float value is:" + result);
            }
        }
    }
}
