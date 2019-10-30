package org.baeldung.custom.persistence.dao;

import org.baeldung.custom.persistence.model.Organization;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    public Organization findByName(String name);

}
