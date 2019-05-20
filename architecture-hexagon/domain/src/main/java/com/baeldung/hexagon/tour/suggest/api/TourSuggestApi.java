package com.baeldung.hexagon.tour.suggest.api;

import com.baeldung.hexagon.tour.suggest.dto.Tour;

import java.util.List;

public interface TourSuggestApi {
    List<Tour> suggestBestPrice(String origin, String destination, int count);
}
