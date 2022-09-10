package com.baeldung.boot.connection.via.client;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.ListDatabasesIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@EnableAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
@EnableMongoRepositories(basePackages = { "com.baeldung.boot.connection.base" })
@SpringBootApplication(scanBasePackages = { "com.baeldung.boot.connection.base" })
public class SpringMongoConnectionViaClientApp extends AbstractMongoClientConfiguration {

    public static void main(String... args) {
        SpringApplicationBuilder app = new SpringApplicationBuilder(SpringMongoConnectionViaClientApp.class);
        app.web(WebApplicationType.NONE);
        app.run(args);
    }

    @Value("${spring.data.mongodb.uri}")
    private String uri;

    @Value("${spring.data.mongodb.database}")
    private String db;

    @Override
    public MongoClient mongoClient() {
        MongoClient client = MongoClients.create(uri);
        ListDatabasesIterable<Document> databases = client.listDatabases();
        databases.forEach(System.out::println);
        return client;
    }

    @Override
    protected String getDatabaseName() {
        return db;
    }
}
