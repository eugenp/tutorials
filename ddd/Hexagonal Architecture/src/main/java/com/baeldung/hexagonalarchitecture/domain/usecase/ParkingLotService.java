package com.baeldung.hexagonalarchitecture.domain.usecase;

import com.baeldung.hexagonalarchitecture.domain.Car;
import com.baeldung.hexagonalarchitecture.domain.ParkingLot;
import com.baeldung.hexagonalarchitecture.domain.exception.ParkingLotNotFoundException;
import com.baeldung.hexagonalarchitecture.port.secondary.ParkingLotPersistencePort;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParkingLotService implements ParkingLotUseCase {

    private final ParkingLotPersistencePort parkingLotPersistencePort;

    @Override
    public ParkingLot park(ObjectId parkingLotId, Car car) {
        ParkingLot parkingLot = parkingLotPersistencePort.findById(parkingLotId)
            .orElseThrow(ParkingLotNotFoundException::new);
        parkingLot.park(car);
        return parkingLotPersistencePort.save(parkingLot);
    }

    @Override
    public ParkingLot unPark(ObjectId parkingLotId, Car car) {
        ParkingLot parkingLot = parkingLotPersistencePort.findById(parkingLotId)
            .orElseThrow(ParkingLotNotFoundException::new);
        parkingLot.unPark(car);
        return parkingLotPersistencePort.save(parkingLot);
    }

    @Override
    public ParkingLot get(ObjectId parkingLotId) {
        return parkingLotPersistencePort.findById(parkingLotId)
            .orElseThrow(ParkingLotNotFoundException::new);
    }

    @Override
    public ParkingLot create(int capacity) {
        ParkingLot build = ParkingLot.builder().capacity(capacity).build();
        return parkingLotPersistencePort.save(build);
    }

}
