package com.baeldung.ports;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.models.Restaurant;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

    Restaurant saveRestaurant(Restaurant restaurant);

    List<Restaurant> findRestaurants();

}