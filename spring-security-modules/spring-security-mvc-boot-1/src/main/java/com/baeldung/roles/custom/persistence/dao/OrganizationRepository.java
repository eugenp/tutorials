package com.baeldung.roles.custom.persistence.dao;

import com.baeldung.roles.custom.persistence.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    public Organization findByName(String name);

}
