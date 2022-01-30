package com.baeldung.architecturehexagonal.infrastructure.repositories;

import java.util.Optional;

import org.springframework.stereotype.Component;

import com.baeldung.architecturehexagonal.domain.model.Restaurant;
import com.baeldung.architecturehexagonal.domain.ports.repositories.IRestaurantRepository;
import com.baeldung.architecturehexagonal.infrastructure.repositories.dao.RestaurantDAO;
import com.baeldung.architecturehexagonal.infrastructure.repositories.jpa.RestaurantJpaRepository;

import lombok.AllArgsConstructor;


@Component
@AllArgsConstructor
public class RestaurantRepository implements IRestaurantRepository {

    private final RestaurantJpaRepository restaurantJpaRepository;

    @Override
    public Optional<Restaurant> get(String name) {
        return restaurantJpaRepository.findById(name).map(RestaurantDAO::toRestaurant);
    }

    @Override
    public Restaurant save(Restaurant restaurant) {
        return restaurantJpaRepository.save(RestaurantDAO.fromRestaurant(restaurant)).toRestaurant();
    }
}
