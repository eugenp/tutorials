package org.baeldung.springcassandra.repository;

import org.baeldung.springcassandra.model.Car;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CarRepository extends CassandraRepository<Car, UUID> {

}
