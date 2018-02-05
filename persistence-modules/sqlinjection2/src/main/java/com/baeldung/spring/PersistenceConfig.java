package com.baeldung.spring;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
@PropertySource({ "classpath:persistence-mysql.properties" })
@ComponentScan({ "com.baeldung.hibernate" })
public class PersistenceConfig {
    

//    @Autowired
//    private Environment env;
//
//    public PersistenceConfig() {
//        super();
//    }
//
//    @Bean
//    public LocalSessionFactoryBean sessionFactory() {
//        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
//        sessionFactory.setDataSource(restDataSource());
//        sessionFactory.setPackagesToScan(new String[] { "com.baeldung.hibernate" });
//        sessionFactory.setHibernateProperties(hibernateProperties());
//
//        return sessionFactory;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
//        emf.setDataSource(restDataSource());
//        emf.setPackagesToScan(new String[] { "com.baeldung.hibernate" });
//
//        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//        emf.setJpaVendorAdapter(vendorAdapter);
//        emf.setJpaProperties(hibernateProperties());
//
//        return emf;
//    }
//
//    @Bean
//    public DataSource restDataSource() {
//        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//        dataSource.setUrl(env.getProperty("jdbc.url"));
//        dataSource.setUsername(env.getProperty("jdbc.user"));
//        dataSource.setPassword(env.getProperty("jdbc.pass"));
//
//        return dataSource;
//    }
//
//    private final Properties hibernateProperties() {
//        final Properties hibernateProperties = new Properties();
//        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
//        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
//
//        hibernateProperties.setProperty("hibernate.show_sql", "true");
//
//        return hibernateProperties;
//    }

}