package com.baeldung.hexagon.tour.suggest.spi;

import com.baeldung.hexagon.tour.suggest.dto.Tour;

import java.util.List;

public interface TourFetchSpi {
    List<Tour> fetchToursFromTo(String origin, String destination);
}
