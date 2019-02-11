package com.baeldung.hexagonalarchitecture.port;

import com.baeldung.hexagonalarchitecture.tube.TubeTravel;

import java.util.Collection;

public interface TubeRepositoryPort {

    boolean save(TubeTravel tubeTravel);
    Collection<TubeTravel> getAll();
    Collection<TubeTravel> getTubeTravelsByDepartureStation(String departureStation);

}
