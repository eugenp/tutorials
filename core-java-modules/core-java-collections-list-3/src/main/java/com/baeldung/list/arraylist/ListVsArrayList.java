package com.baeldung.list.arraylist;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListVsArrayList {

    public static void main(String[] args) {
        ArrayList<String> passengerList = new ArrayList<>();
        passengerList.add("Anna");
        passengerList.add("Binny");
        passengerList.add("Chandra");
        printNames(passengerList);
        printNamesListType(passengerList);

        // passengerList = new LinkedList<>(); // Compile time error: Reference type is ArrayList
        
        // LinkedList passengerList3 = new LinkedList<>();
        // printNames(passengerList3); // Compile time error: the function expects an ArrayList

        List<String> passengerList2 = new LinkedList<>();
        passengerList2.addAll(passengerList);
        printNamesListType(passengerList2); // Works for both ArrayList, LinkedList types 
        
        // Collections and Stream API returns the generic List type
        System.out.println("Passenger List: " + getNames(passengerList));
        System.out.println("Name: " + copyName(passengerList.get(0)));
        System.out.println("Country List: " + getCountries());

    }

    public static void printNames(ArrayList<String> collection) {
        System.out.println(collection);
    }

    public static void printNamesListType(List<String> collection) {
        System.out.println(collection);
    }
    
    public static List<String> getNames(ArrayList<String> collection) {
        return Collections.unmodifiableList(collection);
    }
    
    public static List<String> copyName(String name) {
        return Collections.singletonList(name); 
    }
    
    public static List<String> getCountries() {
        return Stream.of(Locale.getISOCountries()).collect(Collectors.toList());
    }

}
