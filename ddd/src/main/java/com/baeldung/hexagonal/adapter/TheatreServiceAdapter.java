package com.baeldung.hexagonal.adapter;

import com.baeldung.hexagonal.external.service.TheatreService;
import com.baeldung.hexagonal.port.TheatreServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

import static com.baeldung.hexagonal.external.service.TheatreService.Reservation;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@Component
public class TheatreServiceAdapter implements TheatreServicePort {

    @Autowired
    private TheatreService theatreService;

    public Optional<String> reserveSeats(String movieShowId, Set<String> seats) {
        ResponseEntity<Reservation> response = theatreService.postReservation(movieShowId, seats);
        return response.getStatusCode() == CREATED ? Optional.of(response.getBody().getId()): Optional.empty();
    }

    public boolean releaseSeats(String resrevationId) {
        return theatreService.deleteReservation(resrevationId).getStatusCode() == NO_CONTENT;
    }
}
