package org.baeldung.persistence.domain.repository;

import org.baeldung.persistence.domain.model.Role;

import io.katharsis.repository.ResourceRepositoryV2;

/**
 * @author krishan.gandhi
 * The Interface RoleRepository.
 */
public interface RoleRepository extends ResourceRepositoryV2<Role, Long> {

}
