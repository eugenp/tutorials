package com.baeldung.hexagonalarchitecture.domain;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.baeldung.hexagonalarchitecture.domain.exception.CarNotFoundException;
import com.baeldung.hexagonalarchitecture.domain.exception.ParkingLotFullException;
import org.junit.jupiter.api.Test;

class ParkingLotTest {

    private static final Car CAR = new Car("123", "Tesla", "Black");

    @Test
    void givenParkingLotIsFull_whenQueried_thenReturnTrue() {
        ParkingLot parkingLot = ParkingLot.builder().capacity(1).build();
        parkingLot.park(CAR);

        assertThat(parkingLot.isFull()).isTrue();
    }

    @Test
    void givenParkLotIsNotFull_whenCarParked_thenReturnParkedCar() {
        ParkingLot parkingLot = ParkingLot.builder().capacity(1).build();

        assertThat(parkingLot.park(CAR)).isEqualTo(CAR);
    }

    @Test
    void givenParkingLotIfFull_whenParked_thenThrowParkingLotFullException() {
        ParkingLot parkingLot = ParkingLot.builder().capacity(1).build();
        parkingLot.park(CAR);

        assertThatThrownBy(() -> parkingLot.park(CAR)).isInstanceOf(ParkingLotFullException.class);
    }

    @Test
    void givenParkingLotHasCar_whenUnParked_thenReturnUnParkedCar() {
        ParkingLot parkingLot = ParkingLot.builder().capacity(1).build();
        parkingLot.park(CAR);

        assertThat(parkingLot.unPark(CAR)).isEqualTo(CAR);
    }

    @Test
    void givenParkingLotDoesNotHaveCar_whenUnParked_thenThrowCarNotFoundException() {
        ParkingLot parkingLot = ParkingLot.builder().capacity(1).build();

        assertThatThrownBy(() -> parkingLot.unPark(CAR)).isInstanceOf(CarNotFoundException.class);
    }
}