package com.baeldung.hexagonal.tour.domain.suggest.imp;


import com.baeldung.hexagonal.tour.domain.suggest.api.TourSuggestApi;
import com.baeldung.hexagonal.tour.domain.suggest.dto.Tour;
import com.baeldung.hexagonal.tour.domain.suggest.spi.TourFetchSpi;

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
