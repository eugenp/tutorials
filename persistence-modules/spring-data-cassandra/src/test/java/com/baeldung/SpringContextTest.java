package com.baeldung;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Collections;
import java.util.HashMap;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import com.baeldung.spring.data.cassandra.config.CassandraConfig;
import com.baeldung.spring.data.cassandra.model.Book;
import com.datastax.oss.driver.api.core.CqlIdentifier;
import com.datastax.oss.driver.api.core.CqlSession;

import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class SpringContextTest {

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace " + "WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '3' };";

    public static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";

    public static final String DATA_TABLE_NAME = "book";

	@Autowired
    private CassandraAdminOperations adminTemplate;

    @BeforeClass
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        final CqlSession session = CqlSession.builder().addContactPoints(Collections.singleton(new InetSocketAddress("127.0.0.1", 9142))).build();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
        Thread.sleep(5000);
    }

    @Before
    public void createTable() {
        adminTemplate.createTable(true, CqlIdentifier.fromCql(DATA_TABLE_NAME), Book.class, new HashMap<>());
    }

	@Test
    public void whenSpringContextIsBootstrapped_thenNoExceptions() {
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
