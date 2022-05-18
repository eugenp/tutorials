package com.baeldung.list.listvsarraylist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListDemo {

    private List<Passenger> passengers = new ArrayList<>(20);
//    private List<Passenger> passengers = new LinkedList<>(); // No compile time error

    public List<Passenger> addPassenger(Passenger passenger) {
        passengers.add(passenger);
        return passengers;
    }
    
    public List<Passenger> removePassenger(Passenger passenger) {
        passengers.remove(passenger);
        return passengers;
    }

    public List<Passenger> getPassengersBySource(String source) {
        return passengers.stream()
            .filter(it -> it.getSource().equals(source))
            .collect(Collectors.toList());
    }
    
    public List<Passenger> getPassengersByDestination(String destination) {
        return passengers.stream()
            .filter(it -> it.getDestination().equals(destination))
            .collect(Collectors.toList());
    }
    
    public long getKidsCount(List<Passenger> passengerList) {
        return passengerList.stream()
            .filter(it -> (it.getAge() <= 10))
            .count();
    }

    public List<Passenger> getFinalPassengersList() {
        return Collections.unmodifiableList(passengers);
    }

    public List<String> getServicedCountries() {
        return Stream.of(Locale.getISOCountries())
            .collect(Collectors.toList());
    }

}
