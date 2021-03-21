package com.baeldung.soap.ws.client;

import java.util.Optional;

public class CountryServiceClient {

    private CountryService countryService;

    public CountryServiceClient(CountryService countryService) {
        this.countryService = countryService;
    }

    public String getCapitalByCountryName(String countryName) {
        return Optional.of(countryService.findByName(countryName))
          .map(Country::getCapital).orElseThrow(CountryNotFoundException::new);
    }

    public int getPopulationByCountryName(String countryName) {
        return Optional.of(countryService.findByName(countryName))
          .map(Country::getPopulation).orElseThrow(CountryNotFoundException::new);
    }

    public Currency getCurrencyByCountryName(String countryName) {
        return Optional.of(countryService.findByName(countryName))
          .map(Country::getCurrency).orElseThrow(CountryNotFoundException::new);
    }
}
