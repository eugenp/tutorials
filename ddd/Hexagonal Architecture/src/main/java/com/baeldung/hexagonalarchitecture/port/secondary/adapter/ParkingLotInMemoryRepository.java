package com.baeldung.hexagonalarchitecture.port.secondary.adapter;

import com.baeldung.hexagonalarchitecture.domain.ParkingLot;
import com.baeldung.hexagonalarchitecture.port.secondary.ParkingLotPersistencePort;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class ParkingLotInMemoryRepository implements ParkingLotPersistencePort {

    private final Map<ObjectId, ParkingLot> parkingLots;

    public ParkingLotInMemoryRepository() {
        parkingLots = new LinkedHashMap<>();
    }

    @Override
    public Optional<ParkingLot> findById(ObjectId parkingLotId) {
        return Optional.ofNullable(parkingLots.get(parkingLotId));
    }

    @Override
    public ParkingLot save(ParkingLot parkingLot) {
        ObjectId parkingLotId = new ObjectId();
        parkingLot.setId(parkingLotId);
        parkingLots.put(parkingLotId, parkingLot);
        return parkingLot;
    }
}
