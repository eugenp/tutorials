package com.baeldung.hexagonalarchitecture.port.secondary;

import com.baeldung.hexagonalarchitecture.domain.ParkingLot;
import java.util.Optional;
import org.bson.types.ObjectId;

public interface ParkingLotPersistencePort {

    Optional<ParkingLot> findById(ObjectId parkingLotId);

    ParkingLot save(ParkingLot parkingLot);
}
