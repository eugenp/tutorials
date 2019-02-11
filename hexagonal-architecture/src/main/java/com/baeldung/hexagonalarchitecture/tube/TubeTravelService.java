package com.baeldung.hexagonalarchitecture.tube;

import com.baeldung.hexagonalarchitecture.port.TubeRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
@RequiredArgsConstructor
public class TubeTravelService {

    private final TubeRepositoryPort tubeRepositoryPort;

    public void addNewTravel(TubeTravel tubeTravel) {
        validateTubeTravel(tubeTravel);
        tubeRepositoryPort.save(tubeTravel);
    }

    private static void validateTubeTravel(TubeTravel tubeTravel) {
        if (tubeTravel.getDepartureStation().equals(tubeTravel.getArrivalStation())) {
            throw new NonValidTubeTravelException("Departure station must be different to arrival station");
        }
        if (tubeTravel.getArrivalTime().toInstant().isBefore(tubeTravel.getDepartureTime().toInstant())) {
            throw new NonValidTubeTravelException("Departure time must be before to arrival time");
        }
    }

    public Collection<TubeTravel> getAllTubeTravels() {
        return tubeRepositoryPort.getAll();
    }

    public Collection<TubeTravel> getTubeTravelsByDepartureStation(String departureStation) {
        return tubeRepositoryPort.getTubeTravelsByDepartureStation(departureStation);
    }



}
