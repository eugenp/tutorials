package org.baeldung.persistence.domain.repository;



import org.baeldung.persistence.domain.model.User;

import io.katharsis.repository.ResourceRepositoryV2;

public interface UserRepository extends ResourceRepositoryV2<User, Long> {

}
