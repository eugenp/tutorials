package com.baeldung.spring.datasources.topics;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
  basePackageClasses = Topic.class,
  entityManagerFactoryRef = "topicsEntityManagerFactory",
  transactionManagerRef = "topicsTransactionManager"
)
public class TopicsDatasourceConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.topics")
    public DataSourceProperties topicsDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.topics.configuration")
    public DataSource topicsDataSource() {
        return topicsDataSourceProperties()
          .initializeDataSourceBuilder()
          .build();
    }

    @Bean(name = "topicsEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean topicsEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        return builder
          .dataSource(topicsDataSource())
          .packages(Topic.class)
          .build();
    }

    @Bean(name = "topicsTransactionManager")
    public PlatformTransactionManager topicsTransactionManager(
      final @Qualifier("topicsEntityManagerFactory") LocalContainerEntityManagerFactoryBean topicsEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(topicsEntityManagerFactory.getObject()));
    }
}
