package com.baeldung.hexagonal.external.service;

import org.springframework.http.ResponseEntity;

import java.util.Set;

public interface TheatreService {

    ResponseEntity<Reservation> postReservation(String theatreId, String movieShowId, Set<String> seats);

    ResponseEntity<?> deleteReservation(String reservationId);

    class Reservation {
        private final String id;

        public Reservation(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }
    }
}
