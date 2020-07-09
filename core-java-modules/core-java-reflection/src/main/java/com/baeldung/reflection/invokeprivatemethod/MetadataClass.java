package com.baeldung.reflection.invokeprivatemethod;

public class MetadataClass {

    private final Class<?> type;

    public MetadataClass(Class<?> type) {
        this.type = type;
    }

    @SuppressWarnings("unused")
    private String getSimpleName() {
        return type.getSimpleName();
    }
}