package com.baeldung.config;

import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.h2.tools.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@EnableJpaRepositories(basePackages = "com.baeldung.repositories")
// @PropertySource("persistence-h2.properties")
// @PropertySource("persistence-h2-c3p0.properties")
// @PropertySource("persistence-hsqldb.properties")
// @PropertySource("persistence-derby.properties")
//@PropertySource("persistence-sqlite.properties")
public class DbConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("driverClassName"));
        dataSource.setUrl(env.getProperty("url"));
        dataSource.setUsername(env.getProperty("user"));
        dataSource.setPassword(env.getProperty("password"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.baeldung.models" });
        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(additionalProperties());
        return em;
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        if (env.getProperty("hibernate.connection.driver_class") != null) {
            hibernateProperties.setProperty("hibernate.connection.driver_class", env.getProperty("hibernate.connection.driver_class"));
        }
        if (env.getProperty("hibernate.connection.url") != null) {
            hibernateProperties.setProperty("hibernate.connection.url", env.getProperty("hibernate.connection.url"));
        }
        if (env.getProperty("hibernate.hbm2ddl.auto") != null) {
            hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        }
        if (env.getProperty("hibernate.hbm2ddl.auto") != null) {
            hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        }
        if (env.getProperty("hibernate.show_sql") != null) {
            hibernateProperties.setProperty("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        }
        if (env.getProperty("hibernate.connection.provider_class") != null) {
            hibernateProperties.setProperty("hibernate.connection.provider_class", env.getProperty("hibernate.connection.provider_class"));
        }
        if (env.getProperty("hibernate.c3p0.initialPoolSize") != null) {
            hibernateProperties.setProperty("hibernate.c3p0.initialPoolSize", env.getProperty("hibernate.c3p0.initialPoolSize"));
        }
        if (env.getProperty("hibernate.c3p0.min_size") != null) {
            hibernateProperties.setProperty("hibernate.c3p0.min_size", env.getProperty("hibernate.c3p0.min_size"));
        }
        if (env.getProperty("hibernate.c3p0.max_size") != null) {
            hibernateProperties.setProperty("hibernate.c3p0.max_size", env.getProperty("hibernate.c3p0.max_size"));
        }
        if (env.getProperty("hibernate.c3p0.acquire_increment") != null) {
            hibernateProperties.setProperty("hibernate.c3p0.acquire_increment", env.getProperty("hibernate.c3p0.acquire_increment"));
        }
        if (env.getProperty("hibernate.c3p0.idle_test_period") != null) {
            hibernateProperties.setProperty("hibernate.c3p0.idle_test_period", env.getProperty("hibernate.c3p0.idle_test_period"));
        }
        if (env.getProperty("hibernate.c3p0.max_statements") != null) {
            hibernateProperties.setProperty("hibernate.c3p0.max_statements", env.getProperty("hibernate.c3p0.max_statements"));
        }
        if (env.getProperty("hibernate.c3p0.timeout") != null) {
            hibernateProperties.setProperty("hibernate.c3p0.timeout", env.getProperty("hibernate.c3p0.timeout"));
        }
        return hibernateProperties;
    }

}

@Configuration
@Profile("h2")
@PropertySource("persistence-h2.properties")
class H2Config {}

@Configuration
@Profile("hsqldb")
@PropertySource("persistence-hsqldb.properties")
class HsqldbConfig {}


@Configuration
@Profile("derby")
@PropertySource("persistence-derby.properties")
class DerbyConfig {}


@Configuration
@Profile("sqlite")
@PropertySource("persistence-sqlite.properties")
class SqliteConfig {}


@Configuration
@Profile("h2c3p0")
@PropertySource("persistence-h2-c3p0.properties")
class H2c3p0Config {}
