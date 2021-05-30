package com.baeldung.hexagonalarchitecture.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.baeldung.hexagonalarchitecture.domain.Car;
import com.baeldung.hexagonalarchitecture.domain.ParkingLot;
import com.baeldung.hexagonalarchitecture.domain.exception.ParkingLotNotFoundException;
import com.baeldung.hexagonalarchitecture.domain.usecase.ParkingLotService;
import com.baeldung.hexagonalarchitecture.port.secondary.ParkingLotPersistencePort;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParkingLotServiceTest {

    @InjectMocks
    private ParkingLotService parkingLotService;

    @Mock
    private ParkingLotPersistencePort parkingLotPersistencePort;

    private static final Car CAR = new Car("123", "Tesla", "Black");

    @Test
    public void givenParkingLotPresentAndNotFull_whenParked_thenParkCarAndSave() {
        ObjectId parkingLotId = new ObjectId();
        ParkingLot parkingLot = ParkingLot.builder().id(parkingLotId).capacity(1).build();
        when(parkingLotPersistencePort.findById(parkingLotId)).thenReturn(Optional.of(parkingLot));

        parkingLotService.park(parkingLotId, CAR);

        assertThat(parkingLot.isFull()).isTrue();
        verify(parkingLotPersistencePort, times(1)).save(parkingLot);
    }

    @Test
    public void givenParkingLotNotPresent_whenParked_thenThrowParkingLotNotFoundException() {
        ObjectId parkingLotId = new ObjectId();

        assertThatThrownBy(() -> parkingLotService.park(parkingLotId, CAR)).isInstanceOf(
            ParkingLotNotFoundException.class);
    }

    @Test
    public void givenParkingLotANdCarPresent_whenUnParked_thenUnParkAndSave() {
        ObjectId parkingLotId = new ObjectId();
        ParkingLot parkingLot = ParkingLot.builder().id(parkingLotId).capacity(1).build();
        when(parkingLotPersistencePort.findById(parkingLotId)).thenReturn(Optional.of(parkingLot));
        parkingLotService.park(parkingLotId, CAR);
        parkingLotService.unPark(parkingLotId, CAR);
        assertThat(parkingLot.isFull()).isFalse();
        verify(parkingLotPersistencePort, times(2)).save(parkingLot);
    }

    @Test
    public void givenParkingLotNotPresent_whenUnParked_thenThrowParkingLotNotFoundException() {
        ObjectId parkingLotId = new ObjectId();

        assertThatThrownBy(() -> parkingLotService.unPark(parkingLotId, CAR)).isInstanceOf(
            ParkingLotNotFoundException.class);
    }

    @Test
    public void givenCapacity_thenCreateAndSaveParkingLotOfGivenCapacity() {
        int capacity = 5;
        ParkingLot parkingLot = ParkingLot.builder().capacity(capacity).build();

        parkingLotService.create(capacity);

        verify(parkingLotPersistencePort, times(1)).save(parkingLot);
    }

    @Test
    public void givenParkingLotExists_whenQueried_thenReturnFromPersistence() {
        ObjectId parkingLotId = new ObjectId();
        ParkingLot parkingLot = ParkingLot.builder().id(parkingLotId).capacity(1).build();

        when(parkingLotPersistencePort.findById(parkingLotId)).thenReturn(Optional.of(parkingLot));

        assertThat(parkingLotService.get(parkingLotId)).isEqualTo(parkingLot);
    }

    @Test
    public void givenParkingLotDoesntExists_whenQueried_thenThrowParkingLotNotFoundException() {
        ObjectId parkingLotId = new ObjectId();

        assertThatThrownBy(() -> parkingLotService.get(parkingLotId))
            .isInstanceOf(ParkingLotNotFoundException.class);
    }
}