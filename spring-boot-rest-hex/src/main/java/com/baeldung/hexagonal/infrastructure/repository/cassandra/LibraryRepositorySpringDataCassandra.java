package com.baeldung.hexagonal.infrastructure.repository.cassandra;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepositorySpringDataCassandra extends 
			CassandraRepository<BookEntity, String> {
	
}