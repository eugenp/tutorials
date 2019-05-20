package com.baeldung.hexagon.tour.suggest.imp;

import com.baeldung.hexagon.tour.suggest.api.TourSuggestApi;
import com.baeldung.hexagon.tour.suggest.dto.Tour;
import com.baeldung.hexagon.tour.suggest.spi.TourFetchSpi;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class TourSuggestService implements TourSuggestApi {

    private TourFetchSpi tourFetchSpi;

    public TourSuggestService(TourFetchSpi tourFetchSpi) {
        this.tourFetchSpi = tourFetchSpi;
    }

    public List<Tour> suggestBestPrice(String origin, String destination, int count) {
        return tourFetchSpi.fetchToursFromTo(origin, destination)
                .stream()
                .sorted(Comparator.comparingInt(Tour::getPrice))
                .limit(count)
                .collect(Collectors.toList());
    }
}
