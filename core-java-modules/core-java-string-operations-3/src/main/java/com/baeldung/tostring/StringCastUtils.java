package com.baeldung.tostring;

public class StringCastUtils {
    public static String castToString(Object object) {
        if (object instanceof String) {
            return (String) object;
        }
        return null;
    }

    public static String getStringRepresentation(Object object) {
        if (object != null) {
            return object.toString();
        }
        return null;
    }
}
