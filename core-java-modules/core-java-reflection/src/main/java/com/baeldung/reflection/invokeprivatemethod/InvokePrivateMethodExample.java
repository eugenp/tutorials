package com.baeldung.reflection.invokeprivatemethod;

public class InvokePrivateMethodExample {

    @SuppressWarnings("unused")
    private String getSimpleName() {
        return InvokePrivateMethodExample.class.getSimpleName();
    }
}