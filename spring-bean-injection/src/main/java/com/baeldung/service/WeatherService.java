package com.baeldung.service;

public interface WeatherService {

    public int getTemprature(String location);

    public int getHumidity(String location);

    public boolean willItRainToday(String location);
}