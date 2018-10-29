package com.baeldung.dao.repositories.user;

import com.baeldung.domain.user.Possession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PossessionRepository extends JpaRepository<Possession, Long> {

}
