package com.baeldung.recordswithjpa.repository;

import com.baeldung.recordswithjpa.entity.Book;
import com.baeldung.recordswithjpa.records.BookRecord;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends CrudRepository<Book, Long> {
    List<BookRecord> findBookByAuthor(String author);

    @Query("SELECT new com.baeldung.recordswithjpa.records.BookRecord(b.id, b.title, b.author, b.isbn) " +
            "FROM Book b WHERE b.id = :id")
    BookRecord findBookById(@Param("id") Long id);
}