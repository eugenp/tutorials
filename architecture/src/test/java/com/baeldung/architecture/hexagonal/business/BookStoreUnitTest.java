package com.baeldung.architecture.hexagonal.business;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;

import com.baeldung.architecture.hexagonal.business.bordery.driven.BookPersistencePort;
import com.baeldung.architecture.hexagonal.business.bordery.driver.BookRentalPort;
import com.baeldung.architecture.hexagonal.business.bordery.dto.BookDto;

class BookStoreUnitTest {

    MemoryBookPersistenceAdapter persistence = new MemoryBookPersistenceAdapter();
    BookRentalPort bookRental = new BookStore(persistence);

    @Test
    void givenNewBook_whenReadIt_thenReturnItFromAdapter() {
        String bookName = "Hexagonal Architecture";
        String bookAuthor = "Baeldung";
        BookDto book = new BookDto(bookAuthor, bookName);

        assertThat(bookRental.donate(book)).isEqualToComparingFieldByField(persistence.get(3L));
    }

    @Test
    void givenText_whenReadIt_thenReturnIt() {
        String bookName = "Hexagonal Architecture";
        bookRental.searchByName(bookName);

        assertThat(bookRental.searchByName(bookName)).containsExactly(persistence.get(1L));
    }

    @Test
    public void givenNewBook_whenReadIt_thenReturnIt() {
        String bookName = "Hexagonal Architecture";
        String bookAuthor = "Baeldung";
        BookDto book = new BookDto(bookAuthor, bookName);

        BookPersistencePort persistence = mock(BookPersistencePort.class);
        BookRentalPort bookRental = new BookStore(persistence);
        bookRental.donate(book);

        verify(persistence, times(1)).save(book);
    }
}
