package com.baeldung.nullchecking;

import java.util.Objects;
import java.util.stream.Stream;

public class Car {

    Engine engine;

    Integer year;

    public boolean allNull() {

        if (engine != null) {
            return false;
        }

        if (year != null) {
            return false;
        }

        return true;
    }

    public boolean allNullV2() {

        return Stream.of(engine, year)
                .allMatch(Objects::isNull);
    }
}
