package com.baeldung.recordswithjpa.repository;

import com.baeldung.recordswithjpa.entity.CompositeBook;
import com.baeldung.recordswithjpa.records.BookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompositeBookRepository extends JpaRepository<CompositeBook, BookId> {
}