package com.baeldung.hexagonal.tour.domain.suggest.spi;


import com.baeldung.hexagonal.tour.domain.suggest.dto.Tour;

import java.util.List;

public interface TourFetchSpi {
    List<Tour> fetchToursFromTo(String origin, String destination);
}
