package com.baeldung.architecturalpattern.application;

import com.baeldung.architecturalpattern.adapters.CovidRepoImpl;
import com.baeldung.architecturalpattern.adapters.CovidUIServiceImpl;
import com.baeldung.architecturalpattern.dao.Covid;
import com.baeldung.architecturalpattern.hexagon.CovidEngine;
import com.baeldung.architecturalpattern.hexagon.CovidEngineImpl;
import com.baeldung.architecturalpattern.ports.CovidRepo;
import com.baeldung.architecturalpattern.ports.CovidUIService;

public class CovidTracker {

    private static CovidUIService trackerApplication;

    private static CovidRepo repo;

    private static CovidEngine engine;

    public static void feedCountryData(String countryName, int activeNoOfCases, int recoveredCases, int fatalCases) {
        trackerApplication.updateStatus("India", 20, 10, 5);
        trackerApplication.updateStatus("Germany", 50, 25, 10);
        trackerApplication.updateStatus("Italy", 100, 50, 20);
    }

    public static Covid getCountryData(String countryName) {
        return trackerApplication.getStatus(countryName);
    }

    public static void main(String[] args) {

        repo = new CovidRepoImpl();

        engine = new CovidEngineImpl(repo);

        trackerApplication = new CovidUIServiceImpl(engine);

        feedCountryData("India", 20, 10, 5);
        feedCountryData("Germany", 50, 25, 10);
        feedCountryData("Italy", 100, 50, 20);

        // This object holds the information for the Country India
        Covid indiaStatus = getCountryData("Germany");
        System.out.println(indiaStatus.getCountryName() + ":" + indiaStatus.getTotalCases());

    }
}
