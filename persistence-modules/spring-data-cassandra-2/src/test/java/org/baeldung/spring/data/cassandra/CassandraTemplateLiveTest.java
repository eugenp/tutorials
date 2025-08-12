package org.baeldung.spring.data.cassandra;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.baeldung.spring.data.cassandra.model.Book;
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

import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;

@Testcontainers
@SpringBootTest(classes = CassandraTestConfiguration.class)
class CassandraTemplateLiveTest {

    private static final String KEYSPACE_NAME = "testKeySpace";
    private static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace " + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '1' };";
    private static final String KEYSPACE_ACTIVATE_QUERY = "USE " + KEYSPACE_NAME + ";";

    private static final String DATA_TABLE_NAME = "book";

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

    @Autowired
    private CassandraAdminOperations cassandraTemplate;

    @BeforeEach
    void createTable() {
        cassandraTemplate.createTable(true, CqlIdentifier.fromCql(DATA_TABLE_NAME), Book.class, Collections.emptyMap());
    }

    @Test
    void whenSavingBook_thenAvailableOnRetrieval() {
        Book javaBook = new Book(Uuids.timeBased(), "Head First Java", "O'Reilly Media", Collections.singleton("Computer"));

        cassandraTemplate.insert(javaBook);

        SimpleStatement select = QueryBuilder.selectFrom(DATA_TABLE_NAME)
            .all()
            .whereColumn("title")
            .isEqualTo(literal("Head First Java"))
            .allowFiltering()
            .build();

        Book retrievedBook = cassandraTemplate.selectOne(select, Book.class);
        assertEquals(javaBook.getId(), retrievedBook.getId());
    }

    @Test
    void whenSavingBooks_thenAllAvailableOnRetrieval() {
        List<Book> books = List.of(new Book(Uuids.timeBased(), "Head First Java", "O'Reilly Media", Set.of("Computer")), new Book(Uuids.timeBased(), "Clean Code", "Pearson", Set.of("Software")));

        for (Book book : books) {
            cassandraTemplate.insert(book);
        }

        SimpleStatement select = QueryBuilder.selectFrom(DATA_TABLE_NAME)
            .all()
            .build();

        List<Book> retrieved = cassandraTemplate.select(select, Book.class);
        assertThat(retrieved.size(), is(3));
    }


    @AfterAll
    static void tearDown() {
        if (cassandraContainer != null) {
            cassandraContainer.stop();
        }
    }
}
