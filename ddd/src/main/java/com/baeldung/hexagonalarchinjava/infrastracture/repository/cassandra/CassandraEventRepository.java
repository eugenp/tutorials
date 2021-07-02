package com.baeldung.hexagonalarchinjava.infrastracture.repository.cassandra;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface CassandraEventRepository extends CassandraRepository<EventEntity, UUID> {
}
