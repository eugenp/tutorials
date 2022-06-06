package com.baeldung.staticmethods;

public class StringUtils {

    public static boolean isEmpty(CharSequence cs) { return cs == null || cs.length() == 0; }

}
