package com.baeldung.architecturalpattern.application;

import com.baeldung.architecturalpattern.adapters.CovidServiceImpl;
import com.baeldung.architecturalpattern.dao.Covid;
import com.baeldung.architecturalpattern.ports.CovidService;

public class CovidTracker {

    static CovidService trackerApplication = new CovidServiceImpl();

    public static void feedCountryData(String countryName, int activeNoOfCases, int recoveredCases, int fatalCases) {
        trackerApplication.updateStatus("India", 20, 10, 5);
        trackerApplication.updateStatus("Germany", 50, 25, 10);
        trackerApplication.updateStatus("Italy", 100, 50, 20);
    }

    public static Covid getCountryData(String countryName) {
        return trackerApplication.getStatus("India");
    }

    public static void main(String[] args) {

        feedCountryData("India", 20, 10, 5);
        feedCountryData("Germany", 50, 25, 10);
        feedCountryData("Italy", 100, 50, 20);
        // This object holds the information for the Country India
        Covid indiaStatus = getCountryData("India");
        System.out.println(indiaStatus.getCountryName() + ":" + indiaStatus.getTotalCases());
        
    }
}
