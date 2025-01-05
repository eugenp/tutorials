package com.baeldung.bytetoint;

public class ByteToIntConversion {
    static int usingTypeCasting(byte b){
        int i = b;
        return i;
    }

    static int usingIntegerValueOf(byte b){
        return Integer.valueOf(b);
    }

    static int usingByteIntValue(byte b){
        Byte byteObj = Byte.valueOf(b);
        return byteObj.intValue();
    }

    static int usingMathToIntExact(byte b){
        return Math.toIntExact(b);
    }

    static int usingByteUnsignedInt(byte b){
        return Byte.toUnsignedInt(b);
    }
}
