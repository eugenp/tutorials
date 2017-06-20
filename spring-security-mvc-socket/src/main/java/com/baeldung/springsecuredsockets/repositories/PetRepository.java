package com.baeldung.springsecuredsockets.repositories;

import com.baeldung.springsecuredsockets.domain.Pet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PetRepository extends CrudRepository<Pet, Long> {}