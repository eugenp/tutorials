package com.baeldung.entity_dto_differences.repository;

import com.baeldung.entity_dto_differences.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
