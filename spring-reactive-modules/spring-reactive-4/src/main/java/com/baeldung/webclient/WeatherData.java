package com.baeldung.webclient;

import java.util.Objects;

public class WeatherData {

    public WeatherData() {

    }

    public WeatherData(String city, int temperature, String description) {
        this.city = city;
        this.temperature = temperature;
        this.description = description;
    }

    private String city;
    private int temperature;
    private String description;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        WeatherData that = (WeatherData) o;
        return temperature == that.temperature && Objects.equals(city, that.city) && Objects.equals(description,
          that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(city, temperature, description);
    }
}