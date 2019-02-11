package com.baeldung.hexagonalarchitecture.adapter;

import com.baeldung.hexagonalarchitecture.port.TubeTravelRestPort;
import com.baeldung.hexagonalarchitecture.tube.TubeTravel;
import com.baeldung.hexagonalarchitecture.tube.TubeTravelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/tube/travel")
@RequiredArgsConstructor
public class TubeControllerAdapter implements TubeTravelRestPort {

    private final TubeTravelService tubeTravelService;

    @Override
    @PostMapping
    public ResponseEntity<Void> addTravel(@RequestBody TubeTravel tubeTravel) {
        tubeTravelService.addNewTravel(tubeTravel);
        return ResponseEntity.noContent().build();
    }

    @Override
    @GetMapping
    public Collection<TubeTravel> getAll() {
        return tubeTravelService.getAllTubeTravels();
    }

    @Override
    @GetMapping("/departureStation/{departureStation}")
    public Collection<TubeTravel> getByDepartureStation(@PathVariable("departureStation") String departureStation) {
        return tubeTravelService.getTubeTravelsByDepartureStation(departureStation);
    }
}
