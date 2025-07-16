package com.baeldung.spring.data.cassandra.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.baeldung.spring.data.cassandra.model.Book;
import com.datastax.driver.core.utils.UUIDs;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import com.datastax.oss.driver.api.querybuilder.insert.RegularInsert;
import com.google.common.collect.ImmutableSet;

/**
 * Live test for Cassandra testing.
 */
@Testcontainers
@SpringBootTest(classes = CassandraTestConfiguration.class)
class CqlQueriesLiveTest {

    private static final String KEYSPACE_NAME = "testKeySpace";

    private static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace " + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '1' };";
    private static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";
    private static final String DATA_TABLE_NAME = "book";

    @Autowired
    private CassandraAdminOperations cassandraTemplate;

    @Container
    private static final CassandraContainer<?> cassandraContainer = new CassandraContainer<>("cassandra:4.1.8").withExposedPorts(9042);

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

    @BeforeEach
    void createTable() {
        cassandraTemplate.createTable(true, CqlIdentifier.fromCql(DATA_TABLE_NAME), Book.class, Collections.emptyMap());
    }

    @Test
    void whenSavingBook_thenAvailableOnRetrieval_usingQueryBuilder() {
        final UUID uuid = UUIDs.timeBased();

        final RegularInsert insert = QueryBuilder.insertInto(DATA_TABLE_NAME)
            .value("id", QueryBuilder.literal(uuid))
            .value("title", QueryBuilder.literal("Head First Java"))
            .value("publisher", QueryBuilder.literal("OReilly Media"))
            .value("tags", QueryBuilder.literal(ImmutableSet.of("Software")));

        cassandraTemplate.getCqlOperations()
            .execute(insert.asCql());

        final SimpleStatement select = QueryBuilder.selectFrom(DATA_TABLE_NAME)
            .all()
            .limit(10)
            .build();
        Book retrievedBook = cassandraTemplate.selectOne(select, Book.class);

        assertEquals(uuid, retrievedBook.getId());
    }

    @Test
    void whenSavingBook_thenAvailableOnRetrieval_usingCQLStatements() {
        final UUID uuid = UUIDs.timeBased();
        final String insertCql = "insert into book (id, title, publisher, tags) values " + "(" + uuid + ", 'Head First Java', 'OReilly Media', {'Software'})";
        cassandraTemplate.getCqlOperations()
            .execute(insertCql);
        SimpleStatement select = QueryBuilder.selectFrom(DATA_TABLE_NAME)
            .all()
            .limit(10)
            .build();
        final Book retrievedBook = cassandraTemplate.selectOne(select, Book.class);
        assertEquals(uuid, retrievedBook.getId());
    }

    @Test
    void whenSavingBook_thenAvailableOnRetrieval_usingPreparedStatements() {
        final UUID uuid = UUIDs.timeBased();
        final String insertPreparedCql = "insert into book (id, title, publisher, tags) values (?, ?, ?, ?)";

        List<List<Object>> bookArgsList = new ArrayList<>();
        bookArgsList.add(List.of(uuid, "Head First Java", "OReilly Media", ImmutableSet.of("Software")));

        for (List<Object> list : bookArgsList) {
            cassandraTemplate.getCqlOperations()
                .execute(insertPreparedCql, list.toArray());
        }

        final SimpleStatement select = QueryBuilder.selectFrom(DATA_TABLE_NAME)
            .all()
            .build();
        final Book retrievedBook = cassandraTemplate.selectOne(select, Book.class);
        assertEquals(uuid, retrievedBook.getId());
    }
}
