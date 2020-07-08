package com.baeldung.hexagonal.adapters;

import java.util.Arrays;
import java.util.List;

import com.baeldung.hexagonal.model.SportRevenue;
import com.baeldung.hexagonal.ports.FetchSportsRevenue;

public class InMemorySportsDataAdapter implements FetchSportsRevenue {
    
    List<SportRevenue> data;
    
    public InMemorySportsDataAdapter() {
        data = Arrays.asList(
            new SportRevenue("Football",2200000),
            new SportRevenue("Cricket", 1700000),
            new SportRevenue("Baseball",1567000));
    }
    
    @Override
    public SportRevenue retrieveSportRevenue(String sportName) {
        return data.stream()
                 .filter(category -> sportName.equals(category.getName()))
                 .findAny().orElse(null);
    }

}
