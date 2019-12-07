package com.baeldung.orelseoptional;

import java.util.Optional;

public class OptionalUtils {

    public static <T> Optional<T> or(Optional<T> optional, Optional<T> fallback) {
        return optional.isPresent() ? optional : fallback;
    }

//    public static Optional<String> getName(Optional<String> name) {
//        return name.or(() -> getCustomMessage());
//    }
//
//    public static com.google.common.base.Optional<String> getOptionalGuavaName(com.google.common.base.Optional<String> name) {
//        return name.or(getCustomMessageGuava());
//    }

    private static Optional<String> getCustomMessage() {
        return Optional.of("Name not provided");
    }

    private static com.google.common.base.Optional<String> getCustomMessageGuava() {
        return com.google.common.base.Optional.of("Name not provided");
    }
} 