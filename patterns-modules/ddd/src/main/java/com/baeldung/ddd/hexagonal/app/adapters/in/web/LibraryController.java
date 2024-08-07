package com.baeldung.ddd.hexagonal.app.adapters.in.web;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baeldung.ddd.hexagonal.app.domain.Book;
import com.baeldung.ddd.hexagonal.app.domain.Rental;
import com.baeldung.ddd.hexagonal.app.ports.in.LibraryService;

@RestController
@RequestMapping("/library")
public class LibraryController {
	
	@Autowired
    LibraryService libraryService;

    @PostMapping("/books/{bookId}/rent")
    public ResponseEntity<Rental> rentBook(@PathVariable Long bookId) {
    	Rental rental = libraryService.rentBook(bookId);
        return ResponseEntity.status(HttpStatus.CREATED).build().ofNullable(rental);
    }

    @PostMapping("/rentals/{rentalId}")
    public ResponseEntity<Rental> returnBook(@PathVariable Long rentalId) {
    	Rental rental = libraryService.returnBook(rentalId);
        return ResponseEntity.ok().build().ofNullable(rental);
    }

    @GetMapping("/rentals/{rentalId}/fine")
    public ResponseEntity<BigDecimal> calculateFine(@PathVariable Long rentalId) {
        BigDecimal fine = libraryService.calculateFine(rentalId);
        return ResponseEntity.ok(fine);
    }
    
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
    	libraryService.saveBook(new Book("5 Point", "Chetan B"));
        List<Book> books = libraryService.getAllBooks();
        return ResponseEntity.ok(books);
    }
}
