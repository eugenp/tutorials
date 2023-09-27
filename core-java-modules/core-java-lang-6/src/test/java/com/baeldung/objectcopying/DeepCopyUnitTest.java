package com.baeldung.objectcopying;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class DeepCopyUnitTest {

    @Test
    public void givenDeepCopy_whenInsertingIntoOriginalList_thenNewListIsNotMutated() {
        // Given a bookshelf and a shallow copy of the bookshelf
        var alice = new DeepCopy.Book("Alice in Wonderland", "Lewis Carroll");
        var journey = new DeepCopy.Book("Journey to the Center of the Earth", "Jules Verne");
        var books = new ArrayList<DeepCopy.Book>();
        books.add(alice);
        books.add(journey);
        var shelfOne = new DeepCopy.Bookshelf(5, books);
        var shelfTwo = new DeepCopy.Bookshelf(shelfOne);

        // When a new book is added it to the first bookshelf
        var wuthering = new DeepCopy.Book("Wuthering Heights", "Charlotte Bronte");
        shelfOne.getBooks()
            .add(wuthering);

        // Then the first list is updated since the second list of books is a shallow copy
        assertThat(shelfOne.getBooks()
            .size()).isEqualTo(3);
        assertThat(shelfTwo.getBooks()
            .size()).isEqualTo(2);
    }

    @Test
    public void givenDeepCopy_whenMutatingListItem_thenOriginalListItemIsNotMutated() {
        // Given a bookshelf and a shallow copy of the bookshelf
        var alice = new DeepCopy.Book("Alice in Wonderland", "Lewis Carroll");
        var journey = new DeepCopy.Book("Journey to the Center of the Earth", "Jules Verne");
        var shelfOne = new DeepCopy.Bookshelf(5, List.of(alice, journey));
        var shelfTwo = new DeepCopy.Bookshelf(shelfOne);

        // When a book in second bookshelf is renamed
        shelfTwo.getBooks()
            .get(0)
            .setTitle("Alicia in Wonderland");

        // Then the first list's book is updated since the second list of books is a shallow copy
        assertThat(shelfOne.getBooks()
            .get(0)
            .getTitle()).isEqualTo("Alice in Wonderland");
        assertThat(shelfTwo.getBooks()
            .get(0)
            .getTitle()).isEqualTo("Alicia in Wonderland");
    }
}
