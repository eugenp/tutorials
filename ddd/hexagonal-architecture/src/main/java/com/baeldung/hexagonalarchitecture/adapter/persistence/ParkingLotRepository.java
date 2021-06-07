package com.baeldung.hexagonalarchitecture.adapter.persistence;

import com.baeldung.hexagonalarchitecture.domain.ParkingLot;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingLotRepository extends MongoRepository<ParkingLot, ObjectId> {

}
