package com.baeldung.multipledatamodules;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.cassandra.CassandraReactiveRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, 
                                   DataSourceTransactionManagerAutoConfiguration.class, 
                                   HibernateJpaAutoConfiguration.class, 
                                   RedisAutoConfiguration.class, 
                                   RedisRepositoriesAutoConfiguration.class,
                                   CassandraReactiveDataAutoConfiguration.class,
                                   CassandraReactiveRepositoriesAutoConfiguration.class})

@EnableCassandraRepositories(basePackages="com.baeldung.multipledatamodules.cassandra")
@EnableMongoRepositories(basePackages="com.baeldung.multipledatamodules.mongo")
public class SpringDataMultipleModules {

    public static void main(String[] args) {
        SpringApplication.run(SpringDataMultipleModules.class, args);
    }
}
