package com.baeldung.virtualthread.foreignfunction;

import static java.lang.foreign.ValueLayout.JAVA_INT;
import static java.lang.foreign.ValueLayout.JAVA_LONG;

import java.lang.foreign.FunctionDescriptor;
import java.lang.foreign.Linker;
import java.lang.foreign.SymbolLookup;
import java.lang.invoke.MethodHandle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ForeignFunctionClass {

    private static final Logger LOGGER = LoggerFactory.getLogger(ForeignFunctionClass.class);

    public void execute() {
        LOGGER.info("Running foreign function sleep...");

        Linker linker = Linker.nativeLinker();
        SymbolLookup stdlib = linker.defaultLookup();
        MethodHandle sleep = linker.downcallHandle(stdlib.find("sleep")
            .orElseThrow(), FunctionDescriptor.of(JAVA_INT, JAVA_LONG));

        try {
            sleep.invoke(100);
        } catch (Throwable ex) {
            System.out.println("Error in native sleep...");
            throw new RuntimeException(ex);
        }
    }
}
