package com.baeldung.hexagon.tour.suggest;

import com.baeldung.hexagon.tour.suggest.adapter.InMemoryTourFetchAdapter;
import com.baeldung.hexagon.tour.suggest.adapter.SmsAdapter;
import com.baeldung.hexagon.tour.suggest.imp.TourSuggestService;
import com.baeldung.hexagon.tour.suggest.model.TourModel;

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
        List<TourModel> inmemoryTours = Files.readAllLines(
                    Paths.get(SmsApplication.class.getResource("/tours.csv").toURI()))
                .stream()
                .map( line -> line.split(","))
                .map( data -> new TourModel(data[0], data[1], data[2], Integer.parseInt(data[3])))
                .collect(Collectors.toList());
        TourSuggestService suggestService = new TourSuggestService(new InMemoryTourFetchAdapter(inmemoryTours));
        SmsAdapter smsAdapter = new SmsAdapter(suggestService);
        new SmsApplication(smsAdapter).onReceive("", "NEW YORK-LONDON");
    }
}
