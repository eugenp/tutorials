package com.baeldung.nullchecking;

import java.util.Objects;
import java.util.stream.Stream;

public class Car {

    Integer power;

    Integer year;

    public boolean allNull() {

        if (power != null) {
            return false;
        }

        if (year != null) {
            return false;
        }

        return true;
    }

    public boolean allNullV2() {

        return Stream.of(power, year)
                .allMatch(Objects::isNull);
    }
}
