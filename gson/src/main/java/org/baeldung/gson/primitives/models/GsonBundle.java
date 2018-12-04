package org.baeldung.gson.primitives.models;

public class GsonBundle {
    public byte byteValue = (byte) 0x00001111;
    public short shortValue = (short) 3;
    public int intValue = 3;
    public long longValue = 3;
    public float floatValue = 3.5f;
    public double doubleValue = 3.5;
    public boolean booleanValue = true;
    public char charValue = 'a';

    public String toString() {
        return "{" + "byte: " + byteValue + ", " + "short: " + shortValue + ", "
            + "int: " + intValue + ", " + "long: " + longValue + ", " + "float: "
            + floatValue + ", " + "double: " + doubleValue + ", " + "boolean: "
            + booleanValue + ", " + "char: " + charValue + "}";
    }
}
