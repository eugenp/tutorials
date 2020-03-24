package com.baeldung;

public final class StringUtils {

    public static Double convertToDouble(String str) {
        if (str == null) {
            return null;
        }
        return Double.valueOf(str);
    }
}
