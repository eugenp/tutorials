package com.baeldung.hexagonalarchitecture.port;

import com.baeldung.hexagonalarchitecture.tube.TubeTravel;
import org.springframework.http.ResponseEntity;

import java.util.Collection;

public interface TubeTravelRestPort {

    ResponseEntity<Void> addTravel(TubeTravel tubeTravel);
    Collection<TubeTravel> getAll();
    Collection<TubeTravel> getByDepartureStation(String departureStation);
}
