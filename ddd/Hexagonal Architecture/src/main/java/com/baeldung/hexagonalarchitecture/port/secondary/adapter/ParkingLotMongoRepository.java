package com.baeldung.hexagonalarchitecture.port.secondary.adapter;

import com.baeldung.hexagonalarchitecture.domain.ParkingLot;
import com.baeldung.hexagonalarchitecture.port.secondary.ParkingLotPersistencePort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@RequiredArgsConstructor
public class ParkingLotMongoRepository implements ParkingLotPersistencePort {

    private final ParkingLotRepository parkingLotRepository;

    @Override
    public Optional<ParkingLot> findById(ObjectId parkingLotId) {
        return parkingLotRepository.findById(parkingLotId);
    }

    @Override
    public ParkingLot save(ParkingLot parkingLot) {
        return parkingLotRepository.save(parkingLot);
    }
}
