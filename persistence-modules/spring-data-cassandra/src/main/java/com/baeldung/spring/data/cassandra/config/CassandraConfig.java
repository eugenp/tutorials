package com.baeldung.spring.data.cassandra.config;

import com.datastax.oss.driver.api.core.CqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.cassandra.SessionFactory;
import org.springframework.data.cassandra.core.CassandraAdminTemplate;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.cassandra.core.convert.MappingCassandraConverter;
import org.springframework.data.cassandra.core.cql.session.DefaultSessionFactory;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import java.net.InetSocketAddress;

@Configuration
@PropertySource("classpath:cassandra.properties")
@EnableCassandraRepositories(basePackages = "com.baeldung.spring.data.cassandra.repository")
public class CassandraConfig {

    private static final Logger LOG = LoggerFactory.getLogger(CassandraConfig.class);

    @Value("${cassandra.contactpoints}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private int port;

    @Value("${cassandra.keyspace}")
    private String keyspace;

    @Value("${cassandra.localdatacenter}")
    private String localDatacenter;

    @Bean
    public CqlSession cqlSession() {
        LOG.info("Creating CqlSession with contact points [{}] & port [{}]", contactPoints, port);

        return CqlSession.builder()
                .addContactPoint(new InetSocketAddress(contactPoints, port))
                .withLocalDatacenter(localDatacenter)
                .withKeyspace(keyspace)
                .build();
    }

    @Bean
    public SessionFactory sessionFactory(CqlSession cqlSession, CassandraConverter converter) {
        return new DefaultSessionFactory(cqlSession);
    }

    @Bean
    public CassandraMappingContext cassandraMappingContext() {
        return new CassandraMappingContext();
    }

    @Bean
    public CassandraConverter cassandraConverter(CassandraMappingContext mappingContext) {
        return new MappingCassandraConverter(mappingContext);
    }

    @Bean
    public CassandraAdminTemplate cassandraAdminOperations(SessionFactory sessionFactory, CassandraConverter converter) {
        return new CassandraAdminTemplate(sessionFactory, converter);
    }

    @Bean
    public CassandraTemplate adminTemplate(SessionFactory sessionFactory, CassandraConverter converter) {
        return new CassandraTemplate(sessionFactory, converter);
    }
}
