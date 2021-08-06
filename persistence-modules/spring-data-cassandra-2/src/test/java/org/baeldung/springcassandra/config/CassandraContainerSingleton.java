package org.baeldung.springcassandra.config;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import org.testcontainers.containers.CassandraContainer;

public class CassandraContainerSingleton extends CassandraContainer<CassandraContainerSingleton> {

    private static final String IMAGE_VERSION = "cassandra:3.11.2";
    private static final String KEYSPACE_NAME = "test";
    private static CassandraContainerSingleton container;

    private CassandraContainerSingleton() {
        super(IMAGE_VERSION);
    }

    public static CassandraContainerSingleton getInstance() {
        if (container == null) {
            container = new CassandraContainerSingleton();
        }
        return container;
    }

    @Override
    public void start() {
        super.start();
        createKeyspace(super.getCluster());

        System.setProperty("CASSANDRA_KEYSPACE_NAME", KEYSPACE_NAME);
        System.setProperty("CASSANDRA_CONTACT_POINTS", container.getContainerIpAddress());
        System.setProperty("CASSANDRA_PORT", String.valueOf(container.getMappedPort(9042)));
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

    private static void createKeyspace(Cluster cluster) {
        try(Session session = cluster.connect()) {
            session.execute("CREATE KEYSPACE IF NOT EXISTS " + KEYSPACE_NAME + " WITH replication = {'class':'SimpleStrategy','replication_factor':'1'};");
        }
    }

}
