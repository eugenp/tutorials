package com.baeldung.hexagonalarchitecture.port.secondary.adapter;

import com.baeldung.hexagonalarchitecture.domain.ParkingLot;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParkingLotRepository extends MongoRepository<ParkingLot, ObjectId> {

}
