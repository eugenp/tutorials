package org.baeldung.spring.data.cassandra.repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.google.common.collect.ImmutableSet;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.transport.TTransportException;
import org.baeldung.spring.data.cassandra.config.CassandraConfig;
import org.baeldung.spring.data.cassandra.model.Book;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class BookRepositoryIntegrationTest {

    private static final Log LOGGER = LogFactory.getLog(BookRepositoryIntegrationTest.class);

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    public static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";

    public static final String DATA_TABLE_NAME = "book";

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CassandraAdminOperations adminTemplate;

    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1")
                .withPort(9142).build();
        LOGGER.info("Server Started at 127.0.0.1:9142... ");
        Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
        Thread.sleep(5000);
        LOGGER.info("KeySpace created and activated.");
    }

    @Before
    public void createTable() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), Book.class, new HashMap<String, Object>());
    }

    @Test
    public void whenSavingBook_thenAvailableOnRetrieval() {
        Book javaBook = new Book(UUIDs.timeBased(), "Head First Java",
                "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        bookRepository.save(ImmutableSet.of(javaBook));
        Iterable<Book> books = bookRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
        assertEquals(javaBook.getId(), books.iterator().next().getId());
    }

    @Test
    public void whenUpdatingBooks_thenAvailableOnRetrieval() {
        Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        bookRepository.save(ImmutableSet.of(javaBook));
        Iterable<Book> books = bookRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
        javaBook.setTitle("Head First Java Second Edition");
        bookRepository.save(ImmutableSet.of(javaBook));
        Iterable<Book> updateBooks = bookRepository.findByTitleAndPublisher("Head First Java Second Edition", "O'Reilly Media");
        assertEquals(javaBook.getTitle(), updateBooks.iterator().next().getTitle());
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void whenDeletingExistingBooks_thenNotAvailableOnRetrieval() {
        Book javaBook = new Book(UUIDs.timeBased(), "Head First Java", "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        bookRepository.save(ImmutableSet.of(javaBook));
        bookRepository.delete(javaBook);
        Iterable<Book> books = bookRepository.findByTitleAndPublisher("Head First Java", "O'Reilly Media");
        assertNotEquals(javaBook.getId(), books.iterator().next().getId());
    }

    @Test
    public void whenSavingBooks_thenAllShouldAvailableOnRetrieval() {
        Book javaBook = new Book(UUIDs.timeBased(), "Head First Java",
                "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        Book dPatternBook = new Book(UUIDs.timeBased(), "Head Design Patterns",
                "O'Reilly Media", ImmutableSet.of("Computer", "Software"));
        bookRepository.save(ImmutableSet.of(javaBook));
        bookRepository.save(ImmutableSet.of(dPatternBook));
        Iterable<Book> books = bookRepository.findAll();
        int bookCount = 0;
        for (Book book : books) bookCount++;
        assertEquals(bookCount, 2);
    }

    @After
    public void dropTable() {
       adminTemplate.dropTable(CqlIdentifier.cqlId(DATA_TABLE_NAME));
    }

    @AfterClass
    public static void stopCassandraEmbedded() {
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }
}
