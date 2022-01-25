package com.baeldung.spring.datasources.todos;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
  basePackageClasses = Todo.class,
  entityManagerFactoryRef = "todosEntityManagerFactory",
  transactionManagerRef = "todosTransactionManager"
)
public class TodoDatasourceConfiguration {

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.todos")
    public DataSourceProperties todosDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("spring.datasource.todos.configuration")
    public DataSource todosDataSource() {
        return todosDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }

    @Primary
    @Bean(name = "todosEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean todosEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
          .dataSource(todosDataSource())
          .packages(Todo.class)
          .build();
    }

    @Primary
    @Bean(name = "todosTransactionManager")
    public PlatformTransactionManager todosTransactionManager(
      final @Qualifier("todosEntityManagerFactory") LocalContainerEntityManagerFactoryBean todosEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(todosEntityManagerFactory.getObject()));
    }
}
