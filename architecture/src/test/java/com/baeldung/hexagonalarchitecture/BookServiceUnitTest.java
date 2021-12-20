package com.baeldung.hexagonalarchitecture;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.baeldung.hexagonalarchitecture.domain.Book;
import com.baeldung.hexagonalarchitecture.domain.BookService;
import com.baeldung.hexagonalarchitecture.domain.IBookRepository;
import com.baeldung.hexagonalarchitecture.domain.IBookService;

@DisplayName("Book service")
@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {

    private IBookService bookService;

    @Mock
    private IBookRepository bookRepository;

    @BeforeEach
    void init() {
        bookService = new BookService(bookRepository);
    }

    @Test
    @DisplayName("When listing all books then it should use repository")
    void whenListingAllBooks_thenItShouldUseRepository() {
        List<Book> booksFromRepository = new ArrayList<>();
        booksFromRepository.add(new Book("testIsbn", "testTitle"));
        when(bookRepository.findAllBooks()).thenReturn(booksFromRepository);

        List<Book> booksFromService = bookService.listAllBooks();

        verify(bookRepository).findAllBooks();
        assertThat(booksFromService).containsExactlyElementsOf(booksFromRepository);
    }
}
