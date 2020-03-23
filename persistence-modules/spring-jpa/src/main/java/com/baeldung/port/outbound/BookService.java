package com.baeldung.port.outbound;

import static com.baeldung.port.outbound.BookSpecifications.hasAuthor;
import static com.baeldung.port.outbound.BookSpecifications.titleContains;
import static org.springframework.data.jpa.domain.Specifications.where;

import java.util.List;

import com.baeldung.port.model.Book;
import org.springframework.stereotype.Service;

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
