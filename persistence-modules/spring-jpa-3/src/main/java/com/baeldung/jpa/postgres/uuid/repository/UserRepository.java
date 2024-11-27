package com.baeldung.jpa.postgres.uuid.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.baeldung.jpa.postgres.uuid.entity.User;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}

