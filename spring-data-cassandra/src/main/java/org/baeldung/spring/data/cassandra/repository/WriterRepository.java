package org.baeldung.spring.data.cassandra.repository;

import org.baeldung.spring.data.cassandra.model.Writer;
import org.springframework.data.cassandra.repository.CassandraRepository;

public interface WriterRepository extends CassandraRepository<Writer> {}
