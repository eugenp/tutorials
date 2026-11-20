package com.baeldung.tsid;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import io.hypersistence.tsid.TSID;

public class TsidUnitTest {
    private static final Instant NOW = Instant.parse("2026-02-11T12:34:56Z");

    private static final Clock CLOCK = Clock.fixed(NOW, ZoneId.of("UTC"));

    @Test
    void whenGeneratingTsids_thenUniqueValuesAreReturned() {
        Set<TSID> generated = new HashSet<>();

        for (int i = 0; i < 10; ++i) {
            generated.add(TSID.Factory.getTsid());
        }

        assertEquals(10, generated.size());
    }

    @Test
    void whenGeneratingATSID_thenTheCorrectInstantIsReturned() {
        TSID.Factory factory = TSID.Factory.builder()
            .withClock(CLOCK)
            .build();

        TSID tsid = factory.generate();

        assertEquals(NOW, tsid.getInstant());
    }

    @Test
    void whenSerializingAsString_thenTheCorrectValueIsGenerated() {
        TSID.Factory factory = TSID.Factory.builder()
            // These settings ensure the TSID generated is consistent for assertions.
            // Don't do this in real code.
            .withClock(CLOCK)
            .withNode(123)
            .withRandomFunction(() -> 1)
            .build();

        TSID tsid = factory.generate();

        String tsidString = tsid.toString();

        assertEquals("0PEWJX5G0FC0H", tsidString);

        TSID tsid2 = TSID.from(tsidString);

        assertEquals(tsid, tsid2);
    }

    @Test
    void whenSerializingAsLong_thenTheCorrectValueIsGenerated() {
        TSID.Factory factory = TSID.Factory.builder()
            // These settings ensure the TSID generated is consistent for assertions.
            // Don't do this in real code.
            .withClock(CLOCK)
            .withNode(123)
            .withRandomFunction(() -> 1)
            .build();

        TSID tsid = factory.generate();

        Long tsidLong = tsid.toLong();

        assertEquals(809402089079287825L, tsidLong);

        TSID tsid2 = TSID.from(tsidLong);

        assertEquals(tsid, tsid2);
    }
}
