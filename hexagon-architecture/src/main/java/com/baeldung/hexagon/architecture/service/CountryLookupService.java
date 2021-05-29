package com.baeldung.hexagon.architecture.service;

import com.baeldung.hexagon.architecture.dao.CountryDaoInterface;
import com.baeldung.hexagon.architecture.entity.Country;
import com.baeldung.hexagon.architecture.entity.CountryResponse;

public class CountryLookupService {
    private CountryDaoInterface countryDaoInterface;
    private FakeMessageQueue fakeMessageQueue;

    public CountryLookupService(final CountryDaoInterface countryDaoInterface, FakeMessageQueue fakeMessageQueue) {
        this.countryDaoInterface = countryDaoInterface;
        this.fakeMessageQueue = fakeMessageQueue;
    }

    public CountryResponse getCountryByName(String name) {
        Country country = countryDaoInterface.getCountryByName(name);
        fakeMessageQueue.message(country);
        return new CountryResponse(country);
    }
}
