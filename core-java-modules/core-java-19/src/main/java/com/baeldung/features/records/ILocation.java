package com.baeldung.features.records;

public sealed interface ILocation permits Location {
    default String getName() {
        return switch (this) {
            case Location(var name, var ignored) -> name;
        };
    }
}