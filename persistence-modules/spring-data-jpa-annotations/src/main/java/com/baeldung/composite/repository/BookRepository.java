package com.baeldung.composite.repository;

import com.baeldung.composite.entity.Book;
import com.baeldung.composite.entity.BookId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, BookId> {

    List<Book> findByIdName(String name);

    List<Book> findByIdAuthor(String author);

    List<Book> findByGenre(String genre);
}
