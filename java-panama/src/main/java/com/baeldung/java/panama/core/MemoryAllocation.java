package com.baeldung.java.panama.core;

import java.lang.foreign.Arena;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.ValueLayout;

public class MemoryAllocation {

    public static void main(String[] args) {

        try (Arena session = Arena.ofConfined()) {
            String[] greetingStrings = { "hello", "world", "panama", "baeldung" };
            try(Arena arena = Arena.ofAuto()) {
                MemorySegment offHeapSegment = arena.allocateArray(ValueLayout.ADDRESS, greetingStrings.length);
                for (int i = 0; i < greetingStrings.length; i++) {
                    // Allocate a string off-heap, then store a pointer to it
                    MemorySegment cString = arena.allocateUtf8String(greetingStrings[i]);
                    offHeapSegment.setAtIndex(ValueLayout.ADDRESS, i, cString);
                }
            }
        }
    }
}
