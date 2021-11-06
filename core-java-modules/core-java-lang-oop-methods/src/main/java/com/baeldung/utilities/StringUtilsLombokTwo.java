package com.baeldung.utilities;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtilsLombokTwo {

    public static boolean isEmpty(String source) {
        return source == null || source.length() == 0;
    }

    public static String wrap(String source, String wrapWith) {
        return isEmpty(source) ? source : wrapWith + source + wrapWith;
    }

}
