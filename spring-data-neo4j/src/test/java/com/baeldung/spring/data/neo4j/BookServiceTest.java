package com.baeldung.spring.data.neo4j;

import com.baeldung.spring.data.neo4j.config.LibraryNeo4jConfiguration;
import com.baeldung.spring.data.neo4j.model.Book;
import com.baeldung.spring.data.neo4j.model.Person;
import com.baeldung.spring.data.neo4j.service.BookService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = LibraryNeo4jConfiguration.class)
public class BookServiceTest {

    private static final Log LOGGER = LogFactory.getLog(BookServiceTest.class);

    @Autowired
    private BookService bookServiceImpl;

    @Test
    public void testSaveBook() {
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
}
