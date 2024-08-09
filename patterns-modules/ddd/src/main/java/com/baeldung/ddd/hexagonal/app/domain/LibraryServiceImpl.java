package com.baeldung.ddd.hexagonal.app.domain;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baeldung.ddd.hexagonal.app.ports.in.LibraryService;
import com.baeldung.ddd.hexagonal.app.ports.out.BookRepository;
import com.baeldung.ddd.hexagonal.app.ports.out.RentalRepository;


@Service
public class LibraryServiceImpl implements LibraryService {
	
    @Autowired
    BookRepository bookRepository;
	
    @Autowired
    RentalRepository rentalRepository;

    @Override
    public Rental rentBook(Long bookId) {
        Book book = bookRepository.findById(bookId);
        if (book == null) {
            throw new RuntimeException("Book not found");
        }

        Rental rental = new Rental();
        rental.setBook(book);
        rental.setRentDate(LocalDate.now());
        Rental savedRental = rentalRepository.save(rental);
        return savedRental;
    }

    @Override
    public Rental returnBook(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId);
        if (rental == null) {
            throw new RuntimeException("Rental not found");
        }

        rental.setReturnDate(LocalDate.now());
        rentalRepository.save(rental);
        Rental updatedRental = rentalRepository.findById(rentalId);
        return updatedRental;
    }

    @Override
    public BigDecimal calculateFine(Long rentalId) {
        Rental rental = rentalRepository.findById(rentalId);
        if (rental == null) {
            throw new RuntimeException("Rental not found");
        }

        long daysRented = ChronoUnit.DAYS.between(rental.getRentDate(), rental.getReturnDate() != null ? rental.getReturnDate() : LocalDate.now());
        BigDecimal fine = BigDecimal.ZERO;
        if (daysRented > 14) {
            fine = BigDecimal.valueOf(daysRented - 14).multiply(BigDecimal.valueOf(1)); // Assuming $1 per day after 14 days
        }

        rental.setFine(fine);
        rentalRepository.save(rental);

        return fine;
    }

    @Override
    public List<Book> getAllBooks() {
	    return bookRepository.findAllBooks();
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }
}
