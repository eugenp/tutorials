package com.baeldung.weather;

import java.text.MessageFormat;

class CityAndDayLength {

    private final String city;
    private final double dayLengthInHours;

    CityAndDayLength(String city, long dayLengthInSeconds) {
        this.city = city;
        this.dayLengthInHours = dayLengthInSeconds / (60.0 * 60.0);
    }

    @Override
    public String toString() {
        return MessageFormat.format("In {0} there are {1,number,#0.0} hours of light.", city, dayLengthInHours);
    }
}
