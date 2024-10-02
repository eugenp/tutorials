package com.baeldung.parameterresolver;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(BookParameterResolver.class)
public class ParameterResolverExceptionUnitTest {

    @Test
    void givenBook_whenConfiguringParameterResolver_thenParameterResolves(Book book) {
        assertThat(book.getTitle()).isEqualTo("Wuthering Heights");
    }

    // Uncomment these annotations to reproduce the ParameterResolutionException
    // @ParameterizedTest
    // @CsvSource({"Wuthering Heights, Charlotte Bronte", "Jane Eyre, Charlotte Bronte"})
    // @Test
    void givenParameterizedTest_whenUsingTestAnnotation_thenParameterFailsToResolve(String title, String author) {
        Book book = new Book(title, author);
        assertThat(book.getTitle().length()).isGreaterThan(0);
        assertThat(book.getAuthor().length()).isGreaterThan(0);
    }

    // Uncomment these annotations to reproduce the ParameterResolutionException
    // @Test
    void givenBasicTest_whenPassingParameters_thenParameterFailsToResolve(String title, String author) {
        Book book = new Book(title, author);
        assertThat(book.getTitle().length()).isGreaterThan(0);
        assertThat(book.getAuthor().length()).isGreaterThan(0);
    }
}
