package org.baeldung.persistence.multiple.dao.user;

import org.baeldung.persistence.multiple.model.user.Possession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PossessionRepository extends JpaRepository<Possession, Long> {

}
