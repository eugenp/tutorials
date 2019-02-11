package com.baeldung.hexagonalarchitecture.adapter;

import com.baeldung.hexagonalarchitecture.port.TubeRepositoryPort;
import com.baeldung.hexagonalarchitecture.tube.TubeTravel;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class TubeRepositoryInMemoryAdapter implements TubeRepositoryPort {

    private final Set<TubeTravel> tubeTravels = new HashSet<>();

    @Override
    public boolean save(TubeTravel tubeTravel) {
        return tubeTravels.add(tubeTravel);
    }

    @Override
    public Collection<TubeTravel> getAll() {
        return tubeTravels;
    }

    @Override
    public Collection<TubeTravel> getTubeTravelsByDepartureStation(String departureStation) {
        return tubeTravels.stream()
                .filter(tt -> tt.getDepartureStation().equals(departureStation))
                .collect(Collectors.toSet());
    }
}
