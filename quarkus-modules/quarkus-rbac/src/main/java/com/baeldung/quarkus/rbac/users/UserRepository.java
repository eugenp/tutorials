package com.baeldung.quarkus.rbac.users;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collection;
import java.util.List;


@ApplicationScoped
class UserRepository implements PanacheRepository<User> {

    public List<Role> findRoles(final Collection<String> roles) {
        if (roles == null || roles.isEmpty()) {
            return List.of();
        }

        return find("SELECT r FROM Role r WHERE r.name in ?1", roles).project(Role.class).list();
    }
}
