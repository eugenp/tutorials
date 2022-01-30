package com.baeldung.architecturehexagonal.domain.usecases;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.architecturehexagonal.domain.model.Reservation;
import com.baeldung.architecturehexagonal.domain.model.Restaurant;
import com.baeldung.architecturehexagonal.domain.model.Table;
import com.baeldung.architecturehexagonal.domain.ports.errors.RestaurantNotFound;
import com.baeldung.architecturehexagonal.domain.ports.repositories.IReservationRepository;
import com.baeldung.architecturehexagonal.domain.ports.repositories.IRestaurantRepository;

@ExtendWith(MockitoExtension.class)
class GetCurrentReservedCapacityTest {
    private @Mock
    IReservationRepository reservationRepository;
    private @Mock
    IRestaurantRepository restaurantRepository;

    private GetCurrentReservedCapacity getCurrentReservedCapacity;

    private static final Table TABLE_ONE = Table.of(1000L, 1, 4);
    private static final Table TABLE_TWO = Table.of(2000L, 2, 10);
    private static final Table TABLE_THREE = Table.of(3000L, 3, 6);
    private static final Restaurant EXISTING_RESTAURANT = new Restaurant("existing_restaurant", Set.of(TABLE_ONE, TABLE_TWO, TABLE_THREE));
    private static final Reservation RESERVATION = Reservation.of(1L, "me", 3, TABLE_ONE);
    private static final Integer RESERVATION_CAPACITY_OF_EXISTING_RESTAURANT = 20;

    @BeforeEach
    void setUp() {
        getCurrentReservedCapacity = new GetCurrentReservedCapacity(reservationRepository, restaurantRepository);
    }

    @Test
    void givenNonExistingRestaurantName_whenGettingCurrentReservedCapacity_thenItFails() {
        Assertions.assertThrows(RestaurantNotFound.class, () -> getCurrentReservedCapacity.perform("not_existing_restaurant_name"));
    }

    @Test
    void givenAnExistingRestaurantName_withNoReservationsWhenGettingCurrentReservedCapacity_thenItReturns0() throws RestaurantNotFound {
        when(restaurantRepository.get(EXISTING_RESTAURANT.getName())).thenReturn(Optional.of(EXISTING_RESTAURANT));
        when(reservationRepository.getTableReservations(any())).thenReturn(Collections.emptySet());

        Assertions.assertEquals(0, getCurrentReservedCapacity.perform(EXISTING_RESTAURANT.getName()));
    }

    @Test
    void givenAnExistingRestaurantNameWithReservations_whenGettingCurrentReservedCapacity_thenItReturnsTheReservedTotalTableCapacity() throws RestaurantNotFound {
        when(restaurantRepository.get(EXISTING_RESTAURANT.getName())).thenReturn(Optional.of(EXISTING_RESTAURANT));
        when(reservationRepository.getTableReservations(any())).thenReturn(Set.of(RESERVATION));

        Assertions.assertEquals(RESERVATION_CAPACITY_OF_EXISTING_RESTAURANT, getCurrentReservedCapacity.perform(EXISTING_RESTAURANT.getName()));
    }
}