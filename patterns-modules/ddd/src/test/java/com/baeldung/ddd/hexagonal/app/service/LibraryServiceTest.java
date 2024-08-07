package com.baeldung.ddd.hexagonal.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.ddd.hexagonal.app.domain.Book;
import com.baeldung.ddd.hexagonal.app.domain.LibraryServiceImpl;
import com.baeldung.ddd.hexagonal.app.domain.Rental;
import com.baeldung.ddd.hexagonal.app.ports.out.BookRepository;
import com.baeldung.ddd.hexagonal.app.ports.out.RentalRepository;


@SpringBootTest
public class LibraryServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private RentalRepository rentalRepository;

    @InjectMocks
    private LibraryServiceImpl libraryService;

    private Book book;
    private Rental rental;

    @BeforeEach
    public void setUp() {
        book = new Book(1L, "Test Book", "Author");
        rental = new Rental(1L, book, LocalDate.now(), null, BigDecimal.ZERO);
    }

    @Test
    public void testRentBook() {
        when(bookRepository.findById(1L)).thenReturn(book);
        when(rentalRepository.save(any(Rental.class))).thenReturn(rental);

        libraryService.rentBook(1L);

        verify(bookRepository, times(1)).findById(1L);
        verify(rentalRepository, times(1)).save(any(Rental.class));
    }

    @Test
    public void testReturnBook() {
        when(rentalRepository.findById(1L)).thenReturn(rental);

        libraryService.returnBook(1L);

        assertNotNull(rental.getReturnDate());
        verify(rentalRepository, times(2)).findById(1L);
        verify(rentalRepository, times(1)).save(rental);
    }

    @Test
    public void testCalculateFine() {
        rental.setRentDate(LocalDate.now().minusDays(20));
        when(rentalRepository.findById(1L)).thenReturn(rental);

        BigDecimal fine = libraryService.calculateFine(1L);

        assertEquals(new BigDecimal("6"), fine); // Assuming $1 per day after 14 days
        verify(rentalRepository, times(1)).findById(1L);
        verify(rentalRepository, times(1)).save(rental);
    }
}
