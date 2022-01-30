package com.baeldung.architecturehexagonal.domain.usecases;

import java.util.Set;

import com.baeldung.architecturehexagonal.domain.model.Reservation;
import com.baeldung.architecturehexagonal.domain.model.Restaurant;
import com.baeldung.architecturehexagonal.domain.ports.errors.RestaurantNotFound;
import com.baeldung.architecturehexagonal.domain.ports.repositories.IReservationRepository;
import com.baeldung.architecturehexagonal.domain.ports.repositories.IRestaurantRepository;

public class GetCurrentReservedCapacity {
    private final IReservationRepository reservationRepository;
    private final IRestaurantRepository restaurantRepository;

    public GetCurrentReservedCapacity(IReservationRepository reservationRepository, IRestaurantRepository restaurantRepository) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public Integer perform(String restaurantName) throws RestaurantNotFound {
        Restaurant restaurant = restaurantRepository.get(restaurantName).orElseThrow(RestaurantNotFound::new);
        Set<Reservation> reservations = reservationRepository.getTableReservations(restaurant.getTables());
        int reservedCapacity = reservations.stream()
            .mapToInt(r -> r.getTable()
                .getCapacity())
            .sum();
        return reservedCapacity * 100 / restaurant.getTotalCapacity();
    }
}
