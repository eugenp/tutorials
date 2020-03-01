package com.baeldung.rolesauthorities.persistence;

import com.baeldung.rolesauthorities.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);

    void delete(Role role);

}
