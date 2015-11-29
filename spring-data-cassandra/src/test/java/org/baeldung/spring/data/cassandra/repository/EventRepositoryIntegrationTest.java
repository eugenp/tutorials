package org.baeldung.spring.data.cassandra.repository;

import com.datastax.driver.core.utils.UUIDs;
import com.google.common.collect.ImmutableSet;
import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.baeldung.spring.data.cassandra.config.CassandraConfig;
import org.baeldung.spring.data.cassandra.model.Event;
import org.cassandraunit.spring.CassandraUnitTestExecutionListener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cassandra.core.cql.CqlIdentifier;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {CassandraConfig.class})
@TestExecutionListeners({CassandraUnitTestExecutionListener.class, DependencyInjectionTestExecutionListener.class})
public class EventRepositoryIntegrationTest {

    public static final String TIME_BUCKET = "2014-01-01";

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private CassandraAdminOperations adminTemplate;

    @Test
    public void repositoryStoresAndRetrievesEvents() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        adminTemplate.execute("CREATE KEYSPACE IF NOT EXISTS testKeySpace WITH replication = {"
                + " 'class': 'SimpleStrategy', "
                + " 'replication_factor': '3' "
                + "};" );
        adminTemplate.dropTable(CqlIdentifier.cqlId("event"));
        adminTemplate.createTable(true, CqlIdentifier.cqlId("event"), Event.class, new HashMap<String, Object>());
        Event event1 = new Event(UUIDs.timeBased(), "type1", TIME_BUCKET, ImmutableSet.of("tag1", "tag2"));
        Event event2 = new Event(UUIDs.timeBased(), "type1", TIME_BUCKET, ImmutableSet.of("tag3"));
        eventRepository.save(ImmutableSet.of(event1, event2));

        Iterable<Event> events = eventRepository.findByTypeAndBucket("type1", TIME_BUCKET);

        assertThat(events, hasItem(event1));
        assertThat(events, hasItem(event2));
    }

 //   @Test
    public void repositoryDeletesStoredEvents() {
        Event event1 = new Event(UUIDs.timeBased(), "type1", TIME_BUCKET, ImmutableSet.of("tag1", "tag2"));
        Event event2 = new Event(UUIDs.timeBased(), "type1", TIME_BUCKET, ImmutableSet.of("tag3"));
        eventRepository.save(ImmutableSet.of(event1, event2));

        eventRepository.delete(event1);
        eventRepository.delete(event2);

        Iterable<Event> events = eventRepository.findByTypeAndBucket("type1", TIME_BUCKET);

        assertThat(events, not(hasItem(event1)));
        assertThat(events, not(hasItem(event2)));
    }

}
