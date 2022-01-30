package com.baeldung.architecturehexagonal.domain.usecases;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.baeldung.architecturehexagonal.domain.model.Reservation;
import com.baeldung.architecturehexagonal.domain.model.Restaurant;
import com.baeldung.architecturehexagonal.domain.model.Table;
import com.baeldung.architecturehexagonal.domain.ports.errors.ReservationForCustomerAlreadyExists;
import com.baeldung.architecturehexagonal.domain.ports.errors.ReservationSystemError;
import com.baeldung.architecturehexagonal.domain.ports.requests.ReservationRequest;
import com.baeldung.architecturehexagonal.infrastructure.repositories.memory.ReservationMemoryRepository;
import com.baeldung.architecturehexagonal.infrastructure.repositories.memory.RestaurantMemoryRepository;

class ReserveTest {

    RestaurantMemoryRepository restaurantMemoryRepository;
    ReservationMemoryRepository reservationMemoryRepository;
    GetCurrentReservedCapacity getCurrentReservedCapacity;
    CheckAvailability checkAvailability;
    Reserve reserve;
    Reservation reservation;

    @BeforeEach
    void setUp() {
        restaurantMemoryRepository = new RestaurantMemoryRepository();
        reservationMemoryRepository = new ReservationMemoryRepository();
        getCurrentReservedCapacity = new GetCurrentReservedCapacity(reservationMemoryRepository, restaurantMemoryRepository);
        checkAvailability = new CheckAvailability(getCurrentReservedCapacity);
        reserve = new Reserve(reservationMemoryRepository, restaurantMemoryRepository, checkAvailability);
        reservation = null;
    }

    private static Restaurant existingRestaurant() {
        return Restaurant.of("existing restaurant", Set.of(Table.of(1L, 1, 10), Table.of(2L, 2, 5)));
    }

    public static String customer() {
        return "customer name";
    }

    private ReserveTest given() {
        return this;
    }

    private ReserveTest when() {
        return this;
    }

    private ReserveTest then() {
        return this;
    }

    private ReserveTest a_restaurant_exists() {
        restaurantMemoryRepository.save(existingRestaurant());
        return this;
    }

    private ReserveTest a_reservation_exists_for(String customer) throws ReservationSystemError {
        reserve.perform(new ReservationRequest(existingRestaurant().getName(), customer, 2));
        return this;
    }

    private ReserveTest a_reservation_is_requested_for_persons(Integer persons) throws ReservationSystemError {
        reservation = reserve.perform(new ReservationRequest(existingRestaurant().getName(), customer(), persons));
        return this;
    }

    private ReserveTest it_succeeds() {
        assertNotNull(reservation);
        return this;
    }

    private ReserveTest it_fails() {
        assertNull(reservation);
        return this;
    }

    @Test()
    void givenRestaurantExistsAndHasCapacityAndCustomerHasNoOtherReservation_whenReservationIsRequested_thenItSucceeds() throws ReservationSystemError {
        given()
            .a_restaurant_exists()
        .when()
            .a_reservation_is_requested_for_persons(2)
        .then()
            .it_succeeds();
    }

    @Test
    void givenCustomerHasOtherReservation_whenReservationIsRequested_thenItFails() throws ReservationSystemError {
        a_restaurant_exists();
        a_reservation_exists_for(customer());

        assertThrows(ReservationForCustomerAlreadyExists.class, () -> a_reservation_is_requested_for_persons(2));
        it_fails();
    }
}