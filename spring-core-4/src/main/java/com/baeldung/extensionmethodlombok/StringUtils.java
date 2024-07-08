package com.baeldung.extensionmethodlombok;

public class StringUtils {

    public static String reverse(String str) {
        return new StringBuilder(str).reverse()
            .toString();
    }
}