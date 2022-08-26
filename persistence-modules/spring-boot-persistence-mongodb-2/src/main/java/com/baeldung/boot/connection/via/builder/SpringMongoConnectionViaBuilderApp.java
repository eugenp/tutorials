package com.baeldung.boot.connection.via.builder;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.ConnectionString;

@EnableAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
@EnableMongoRepositories(basePackages = { "com.baeldung.boot.connection.base" })
@SpringBootApplication(scanBasePackages = { "com.baeldung.boot.connection.base" })
public class SpringMongoConnectionViaBuilderApp {

    public static void main(String... args) {
        SpringApplicationBuilder app = new SpringApplicationBuilder(SpringMongoConnectionViaBuilderApp.class);
        app.web(WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    public MongoClientSettingsBuilderCustomizer customizer(@Value("${custom.uri}") String uri) {
        ConnectionString connection = new ConnectionString(uri);
        return settings -> settings.applyConnectionString(connection);
    }
}
