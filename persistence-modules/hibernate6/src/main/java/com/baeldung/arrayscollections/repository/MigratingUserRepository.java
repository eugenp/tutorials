package com.baeldung.arrayscollections.repository;

import com.baeldung.arrayscollections.entity.MigratingUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MigratingUserRepository extends JpaRepository<MigratingUser, Long> {
}
