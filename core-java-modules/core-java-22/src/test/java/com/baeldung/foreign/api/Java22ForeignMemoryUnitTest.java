package com.baeldung.foreign.api;

import org.junit.jupiter.api.Test;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SequenceLayout;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.VarHandle;
import java.nio.ByteBuffer;

import static org.junit.jupiter.api.Assertions.*;

public class Java22ForeignMemoryUnitTest {

    @Test
    void whenGlobalArenaClosed_thenThrowsException() {
        Arena global = Arena.global();

        assertThrowsExactly(UnsupportedOperationException.class, () -> global.close());
    }

    @Test
    void whenSharedArenaCreated_thenAccessByMultipleThreads() {
        Arena sharedArena = Arena.ofShared();

        new Thread(() -> {
            sharedArena.allocate(20);
        });

        new Thread(() -> {
            sharedArena.allocate(20);
        }).start();
    }

    @Test
    void whenConfinedArenaCreated_thenAccessByOwnerThread() {
        Arena confiedArena = Arena.ofConfined();

        new Thread(() -> {
            confiedArena.allocate(20);
        });

        new Thread(() -> assertThrows(WrongThreadException.class, () -> confiedArena.allocate(10))).start();
    }

    @Test
    void whenNativeMemorySegmentSet_thenAccessTheValue() {
        Arena arena = Arena.ofAuto();
        MemorySegment segment = arena.allocate(20);

        ByteBuffer buffer = segment.asByteBuffer();

        buffer.putInt(0, 42);
        int value = buffer.getInt(0);

        assertEquals(42, value);
    }

    @Test
    void whenArraySegmentSet_thenAccessTheValue() {
        MemorySegment segment = MemorySegment.ofArray(new int[20]);

        segment.set(ValueLayout.JAVA_INT, 0, 42);
        int value = segment.get(ValueLayout.JAVA_INT, 0);

        assertEquals(42, value);
    }

    @Test
    void whenByteBufferMemorySegmentSet_thenAccessTheValue() {
        MemorySegment segment = MemorySegment.ofBuffer(ByteBuffer.allocate(20));

        segment.set(ValueLayout.JAVA_BOOLEAN, 0, true);
        boolean value = segment.get(ValueLayout.JAVA_BOOLEAN, 0);

        assertTrue(value);
    }

    @Test
    void whenSlicingMemorySegmentAndSet_thenAccessTheValue() {
        Arena arena = Arena.ofAuto();
        MemorySegment memorySegment;
        memorySegment = arena.allocate(20);

        MemorySegment segment1 = memorySegment.asSlice(0, 4);
        MemorySegment segment2 = memorySegment.asSlice(4, 4);
        MemorySegment segment3 = memorySegment.asSlice(8, 4);

        VarHandle intHandle = ValueLayout.JAVA_INT.varHandle();

        intHandle.set(segment1, 0, Integer.MIN_VALUE);
        intHandle.set(segment2, 0, 0);
        intHandle.set(segment3, 0, Integer.MAX_VALUE);

        assertEquals(intHandle.get(segment1, 0), Integer.MIN_VALUE);
        assertEquals(intHandle.get(segment2, 0), 0);
        assertEquals(intHandle.get(segment3, 0), Integer.MAX_VALUE);
    }

    @Test
    void whenCreatingMemoryLayout_thenNotNull() {
        int numberOfPoints = 10;
        MemoryLayout pointLayout = MemoryLayout.structLayout(ValueLayout.JAVA_INT.withName("x"),
                ValueLayout.JAVA_INT.withName("y"));
        SequenceLayout pointsLayout = MemoryLayout.sequenceLayout(numberOfPoints, pointLayout);

        assertNotNull(pointsLayout);
    }

    @Test
    void whenValueLayoutCreated_thenCheckSize() {
        ValueLayout intLayout = ValueLayout.JAVA_INT;
        ValueLayout charLayout = ValueLayout.JAVA_CHAR;

        assertEquals(intLayout.byteSize(), 4);
        assertEquals(charLayout.byteSize(), 2);
    }

    @Test
    void whenSequenceLayoutCreated_thenCheckElementCount() {
        SequenceLayout sequenceLayout = MemoryLayout.sequenceLayout(10, ValueLayout.JAVA_INT);

        assertEquals(10, sequenceLayout.elementCount());
    }

    @Test
    void whenGroupLayoutCreated_thenCheckNotNull() {
        MemoryLayout memoryLayout1 = ValueLayout.JAVA_INT;
        MemoryLayout memoryLayout2 = MemoryLayout.structLayout(ValueLayout.JAVA_LONG);
        MemoryLayout complexLayout = MemoryLayout.structLayout(memoryLayout1, MemoryLayout.paddingLayout(4), memoryLayout2);

        assertNotNull(complexLayout);
    }

    @Test
    void whenVarHandleCreatedAndSet_thenAccessTheValue() {
        int value = 10;
        MemoryLayout pointLayout = MemoryLayout.structLayout(ValueLayout.JAVA_INT.withName("x"),
                ValueLayout.JAVA_INT.withName("y"));
        VarHandle xHandle = pointLayout.varHandle(MemoryLayout.PathElement.groupElement("x"));

        Arena arena = Arena.ofAuto();
        MemorySegment segment = arena.allocate(pointLayout);

        xHandle.set(segment, 0, (int) value);
        int xValue = (int) xHandle.get(segment, 0);

        assertEquals(xValue, value);
    }

    @Test
    void whenVarHandleCreatedAndSet_thenAccessAll() {
        int numberOfPoints = 10;
        MemoryLayout pointLayout = MemoryLayout.structLayout(ValueLayout.JAVA_INT.withName("x"),
                ValueLayout.JAVA_INT.withName("y"));

        SequenceLayout pointsLayout = MemoryLayout.sequenceLayout(numberOfPoints, pointLayout);
        VarHandle xHandle = pointsLayout.varHandle(MemoryLayout.PathElement.sequenceElement(),
                MemoryLayout.PathElement.groupElement("x"));

        Arena arena = Arena.ofAuto();
        MemorySegment segment = arena.allocate(pointsLayout);

        for (int i = 0; i < numberOfPoints; i++) {
            xHandle.set(segment, 0, i, i);
        }

        for (int i = 0; i < numberOfPoints; i++) {
            assertEquals(i, xHandle.get(segment, 0, i));
        }
    }

    @Test
    void whenInvokeNativeFunction_thenCheckEqual() throws Throwable {
        Linker linker = Linker.nativeLinker();
        var symbol = linker.defaultLookup().find("strlen").orElseThrow();
        MethodHandle strlen = linker.downcallHandle(symbol, FunctionDescriptor.of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));

        Arena arena = Arena.ofAuto();
        MemorySegment str = arena.allocateFrom("Hello");

        long len = (long) strlen.invoke(str);
        assertEquals(5, len);
    }

}
