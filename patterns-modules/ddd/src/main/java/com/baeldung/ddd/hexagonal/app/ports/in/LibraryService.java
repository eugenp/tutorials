package com.baeldung.ddd.hexagonal.app.ports.in;

import java.math.BigDecimal;
import java.util.List;

import com.baeldung.ddd.hexagonal.app.domain.Book;
import com.baeldung.ddd.hexagonal.app.domain.Rental;

public interface LibraryService {
	Rental rentBook(Long bookId);
    Rental returnBook(Long rentalId);
    BigDecimal calculateFine(Long rentalId);
    List<Book> getAllBooks();
    Book saveBook(Book book);
}
