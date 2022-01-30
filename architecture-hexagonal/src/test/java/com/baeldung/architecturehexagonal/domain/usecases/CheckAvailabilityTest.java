package com.baeldung.architecturehexagonal.domain.usecases;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.architecturehexagonal.domain.ports.errors.RestaurantNotFound;

@ExtendWith(MockitoExtension.class)
class CheckAvailabilityTest {
    @Mock
    private GetCurrentReservedCapacity getCurrentReservedCapacity;
    private CheckAvailability checkAvailability;

    @BeforeEach
    void setUp() {
        checkAvailability = new CheckAvailability(getCurrentReservedCapacity);
    }

    @Test
    void givenCapacityIsMoreThan60PercentReserved_whenAvailabilityIsChecked_thenItReturnsFalse() throws RestaurantNotFound {
        when(getCurrentReservedCapacity.perform("test1")).thenReturn(61);

        Assertions.assertFalse(checkAvailability.perform("test1"));
    }

    @Test
    void givenCapacityIsLessThan60PercentReserved_whenAvailabilityIsChecked_thenItReturnsTrue() throws RestaurantNotFound {
        when(getCurrentReservedCapacity.perform("test2")).thenReturn(59);

        Assertions.assertTrue(checkAvailability.perform("test2"));
    }
}