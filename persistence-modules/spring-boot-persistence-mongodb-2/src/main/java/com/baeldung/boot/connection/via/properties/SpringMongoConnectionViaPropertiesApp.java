package com.baeldung.boot.connection.via.properties;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.embedded.EmbeddedMongoAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@PropertySource("classpath:connection.via.properties/app.properties")
@EnableAutoConfiguration(exclude = EmbeddedMongoAutoConfiguration.class)
@EnableMongoRepositories(basePackages = { "com.baeldung.boot.connection.base" })
@SpringBootApplication(scanBasePackages = { "com.baeldung.boot.connection.base" })
public class SpringMongoConnectionViaPropertiesApp {

    public static void main(String... args) {
        SpringApplicationBuilder app = new SpringApplicationBuilder(SpringMongoConnectionViaPropertiesApp.class);
        app.web(WebApplicationType.NONE);
        app.run(args);
    }
}
