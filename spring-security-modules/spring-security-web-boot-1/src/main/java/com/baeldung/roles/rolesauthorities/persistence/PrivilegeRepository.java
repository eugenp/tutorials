package com.baeldung.roles.rolesauthorities.persistence;

import com.baeldung.roles.rolesauthorities.model.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrivilegeRepository extends JpaRepository<Privilege, Long> {

    Privilege findByName(String name);

    void delete(Privilege privilege);

}
