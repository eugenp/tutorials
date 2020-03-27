package com.baeldung.architecturalpattern.adapters;

import java.util.HashMap;
import java.util.Map;

import com.baeldung.architecturalpattern.dao.Covid;
import com.baeldung.architecturalpattern.ports.CovidRepo;

public class CovidRepoImpl implements CovidRepo {

    private Map<String, Covid> covidRepo = new HashMap<String, Covid>();

    @Override
    public void updateStatus(String countryName, int activeCase, int recoveredCase, int fatalCase) {
        covidRepo.put(countryName, new Covid(countryName, activeCase, recoveredCase, fatalCase));
    }

    @Override
    public Covid getStatus(String countryName) {
        return covidRepo.get(countryName);
    }

}
