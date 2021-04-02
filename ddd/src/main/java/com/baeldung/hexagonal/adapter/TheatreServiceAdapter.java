package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.external.service.TheatreService;
import com.baeldung.hexagonal.port.TheatreServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class TheatreServiceAdapter implements TheatreServicePort {

    @Autowired
    private TheatreService theatreService;

    public Optional<String> reserveSeats(String theatreId, String movieShowId, Set<String> seats) {
        ResponseEntity<TheatreService.Reservation> response = theatreService.postReservation(theatreId, movieShowId, seats);
        if (response.getStatusCode() == HttpStatus.CREATED) {
            return Optional.of(response.getBody().getId());
        }
        return Optional.empty();
    }

    public boolean releaseSeats(String resrevationId) {
        ResponseEntity<?> response = theatreService.deleteReservation(resrevationId);
        return response.getStatusCode() == HttpStatus.NO_CONTENT;
    }
}
