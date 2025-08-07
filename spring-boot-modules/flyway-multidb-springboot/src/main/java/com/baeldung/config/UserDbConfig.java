package com.baeldung.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManagerFactory;
import org.flywaydb.core.Flyway;
import java.util.Map;
import org.springframework.boot.jdbc.DataSourceBuilder;
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

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.baeldung.repository.user",
        entityManagerFactoryRef = "userEntityManagerFactory",
        transactionManagerRef = "userTransactionManager"
)
public class UserDbConfig {

    @Bean
    @Primary
    public DataSource userDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:userdb")
                .username("sa")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Bean
    @Primary
    public LocalContainerEntityManagerFactoryBean userEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {

        return builder
                .dataSource(userDataSource())
                .packages("com.baeldung.entity")
                .persistenceUnit("userPU")
                .properties(Map.of("hibernate.hbm2ddl.auto", "none"))
                .build();
    }

    @Bean
    @Primary
    public PlatformTransactionManager userTransactionManager(
            EntityManagerFactory userEntityManagerFactory) {

        return new JpaTransactionManager(userEntityManagerFactory);
    }

    @PostConstruct
    public void migrateUserDb() {
        Flyway.configure()
                .dataSource(userDataSource())
                .locations("classpath:db/migration/userdb")
                .load()
                .migrate();
    }
}
