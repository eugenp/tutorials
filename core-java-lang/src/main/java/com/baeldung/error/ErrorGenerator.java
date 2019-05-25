package com.baeldung.error;

public class ErrorGenerator {
    public void throwException() throws Exception {
        throw new Exception("checked");
    }

    public void throwRuntimeException() {
        throw new RuntimeException("unchecked");
    }

    public void throwError() {
        throw new Error("unchecked");
    }
}
