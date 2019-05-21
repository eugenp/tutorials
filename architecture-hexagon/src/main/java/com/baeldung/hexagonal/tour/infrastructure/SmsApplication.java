package com.baeldung.hexagonal.tour.infrastructure;


import com.baeldung.hexagonal.tour.domain.suggest.api.TourSuggestApi;
import com.baeldung.hexagonal.tour.domain.suggest.imp.TourSuggestService;
import com.baeldung.hexagonal.tour.infrastructure.primary.sms.adapter.SmsAdapter;
import com.baeldung.hexagonal.tour.infrastructure.secondary.repository.adapter.InMemoryTourFetchAdapter;
import com.baeldung.hexagonal.tour.infrastructure.secondary.repository.model.TourModel;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class SmsApplication {

    private SmsAdapter smsAdapter;

    public SmsApplication(SmsAdapter restAdapter) {
        this.smsAdapter = restAdapter;
    }

    public void onReceive(String mobile, String sms) {
        String result = smsAdapter.getSuggestions(sms);
        //send result to mobile
    }

    public static void main(String[] args) throws Exception {
        List<TourModel> tours = Files.readAllLines(
                    Paths.get(SmsApplication.class.getResource("/tours.csv").toURI()))
                .stream()
                .map( line -> line.split(","))
                .map( data -> new TourModel(data[0], data[1], data[2], Integer.parseInt(data[3])))
                .collect(Collectors.toList());
        TourSuggestApi suggestApi = new TourSuggestService(new InMemoryTourFetchAdapter(tours));
        SmsAdapter smsAdapter = new SmsAdapter(suggestApi);
        new SmsApplication(smsAdapter).onReceive("", "NEW YORK-LONDON");
    }
}
