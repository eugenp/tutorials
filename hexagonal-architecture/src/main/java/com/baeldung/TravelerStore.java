package com.baeldung;

import java.util.ArrayList;
import java.util.List;

public class TravelerStore {

    private List<Traveler> travelers = new ArrayList<>();

    public void addTraveler(Traveler traveler) {
        travelers.add(traveler);
    }

    public boolean storeHasTraveler(Traveler traveler) {
        return travelers.contains(traveler);
    }
}
