package com.baeldung.hexagonal.architecture;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication(exclude = {
  MongoAutoConfiguration.class, 
  MongoDataAutoConfiguration.class,
  CassandraDataAutoConfiguration.class
  })

@PropertySource(value = { "classpath:hexarch.properties" })
public class HexarchApplication {

        public static void main(String[] args) {
                SpringApplication.run(HexarchApplication.class, args);
        }

}
