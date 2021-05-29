package com.baeldung.hexagon.architecture.dao;

import com.baeldung.hexagon.architecture.entity.Country;

import java.util.HashMap;

public class CountryDaoMock implements CountryDaoInterface{
    private HashMap<String, Country> countries = new HashMap<>();

    public CountryDaoMock() {
        countries.put("Sweden", new Country("Sweden", 10000000));
    }

    @Override
    public Country getCountryByName(final String name) {
        return countries.get(name);
    }

}
