package com.baeldung.cassandra.java.client.repository;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.testcontainers.containers.CassandraContainer;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KeyspaceRepositoryLiveTest {
    @Rule
    public CassandraContainer<?> cassandra = new CassandraContainer<>("cassandra:3.11.2");
    private KeyspaceRepository schemaRepository;
    private Session session;

    @Before
    public void connect() {
        cassandra.start();

        this.session = Cluster
                .builder()
                .addContactPoint(cassandra.getHost())
                .withPort(cassandra.getMappedPort(CassandraContainer.CQL_PORT))
                .build()
                .newSession();

        schemaRepository = new KeyspaceRepository(session);
    }

    @Test
    public void whenCreatingAKeyspace_thenCreated() {
        String keyspaceName = "testBaeldungKeyspace";
        schemaRepository.createKeyspace(keyspaceName, "SimpleStrategy", 1);

        // ResultSet result = session.execute("SELECT * FROM system_schema.keyspaces WHERE keyspace_name = 'testBaeldungKeyspace';");

        ResultSet result = session.execute("SELECT * FROM system_schema.keyspaces;");

        // Check if the Keyspace exists in the returned keyspaces.
        List<String> matchedKeyspaces = result.all().stream().filter(r -> r.getString(0).equals(keyspaceName.toLowerCase())).map(r -> r.getString(0)).collect(Collectors.toList());
        assertEquals(matchedKeyspaces.size(), 1);
        assertTrue(matchedKeyspaces.get(0).equals(keyspaceName.toLowerCase()));
    }

    @Test
    public void whenDeletingAKeyspace_thenDoesNotExist() {
        String keyspaceName = "testBaeldungKeyspace";

        schemaRepository.createKeyspace(keyspaceName, "SimpleStrategy", 1);
        schemaRepository.deleteKeyspace(keyspaceName);

        ResultSet result = session.execute("SELECT * FROM system_schema.keyspaces;");
        boolean isKeyspaceCreated = result.all().stream().anyMatch(r -> r.getString(0).equals(keyspaceName.toLowerCase()));
        assertFalse(isKeyspaceCreated);
    }
}
