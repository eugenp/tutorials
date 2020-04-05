package com.baeldung.mockito.additionalanswers;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    Book getByBookId(Long bookId);
    List<Book> getAllByAuthor(String author);
    Book getBookByTitle(String title);
    Book getBookByTitleAnAndAuthorAndNumberOfPages(String title, String author, int numOfPages);
    Book getBookByTitleAndAuthor(String title, String author);
}
