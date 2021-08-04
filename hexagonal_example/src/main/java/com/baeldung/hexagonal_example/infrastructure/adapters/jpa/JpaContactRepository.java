package com.baeldung.hexagonal_example.infrastructure.adapters.jpa;

import org.springframework.data.repository.CrudRepository;

interface JpaContactRepository extends CrudRepository<ContactEntity, String> {
}
