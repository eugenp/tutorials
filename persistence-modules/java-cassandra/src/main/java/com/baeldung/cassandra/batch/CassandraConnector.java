package com.baeldung.cassandra.batch;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;

import java.net.InetSocketAddress;

public class CassandraConnector {

    private CqlSession session;

    public void connect(final String node, final Integer port, final String dataCenter) {
        CqlSessionBuilder builder = CqlSession.builder();
	    builder.addContactPoint(new InetSocketAddress(node, port));
        if (dataCenter != null && !dataCenter.isEmpty()) {
            builder.withLocalDatacenter(dataCenter);
        }

        session = builder.build();
    }

    public CqlSession getSession() {
		return this.session;
    }

    public void close() {
        session.close();
    }
}
