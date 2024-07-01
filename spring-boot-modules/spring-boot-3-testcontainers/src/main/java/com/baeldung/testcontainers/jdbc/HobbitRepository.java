package com.baeldung.testcontainers.jdbc;

import org.springframework.data.repository.CrudRepository;

public interface HobbitRepository extends CrudRepository<Hobbit, Long> {

}
