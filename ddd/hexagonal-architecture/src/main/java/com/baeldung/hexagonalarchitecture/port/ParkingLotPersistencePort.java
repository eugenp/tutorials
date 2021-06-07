package com.baeldung.hexagonalarchitecture.port;

import com.baeldung.hexagonalarchitecture.domain.ParkingLot;

import org.bson.types.ObjectId;

public interface ParkingLotPersistencePort {

    ParkingLot findById(ObjectId parkingLotId);

    ParkingLot save(ParkingLot parkingLot);
}
