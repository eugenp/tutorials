package com.baeldung.suppressed;

public class ExceptionalResource implements AutoCloseable {
    
    public void processSomething() {
        throw new IllegalArgumentException("Thrown from processSomething()");
    }

    @Override
    public void close() throws Exception {
        throw new NullPointerException("Thrown from close()");
    }
}
