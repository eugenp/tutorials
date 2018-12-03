package com.baeldung.gson_primitive_types.models;

public class GsonBitString {
    public byte value = (byte) 1;

    public String toString() {
        return "{byte: " + value + "}";
    }
}
