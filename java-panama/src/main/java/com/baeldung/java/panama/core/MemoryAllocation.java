package com.baeldung.java.panama.core;

import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.SegmentAllocator;
import java.lang.foreign.ValueLayout;

public class MemoryAllocation {

    public static void main(String[] args) throws Throwable {

        try (MemorySession session = MemorySession.openConfined()) {
            String[] greetingStrings = { "hello", "world", "panama", "baeldung" };
            SegmentAllocator allocator = SegmentAllocator.implicitAllocator();
            MemorySegment offHeapSegment = allocator.allocateArray(ValueLayout.ADDRESS, greetingStrings.length);
            for (int i = 0; i < greetingStrings.length; i++) {
                // Allocate a string off-heap, then store a pointer to it
                MemorySegment cString = allocator.allocateUtf8String(greetingStrings[i]);
                offHeapSegment.setAtIndex(ValueLayout.ADDRESS, i, cString);
            }
        }
    }
}
