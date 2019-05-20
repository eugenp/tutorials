package com.baeldung.hexagon.tour.suggest.adapter;

import com.baeldung.hexagon.tour.suggest.dto.Tour;
import com.baeldung.hexagon.tour.suggest.model.TourModel;
import com.baeldung.hexagon.tour.suggest.spi.TourFetchSpi;

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
