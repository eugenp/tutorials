package org.baeldung.persistence.domain.repository;

import org.baeldung.persistence.domain.model.User;

import io.katharsis.repository.ResourceRepositoryV2;

/**
 * @author krishan.gandhi
 * The Interface UserRepository.
 */
public interface UserRepository extends ResourceRepositoryV2<User, Long> {

}
