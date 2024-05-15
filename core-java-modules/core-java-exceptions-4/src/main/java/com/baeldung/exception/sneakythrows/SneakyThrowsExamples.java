package com.baeldung.exception.sneakythrows;

import lombok.SneakyThrows;

import java.io.IOException;

public class SneakyThrowsExamples {

    public static <E extends Throwable> void sneakyThrow(Throwable e) throws E {
        throw (E) e;
    }

    public static void throwSneakyIOException() {
        sneakyThrow(new IOException("sneaky"));
    }

    @SneakyThrows
    public static void throwSneakyIOExceptionUsingLombok() {
        throw new IOException("lombok sneaky");
    }

}
