package com.baeldung.architecturehexagonal.infrastructure.repositories.memory;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.baeldung.architecturehexagonal.domain.model.Restaurant;
import com.baeldung.architecturehexagonal.domain.ports.repositories.IRestaurantRepository;

public class RestaurantMemoryRepository implements IRestaurantRepository {

    private final Set<Restaurant> restaurants;

    @Override
    public Optional<Restaurant> get(String name) {
        return restaurants.stream().filter(restaurant -> restaurant.getName().equals(name)).findFirst();
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        restaurants.add(restaurant);
        return restaurant;
    }

    public RestaurantMemoryRepository() {
        this.restaurants = new HashSet<>();
    }
}
