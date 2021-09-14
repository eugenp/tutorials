package com.baeldung.boot.hexagonal.infrastructure;

import com.baeldung.boot.hexagonal.infrastructure.entities.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookJpaRepository extends JpaRepository<BookEntity, Long> {
}
