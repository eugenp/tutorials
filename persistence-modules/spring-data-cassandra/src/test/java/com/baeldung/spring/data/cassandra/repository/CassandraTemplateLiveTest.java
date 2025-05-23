package com.baeldung.spring.data.cassandra.repository;

import com.baeldung.spring.data.cassandra.config.CassandraConfig;
import com.baeldung.spring.data.cassandra.model.Book;
import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.cql.SimpleStatement;
import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.datastax.oss.driver.api.querybuilder.QueryBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.CassandraOperations;
import org.springframework.data.cassandra.core.cql.CqlIdentifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.CassandraContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import static com.datastax.oss.driver.api.querybuilder.QueryBuilder.literal;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

@Testcontainers
@SpringBootTest
@ContextConfiguration(classes = CassandraConfig.class)
public class CassandraTemplateLiveTest {

    private static final String DATA_TABLE_NAME = "book";

    static CassandraContainer<?> cassandraContainer =
            new CassandraContainer<>("cassandra:4.1.8").withExposedPorts(9042);

    @DynamicPropertySource
    static void cassandraProperties(DynamicPropertyRegistry registry) {
        registry.add("cassandra.contactpoints", () -> cassandraContainer.getHost());
        registry.add("cassandra.port", () -> cassandraContainer.getMappedPort(9042));
        registry.add("cassandra.keyspace", () -> "testKeySpace");
        registry.add("cassandra.localdatacenter", () -> cassandraContainer.getLocalDatacenter());
    }

    @BeforeClass
    public static void startContainerAndCreateKeyspace() {
        cassandraContainer.start();
        try (CqlSession session = CqlSession.builder()
                .addContactPoint(cassandraContainer.getContactPoint())
                .withLocalDatacenter(cassandraContainer.getLocalDatacenter())
                .build()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS testKeySpace WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': 1 };");
        }
    }

    @Autowired
    private CassandraOperations cassandraTemplate;

    @Autowired
    private CassandraAdminOperations adminTemplate;

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
    public void whenSavingBook_thenAvailableOnRetrieval() {
        Book javaBook = new Book(
                Uuids.timeBased(),
                "Head First Java",
                "O'Reilly Media",
                Collections.singleton("Computer")
        );

        cassandraTemplate.insert(javaBook);

        SimpleStatement select = QueryBuilder.selectFrom(DATA_TABLE_NAME)
                .all()
                .whereColumn("title").isEqualTo(literal("Head First Java"))
                .build();

        Book retrievedBook = cassandraTemplate.selectOne(select, Book.class);
        assertEquals(javaBook.getId(), retrievedBook.getId());
    }

    @Test
    public void whenSavingBooks_thenAllAvailableOnRetrieval() {
        List<Book> books = List.of(
                new Book(Uuids.timeBased(), "Head First Java", "O'Reilly Media", Set.of("Computer")),
                new Book(Uuids.timeBased(), "Clean Code", "Pearson", Set.of("Software"))
        );

        cassandraTemplate.insert(books);

        SimpleStatement select = QueryBuilder.selectFrom(DATA_TABLE_NAME)
                .all()
                .build();

        List<Book> retrieved = cassandraTemplate.select(select, Book.class);
        assertThat(retrieved.size(), is(2));
    }

    @After
    public void dropTable() {
        adminTemplate.dropTable(CqlIdentifier.of(DATA_TABLE_NAME).getClass());
    }

    @AfterClass
    public static void tearDown() {
        if (cassandraContainer != null) {
            cassandraContainer.stop();
        }
    }
}
