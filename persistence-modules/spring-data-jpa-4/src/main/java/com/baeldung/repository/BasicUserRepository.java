package com.baeldung.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.baeldung.model.BasicUser;

@Repository
@Transactional
public interface BasicUserRepository extends JpaRepository<BasicUser, Long> {
    
    @EntityGraph(attributePaths = "permissions")
    Optional<BasicUser> findDetailedByUsername(String username);

}
