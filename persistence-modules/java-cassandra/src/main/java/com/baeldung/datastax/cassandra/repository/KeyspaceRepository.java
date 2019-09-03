package com.baeldung.datastax.cassandra.repository;

import com.datastax.oss.driver.api.core.CqlSession;

public class KeyspaceRepository {
    private final CqlSession session;

    public KeyspaceRepository(CqlSession session) {
        this.session = session;
    }

    public void createKeyspace(String keyspaceName, String replicationStrategy, int numberOfReplicas) {
        StringBuilder sb = new StringBuilder("CREATE KEYSPACE IF NOT EXISTS ").append(keyspaceName)
            .append(" WITH replication = {")
            .append("'class':'").append(replicationStrategy)
            .append("','replication_factor':").append(numberOfReplicas)
            .append("};");

        final String query = sb.toString();

        session.execute(query);
    }

    public void useKeyspace(String keyspace) {
        session.execute("USE " + keyspace);
    }
}
