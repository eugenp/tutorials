package com.baeldung.hexagonalapp.core;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BookTest {

    @Test
    void whenLendingOut_theBorrowerNameIsUpdated() {
        Book book = new Book(1L, "Moby Dick", "Herman Melville");
        book.lendOutTo("John Doe");

        assertThat(book.getBorrower()).isEqualTo("John Doe");
    }

    @Test
    void whenLendingOutABorrowedBook_thenBookAlreadyLentOutExceptionIsThrown() {
        Book book = new Book(1L, "Moby Dick", "Herman Melville");
        book.lendOutTo("Jane Doe");

        assertThatThrownBy(() -> book.lendOutTo("John Smith"))
                .isInstanceOf(BookAlreadyLentOut.class);
    }

    @Test
    void whenGivingBackABook_thenBorrowerIsUpdated() {
        Book book = new Book(1L, "Moby Dick", "Herman Melville");
        book.lendOutTo("Jane Doe");

        book.giveBack();

        assertThat(book.getBorrower()).isNull();
    }
}