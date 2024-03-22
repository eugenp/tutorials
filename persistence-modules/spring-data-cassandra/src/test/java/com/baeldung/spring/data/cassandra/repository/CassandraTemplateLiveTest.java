package com.baeldung.spring.data.cassandra.repository;

import static junit.framework.TestCase.assertNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.spring.data.cassandra.config.CassandraConfig;
import com.baeldung.spring.data.cassandra.model.Book;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.google.common.collect.ImmutableSet;

/**
 * Live test for Cassandra testing.
 *
 * This can be converted to IntegrationTest once cassandra-unit tests can be executed in parallel and
 * multiple test servers started as part of test suite.
 *
 * Open cassandra-unit issue for parallel execution: https://github.com/jsevellec/cassandra-unit/issues/155
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class CassandraTemplateLiveTest {
    private static final Log LOGGER = LogFactory.getLog(CassandraTemplateLiveTest.class);

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace " + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    public static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";

    public static final String DATA_TABLE_NAME = "book";

    @Autowired
    private CassandraAdminOperations adminTemplate;

    @Autowired
    private CassandraOperations cassandraTemplate;

    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, ConfigurationException, IOException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        final CqlSession session = CqlSession.builder().addContactPoints(Collections.singleton(new InetSocketAddress("127.0.0.1", 9142))).build();
        LOGGER.info("Server Started at 127.0.0.1:9142... ");
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
        LOGGER.info("KeySpace created and activated.");
        Thread.sleep(5000);
    }

    @Before
    public void createTable() {
        adminTemplate.createTable(true, CqlIdentifier.fromCql(DATA_TABLE_NAME), Book.class, new HashMap<>());
    }

    @Test
    public void whenSavingBook_thenAvailableOnRetrieval() {
        final Book javaBook = new Book(UUID.randomUUID(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        cassandraTemplate.insert(javaBook);
        final String select = "SELECT * FROM book where title = 'Head First Java' AND publisher = \'O'Reilly Media\' LIMIT 10";
        final Book retrievedBook = cassandraTemplate.selectOne(select, Book.class);
        assertEquals(javaBook.getId(), retrievedBook.getId());
    }

    @Test
    public void whenSavingBooks_thenAllAvailableOnRetrieval() {
        final Book javaBook = new Book(UUID.randomUUID(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        final Book dPatternBook = new Book(UUID.randomUUID(), "Head Design Patterns", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        final List<Book> bookList = new ArrayList<>();
        bookList.add(javaBook);
        bookList.add(dPatternBook);
        cassandraTemplate.insert(bookList);

        final List<Book> retrievedBooks = cassandraTemplate.select("SELECT * FROM book limit 10", Book.class);
        assertThat(retrievedBooks.size(), is(2));
        assertEquals(javaBook.getId(), retrievedBooks.get(0).getId());
        assertEquals(dPatternBook.getId(), retrievedBooks.get(1).getId());
    }

    @Test
    public void whenUpdatingBook_thenShouldUpdatedOnRetrieval() {
        final Book javaBook = new Book(UUID.randomUUID(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        cassandraTemplate.insert(javaBook);
        final Book retrievedBook = cassandraTemplate.selectOne("SELECT * FROM book limit 10", Book.class);
        retrievedBook.setTags(ImmutableSet.of("Java", "Programming"));
        cassandraTemplate.update(retrievedBook);
        final Book retrievedUpdatedBook = cassandraTemplate.selectOne("SELECT * FROM book limit 10", Book.class);
        assertEquals(retrievedBook.getTags(), retrievedUpdatedBook.getTags());
    }

    @Test
    public void whenDeletingASelectedBook_thenNotAvailableOnRetrieval() {
        final Book javaBook = new Book(UUID.randomUUID(), "Head First Java", "OReilly Media", ImmutableSet.of("Computer", "Software"));
        cassandraTemplate.insert(javaBook);
        cassandraTemplate.delete(javaBook);
        final Book retrievedUpdatedBook = cassandraTemplate.selectOne("SELECT * FROM book limit 10", Book.class);
        assertNull(retrievedUpdatedBook);
    }

    @Test
    public void whenDeletingAllBooks_thenNotAvailableOnRetrieval() {
        final Book javaBook = new Book(UUID.randomUUID(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        final Book dPatternBook = new Book(UUID.randomUUID(), "Head Design Patterns", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        cassandraTemplate.insert(javaBook);
        cassandraTemplate.insert(dPatternBook);
        //TODO
        // cassandraTemplate.deleteAll(Book.class);
        final Book retrievedUpdatedBook = cassandraTemplate.selectOne("SELECT * FROM book limit 10", Book.class);
        assertNull(retrievedUpdatedBook);
    }

    @Test
    public void whenAddingBooks_thenCountShouldBeCorrectOnRetrieval() {
        final Book javaBook = new Book(UUID.randomUUID(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        final Book dPatternBook = new Book(UUID.randomUUID(), "Head Design Patterns", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        cassandraTemplate.insert(javaBook);
        cassandraTemplate.insert(dPatternBook);
        final long bookCount = cassandraTemplate.count(Book.class);
        assertEquals(2, bookCount);
    }

    @After
    public void dropTable() {
        adminTemplate.dropTable(CqlIdentifier.fromCql(DATA_TABLE_NAME));
    }

    @AfterClass
    public static void stopCassandraEmbedded() {
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }
}
