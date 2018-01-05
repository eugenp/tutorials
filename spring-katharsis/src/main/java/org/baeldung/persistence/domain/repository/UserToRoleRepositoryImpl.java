package org.baeldung.persistence.domain.repository;

import org.baeldung.persistence.domain.model.Role;
import org.baeldung.persistence.domain.model.User;
import org.springframework.stereotype.Component;

import io.katharsis.repository.RelationshipRepositoryBase;

/**
 * @author krishan.gandhi
 * The Class UserToRoleRepositoryImpl.
 */
@Component
public class UserToRoleRepositoryImpl extends RelationshipRepositoryBase<User, Long, Role, Long> {

    /**
     * Instantiates a new user to role repository impl.
     */
    public UserToRoleRepositoryImpl() {
        super(User.class, Role.class);
    }
}
