package com.baeldung.utilities;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public final class StringUtilsLombokOne {

    public static boolean isEmpty(String source) {
        return source == null || source.length() == 0;
    }

    public static String wrap(String source, String wrapWith) {
        return isEmpty(source) ? source : wrapWith + source + wrapWith;
    }

}
