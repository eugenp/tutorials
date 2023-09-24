package com.baeldung.objectcopying;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class ShallowCopyUnitTest {

    @Test
    public void givenShallowCopy_whenInsertingIntoNewList_thenOriginalIsMutated() {
        // Given a bookshelf and a shallow copy of the bookshelf
        var alice = new ShallowCopy.Book("Alice in Wonderland", "Lewis Carroll");
        var journey = new ShallowCopy.Book("Journey to the Center of the Earth", "Jules Verne");
        var books = new ArrayList<ShallowCopy.Book>();
        books.add(alice);
        books.add(journey);
        var shelfOne = new ShallowCopy.Bookshelf(5, books);
        var shelfTwo = new ShallowCopy.Bookshelf(shelfOne);
        System.out.println("Shelf One: " + shelfOne);
        System.out.println("Shelf Two: " + shelfTwo);

        // When a new book is added it to the second bookshelf
        var wuthering = new ShallowCopy.Book("Wuthering Heights", "Charlotte Bronte");
        shelfTwo.getBooks()
            .add(wuthering);

        // Then the first list is updated since the second list of books is a shallow copy
        System.out.println("Shelf One: " + shelfOne);
        System.out.println("Shelf Two: " + shelfTwo);
        assertThat(shelfOne.getBooks()).isEqualTo(shelfTwo.getBooks());
    }

    @Test
    public void givenShallowCopy_whenMutatingListItem_thenOriginalListItemIsMutated() {
        // Given a bookshelf and a shallow copy of the bookshelf
        var alice = new ShallowCopy.Book("Alice in Wonderland", "Lewis Carroll");
        var journey = new ShallowCopy.Book("Journey to the Center of the Earth", "Jules Verne");
        var shelfOne = new ShallowCopy.Bookshelf(5, List.of(alice, journey));
        var shelfTwo = new ShallowCopy.Bookshelf(shelfOne);
        System.out.println("Shelf One: " + shelfOne);
        System.out.println("Shelf Two: " + shelfTwo);

        // When a book in second bookshelf is renamed
        shelfTwo.getBooks()
            .get(0)
            .setTitle("Alicia in Wonderland");

        // Then the first list's book is updated since the second list of books is a shallow copy
        System.out.println("Shelf One: " + shelfOne);
        System.out.println("Shelf Two: " + shelfTwo);
        assertThat(shelfOne.getBooks()).isEqualTo(shelfTwo.getBooks());
    }
}
