package com.baeldung.smartdoc;

import java.util.List;

import jakarta.annotation.Resource;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
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

/**
 * The type Book controller.
 *
 * @author Baeldung.
 */

@RestController
@RequestMapping("/api/v1")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    /**
     * Create book.
     *
     * @param book the book
     * @return the book
     */
    @PostMapping("/books")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        bookRepository.add(book);

        return ResponseEntity.ok(book);
    }

    /**
     * Get all books.
     *
     * @return the list
     */
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookRepository.getBooks());
    }

    /**
     * Gets book by id.
     *
     * @param bookId the book id|1
     * @return the book by id
     */
    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable(value = "id") Long bookId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        return ResponseEntity.ok(book);
    }

    /**
     * Update book response entity.
     *
     * @param bookId      the book id|1
     * @param bookDetails the book details
     * @return the response entity
     */
    @PutMapping("/books/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable(value = "id") Long bookId, @Valid @RequestBody Book bookDetails) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        book.setAuthor(bookDetails.getAuthor());
        book.setPrice(bookDetails.getPrice());
        book.setTitle(bookDetails.getTitle());

        bookRepository.add(book);
        return ResponseEntity.ok(book);
    }

    /**
     * Delete book.
     *
     * @param bookId the book id|1
     * @return the true
     */
    @DeleteMapping("/book/{id}")
    public ResponseEntity<Boolean> deleteBook(@PathVariable(value = "id") Long bookId) {
        Book book = bookRepository.findById(bookId)
            .orElseThrow(() -> new ResponseStatusException(HttpStatusCode.valueOf(404)));
        return ResponseEntity.ok(bookRepository.delete(book));
    }

}
