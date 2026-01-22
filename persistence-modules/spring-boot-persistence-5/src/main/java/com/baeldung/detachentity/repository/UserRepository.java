package com.baeldung.detachentity.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.detachentity.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, DetachableRepository<User> {
}
