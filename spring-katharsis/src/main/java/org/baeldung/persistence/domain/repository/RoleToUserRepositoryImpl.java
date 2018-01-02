package org.baeldung.persistence.domain.repository;

import org.baeldung.persistence.domain.model.Role;
import org.baeldung.persistence.domain.model.User;
import org.springframework.stereotype.Component;

import io.katharsis.repository.RelationshipRepositoryBase;

/**
 * @author krishan.gandhi
 * The Class RoleToUserRepositoryImpl.
 */
@Component
public class RoleToUserRepositoryImpl extends RelationshipRepositoryBase<Role, Long, User, Long> {

    /**
     * Instantiates a new role to user repository impl.
     */
    public RoleToUserRepositoryImpl() {
        super(Role.class, User.class);
    }
}
