package com.baeldung.hexagonal.architecture.domain;

import com.baeldung.hexagonal.architecture.adapter.out.BookWriterStub;
import com.baeldung.hexagonal.architecture.port.out.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.Collections.EMPTY_LIST;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class BookServiceDomainUnitTest {

    private BookWriterStub writer;
    private BookRepository bookRepository;
    private BookServiceDomain bookServiceDomain;

    @BeforeEach
    public void setUp() {
        bookRepository = mock(BookRepository.class);
        writer = new BookWriterStub();
        bookServiceDomain = new BookServiceDomain(bookRepository, writer);
    }

    @Test
    void givenListOfBooks_whenInvoke_thenListBooksWithCapitalizedTitleAndAuthor() {
        Book book1 = new Book("1111", "title1test", "author1test");
        Book book2 = new Book("2222", "title2test", "author2test");
        List<Book> books = Arrays.asList(book1, book2);
        when(bookRepository.getAllBooks()).thenReturn(books);

        bookServiceDomain.invoke();

        verify(bookRepository).getAllBooks();
        List<String> lines = writer.getWrittenOutLines();
        assertThat(lines.get(0)).isEqualTo("TITLE1TEST - author1test");
        assertThat(lines.get(1)).isEqualTo("TITLE2TEST - author2test");
    }

    @Test
    void givenNoBooksPresent_whenInvoke_thenShouldReturnEmptyString() {
        when(bookRepository.getAllBooks()).thenReturn(EMPTY_LIST);

        bookServiceDomain.invoke();

        verify(bookRepository).getAllBooks();
        List<String> lines = writer.getWrittenOutLines();
        assertThat(lines).isEmpty();
    }
}