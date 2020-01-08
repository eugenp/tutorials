package com.baeldung.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.baeldung.models.Restaurant;
import com.baeldung.ports.RestaurantRepository;
import com.baeldung.ports.RestaurantService;

public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.saveRestaurant(restaurant);
    }

    @Override
    public List<Restaurant> getRestaurantDetails() {
        return restaurantRepository.findRestaurants();
    }

}