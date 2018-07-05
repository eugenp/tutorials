package org.baeldung.rolesauthorities.persistence;

import org.baeldung.rolesauthorities.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    void delete(Role role);

}
