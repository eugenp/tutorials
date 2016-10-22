package com.baeldung.spring.data.neo4j.config;

import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@ComponentScan(basePackages = { "com.baeldung.spring.data.neo4j.services" })
@Configuration
@EnableNeo4jRepositories(basePackages = "com.baeldung.spring.data.neo4j.repostory")
public class MovieDatabaseNeo4jConfiguration extends Neo4jConfiguration {

    public static final String URL = System.getenv("NEO4J_URL") != null ? System.getenv("NEO4J_URL") : "http://neo4j:movies@localhost:7474";

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        org.neo4j.ogm.config.Configuration config = new org.neo4j.ogm.config.Configuration();
        config.driverConfiguration().setDriverClassName("org.neo4j.ogm.drivers.http.driver.HttpDriver").setURI(URL);
        return config;
    }

    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "com.baeldung.spring.data.neo4j.domain");
    }
}
