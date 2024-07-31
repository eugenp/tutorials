package com.baeldung.list.listvsarraylist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ArrayListDemo {

    private ArrayList<Passenger> passengers = new ArrayList<>(20);
//    private LinkedList<Passenger> passengers = new LinkedList<>(); // compile time error

    public ArrayList<Passenger> addPassenger(Passenger passenger) {
        passengers.add(passenger);
        return passengers;
    }
    
    public ArrayList<Passenger> removePassenger(Passenger passenger) {
        passengers.remove(passenger);
        return passengers;
    }

    public ArrayList<Passenger> getPassengersBySource(String source) {
        return new ArrayList<Passenger>(passengers.stream()
            .filter(it -> it.getSource().equals(source))
            .collect(Collectors.toList()));
    }
    
    public ArrayList<Passenger> getPassengersByDestination(String destination) {
        return new ArrayList<Passenger> (passengers.stream()
            .filter(it -> it.getDestination().equals(destination))
            .collect(Collectors.toList()));
    }
    
    public long getKidsCount(ArrayList<Passenger> passengerList) {
        return passengerList.stream()
            .filter(it -> (it.getAge() <= 10))
            .count();
    }

    public ArrayList<Passenger> getFinalPassengersList() {
        return new ArrayList<Passenger> (Collections.unmodifiableList(passengers));
    }

    public ArrayList<String> getServicedCountries() {
        return new ArrayList<String> (Stream.of(Locale.getISOCountries())
            .collect(Collectors.toList()));
    }

}
