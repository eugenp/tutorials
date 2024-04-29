package com.baeldung.assertj.exceptions;

import java.util.Arrays;
import java.util.List;

public final class CityUtils {

    private static final List<String> CITIES = Arrays.asList("Tamassint", "London", "Madrid", "New york");

    public static String search(String searchedCity) {
        return CITIES.stream()
            .filter(searchedCity::equals)
            .findFirst()
            .orElseThrow(() -> new CityNotFoundException(searchedCity, "The specified city is not found"));
    }

}
