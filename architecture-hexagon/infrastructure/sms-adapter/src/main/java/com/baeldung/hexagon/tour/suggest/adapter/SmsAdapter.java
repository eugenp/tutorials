package com.baeldung.hexagon.tour.suggest.adapter;

import com.baeldung.hexagon.tour.suggest.api.TourSuggestApi;

import java.util.stream.Collectors;

public class SmsAdapter {

    private TourSuggestApi tourSuggestApi;

    public SmsAdapter(TourSuggestApi tourSuggestApi) {
        this.tourSuggestApi = tourSuggestApi;
    }

    public String getSuggestions(String criteria) {
        String[] inputs = criteria.split("-");
        return tourSuggestApi.suggestBestPrice(inputs[0], inputs[1], 5)
                .stream()
                .map( t -> t.getName() + " " + t.getPrice())
                .collect(Collectors.joining("\n"));
    }
}
