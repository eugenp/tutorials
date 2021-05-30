package com.baeldung.hexagonalarchitecture.domain.usecase;

import com.baeldung.hexagonalarchitecture.domain.Car;
import com.baeldung.hexagonalarchitecture.domain.ParkingLot;
import org.bson.types.ObjectId;

public interface ParkingLotUseCase {

    ParkingLot create(int capacity);

    ParkingLot park(ObjectId parkingLotId, Car car);

    ParkingLot unPark(ObjectId parkingLotId, Car car);

    ParkingLot get(ObjectId parkingLotId);
}
