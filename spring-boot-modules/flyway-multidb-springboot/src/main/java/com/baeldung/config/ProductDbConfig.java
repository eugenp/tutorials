package com.baeldung.config;

import jakarta.annotation.PostConstruct;
import javax.sql.DataSource;
import java.util.Map;
import jakarta.persistence.EntityManagerFactory;
import org.flywaydb.core.Flyway;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.*;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.baeldung.repository.product",
        entityManagerFactoryRef = "productEntityManagerFactory",
        transactionManagerRef = "productTransactionManager"
)
public class ProductDbConfig {

    @Bean
    public DataSource productDataSource() {
        return DataSourceBuilder.create()
                .url("jdbc:h2:mem:productdb")
                .username("sa")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean productEntityManagerFactory(
            EntityManagerFactoryBuilder builder) {

        return builder
                .dataSource(productDataSource())
                .packages("com.baeldung.entity")
                .persistenceUnit("productPU")
                .properties(Map.of("hibernate.hbm2ddl.auto", "create"))
                .build();
    }

    @Bean
    public PlatformTransactionManager productTransactionManager(
            EntityManagerFactory productEntityManagerFactory) {

        return new JpaTransactionManager(productEntityManagerFactory);
    }

    @PostConstruct
    public void migrateProductDb() {
        Flyway.configure()
                .dataSource(productDataSource())
                .locations("classpath:db/migration/productdb")
                .load()
                .migrate();
    }
}
