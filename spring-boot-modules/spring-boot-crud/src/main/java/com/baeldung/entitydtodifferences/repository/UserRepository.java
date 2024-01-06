package com.baeldung.entitydtodifferences.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baeldung.entitydtodifferences.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

}
