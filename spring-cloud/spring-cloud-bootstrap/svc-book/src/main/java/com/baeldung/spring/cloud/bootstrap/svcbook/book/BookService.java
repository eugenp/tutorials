package com.baeldung.spring.cloud.bootstrap.svcbook.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long bookId) {
        return Optional.ofNullable(bookRepository.findOne(bookId))
            .orElseThrow(() -> new BookNotFoundException("Book not found. ID: " + bookId));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Book createBook(Book book) {
        Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());
        return bookRepository.save(newBook);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBook(Long bookId) {
        bookRepository.delete(bookId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Book updateBook(Map<String, String> updates, Long bookId) {
        Book book = findBookById(bookId);
        updates.keySet().forEach(key -> {
            switch (key) {
            case "author":
                book.setAuthor(updates.get(key));
                break;
            case "title":
                book.setTitle(updates.get(key));
            }
        });
        return bookRepository.save(book);
    }
}
