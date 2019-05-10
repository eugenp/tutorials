package com.baeldung.hexagonal.datafeed;

import com.baeldung.hexagonal.WeatherAlert;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class HttpDataFeedAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(HttpDataFeedAdapter.class);

    private DataFeeder dataFeeder;

    public HttpDataFeedAdapter(DataFeeder dataFeeder) {
        this.dataFeeder = dataFeeder;
    }

    public void doWeatherAlert(HttpServletRequest request) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        WeatherAlert weatherAlert = mapper.readValue(request.getInputStream(), WeatherAlert.class);
        LOG.info("Sending data feed weather alert received via HTTP [{}]", weatherAlert);
        this.dataFeeder.trigger(weatherAlert);
    }
}
