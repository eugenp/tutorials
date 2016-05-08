package com.baeldung.spring.data.neo4j.config;


import org.neo4j.ogm.session.SessionFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@ComponentScan("com.baeldung.spring.data.neo4j")
@EnableNeo4jRepositories(basePackages = "com.baeldung.spring.data.neo4j.repostory")
public class LibraryNeo4jConfiguration extends Neo4jConfiguration {

    public static final String URL = System.getenv("NEO4J_URL") != null ? System.getenv("NEO4J_URL") : "http://localhost:7474";

    @Override
    public Neo4jServer neo4jServer() {
        return new RemoteServer(URL,"neo4j","password");
    }

    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory("com.baeldung.spring.data.neo4j.model");
    }
}