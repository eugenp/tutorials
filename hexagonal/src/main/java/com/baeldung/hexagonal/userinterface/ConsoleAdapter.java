package com.baeldung.hexagonal.userinterface;

import com.baeldung.hexagonal.business.WeatherQuery;
import com.baeldung.hexagonal.business.WeatherStatus;
import java.util.Scanner;

public class ConsoleAdapter {

    private final WeatherQuery weatherQuery;

    public ConsoleAdapter(WeatherQuery weatherQuery) {
        this.weatherQuery = weatherQuery;
    }

    public void start() {
        System.out.println("Please enter a city");

        Scanner console = new Scanner(System.in);
        String city = console.next();

        WeatherStatus weatherStatus = weatherQuery.query(city);

        System.out.println(weatherStatus);
    }
}
