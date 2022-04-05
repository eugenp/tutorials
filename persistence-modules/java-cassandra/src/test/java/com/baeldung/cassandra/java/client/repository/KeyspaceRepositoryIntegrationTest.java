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
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.baeldung.cassandra.java.client.CassandraConnector;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Session;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class KeyspaceRepositoryIntegrationTest {

    private KeyspaceRepository schemaRepository;

    private Session session;

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

        // schemaRepository.createKeyspace(keyspaceName, "SimpleStrategy", 1);
        schemaRepository.deleteKeyspace(keyspaceName);

        ResultSet result = session.execute("SELECT * FROM system_schema.keyspaces;");
        boolean isKeyspaceCreated = result.all().stream().anyMatch(r -> r.getString(0).equals(keyspaceName.toLowerCase()));
        assertFalse(isKeyspaceCreated);
    }

    @AfterClass
    public static void cleanup() {
        EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
    }
}
