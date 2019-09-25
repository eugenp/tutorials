package com.baeldung.hexagonal.architecture.core;

import com.baeldung.hexagonal.architecture.model.Book;
import com.baeldung.hexagonal.architecture.model.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
class LibraryUnitTest {
    @Mock
    private BookRepository bookRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private Library subject;

    @Test
    public void whenGetBooksIsCalled_thenTheCallIsForwardedToTheRepository() {
        subject.getBooks();

        verify(bookRepository).loadBooks();

        verifyNoMoreInteractions(bookRepository, userRepository);
    }

    @Test
    public void givenAnAvailableBook_whenRentBookIsCalled_thenTheCallIsSuccess() {
        Book book = new Book(1L, "book", null);
        User user = new User(2L, "name");

        when(bookRepository.loadBook(1L)).thenReturn(book);
        when(userRepository.loadUser(2L)).thenReturn(user);

        subject.rentBook(1L, 2L);

        // the book should now have a renter
        assertEquals(2L, book.getRenter().getId());

        verify(bookRepository).loadBook(1L);
        verify(userRepository).loadUser(2L);

        verify(bookRepository).saveBook(book);

        verifyNoMoreInteractions(bookRepository, userRepository);
    }

    @Test
    public void givenAnUnavailableBook_whenRentBookIsCalled_thenTheCallFails() {
        User otherUser = new User(1L, "Pi");
        Book book = new Book(1L, "book", otherUser);
        User user = new User(2L, "name");

        when(bookRepository.loadBook(1L)).thenReturn(book);
        when(userRepository.loadUser(2L)).thenReturn(user);

        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            subject.rentBook(1L, 2L);
        });

        verify(bookRepository).loadBook(1L);
        verify(userRepository).loadUser(2L);

        verifyNoMoreInteractions(bookRepository, userRepository);
    }
}