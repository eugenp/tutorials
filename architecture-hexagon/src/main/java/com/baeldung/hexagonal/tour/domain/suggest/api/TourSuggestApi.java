package com.baeldung.hexagonal.tour.domain.suggest.api;


import com.baeldung.hexagonal.tour.domain.suggest.dto.Tour;

import java.util.List;

public interface TourSuggestApi {
    List<Tour> suggestBestPrice(String origin, String destination, int count);
}
