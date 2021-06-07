package com.baeldung.hexagonalarchitecture.domain;

import org.bson.types.ObjectId;

public interface ParkingLotUseCase {

    ParkingLot create(int capacity);

    ParkingLot park(ObjectId parkingLotId, Car car);

    ParkingLot unPark(ObjectId parkingLotId, Car car);

    ParkingLot get(ObjectId parkingLotId);
}
