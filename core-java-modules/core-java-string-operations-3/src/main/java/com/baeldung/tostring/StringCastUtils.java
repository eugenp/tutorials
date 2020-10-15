package com.baeldung.tostring;

public class StringCastUtils {
    public static String castToString(Object object) {
        if (object instanceof String) {
            return (String) object;
        }
        return null;
    }
}
