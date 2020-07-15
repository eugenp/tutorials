package com.baeldung.hexagonalexample;


import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.Bean;

@SpringBootApplication()
@EnableAutoConfiguration(exclude = {CassandraDataAutoConfiguration.class, MongoAutoConfiguration.class})
public class HexagonalApplication {
    private static final Logger LOG = LoggerFactory.getLogger(HexagonalApplication.class);

    public static void main(final String[] args) {
        SpringApplication.run(HexagonalApplication.class, args);
        LOG.info("First article");

    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
