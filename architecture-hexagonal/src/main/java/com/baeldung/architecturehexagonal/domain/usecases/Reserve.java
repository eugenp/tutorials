package com.baeldung.architecturehexagonal.domain.usecases;

import java.util.Optional;

import com.baeldung.architecturehexagonal.domain.model.Reservation;
import com.baeldung.architecturehexagonal.domain.ports.errors.ReservationForCustomerAlreadyExists;
import com.baeldung.architecturehexagonal.domain.ports.errors.RestaurantOverReservationCapacity;
import com.baeldung.architecturehexagonal.domain.ports.errors.RestaurantNotFound;
import com.baeldung.architecturehexagonal.domain.ports.requests.ReservationRequest;
import com.baeldung.architecturehexagonal.domain.model.Table;
import com.baeldung.architecturehexagonal.domain.ports.errors.ReservationError;
import com.baeldung.architecturehexagonal.domain.ports.errors.ReservationSystemError;
import com.baeldung.architecturehexagonal.domain.ports.repositories.IReservationRepository;
import com.baeldung.architecturehexagonal.domain.ports.repositories.IRestaurantRepository;

public class Reserve {
    private final IReservationRepository reservationRepository;
    private final IRestaurantRepository restaurantRepository;
    private final CheckAvailability checkAvailability;

    public Reserve(IReservationRepository reservationRepository, IRestaurantRepository restaurantRepository, CheckAvailability checkAvailability) {
        this.reservationRepository = reservationRepository;
        this.restaurantRepository = restaurantRepository;
        this.checkAvailability = checkAvailability;
    }

    public Reservation perform(ReservationRequest reservationRequest) throws ReservationSystemError {
        // if another active reservation with same customer name, reservation fails
        if (reservationRepository.getCustomerReservation(reservationRequest.getCustomerName()).isPresent())
            throw new ReservationForCustomerAlreadyExists();
        // if there is no availability, reservation fails
        if (Boolean.FALSE.equals(checkAvailability.perform(reservationRequest.getRestaurantName())))
            throw new RestaurantOverReservationCapacity();
        Optional<Table> tableOptional = restaurantRepository.get(reservationRequest.getRestaurantName())
            .orElseThrow(RestaurantNotFound::new)
            .getTables()
            .stream()
            .filter(t -> t.getCapacity() >= reservationRequest.getNumberOfPersons())
            .findFirst();
        if (tableOptional.isEmpty())
            throw new ReservationError();
        Reservation reservation = new Reservation(null, reservationRequest.getCustomerName(), reservationRequest.getNumberOfPersons(), tableOptional.get());
        return reservationRepository.save(reservation);
    }
}
