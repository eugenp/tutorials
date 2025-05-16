package com.baeldung.spring.data.cassandra.repository;

import com.baeldung.spring.data.cassandra.config.CassandraConfig;
import com.baeldung.spring.data.cassandra.model.Book;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.google.common.collect.ImmutableSet;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.cql.CqlIdentifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class BookRepositoryLiveTest {

    private static final String KEYSPACE_CREATION_QUERY =
            "CREATE KEYSPACE IF NOT EXISTS testKeySpace " +
                    "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '1' };";

    private static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";
    private static final String TABLE_NAME = "book";
    static CassandraContainer<?> cassandraContainer;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private CassandraAdminOperations adminTemplate;

    @BeforeClass
    public static void setupCassandra() {
        cassandraContainer = new CassandraContainer<>(
                DockerImageName.parse("cassandra:4.1.9")
        ).withExposedPorts(9042);
        cassandraContainer.start();

        try (CqlSession session = CqlSession.builder()
                .addContactPoint(cassandraContainer.getContactPoint())
                .withLocalDatacenter(cassandraContainer.getLocalDatacenter())
                .build()) {

            session.execute(SimpleStatement.newInstance(KEYSPACE_CREATION_QUERY));
            session.execute(SimpleStatement.newInstance(KEYSPACE_ACTIVATE_QUERY));
        }
    }

    @Before
    public void createTable() {
        adminTemplate.createTable(
                true,
                CqlIdentifier.of(TABLE_NAME).toCqlIdentifier(),
                Book.class,
                null
        );
    }

    @Test
    public void whenSavingBook_thenAvailableOnRetrieval() {
        Book book = new Book(
                Uuids.timeBased(),
                "Effective Java",
                "Addison-Wesley",
                ImmutableSet.of("Programming", "Java")
        );

        Book savedBook = bookRepository.save(book);
        Optional<Book> foundBook = bookRepository.findById(savedBook.getId());

        assertTrue(foundBook.isPresent());
        assertEquals(savedBook.getTitle(), foundBook.get().getTitle());
    }

    @Test
    public void whenUpdatingBooks_thenAvailableOnRetrieval() {
        final Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        bookRepository.save(javaBook);
        final Iterable<Book> books = bookRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
        javaBook.setTitle("Head First Java Second Edition");
        bookRepository.save(javaBook);
        final Iterable<Book> updateBooks = bookRepository.findByTitleAndPublisher("Head First Java Second Edition", "O'Reilly Media");
        assertEquals(javaBook.getTitle(), updateBooks.iterator().next().getTitle());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void whenDeletingExistingBooks_thenNotAvailableOnRetrieval() {
        final Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        bookRepository.save(javaBook);
        bookRepository.delete(javaBook);
        final Iterable<Book> books = bookRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
        assertNotEquals(javaBook.getId(), books.iterator().next().getId());
    }

    @Test
    public void whenSavingBooks_thenAllShouldAvailableOnRetrieval() {
        final Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        final Book dPatternBook = new Book(UUIDs.timeBased(), "Head Design Patterns", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        bookRepository.save(javaBook);
        bookRepository.save(dPatternBook);
        final Iterable<Book> books = bookRepository.findAll();
        int bookCount = 0;
        for (final Book book : books) {
            bookCount++;
        }
        assertEquals(bookCount, 2);
    }

    @After
    public void dropTable() {
        adminTemplate.dropTable(CqlIdentifier.of(TABLE_NAME).getClass());
    }

    @AfterClass
    public static void tearDown() {
        if (cassandraContainer != null) {
            cassandraContainer.stop();
        }
    }
}
