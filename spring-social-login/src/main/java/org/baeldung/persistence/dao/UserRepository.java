package org.baeldung.persistence.dao;

import org.baeldung.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(final String username);

}
