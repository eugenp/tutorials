package com.baeldung.composite.repository;

import com.baeldung.composite.BookApplication;
import com.baeldung.composite.entity.Book;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = BookApplication.class)
public class BookRepositoryIntegrationTest {

    public static final String JAVA_101 = "Java101";
    public static final String JANE = "Jane";
    @Autowired
    BookRepository repository;

    @Before
    public void setUp() {
        Book book1 = new Book("John", JAVA_101, "Tech", 20);
        Book book2 = new Book(JANE, JAVA_101, "Arch", 25);
        Book book3 = new Book(JANE, "Scala101", "Tech", 23);

        repository.saveAll(Arrays.asList(book1, book2, book3));
    }

    @After
    public void tearDown() {
        repository.deleteAll();
    }

    @Test
    public void testFindByName() {
        List<Book> books = repository.findByIdName(JAVA_101);

        assertEquals(2, books.size());
    }

    @Test
    public void testFindByAuthor() {
        List<Book> books = repository.findByIdAuthor(JANE);

        assertEquals(2, books.size());
    }

    @Test
    public void testFindByGenre() {
        List<Book> books = repository.findByIdAuthor(JANE);

        assertEquals(2, books.size());
    }
}