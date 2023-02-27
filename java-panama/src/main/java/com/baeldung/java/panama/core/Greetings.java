package com.baeldung.java.panama.core;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

import static java.lang.foreign.ValueLayout.*;

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
        ;

        try (MemorySession memorySession = MemorySession.openConfined()) {
            MemorySegment greetingSegment = memorySession.allocateUtf8String(greeting);
            methodHandle.invoke(greetingSegment);
        }
    }
}
