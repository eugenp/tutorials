package com.baeldung.spring.data.neo4j;

import com.baeldung.spring.data.neo4j.config.LibraryNeo4jConfiguration;
import com.baeldung.spring.data.neo4j.model.Book;
import com.baeldung.spring.data.neo4j.model.Person;
import com.baeldung.spring.data.neo4j.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibraryNeo4jConfiguration.class)
public class BookServiceTest {

    @Autowired
    private BookService bookServiceImpl;

    @Test
    public void testSavingBook() {
        final Person author1 = new Person();
        author1.setName("Mark Twain");
        author1.setBorn(1835);
        final Book book =  new Book();
        book.setTitle("The Adventures of Tom Sawyer");
        book.setReleased(1876);
        book.setPerson(author1);

        final Book savedBook = bookServiceImpl.save(book);
        assertEquals(book.getTitle(), savedBook.getTitle());
   }

    @Test
    public void testFindingTheSavedBook() {
        final Person author1 = new Person();
        author1.setName("Edgar Allan Poe");
        author1.setBorn(1809);
        final Book book =  new Book();
        book.setTitle("The Cask of Amontillado");
        book.setReleased(1846);
        book.setPerson(author1);

        bookServiceImpl.save(book);
        final Book retrievedBook = bookServiceImpl.findBookById(book.getId());
        assertEquals(book.getTitle(), retrievedBook.getTitle());
    }

    @Test
    public void testCountTheSavedBooks() {
        long bookCount = bookServiceImpl.bookCount();
        assertEquals(bookCount, 2);
    }
    @Test
    public void testDeletingASavedBook() {
        final Person author1 = new Person();
        author1.setName("Rider Haggard");
        author1.setBorn(1856);
        final Book book =  new Book();
        book.setTitle("King Solomon's Mines");
        book.setReleased(1885);
        book.setPerson(author1);

        final Book savedBook = bookServiceImpl.save(book);
        bookServiceImpl.delete(savedBook.getId());
        final Book retrievedBook = bookServiceImpl.findBookById(book.getId());
        assertNull(retrievedBook);
    }

    @Test
    public void testDeleteAllSavedBook() {
        bookServiceImpl.deleteAllInGraph();
        final long bookCount = bookServiceImpl.bookCount();
        assertEquals(bookCount, 0);
    }
}
