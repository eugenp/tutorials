package com.baeldung.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.models.Restaurant;
import com.baeldung.ports.RestaurantRepository;
import com.baeldung.ports.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    RestaurantRepository restaurantRepository;

    @Override
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public List<Restaurant> getRestaurantDetails() {
        return restaurantRepository.findAll();
    }

}