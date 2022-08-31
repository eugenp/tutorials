package com.baeldung.instantsource;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

public class InstantWrapper {
    Clock clock;

    private InstantWrapper() {
        this.clock = Clock.systemDefaultZone();
    }

    private InstantWrapper(ZonedDateTime zonedDateTime) {
        this.clock = Clock.fixed(zonedDateTime.toInstant(), zonedDateTime.getZone());
    }

    private InstantWrapper(LocalDateTime localDateTime) {
        this.clock = Clock.fixed(localDateTime.toInstant(ZoneOffset.UTC), ZoneOffset.UTC);
    }

    public static InstantWrapper of() {
        return new InstantWrapper();
    }

    public static InstantWrapper of(ZonedDateTime zonedDateTime) {
        return new InstantWrapper(zonedDateTime);
    }

    public static InstantWrapper of(LocalDateTime localDateTime) {
        return new InstantWrapper(localDateTime);
    }

    public Instant instant() {
        return clock.instant();
    }
}
