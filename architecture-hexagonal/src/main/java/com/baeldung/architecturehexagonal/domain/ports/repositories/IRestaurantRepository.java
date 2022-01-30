package com.baeldung.architecturehexagonal.domain.ports.repositories;
import java.util.Optional;

import com.baeldung.architecturehexagonal.domain.model.Restaurant;

public interface IRestaurantRepository {
    Optional<Restaurant> get(String name);
    Restaurant save(Restaurant restaurant);
}
