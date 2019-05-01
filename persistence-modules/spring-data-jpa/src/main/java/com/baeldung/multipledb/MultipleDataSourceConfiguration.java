package com.baeldung.multipledb;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * By default, the persistence-multiple-db.properties file is read for productDataSource() and userDataSource() 
 * in PersistenceProductConfiguration and PersistenceUserConfiguration respectively. 
 * <p>
 * If we need to use persistence-multiple-db-boot.properties then uncomment the below @Configuration class and comment out
 * productDataSource() and userDataSource() in PersistenceProductConfiguration and PersistenceUserConfiguration. 
 */
//@Configuration
@PropertySource({"classpath:persistence-multiple-db-boot.properties"})
public class MultipleDataSourceConfiguration {
    
    @Bean
    @ConfigurationProperties(prefix="spring.user")
    public DataSource userDataSource() {
        return DataSourceBuilder.create().build();
    }
    
    @Bean
    @ConfigurationProperties(prefix="spring.product")
    public DataSource productDataSource() {
        return DataSourceBuilder.create().build();
    }
}