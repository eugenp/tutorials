package com.baeldung.java.panama;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;
import java.util.Objects;

import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;


public class HelloWorld {

    public static void main(String[] args) throws Throwable {

        String symbolName = "printf";
        String greetings = "Hello World from Project Panama Baeldung Article";

        Linker nativeLinker = Linker.nativeLinker();
        SymbolLookup stdlibLookup = nativeLinker.defaultLookup();
        SymbolLookup loaderLookup = SymbolLookup.loaderLookup();

        FunctionDescriptor functionDescriptor = FunctionDescriptor.of(
                JAVA_INT.withBitAlignment(32),
                ADDRESS.withBitAlignment(64)
        );

        MethodHandle methodHandle =
                loaderLookup.lookup(symbolName)
                        .or(() -> stdlibLookup.lookup(symbolName))
                        .map(
                                symbolAddress -> nativeLinker.downcallHandle(symbolAddress, functionDescriptor)
                        ).orElse(null);


        Objects.requireNonNull(methodHandle);

        try (MemorySession memorySession = MemorySession.openConfined()) {
            MemorySegment nativeGreetings = memorySession.allocateUtf8String(greetings + "\n");
            methodHandle.invoke(nativeGreetings);
        }
    }
}
