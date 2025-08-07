package com.baeldung.instantandlong;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

public class InstantAndLong {

    public static void main(String[] args) {
        long nowLong = Instant.now().toEpochMilli();

        long someDayLong = 1_753_610_399_076L;
        Instant someDay = Instant.ofEpochMilli(someDayLong);

        long expirationPeriod = 2_592_000_000L; // 30 days in milliseconds
        Instant now = Instant.now();
        Instant expirationTime = now.plus(expirationPeriod, ChronoUnit.MILLIS);

        expirationTime = Instant.now().plusMillis(2_592_000_000L);

        Instant aDayAgo = now.minus(86_400_000L, ChronoUnit.MILLIS);

        aDayAgo = now.plus(-86_400_000L, ChronoUnit.MILLIS);
        
        expirationPeriod = 30    // number of days
                         * 24    // hours in one day
                         * 3600  // seconds in one hour
                         * 1000L;// from seconds to milliseconds

        nowLong = Instant.now().toEpochMilli();

        long expirationTimeLong = nowLong + expirationPeriod;
    }

}
