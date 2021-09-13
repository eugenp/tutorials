package com.baeldung.boot.hexagonal.application.api;

import com.baeldung.boot.hexagonal.domain.models.Book;
import com.baeldung.boot.hexagonal.domain.ports.in.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/book")
@RequiredArgsConstructor
@Slf4j
public class BookApi {

    private final BookService bookService;

    @PostMapping()
    public ResponseEntity<Response> addBook(@RequestBody Book book) {
        log.info("Book Details: {}", book);
        bookService.addBook(book);
        return new ResponseEntity<>(new Response(HttpStatus.CREATED.value(), "A new book has been added to the library"), HttpStatus.CREATED);
    }

    @PutMapping()
    public ResponseEntity<Response> updateBook(@RequestBody Book book) {
        log.info("Book Details: {}", book);
        if (book.getId() == null)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Please specify a book id");
        bookService.updateBook(book);
        return new ResponseEntity<>(new Response(HttpStatus.OK.value(), "A book with id " + book.getId() + " has been updated in the library"), HttpStatus.OK);
    }

    @GetMapping("/{bookId}")
    public Book getBook(@PathVariable("bookId") long bookId) {
        log.info("Getting book details for book id: {}", bookId);
        return bookService.getBook(bookId);
    }

    @GetMapping()
    public List<Book> getAllBooks() {
        log.info("Getting all the books in the library");
        return bookService.getBooks();
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Response> deleteBook(@PathVariable("bookId") long bookId) {
        log.info("Deleting book with id {}", bookId);
        bookService.deleteBook(bookId);
        return ResponseEntity.ok(new Response(HttpStatus.OK.value(), "The book with id " + bookId + " is deleted "));
    }

}
