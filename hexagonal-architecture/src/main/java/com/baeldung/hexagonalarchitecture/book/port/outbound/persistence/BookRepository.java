package com.baeldung.hexagonalarchitecture.book.port.outbound.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}
