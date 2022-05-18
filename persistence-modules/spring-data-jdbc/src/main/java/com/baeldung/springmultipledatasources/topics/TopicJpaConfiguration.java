package com.baeldung.springmultipledatasources.topics;

import org.springframework.beans.factory.annotation.Qualifier;
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
public class TopicJpaConfiguration {

    @Bean
    public LocalContainerEntityManagerFactoryBean topicsEntityManagerFactory(
      @Qualifier("topicsDataSource") DataSource dataSource,
      EntityManagerFactoryBuilder builder
    ) {
        return builder
          .dataSource(dataSource)
          .packages(Topic.class)
          .build();
    }

    @Bean
    public PlatformTransactionManager topicsTransactionManager(
      @Qualifier("topicsEntityManagerFactory") LocalContainerEntityManagerFactoryBean topicsEntityManagerFactory) {
        return new JpaTransactionManager(Objects.requireNonNull(topicsEntityManagerFactory.getObject()));
    }
}
