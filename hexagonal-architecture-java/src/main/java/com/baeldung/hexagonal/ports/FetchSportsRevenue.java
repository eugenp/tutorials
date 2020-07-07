package com.baeldung.hexagonal.ports;

import com.baeldung.hexagonal.model.SportRevenue;

public interface FetchSportsRevenue {
    public SportRevenue retrieveSportRevenue(String sportName);
}
