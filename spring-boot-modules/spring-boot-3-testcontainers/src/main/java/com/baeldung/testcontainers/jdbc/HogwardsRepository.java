package com.baeldung.testcontainers.jdbc;

import org.springframework.data.repository.CrudRepository;

public interface HogwardsRepository extends CrudRepository<Wizard, Long> {

}
