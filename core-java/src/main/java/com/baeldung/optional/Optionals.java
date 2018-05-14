package com.baeldung.optional;

import java.util.Optional;

public class Optionals {

    public static Optional<String> getNameJava8(Optional<String> name) {
        if (name.isPresent()) {
            return name;
        }
        return Optional.empty();
    }

    public static Optional<String> getName(Optional<String> name) {
        return name.or(() -> Optional.empty());
    }

    public static com.google.common.base.Optional<String> getOptionalGuavaName(com.google.common.base.Optional<String> name) {
        return name.or(com.google.common.base.Optional.absent());
    }
}