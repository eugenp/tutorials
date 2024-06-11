package com.baeldung.javafeatures;

import static java.lang.foreign.FunctionDescriptor.*;

import java.lang.foreign.Arena;
import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.SymbolLookup;
import java.lang.foreign.ValueLayout;
import java.lang.invoke.MethodHandle;

public class MemoryAPIExample {
public long getLengthUsingNativeMethod(String string) throws Throwable {
    SymbolLookup stdlib = Linker.nativeLinker().defaultLookup();
    MethodHandle strlen =
      Linker.nativeLinker()
        .downcallHandle(
          stdlib.find("strlen").orElseThrow(),
          of(ValueLayout.JAVA_LONG, ValueLayout.ADDRESS));

    try (Arena offHeap = Arena.ofConfined()) {
        MemorySegment str = offHeap.allocateFrom(string);

        long len = (long) strlen.invoke(str);
        System.out.println("Finding String length using strlen function: " + len);
        return len;
    }
}
}
