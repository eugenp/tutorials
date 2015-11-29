package org.baeldung.spring.data.cassandra.repository;


import org.baeldung.spring.data.cassandra.model.Event;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.data.cassandra.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CassandraRepository<Event> {
    @Query("select * from event where type = ?0 and bucket=?1")
    Iterable<Event> findByTypeAndBucket(String type, String bucket);
}

