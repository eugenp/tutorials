package com.baeldung.persistence.dao;

import com.baeldung.persistence.model.Book;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.baeldung.persistence.dao.BookSpecifications.hasAuthor;
import static com.baeldung.persistence.dao.BookSpecifications.titleContains;
import static org.springframework.data.jpa.domain.Specification.where;

@Service
public class BookService {

    private BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> query(String author, String title) {
        return bookRepository.findAll(where(hasAuthor(author)).and(titleContains(title)));
    }

}
