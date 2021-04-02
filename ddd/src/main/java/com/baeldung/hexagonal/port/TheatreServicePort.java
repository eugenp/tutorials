package com.baeldung.hexagonal.port;

import java.util.Optional;
import java.util.Set;

public interface TheatreServicePort {
    Optional<String> reserveSeats(String theatreId, String movieShowId, Set<String> seats);
    boolean releaseSeats(String resrevationId);
}
