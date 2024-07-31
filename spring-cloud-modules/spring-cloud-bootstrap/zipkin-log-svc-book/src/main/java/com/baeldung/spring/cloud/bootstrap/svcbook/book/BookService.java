package com.baeldung.spring.cloud.bootstrap.svcbook.book;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Preconditions;

@Service
@Transactional(readOnly = true)
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    public Book findBookById(Long bookId) {
        return bookRepository.findById(bookId)
            .orElseThrow(() -> new BookNotFoundException(String.format("Book not found. ID: %s", bookId)));
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Book createBook(Book book) {
        final Book newBook = new Book();
        newBook.setTitle(book.getTitle());
        newBook.setAuthor(book.getAuthor());
        return bookRepository.save(newBook);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteBook(Long bookId) {
        bookRepository.deleteById(bookId);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Book updateBook(Map<String, String> updates, Long bookId) {
        final Book book = findBookById(bookId);
        updates.keySet()
            .forEach(key -> {
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

    @Transactional(propagation = Propagation.REQUIRED)
    public Book updateBook(Book book, Long bookId) {
        Preconditions.checkNotNull(book);
        Preconditions.checkState(book.getId() == bookId);
        Preconditions.checkArgument(bookRepository.findById(bookId)
            .isPresent());
        return bookRepository.save(book);
    }
}
