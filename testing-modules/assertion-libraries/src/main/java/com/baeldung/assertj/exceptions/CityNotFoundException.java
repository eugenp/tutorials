package com.baeldung.assertj.exceptions;

public class CityNotFoundException extends RuntimeException {

    private String city;
    private String message;

    CityNotFoundException(String city, String message) {
        this.city = city;
        this.message = message;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
