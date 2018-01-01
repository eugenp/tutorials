package org.baeldung.persistence.domain.repository;



import org.baeldung.persistence.domain.model.Role;

import io.katharsis.repository.ResourceRepositoryV2;

public interface RoleRepository extends ResourceRepositoryV2<Role, Long> {

}
