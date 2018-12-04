package org.baeldung.gson.primitives.models;

public class GsonBitString {
    public byte value = (byte) 1;

    public String toString() {
        return "{byte: " + value + "}";
    }
}
