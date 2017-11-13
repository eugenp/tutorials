package com.baeldung.injection;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class RestaurantFinder {

    public List<Restaurant> find(Long userId) {
        return Arrays.asList(new Restaurant("Red Lobster"), new Restaurant("Bubba Gump"));
    }

}
