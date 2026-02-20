package com.baeldung.rwrouting;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackageClasses = Order.class,
  entityManagerFactoryRef = "routingEntityManagerFactory",
  transactionManagerRef = "routingTransactionManager"
)
public class DataSourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.readwrite")
    public DataSourceProperties readWriteProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.readonly")
    public DataSourceProperties readOnlyProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource readWriteDataSource() {
        return readWriteProperties()
          .initializeDataSourceBuilder()
          .build();
    }

    @Bean
    public DataSource readOnlyDataSource() {
        return readOnlyProperties()
          .initializeDataSourceBuilder()
          .build();
    }

    @Bean
    @Primary
    public TransactionRoutingDataSource routingDataSource() {
        TransactionRoutingDataSource routingDataSource =
          new TransactionRoutingDataSource();

        Map<Object, Object> dataSourceMap = new HashMap<>();
        dataSourceMap.put(
          DataSourceType.READ_WRITE, readWriteDataSource());
        dataSourceMap.put(
          DataSourceType.READ_ONLY, readOnlyDataSource());

        routingDataSource.setTargetDataSources(dataSourceMap);
        routingDataSource.setDefaultTargetDataSource(
          readWriteDataSource());

        return routingDataSource;
    }

    @Bean
    public DataSourceInitializer readWriteInitializer(
      @Qualifier("readWriteDataSource")
      DataSource readWriteDataSource) {
        ResourceDatabasePopulator populator =
          new ResourceDatabasePopulator();
        populator.addScript(
          new ClassPathResource("rwrouting-schema.sql"));

        DataSourceInitializer init =
          new DataSourceInitializer();
        init.setDataSource(readWriteDataSource);
        init.setDatabasePopulator(populator);
        return init;
    }

    @Bean
    public DataSourceInitializer readOnlyInitializer(
      @Qualifier("readOnlyDataSource")
      DataSource readOnlyDataSource) {
        ResourceDatabasePopulator populator =
          new ResourceDatabasePopulator();
        populator.addScript(
          new ClassPathResource("rwrouting-schema.sql"));

        DataSourceInitializer init =
          new DataSourceInitializer();
        init.setDataSource(readOnlyDataSource);
        init.setDatabasePopulator(populator);
        return init;
    }

    @Bean
    public DataSource dataSource() {
        return new LazyConnectionDataSourceProxy(
          routingDataSource());
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean
      routingEntityManagerFactory(
        EntityManagerFactoryBuilder builder) {
        return builder
          .dataSource(dataSource())
          .packages(Order.class)
          .build();
    }

    @Bean
    public PlatformTransactionManager routingTransactionManager(
      LocalContainerEntityManagerFactoryBean
        routingEntityManagerFactory) {
        return new JpaTransactionManager(
          Objects.requireNonNull(
            routingEntityManagerFactory.getObject()));
    }
}
