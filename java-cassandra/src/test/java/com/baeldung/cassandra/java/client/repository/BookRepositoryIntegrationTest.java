package com.baeldung.cassandra.java.client.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.cassandra.java.client.CassandraConnector;
import com.baeldung.cassandra.java.client.domain.Book;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.exceptions.InvalidQueryException;
import com.datastax.driver.core.utils.UUIDs;

public class BookRepositoryIntegrationTest {

    private KeyspaceRepository schemaRepository;

    private BookRepository bookRepository;

    private Session session;

    final String KEYSPACE_NAME = "testLibrary";
    final String BOOKS = "books";
    final String BOOKS_BY_TITLE = "booksByTitle";

    @BeforeClass
    public static void init() throws ConfigurationException, TTransportException, IOException, InterruptedException {
        // Start an embedded Cassandra Server
        EmbeddedCassandraServerHelper.startEmbeddedCassandra(20000L);
    }

    @Before
    public void connect() {
        CassandraConnector client = new CassandraConnector();
        client.connect("127.0.0.1", 9142);
        this.session = client.getSession();
        schemaRepository = new KeyspaceRepository(session);
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

    @AfterClass
    public static void cleanup() {
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }
}
