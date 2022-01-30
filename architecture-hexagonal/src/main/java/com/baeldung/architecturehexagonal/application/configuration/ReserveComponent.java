package com.baeldung.architecturehexagonal.application.configuration;

import org.springframework.stereotype.Component;

import com.baeldung.architecturehexagonal.domain.ports.repositories.IReservationRepository;
import com.baeldung.architecturehexagonal.domain.ports.repositories.IRestaurantRepository;
import com.baeldung.architecturehexagonal.domain.usecases.CheckAvailability;
import com.baeldung.architecturehexagonal.domain.usecases.Reserve;

@Component
public class ReserveComponent extends Reserve {
    public ReserveComponent(IReservationRepository reservationRepository, IRestaurantRepository restaurantRepository, CheckAvailability checkAvailability) {
        super(reservationRepository, restaurantRepository, checkAvailability);
    }
}
