package org.baeldung.persistence.multiple.dao.user;

import org.baeldung.persistence.multiple.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

}
