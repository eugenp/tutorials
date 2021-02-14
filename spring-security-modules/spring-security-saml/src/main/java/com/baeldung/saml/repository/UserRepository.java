package com.baeldung.saml.repository;

import com.baeldung.saml.auth.StoredUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<StoredUser, String> {
    Optional<StoredUser> findByUsernameIgnoreCase(String username);
}
