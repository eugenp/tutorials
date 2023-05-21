package com.baeldung.library.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.baeldung.library.core.Book;
import com.baeldung.library.core.Library;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    void givenTheLibraryWithABook_whenRemoveABook_thenLibraryIsEmpty()
        throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Library library = new Library();
        Book theLordOfTheRings = new Book("The Lord of the Rings", "J.R.R. Tolkien");
        library.addBook(theLordOfTheRings);
        Method removeBook = Library.class.getDeclaredMethod("removeBook", Book.class);
        removeBook.setAccessible(true);
        removeBook.invoke(library, theLordOfTheRings);
        int expected = 0;
        int actual = library.getBooks().size();
        assertEquals(expected, actual);
    }
@Test
void givenTheLibraryWithSeveralBook_whenRemoveABookByAuthor_thenLibraryHasNoBooksByTheAuthor() {
    TestLibrary library = new TestLibrary();
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