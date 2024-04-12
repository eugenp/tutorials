package com.baeldung.cassandra.java.client.repository;

import com.baeldung.cassandra.java.client.domain.Book;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.datastax.driver.core.utils.UUIDs;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.testcontainers.containers.CassandraContainer;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class BookRepositoryLiveTest {
    @Rule
    public CassandraContainer<?> cassandra = new CassandraContainer<>("cassandra:3.11.2");

    private BookRepository bookRepository;

    private Session session;

    final String KEYSPACE_NAME = "testLibrary";
    final String BOOKS = "books";
    final String BOOKS_BY_TITLE = "booksByTitle";

    @Before
    public void connect() {
        cassandra.start();

        this.session = Cluster
                .builder()
                .addContactPoint(cassandra.getHost())
                .withPort(cassandra.getMappedPort(CassandraContainer.CQL_PORT))
                .build()
                .newSession();

        KeyspaceRepository schemaRepository = new KeyspaceRepository(session);
        schemaRepository.createKeyspace(KEYSPACE_NAME, "SimpleStrategy", 1);
        schemaRepository.useKeyspace(KEYSPACE_NAME);
        bookRepository = new BookRepository(session);
    }

    @Test
    public void whenCreatingATable_thenCreatedCorrectly() {
        bookRepository.deleteTable(BOOKS);
        bookRepository.createTable();

        ResultSet result = session.execute("SELECT * FROM " + KEYSPACE_NAME + "." + BOOKS + ";");

        // Collect all the column names in one list.
        List<String> columnNames = result.getColumnDefinitions().asList().stream().map(cl -> cl.getName()).collect(Collectors.toList());
        assertEquals(columnNames.size(), 4);
        assertTrue(columnNames.contains("id"));
        assertTrue(columnNames.contains("title"));
        assertTrue(columnNames.contains("author"));
        assertTrue(columnNames.contains("subject"));
    }

    @Test
    public void whenAlteringTable_thenAddedColumnExists() {
        bookRepository.deleteTable(BOOKS);
        bookRepository.createTable();

        bookRepository.alterTablebooks("publisher", "text");

        ResultSet result = session.execute("SELECT * FROM " + KEYSPACE_NAME + "." + BOOKS + ";");

        boolean columnExists = result.getColumnDefinitions().asList().stream().anyMatch(cl -> cl.getName().equals("publisher"));
        assertTrue(columnExists);
    }

    @Test
    public void whenAddingANewBook_thenBookExists() {
        bookRepository.deleteTable(BOOKS_BY_TITLE);
        bookRepository.createTableBooksByTitle();

        String title = "Effective Java";
        String author = "Joshua Bloch";
        Book book = new Book(UUIDs.timeBased(), title, author, "Programming");
        bookRepository.insertbookByTitle(book);

        Book savedBook = bookRepository.selectByTitle(title);
        assertEquals(book.getTitle(), savedBook.getTitle());
    }

    @Test
    public void whenAddingANewBookBatch_ThenBookAddedInAllTables() {
        // Create table books
        bookRepository.deleteTable(BOOKS);
        bookRepository.createTable();

        // Create table booksByTitle
        bookRepository.deleteTable(BOOKS_BY_TITLE);
        bookRepository.createTableBooksByTitle();

        String title = "Effective Java";
        String author = "Joshua Bloch";
        Book book = new Book(UUIDs.timeBased(), title, author, "Programming");
        bookRepository.insertBookBatch(book);

        List<Book> books = bookRepository.selectAll();

        assertEquals(1, books.size());
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Effective Java")));

        List<Book> booksByTitle = bookRepository.selectAllBookByTitle();

        assertEquals(1, booksByTitle.size());
        assertTrue(booksByTitle.stream().anyMatch(b -> b.getTitle().equals("Effective Java")));
    }

    @Test
    public void whenSelectingAll_thenReturnAllRecords() {
        bookRepository.deleteTable(BOOKS);
        bookRepository.createTable();

        Book book = new Book(UUIDs.timeBased(), "Effective Java", "Joshua Bloch", "Programming");
        bookRepository.insertbook(book);

        book = new Book(UUIDs.timeBased(), "Clean Code", "Robert C. Martin", "Programming");
        bookRepository.insertbook(book);

        List<Book> books = bookRepository.selectAll();

        assertEquals(2, books.size());
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Effective Java")));
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Clean Code")));
    }

    @Test
    public void whenDeletingABookByTitle_thenBookIsDeleted() {
        bookRepository.deleteTable(BOOKS_BY_TITLE);
        bookRepository.createTableBooksByTitle();

        Book book = new Book(UUIDs.timeBased(), "Effective Java", "Joshua Bloch", "Programming");
        bookRepository.insertbookByTitle(book);

        book = new Book(UUIDs.timeBased(), "Clean Code", "Robert C. Martin", "Programming");
        bookRepository.insertbookByTitle(book);

        bookRepository.deletebookByTitle("Clean Code");

        List<Book> books = bookRepository.selectAllBookByTitle();
        assertEquals(1, books.size());
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Effective Java")));
        assertFalse(books.stream().anyMatch(b -> b.getTitle().equals("Clean Code")));

    }

    @Test(expected = InvalidQueryException.class)
    public void whenDeletingATable_thenUnconfiguredTable() {
        bookRepository.createTable();
        bookRepository.deleteTable(BOOKS);

        session.execute("SELECT * FROM " + KEYSPACE_NAME + "." + BOOKS + ";");
    }
}
