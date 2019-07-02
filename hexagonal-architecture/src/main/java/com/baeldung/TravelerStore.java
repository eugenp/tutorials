package com.baeldung;

import java.util.ArrayList;
import java.util.List;

public class TravelerStore implements Storage {

    private List<Traveler> travelers = new ArrayList<>();

    @Override
    public void addTraveler(Traveler traveler) {
        travelers.add(traveler);
    }

    @Override
    public boolean storeHasTraveler(Traveler traveler) {
        return travelers.contains(traveler);
    }
}
