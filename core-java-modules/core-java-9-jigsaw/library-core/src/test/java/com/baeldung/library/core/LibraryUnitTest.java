package com.baeldung.library.core;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class LibraryUnitTest {

    @Test
    void givenEmptyLibrary_whenAddABook_thenLibraryHasOneBook() {
        Library library = new Library();
        Book theLordOfTheRings = new Book("The Lord of the Rings", "J.R.R. Tolkien");
        library.addBook(theLordOfTheRings);
        int expected = 1;
        int actual = library.getBooks().size();
        assertEquals(expected, actual);
    }

    @Test
    void givenTheLibraryWithABook_whenRemoveABook_thenLibraryIsEmpty() {
        Library library = new Library();
        Book theLordOfTheRings = new Book("The Lord of the Rings", "J.R.R. Tolkien");
        library.addBook(theLordOfTheRings);
        library.removeBook(theLordOfTheRings);
        int expected = 0;
        int actual = library.getBooks().size();
        assertEquals(expected, actual);
    }

    @Test
    void givenTheLibraryWithSeveralBook_whenRemoveABookByAuthor_thenLibraryHasNoBooksByTheAuthor() {
        Library library = new Library();
        Book theLordOfTheRings = new Book("The Lord of the Rings", "J.R.R. Tolkien");
        Book theHobbit = new Book("The Hobbit", "J.R.R. Tolkien");
        Book theSilmarillion = new Book("The Silmarillion", "J.R.R. Tolkien");
        Book theHungerGames = new Book("The Hunger Games", "Suzanne Collins");
        library.addBook(theLordOfTheRings);
        library.addBook(theHobbit);
        library.addBook(theSilmarillion);
        library.addBook(theHungerGames);
        library.removeBookByAuthor("J.R.R. Tolkien");
        int expected = 1;
        int actual = library.getBooks().size();
        assertEquals(expected, actual);
    }

}