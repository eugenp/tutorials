package com.baeldung.gson.primitives.models;

public class PrimitiveBundle {
    public byte byteValue;
    public short shortValue;
    public int intValue;
    public long longValue;
    public float floatValue;
    public double doubleValue;
    public boolean booleanValue;
    public char charValue;

    public String toString() {
        return "{" + "byte: " + byteValue + ", " + "short: " + shortValue + ", "
            + "int: " + intValue + ", " + "long: " + longValue + ", "
            + "float: " + floatValue + ", " + "double: " + doubleValue + ", "
            + "boolean: " + booleanValue + ", " + "char: " + charValue + "}";
    }
}
