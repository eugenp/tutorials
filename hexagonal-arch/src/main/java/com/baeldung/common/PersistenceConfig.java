package com.baeldung.common;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@EnableJpaAuditing
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories("com.baeldung.*")
public class PersistenceConfig {

    @Autowired
    private Environment env;

    public PersistenceConfig() {
        super();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();
        entityManager.setDataSource(dataSource());
        entityManager.setPackagesToScan("com.baeldung.*");
        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManager.setJpaVendorAdapter(vendorAdapter);
        entityManager.setJpaProperties(additionalProperties());
        return entityManager;
    }

    @Bean
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(env.getProperty("spring.datasource.hikari.jdbc-url"));
        config.setUsername(env.getProperty("spring.datasource.hikari.username"));
        config.setPassword(env.getProperty("spring.datasource.hikari.password"));
        config.setDriverClassName(env.getProperty("spring.datasource.hikari.driver-class-name"));
        return new HikariDataSource(config);
    }

    /**
     * Additional properties.
     *
     * @return the properties
     */
    private Properties additionalProperties() {
            final Properties hibernateProperties = new Properties();
            hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
            hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
            hibernateProperties.setProperty("spring.data.jpa.repositories.enabled",
                            env.getProperty("spring.data.jpa.repositories.enabled"));
            hibernateProperties.setProperty("hibernate.format_sql", env.getProperty("hibernate.format_sql"));
            return hibernateProperties;
    }
    
 }