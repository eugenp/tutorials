package com.baeldung.hexagonalarchitecture.adapter.persistence;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.baeldung.hexagonalarchitecture.domain.ParkingLot;

import org.junit.jupiter.api.Test;

class ParkingLotInMemoryRepositoryTest {

    @Test
    void givenParkingLotPresent_whenQueried_thenExpectParkingLot() {
        ParkingLotInMemoryRepository parkingLotInMemoryRepository = new ParkingLotInMemoryRepository();
        ParkingLot parkingLot = ParkingLot.builder()
            .capacity(5)
            .build();

        ParkingLot savedParkingLot = parkingLotInMemoryRepository.save(parkingLot);

        assertThat(parkingLotInMemoryRepository.findById(savedParkingLot.getId())).isEqualTo(parkingLot);
    }

    @Test
    void givenParkingLot_thenSaveAndReturnSavedParkingLot() {
        ParkingLotInMemoryRepository parkingLotInMemoryRepository = new ParkingLotInMemoryRepository();
        ParkingLot parkingLot = ParkingLot.builder()
            .capacity(5)
            .build();

        ParkingLot savedParkingLot = parkingLotInMemoryRepository.save(parkingLot);

        assertThat(savedParkingLot.getId()).isNotNull();
    }
}