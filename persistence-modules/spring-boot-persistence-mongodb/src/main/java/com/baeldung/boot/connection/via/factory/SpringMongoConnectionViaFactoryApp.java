package com.baeldung.boot.connection.via.factory;

import com.mongodb.ConnectionString;
import com.mongodb.client.MongoClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;

@SpringBootApplication
public class SpringMongoConnectionViaFactoryApp {

    public static void main(String... args) {
        SpringApplicationBuilder app = new SpringApplicationBuilder(SpringMongoConnectionViaFactoryApp.class);
        app.web(WebApplicationType.NONE);
        app.run(args);
    }

    @Bean
    public MongoClientFactoryBean mongo(@Value("${custom.uri}") String uri) throws Exception {
        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
        ConnectionString conn = new ConnectionString(uri);
        mongo.setConnectionString(conn);

        mongo.setSingleton(false);

        MongoClient client = mongo.getObject();
        client.listDatabaseNames()
          .forEach(System.out::println);
        return mongo;
    }
}