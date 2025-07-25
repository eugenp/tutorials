package com.baeldung.context;

public class MappingContext {

    public String normalizeName(String name) {
        return name == null ? null : name.trim()
            .toUpperCase();
    }
}
