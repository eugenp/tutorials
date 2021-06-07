package com.baeldung.hexagonalarchitecture.adapter.persistence;

import com.baeldung.hexagonalarchitecture.domain.ParkingLot;
import com.baeldung.hexagonalarchitecture.port.ParkingLotPersistencePort;

import org.bson.types.ObjectId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

@Primary
@Component
@RequiredArgsConstructor
public class ParkingLotMongoRepository implements ParkingLotPersistencePort {

    private final ParkingLotRepository parkingLotRepository;

    @Override
    public ParkingLot findById(ObjectId parkingLotId) {
        return parkingLotRepository.findById(parkingLotId)
            .orElse(null);
    }

    @Override
    public ParkingLot save(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }
}
