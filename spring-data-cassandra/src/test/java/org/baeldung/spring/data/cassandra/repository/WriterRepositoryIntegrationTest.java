package org.baeldung.spring.data.cassandra.repository;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.thrift.transport.TTransportException;
import org.baeldung.spring.data.cassandra.config.CassandraConfig;
import org.baeldung.spring.data.cassandra.model.Writer;
import org.baeldung.spring.data.cassandra.model.Writer.WriterType;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.repository.support.BasicMapId;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;
import com.google.common.collect.ImmutableSet;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CassandraConfig.class)
public class WriterRepositoryIntegrationTest {
    private static final Log LOGGER = LogFactory.getLog(WriterRepositoryIntegrationTest.class);

    public static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE IF NOT EXISTS testKeySpace WITH replication = { 'class': 'SimpleStrategy', 'replication_factor': '1' };";

    public static final String KEYSPACE_ACTIVATE_QUERY = "USE testKeySpace;";

    public static final String DATA_TABLE_NAME = "writer";

    @Autowired
    private WriterRepository writerRepository;

    @Autowired
    private CassandraAdminOperations adminTemplate;

    @BeforeClass
    public static void startCassandraEmbedded()
            throws InterruptedException, TTransportException, ConfigurationException, IOException {
        EmbeddedCassandraServerHelper.startEmbeddedCassandra();
        final Cluster cluster = Cluster.builder().addContactPoints("127.0.0.1").withPort(9142).build();
        LOGGER.info("Server Started at 127.0.0.1:9142... ");
        final Session session = cluster.connect();
        session.execute(KEYSPACE_CREATION_QUERY);
        session.execute(KEYSPACE_ACTIVATE_QUERY);
        LOGGER.info("KeySpace created and activated.");
        Thread.sleep(5000);
    }

    @Before
    public void createTable() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        adminTemplate.createTable(true, CqlIdentifier.cqlId(DATA_TABLE_NAME), Writer.class,
                new HashMap<String, Object>());
    }

    @Test
    public void canInsertAndRetrieve() {
        final Writer writer = new Writer(UUIDs.random(), "FirstName", "LastName", WriterType.DETECTIVE);
        writerRepository.save(ImmutableSet.of(writer));

        final Writer insertedWriter = writerRepository.findOne(writer.getMapId());
        assertEquals(writer.getId(), insertedWriter.getId());
        assertEquals(writer.getFirstName(), insertedWriter.getFirstName());
        assertEquals(writer.getLastName(), insertedWriter.getLastName());
    }

    @Test
    public void canRetrieveList() {
        ArrayList<Writer> entities = new ArrayList<Writer>();
        entities.add(new Writer(UUIDs.random(), "FirstName1", "LastName1", WriterType.DETECTIVE));
        entities.add(new Writer(UUIDs.random(), "FirstName2", "LastName2", WriterType.POET));
        entities.add(new Writer(UUIDs.random(), "FirstName3", "LastName3", WriterType.JOUNALIST));
        entities.add(new Writer(UUIDs.random(), "FirstName4", "LastName4", WriterType.UNKNOWN));
        writerRepository.save(entities);

        final Iterable<Writer> list = writerRepository.findAll();
        assertEquals(list.iterator().hasNext(), true);
    }

    @Test
    public void canUpdate() {
        final Writer writer = new Writer(UUIDs.random(), "FirstName", "LastName", WriterType.DETECTIVE);
        writerRepository.save(ImmutableSet.of(writer));

        Writer insertedWriter = writerRepository.findOne(writer.getMapId());
        assertEquals(writer.getId(), insertedWriter.getId());

        writer.setFirstName(insertedWriter.getFirstName() + " <updated>");
        writerRepository.save(insertedWriter);

        final Writer updatedWriter = writerRepository.findOne(BasicMapId.id("id", writer.getId()));
        assertEquals(insertedWriter.getFirstName(), updatedWriter.getFirstName());
    }

    @Test
    public void canInsertAndDelete() {
        final Writer writer = new Writer(UUIDs.random(), "ToBeDeleted", "ToBeDeleted", WriterType.JOUNALIST);
        writerRepository.save(writer);

        assertEquals(writerRepository.exists(writer.getMapId()), true);

        writerRepository.delete(writer.getMapId());
        assertEquals(writerRepository.exists(writer.getMapId()), false);
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
