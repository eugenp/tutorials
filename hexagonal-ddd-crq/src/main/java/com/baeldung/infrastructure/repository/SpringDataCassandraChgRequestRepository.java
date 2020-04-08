package com.baeldung.infrastructure.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpringDataCassandraChgRequestRepository extends CassandraRepository<ChgRequestEntity, UUID> {
}
