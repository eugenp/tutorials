package com.baeldung.spring.data.cassandra.test.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;

@Configuration
public class CassandraConfig extends AbstractCassandraConfiguration {
    @Override
    protected String getKeyspaceName() {
        return "inventory";
    }

    @Override
    public String getContactPoints() {
        return "localhost";
    }

    @Override
    protected String getLocalDataCenter() {
         return "datacenter1";
    }


}
