package com.baeldung.hexagonalarchitecture.domain;

import com.baeldung.hexagonalarchitecture.port.ParkingLotPersistencePort;

import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ParkingLotService implements ParkingLotUseCase {

    private final ParkingLotPersistencePort parkingLotPersistencePort;

    @Override
    public ParkingLot park(ObjectId parkingLotId, Car car) {
        ParkingLot parkingLot = parkingLotPersistencePort.findById(parkingLotId);
        parkingLot.park(car);
        return parkingLotPersistencePort.save(parkingLot);
    }

    @Override
    public ParkingLot unPark(ObjectId parkingLotId, Car car) {
        ParkingLot parkingLot = parkingLotPersistencePort.findById(parkingLotId);
        parkingLot.unPark(car);
        return parkingLotPersistencePort.save(parkingLot);
    }

    @Override
    public ParkingLot get(ObjectId parkingLotId) {
        return parkingLotPersistencePort.findById(parkingLotId);
    }

    @Override
    public ParkingLot create(int capacity) {
        ParkingLot build = ParkingLot.builder()
            .capacity(capacity)
            .build();
        return parkingLotPersistencePort.save(build);
    }

}
