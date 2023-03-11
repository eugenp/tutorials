package com.baeldung.java.panama.core;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;

public class Greetings {

    public static void main(String[] args) throws Throwable {

        String symbolName = "printf";
        String greeting = "Hello World from Project Panama Baeldung Article";

        Linker nativeLinker = Linker.nativeLinker();
        SymbolLookup stdlibLookup = nativeLinker.defaultLookup();
        SymbolLookup loaderLookup = SymbolLookup.loaderLookup();

        FunctionDescriptor descriptor = FunctionDescriptor.of(JAVA_INT, ADDRESS);

        MethodHandle methodHandle = loaderLookup.lookup(symbolName)
          .or(() -> stdlibLookup.lookup(symbolName))
          .map(symbolSegment -> nativeLinker.downcallHandle(symbolSegment, descriptor))
          .orElse(null);

        if (methodHandle == null) {
            throw new NoSuchMethodError("Method Handle was not found");
        }

        try (MemorySession memorySession = MemorySession.openConfined()) {
            MemorySegment greetingSegment = memorySession.allocateUtf8String(greeting);
            methodHandle.invoke(greetingSegment);
        }
    }
}
