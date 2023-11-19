package com.baeldung.deserializationfilters.utils;

import java.io.ObjectInputFilter;

public class FilterUtils {

    private static final String DEFAULT_PACKAGE_PATTERN = "java.base/*;!*";
    private static final String POJO_PACKAGE = "com.baeldung.deserializationfilters.pojo";

    private FilterUtils() {
    }

    private static ObjectInputFilter baseFilter(String parameter, int max) {
        return ObjectInputFilter.Config.createFilter(String.format("%s=%d;%s.**;%s", parameter, max, POJO_PACKAGE, DEFAULT_PACKAGE_PATTERN));
    }

    public static ObjectInputFilter fallbackFilter() {
        return ObjectInputFilter.Config.createFilter(String.format("%s", DEFAULT_PACKAGE_PATTERN));
    }

    public static ObjectInputFilter safeSizeFilter(int max) {
        return baseFilter("maxbytes", max);
    }

    public static ObjectInputFilter safeArrayFilter(int max) {
        return baseFilter("maxarray", max);
    }

    public static ObjectInputFilter safeDepthFilter(int max) {
        return baseFilter("maxdepth", max);
    }
}
