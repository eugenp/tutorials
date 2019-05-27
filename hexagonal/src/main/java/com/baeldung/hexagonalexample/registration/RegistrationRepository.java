package com.baeldung.hexagonalexample.registration;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
interface RegistrationRepository extends CrudRepository<Registration, Long>, RegistrationPersistencePort {
}
