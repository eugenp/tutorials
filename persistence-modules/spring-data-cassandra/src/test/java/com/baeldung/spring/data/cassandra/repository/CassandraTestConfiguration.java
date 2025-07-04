package com.baeldung.spring.data.cassandra.repository;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;

import com.datastax.oss.driver.api.core.CqlSession;

@TestConfiguration
public class CassandraTestConfiguration {

    @Bean
    public CassandraAdminTemplate cassandraTemplate(CqlSession session, CassandraConverter converter) {
        return new CassandraAdminTemplate(session, converter);
    }
}
