package com.baeldung.javafeatures.entities;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class BookCopyUnitTest {

    @Test
    public void whenShallowCopyInvoked_thenObjectsAreNotSame_butParametersAreSame() {
        Book originalBook = new Book(new Title("Original"), "Baeldung");

        Book shallowCopy = new Book(originalBook.getTitle(), "Baeldung");

        assertThat(shallowCopy).isNotSameAs(originalBook);
        assertThat(shallowCopy.getTitle()).isSameAs(originalBook.getTitle());
    }

    @Test
    public void whenModifyingOriginal_thenFieldsOfCopyAreChanged() {
        Book originalBook = new Book(new Title("Original"), "Baeldung");
        Book shallowCopy = new Book(originalBook.getTitle(), "Baeldung");

        shallowCopy.getTitle()
            .setName("Copy");

        assertThat(originalBook.getTitle()
            .getName()).isNotEqualTo("Original");
        assertThat(originalBook.getTitle()
            .getName()).isEqualTo(shallowCopy.getTitle()
            .getName());
    }

    @Test
    public void whenModifyingOriginal_thenFieldsOfDeepCopyAreNotAffected() {
        Book originalBook = new Book(new Title("Original"), "Baeldung");
        Book deepCopy = originalBook.deepCopy();

        deepCopy.getTitle()
            .setName("Copy");

        assertThat(originalBook.getTitle()
            .getName()).isEqualTo("Original");
        assertThat(originalBook).isNotSameAs(deepCopy);
        assertThat(originalBook.getTitle()).isNotSameAs(deepCopy.getTitle());
    }
}