package com.baeldung.hexagonalapp.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
interface JpaBookRepository extends JpaRepository<BookEntity, Long> {
}
