package com.baeldung.hexarch.boostrore.repository;

import com.baeldung.hexarch.boostrore.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorsRepository extends JpaRepository<Author, Long> {
    Optional<Author> findByEmailId(final String emailId);
    void deleteByEmailId(final String emailId);
}
