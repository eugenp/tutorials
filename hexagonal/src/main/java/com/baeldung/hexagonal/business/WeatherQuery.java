package com.baeldung.hexagonal.business;

public interface WeatherQuery {

        WeatherStatus query(String city);
}
