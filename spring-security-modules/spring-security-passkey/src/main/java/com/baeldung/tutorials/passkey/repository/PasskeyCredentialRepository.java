package com.baeldung.tutorials.passkey.repository;

import com.baeldung.tutorials.passkey.domain.PasskeyCredential;
import org.springframework.data.repository.CrudRepository;

public interface PasskeyCredentialRepository extends CrudRepository<PasskeyCredential, Long> {
}
