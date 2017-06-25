package com.baeldung.spring.data.neo4j.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.baeldung.spring.data.neo4j.services" })
@EnableNeo4jRepositories(basePackages = "com.baeldung.spring.data.neo4j.repostory")
@Profile({ "embedded", "test" })
public class MovieDatabaseNeo4jTestConfiguration extends Neo4jConfiguration {

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        final org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config.driverConfiguration()
            .setDriverClassName("org.neo4j.ogm.drivers.embedded.driver.EmbeddedDriver");
        return config;
    }

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "com.baeldung.spring.data.neo4j.domain");
    }

}
