package com.baeldung.spring.oracle.pooling.configuration;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
@Profile("c3p0")
public class C3P0Configuration {

    @Bean
    public DataSource dataSource() throws SQLException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser("books");
        dataSource.setPassword("books");
        dataSource.setJdbcUrl("jdbc:oracle:thin:@//localhost:11521/ORCLPDB1");
        dataSource.setMinPoolSize(5);
        dataSource.setMaxPoolSize(10);
        return dataSource;
    }    
}
