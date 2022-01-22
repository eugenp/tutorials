package com.baeldung.utilities.warning;

@SuppressWarnings("java:S1118")
public final class StringUtilsSuppressWarning {

    public static boolean isEmpty(String source) {
        return source == null || source.length() == 0;
    }

    public static String wrap(String source, String wrapWith) {
        return isEmpty(source) ? source : wrapWith + source + wrapWith;
    }

}
