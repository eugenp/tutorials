package com.baeldung.quarkus.service;

import com.baeldung.quarkus.model.Book;
import com.baeldung.quarkus.repository.BookRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@QuarkusTest
class LibraryServiceInjectMockUnitTest {

    @Inject
    LibraryService libraryService;

    @InjectMock
    BookRepository bookRepository;

    @BeforeEach
    void setUp() {
        when(bookRepository.findBy("Frank Herbert"))
          .thenReturn(Arrays.stream(new Book[] {
            new Book("Dune", "Frank Herbert"),
            new Book("Children of Dune", "Frank Herbert")}));
    }

    @Test
    void whenFindByAuthor_thenBooksShouldBeFound() {
        assertEquals(2, libraryService.find("Frank Herbert").size());
    }

}
