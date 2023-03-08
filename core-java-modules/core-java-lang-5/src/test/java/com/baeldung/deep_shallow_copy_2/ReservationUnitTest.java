package com.baeldung.deep_shallow_copy_2;

import org.junit.jupiter.api.Assertions;

import com.baeldung.deep_shallow_copy_2.Reservation;
import java.time.Instant;
import java.util.Date;
import org.junit.jupiter.api.Test;

public class ReservationUnitTest {

    @Test
    public void givenReservation_whenDeepCopying_ThenFlyInfoIsDeeplyCopiedAsWell() {
        Reservation reservation = new Reservation();
        reservation.setReservationId(14L);
        reservation.setFlightInfo(new FlightInfo(1L, "ABC", new Date(), new Date(Instant.now().toEpochMilli() + 7200000)));

        Reservation clone = reservation.clone();

        Assertions.assertNotSame(clone.getFlightInfo(), reservation.getFlightInfo());
        Assertions.assertEquals(clone.getFlightInfo(), reservation.getFlightInfo());
    }
}