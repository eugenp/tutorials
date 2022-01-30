package com.baeldung.architecturehexagonal.application.configuration;

import org.springframework.stereotype.Component;

import com.baeldung.architecturehexagonal.domain.ports.repositories.IReservationRepository;
import com.baeldung.architecturehexagonal.domain.ports.repositories.IRestaurantRepository;

@Component
public class GetCurrentReservedCapacityComponent extends com.baeldung.architecturehexagonal.domain.usecases.GetCurrentReservedCapacity {
    public GetCurrentReservedCapacityComponent(IReservationRepository reservationRepository, IRestaurantRepository restaurantRepository) {
        super(reservationRepository, restaurantRepository);
    }
}
