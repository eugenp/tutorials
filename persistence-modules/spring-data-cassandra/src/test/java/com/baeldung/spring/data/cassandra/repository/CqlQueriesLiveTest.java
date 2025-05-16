package com.baeldung.spring.data.cassandra.repository;

import com.baeldung.spring.data.cassandra.config.CassandraConfig;
import com.baeldung.spring.data.cassandra.model.Book;
import com.datastax.driver.core.querybuilder.Insert;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.cql.Statement;
import com.google.common.collect.ImmutableSet;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.cql.CqlIdentifier;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static junit.framework.TestCase.assertEquals;

/**
 * Live test for Cassandra testing.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class CqlQueriesLiveTest {
    private static final Logger LOG = LoggerFactory.getLogger(CqlQueriesLiveTest.class);
    private static final String KEYSPACE_CREATION_QUERY =
            "CREATE KEYSPACE IF NOT EXISTS testKeySpace " +
                    "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '1' };";
    private static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";
    private static final String DATA_TABLE_NAME = "book";
    static CassandraContainer<?> cassandraContainer;
    @Autowired
    private CassandraAdminOperations adminTemplate;
    @Autowired
    private CassandraOperations cassandraTemplate;

    @BeforeClass
    public static void setupCassandra() {
        cassandraContainer = new CassandraContainer<>(
                DockerImageName.parse("cassandra:4.1.9"))
                .withExposedPorts(9042);
        cassandraContainer.start();

        try (CqlSession session = CqlSession.builder()
                .addContactPoint(cassandraContainer.getContactPoint())
                .withLocalDatacenter(cassandraContainer.getLocalDatacenter())
                .build()) {

            session.execute(SimpleStatement.newInstance(KEYSPACE_CREATION_QUERY));
            session.execute(SimpleStatement.newInstance(KEYSPACE_ACTIVATE_QUERY));
        }
    }

    @AfterClass
    public static void tearDown() {
        if (cassandraContainer != null) {
            cassandraContainer.stop();
        }
    }

    @Before
    public void createTable() {
        adminTemplate.createTable(
                true,
                CqlIdentifier.of(DATA_TABLE_NAME).toCqlIdentifier(),
                Book.class,
                Collections.emptyMap()
        );
    }

    @Test
    public void whenSavingBook_thenAvailableOnRetrieval_usingQueryBuilder() {
        final UUID uuid = UUIDs.timeBased();
        final Insert insert = QueryBuilder.insertInto(DATA_TABLE_NAME).value("id", uuid).value("title", "Head First Java").value("publisher", "OReilly Media").value("tags", ImmutableSet.of("Software"));
        cassandraTemplate.execute((Statement<?>) insert);
        final Select select = QueryBuilder.select().from("book").limit(10);
        final Book retrievedBook = cassandraTemplate.selectOne((Statement<?>) select, Book.class);
        assertEquals(uuid, retrievedBook.getId());
    }

    @Test
    public void whenSavingBook_thenAvailableOnRetrieval_usingCQLStatements() {
        final UUID uuid = UUIDs.timeBased();
        final String insertCql = "insert into book (id, title, publisher, tags) values " + "(" + uuid + ", 'Head First Java', 'OReilly Media', {'Software'})";
        cassandraTemplate.getCqlOperations().execute(insertCql);
        SimpleStatement select = com.datastax.oss.driver.api.querybuilder.QueryBuilder.selectFrom(DATA_TABLE_NAME)
                .all()
                .limit(10)
                .build();
        final Book retrievedBook = cassandraTemplate.selectOne((Statement<?>) select, Book.class);
        assertEquals(uuid, retrievedBook.getId());
    }

    @Test
    public void whenSavingBook_thenAvailableOnRetrieval_usingPreparedStatements() throws InterruptedException {
        final UUID uuid = UUIDs.timeBased();
        final String insertPreparedCql = "insert into book (id, title, publisher, tags) values (?, ?, ?, ?)";
        final List<Object> singleBookArgsList = new ArrayList<>();
        final List<List<?>> bookList = new ArrayList<>();
        singleBookArgsList.add(uuid);
        singleBookArgsList.add("Head First Java");
        singleBookArgsList.add("OReilly Media");
        singleBookArgsList.add(ImmutableSet.of("Software"));
        bookList.add(singleBookArgsList);
        cassandraTemplate.getCqlOperations().execute(insertPreparedCql, bookList);
        // This may not be required, just added to avoid any transient issues
        Thread.sleep(5000);
        final Select select = QueryBuilder.select().from("book");
        final Book retrievedBook = cassandraTemplate.selectOne((Statement<?>) select, Book.class);
        assertEquals(uuid, retrievedBook.getId());
    }

    @After
    public void dropTable() {
        adminTemplate.dropTable(CqlIdentifier.of(DATA_TABLE_NAME).getClass());
    }

}
