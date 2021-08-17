package com.baeldung.arangodb.configuration;

import com.arangodb.ArangoDB;
import com.arangodb.springframework.annotation.EnableArangoRepositories;
import com.arangodb.springframework.config.ArangoConfiguration;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableArangoRepositories(basePackages = {"com.baeldung"})
public class ArangoDbConfiguration implements ArangoConfiguration {

    @Override
    public ArangoDB.Builder arango() {
        return new ArangoDB.Builder()
          .host("127.0.0.1", 8529)
          .user("root")
          .password("password");
    }

    @Override
    public String database() {
        return "baeldung-database";
    }
}
