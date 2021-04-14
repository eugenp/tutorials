package com.baeldung.ddd.hexagonal.travel.cache;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.baeldung.ddd.hexagonal.travel.domain.Reservation;

public class ReservationCache {

    private static Map<String, Reservation> ticketCache = new ConcurrentHashMap<String, Reservation>();

    public static Reservation get(String ticketId) {
        return ticketCache.get(ticketId);
    }

    public static Collection<Reservation> getAllReservations() {
        return ticketCache.values();
    }

    public static void put(String ticketId, Reservation ticket) {
        ticketCache.put(ticketId, ticket);
    }
}
