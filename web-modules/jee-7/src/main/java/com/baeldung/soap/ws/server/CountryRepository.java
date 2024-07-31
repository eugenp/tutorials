package com.baeldung.soap.ws.server;

import java.util.HashMap;
import java.util.Map;

public class CountryRepository {

    private static final Map<String, Country> countries = new HashMap<>();

    {
        initData();
    }

    private final static void initData() {
        Country usa = new Country();
        usa.setName("USA");
        usa.setCapital("Washington D.C.");
        usa.setCurrency(Currency.USD);
        usa.setPopulation(323947000);

        countries.put(usa.getName(), usa);

        Country india = new Country();
        india.setName("India");
        india.setCapital("New Delhi");
        india.setCurrency(Currency.INR);
        india.setPopulation(1295210000);

        countries.put(india.getName(), india);

        Country france = new Country();
        france.setName("France");
        france.setCapital("Paris");
        france.setCurrency(Currency.EUR);
        france.setPopulation(66710000);

        countries.put(france.getName(), france);
    }

    public Country findCountry(String name) {
        return countries.get(name);
    }
}
