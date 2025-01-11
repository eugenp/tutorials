package com.baeldung.statickeyword;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static com.baeldung.statickeyword.Library.MAX_BOOKS;

class LibraryUnitTest {

    private Library library;

    @BeforeEach
    void setUp() {
        library = new Library("Test Library", 100);
        Library.totalBooks = 100;

    }

    @Test
    void givenLibraryClass_whenAccessingStaticMethod_thenReturnCorrectOutput() {
        assertEquals(100, Library.getTotalBooks());
    }

    @Test
    void givenLibraryClass_whenAccessingNestedStaticClass_thenReturnCorrectAverageBooks() {
        Library secondlibrary = new Library("Another Library", 200);
        Library[] libraries = { library, secondlibrary };
        assertEquals(150, Library.LibraryStatistics.getAverageBooks(libraries));
    }

    @Test
    void givenLibraryClass_whenAccessingNestedRecord_thenReturnRecordData() {
        Library.BookRecord bookRecord = new Library.BookRecord("Test Book", "Test Author");
        assertEquals("Test Book", bookRecord.title());
        assertEquals("Test Author", bookRecord.author());
    }

    @Test
    void givenLibraryClass_whenMemberIsStaticImport_thenReturnCorrectMaxValue() {
        assertEquals(10000, MAX_BOOKS);
    }

}