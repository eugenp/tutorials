package com.baeldung.cassandra.batch;

import com.datastax.oss.driver.api.core.CqlSession;
import com.datastax.oss.driver.api.core.CqlSessionBuilder;

import java.net.InetSocketAddress;

import org.apache.commons.lang3.StringUtils;

public class CassandraConnector {

    private CqlSession session;

    public void connect(final String node, final Integer port, final String dataCenter) {
        CqlSessionBuilder builder = CqlSession.builder();
	    builder.addContactPoint(new InetSocketAddress(node, port));
        if (StringUtils.isNotBlank(dataCenter)) {
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
