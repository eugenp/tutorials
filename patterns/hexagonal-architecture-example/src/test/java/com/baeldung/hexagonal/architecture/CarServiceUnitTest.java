package com.baeldung.hexagonal.architecture;

import com.baeldung.pattern.hexagonal.architecture.application.domain.Car;
import com.baeldung.pattern.hexagonal.architecture.application.port.outgoing.LoadCarPort;
import com.baeldung.pattern.hexagonal.architecture.application.port.outgoing.SaveCarPort;
import com.baeldung.pattern.hexagonal.architecture.application.services.CarService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class CarServiceUnitTest {

    private LoadCarPort loadCarPort;
    private SaveCarPort saveCarPort;

    private CarService carService;


    @BeforeEach
    void setup() {
        loadCarPort = mock(LoadCarPort.class);
        saveCarPort = mock(SaveCarPort.class);

        carService = new CarService(loadCarPort, saveCarPort);

    }

    @Test
    void testRent_WhenCarNotFound_ThenShouldThrowNoSuchElementException() {
        doReturn(Optional.empty()).when(loadCarPort).load(0L);
        Assertions.assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> carService.rent(0L));
    }

    @Test
    void testRent_WhenHasRentIsFalse_ThenShouldReturnFalse() {
        Car car = mock(Car.class);

        doReturn(false).when(car).rent();
        doReturn(Optional.of(car)).when(loadCarPort).load(1L);

        assertFalse(carService.rent(1L));
        verify(saveCarPort, times(0)).save(car);
    }

    @Test
    void testRent_WhenHasRentIsTrue_ThenShouldReturnTrue() {
        Car car = mock(Car.class);

        doReturn(true).when(car).rent();
        doReturn(Optional.of(car)).when(loadCarPort).load(1L);

        assertTrue(carService.rent(1L));
        verify(saveCarPort, times(1)).save(car);
    }

    @Test
    void testReturnRental_WhenCarNotFound_ThenShouldThrowNoSuchElementException() {
        doReturn(Optional.empty()).when(loadCarPort).load(0L);
        Assertions.assertThatExceptionOfType(NoSuchElementException.class).isThrownBy(() -> carService.returnRental(0L));
    }

    @Test
    void testReturnRental_WhenHasReturnedIsFalse_ThenShouldReturnFalse() {
        Car car = mock(Car.class);

        doReturn(false).when(car).returnRental();
        doReturn(Optional.of(car)).when(loadCarPort).load(1L);

        assertFalse(carService.returnRental(1L));
        verify(saveCarPort, times(0)).save(car);
    }


    @Test
    void testReturnRental_WhenHasReturnedIsTrue_ThenShouldReturnTrue() {
        Car car = mock(Car.class);

        doReturn(true).when(car).returnRental();
        doReturn(Optional.of(car)).when(loadCarPort).load(1L);

        assertTrue(carService.returnRental(1L));
        verify(saveCarPort, times(1)).save(car);
    }

}
