package com.baeldung.collections.mulipletypesinmap;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class InstantTypeValue implements DynamicTypeValue {
    private static DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        .withZone(ZoneId.systemDefault());
    private Instant value;

    public InstantTypeValue(Instant value) {
        this.value = value;
    }

    @Override
    public String valueDescription() {
        if (value == null) {
            return "The value is null.";
        }
        return String.format("The value is an instant: %s", FORMATTER.format(value));
    }

}
