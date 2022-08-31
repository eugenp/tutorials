package com.baeldung.staticmethods;

public final class CustomStringUtils {

    private CustomStringUtils() {}

    public static boolean isEmpty(CharSequence cs) { return cs == null || cs.length() == 0; }

}
