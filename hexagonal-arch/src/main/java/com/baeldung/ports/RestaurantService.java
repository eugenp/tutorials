package com.baeldung.ports;

import java.util.List;

import com.baeldung.models.Restaurant;

public interface RestaurantService {

    Restaurant saveRestaurant(Restaurant restaurant);

    List<Restaurant> getRestaurantDetails();

}