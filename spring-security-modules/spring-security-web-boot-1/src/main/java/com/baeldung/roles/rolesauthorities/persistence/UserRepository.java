package com.baeldung.roles.rolesauthorities.persistence;

import com.baeldung.roles.rolesauthorities.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    void delete(User user);

}
