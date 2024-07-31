package com.baeldung.singletonbean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.ejb.ConcurrencyManagement;
import javax.ejb.ConcurrencyManagementType;
import javax.ejb.Singleton;
import javax.ejb.Startup;

@Singleton
@Startup
@ConcurrencyManagement(ConcurrencyManagementType.BEAN)
public class CountryStateBeanManagedBean implements CountryState {

    private final Map<String, List<String>> countryStatesMap = new HashMap<String, List<String>>();

    @PostConstruct
    public void initialize() {

        List<String> states = new ArrayList<String>();
        states.add("Texas");
        states.add("Alabama");
        states.add("Alaska");
        states.add("Arizona");
        states.add("Arkansas");

        countryStatesMap.put("UnitedStates", states);
    }

    public List<String> getStates(String country) {
        return countryStatesMap.get(country);
    }

    public synchronized void setStates(String country, List<String> states) {
        countryStatesMap.put(country, states);
    }
}
