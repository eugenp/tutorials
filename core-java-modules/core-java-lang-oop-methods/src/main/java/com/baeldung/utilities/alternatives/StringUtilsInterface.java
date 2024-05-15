package com.baeldung.utilities.alternatives;

public interface StringUtilsInterface {

    static boolean isEmpty(String source) {
        return source == null || source.length() == 0;
    }

    static String wrap(String source, String wrapWith) {
        return isEmpty(source) ? source : wrapWith + source + wrapWith;
    }

}
