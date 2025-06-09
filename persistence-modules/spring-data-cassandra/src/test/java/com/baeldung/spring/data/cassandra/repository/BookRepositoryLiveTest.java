package com.baeldung.spring.data.cassandra.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.baeldung.spring.data.cassandra.model.Book;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.oss.driver.api.core.CqlSession;
import com.google.common.collect.ImmutableSet;

@Testcontainers
@SpringBootTest
class BookRepositoryLiveTest {

    private static final String KEYSPACE_NAME = "testKeySpace";
    private static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace " + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '1' };";

    private static final String KEYSPACE_ACTIVATE_QUERY = "USE " + KEYSPACE_NAME + ";";
    private static final String TABLE_NAME = "book";

    @Container
    private static final CassandraContainer<?> cassandraContainer = new CassandraContainer<>("cassandra:4.1.8").withExposedPorts(9042);

    @Autowired
    private BookRepository bookRepository;

    @AfterEach
    void cleanUpDatabase() {
        bookRepository.deleteAll();
    }

    @BeforeAll
    static void setupCassandraConnectionProperties() {
        System.setProperty("spring.cassandra.keyspace-name", KEYSPACE_NAME);
        System.setProperty("spring.cassandra.contact-points", cassandraContainer.getContainerIpAddress());
        System.setProperty("spring.cassandra.port", String.valueOf(cassandraContainer.getMappedPort(9042)));

        try (CqlSession session = CqlSession.builder()
            .addContactPoint(cassandraContainer.getContactPoint())
            .withLocalDatacenter(cassandraContainer.getLocalDatacenter())
            .build()) {

            session.execute(KEYSPACE_CREATION_QUERY);
            session.execute(KEYSPACE_ACTIVATE_QUERY);
        }
    }

    @AfterAll
    static void tearDown() {
        if (cassandraContainer != null) {
            cassandraContainer.stop();
        }
    }

    @Test
    void whenSavingBook_thenAvailableOnRetrieval() {
        final Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        bookRepository.save(javaBook);

        final Iterable<Book> books = bookRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
        assertEquals(javaBook.getId(), books.iterator()
            .next()
            .getId());
    }

    @Test
    void whenUpdatingBooks_thenAvailableOnRetrieval() {
        final Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        bookRepository.save(javaBook);
        final Iterable<Book> books = bookRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
        javaBook.setTitle("Head First Java Second Edition");
        bookRepository.save(javaBook);
        final Iterable<Book> updateBooks = bookRepository.findByTitleAndPublisher("Head First Java Second Edition", "O'Reilly Media");
        assertEquals(javaBook.getTitle(), updateBooks.iterator()
            .next()
            .getTitle());
    }

    @Test
    void whenDeletingExistingBooks_thenNotAvailableOnRetrieval() {
        final Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        bookRepository.save(javaBook);
        bookRepository.delete(javaBook);
        final Iterable<Book> books = bookRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
        assertThrows(NoSuchElementException.class, () -> {
            books.iterator().next();
        });
    }

    @Test
    void whenSavingBooks_thenAllShouldAvailableOnRetrieval() {
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
}
