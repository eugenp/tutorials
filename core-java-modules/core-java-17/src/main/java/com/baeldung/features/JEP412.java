package com.baeldung.features;

import jdk.incubator.foreign.CLinker;
import jdk.incubator.foreign.FunctionDescriptor;
import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.SymbolLookup;

import java.io.IOException;
import java.lang.invoke.MethodType;

import static jdk.incubator.foreign.ResourceScope.newImplicitScope;

public class JEP412 {

    private static final SymbolLookup libLookup;

    static {
        var resource = JEP412.class.getResource("/compile_c.sh");
        try {
            var process = new ProcessBuilder("sh", resource.getPath()).start();
            while (process.isAlive()) {}
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        var path = JEP412.class.getResource("/print_name.so").getPath();
        System.load(path);
        libLookup = SymbolLookup.loaderLookup();
    }

    public String getPrintNameFormat(String name){

        var printMethod = libLookup.lookup("printName");

        if (printMethod.isPresent()) {
            var methodReference = CLinker.getInstance()
                .downcallHandle(
                    printMethod.get(),
                    MethodType.methodType(MemoryAddress.class, MemoryAddress.class),
                    FunctionDescriptor.of(CLinker.C_POINTER, CLinker.C_POINTER)
                );

            try {
                var nativeString = CLinker.toCString(name, newImplicitScope());
                var invokeReturn = methodReference.invoke(nativeString.address());
                var memoryAddress = (MemoryAddress) invokeReturn;
                return CLinker.toJavaString(memoryAddress);
            } catch (Throwable throwable) {
                throw new RuntimeException(throwable);
            }
        }
        throw new RuntimeException("printName function not found.");
    }
}


