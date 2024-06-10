package com.baeldung.criteriabuilder;

import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.JpaRepository;


@Repository
public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {
    // No additional methods needed here
}

