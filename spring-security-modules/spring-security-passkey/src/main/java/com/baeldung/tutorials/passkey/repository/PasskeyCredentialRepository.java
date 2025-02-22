package com.baeldung.tutorials.passkey.repository;

import com.baeldung.tutorials.passkey.domain.PasskeyCredential;
import com.baeldung.tutorials.passkey.domain.PasskeyUser;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PasskeyCredentialRepository extends CrudRepository<PasskeyCredential, Long> {
    Optional<PasskeyCredential> findByCredentialId(String credentialId);

    @Query("select * from PASSKEY_CREDENTIALS where USER_ID = :userId")
    List<PasskeyCredential> findByUser(@Param("userId") long userId);
}
