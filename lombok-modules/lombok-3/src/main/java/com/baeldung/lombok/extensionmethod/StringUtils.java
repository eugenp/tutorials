package com.baeldung.lombok.extensionmethod;

public class StringUtils {

    public static String reverse(String str) {
        return new StringBuilder(str).reverse()
            .toString();
    }
}
