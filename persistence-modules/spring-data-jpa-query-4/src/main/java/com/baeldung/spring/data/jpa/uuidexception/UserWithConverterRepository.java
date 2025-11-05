package com.baeldung.spring.data.jpa.uuidexception;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserWithConverterRepository extends JpaRepository<UserWithConverter, Long> {
    Optional<UserWithConverter> findById(Long id);

    Optional<UserWithConverter> findByUuid(UUID uuid);

}
