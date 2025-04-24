package com.baeldung.tutorials.passkey.repository;

import com.baeldung.tutorials.passkey.domain.PasskeyUser;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface PasskeyUserRepository extends CrudRepository<PasskeyUser, Long> {
    Optional<PasskeyUser> findByName(String name);
    Optional<PasskeyUser> findByExternalId(String externalId);
}
