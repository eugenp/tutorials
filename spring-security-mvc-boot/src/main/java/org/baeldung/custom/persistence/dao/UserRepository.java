package org.baeldung.custom.persistence.dao;

import org.baeldung.custom.persistence.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(final String username);

    @Transactional
    void removeUserByUsername(String username);

}
