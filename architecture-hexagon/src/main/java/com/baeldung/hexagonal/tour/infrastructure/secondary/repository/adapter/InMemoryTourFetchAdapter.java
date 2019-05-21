package com.baeldung.hexagonal.tour.infrastructure.secondary.repository.adapter;


import com.baeldung.hexagonal.tour.domain.suggest.dto.Tour;
import com.baeldung.hexagonal.tour.domain.suggest.spi.TourFetchSpi;
import com.baeldung.hexagonal.tour.infrastructure.secondary.repository.model.TourModel;

import java.util.List;
import java.util.stream.Collectors;

public class InMemoryTourFetchAdapter implements TourFetchSpi {

    private List<TourModel> tourModels;

    public InMemoryTourFetchAdapter(List<TourModel> tourModels) {
        this.tourModels = tourModels;
    }

    @Override
    public List<Tour> fetchToursFromTo(String origin, String destination) {
        return tourModels.stream()
                .filter(t ->
                        t.getSource().equals(origin) && t.getDestination().equals(destination))
                .map(t -> new Tour(t.getName(), t.getPrice()))
                .collect(Collectors.toList());
    }
}
