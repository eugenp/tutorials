package org.baeldung.spring43.defaultmethods;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class TransactionalTestConfiguration {

    @Bean
    public DataSource getDataSource() {
        SimpleDriverDataSource simpleDriverDataSource = new SimpleDriverDataSource();
        simpleDriverDataSource.setDriverClass(org.hsqldb.jdbcDriver.class);
        simpleDriverDataSource.setUrl("jdbc:hsqldb:mem:app-db");
        simpleDriverDataSource.setUsername("sa");
        simpleDriverDataSource.setPassword("");
        return simpleDriverDataSource;
    }

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new DataSourceTransactionManager(getDataSource());
    }

}
