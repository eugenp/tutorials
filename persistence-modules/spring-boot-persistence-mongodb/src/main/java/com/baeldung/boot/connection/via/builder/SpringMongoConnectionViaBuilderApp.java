package com.baeldung.boot.connection.via.builder;

import com.mongodb.ConnectionString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoClientSettingsBuilderCustomizer;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
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