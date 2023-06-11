package com.baeldung.spring.data.neo4j.config;

import org.neo4j.driver.Driver;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.neo4j.core.DatabaseSelectionProvider;
import org.springframework.data.neo4j.core.UserSelectionProvider;
import org.springframework.data.neo4j.core.transaction.Neo4jTransactionManager;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.baeldung.spring.data.neo4j.services" })
@EnableNeo4jRepositories(basePackages = "com.baeldung.spring.data.neo4j.repository")
@Profile({ "embedded", "test" })
public class MovieDatabaseNeo4jTestConfiguration {

    @Bean
    public org.neo4j.ogm.config.Configuration getConfiguration() {
        return new org.neo4j.ogm.config.Configuration.Builder().build();
    }

    @Bean
    public SessionFactory getSessionFactory() {
        return new SessionFactory(getConfiguration(), "com.baeldung.spring.data.neo4j.domain");
    }

    @Bean
    public PlatformTransactionManager transactionManager(Driver driver,
        DatabaseSelectionProvider databaseSelectionProvider, UserSelectionProvider userSelectionProvider) {

        return Neo4jTransactionManager
                .with(driver)
                .withDatabaseSelectionProvider(databaseSelectionProvider)
                .withUserSelectionProvider(userSelectionProvider)
                .build();
    }

}
