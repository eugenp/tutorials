package com.baeldung.java14.foreign.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.nio.ByteBuffer;

import org.junit.jupiter.api.Test;

public class ForeignMemoryUnitTest {

    @Test
    void whenAValueIsSet_thenAccessTheValue() {
        try (Arena memorySession = Arena.ofConfined()) {
            MemorySegment segment = memorySession.allocate(4);
            ByteBuffer buffer = segment.asByteBuffer();

            buffer.putInt(0, 42);
            int value = buffer.getInt(0);

            assertEquals(42, value);
        }
    }

    @Test
    void whenMultipleValuesAreSet_thenAccessAll() {
        try (Arena memorySession = Arena.ofConfined()) {
            MemorySegment segment = memorySession.allocate(12);
            ByteBuffer buffer = segment.asByteBuffer();

            buffer.putInt(0, 10);
            buffer.putInt(4, 20);
            buffer.putInt(8, 30);

            assertEquals(10, buffer.getInt(0));
            assertEquals(20, buffer.getInt(4));
            assertEquals(30, buffer.getInt(8));
        }
    }

    @Test
    void whenSetValuesWithMemoryLayout_thenTheyCanBeRetrieved() {
        ByteBuffer buffer = ByteBuffer.allocate(12);

        buffer.putInt(0, 1);
        buffer.putInt(4, 2);
        buffer.putInt(8, 3);

        int x = buffer.getInt(0);
        int y = buffer.getInt(4);
        int z = buffer.getInt(8);

        assertEquals(1, x);
        assertEquals(2, y);
        assertEquals(3, z);
    }

    @Test
    void whenSlicingMemorySegment_thenTheyCanBeAccessedIndividually() {
        try (Arena memorySession = Arena.ofConfined()) {
            MemorySegment segment = memorySession.allocate(12);
            ByteBuffer buffer = segment.asByteBuffer();

            buffer.putInt(0, 10);
            buffer.putInt(4, 20);
            buffer.putInt(8, 30);

            MemorySegment slice1 = segment.asSlice(0, 4);
            MemorySegment slice2 = segment.asSlice(4, 4);
            MemorySegment slice3 = segment.asSlice(8, 4);

            assertEquals(10, slice1.asByteBuffer().getInt(0));
            assertEquals(20, slice2.asByteBuffer().getInt(0));
            assertEquals(30, slice3.asByteBuffer().getInt(0));
        }
    }
}
