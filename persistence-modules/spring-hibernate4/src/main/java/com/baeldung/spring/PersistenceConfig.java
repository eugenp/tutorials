package com.baeldung.spring;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.baeldung.hibernate.audit.AuditorAwareImpl;
import com.baeldung.persistence.dao.IBarAuditableDao;
import com.baeldung.persistence.dao.IBarDao;
import com.baeldung.persistence.dao.IFooAuditableDao;
import com.baeldung.persistence.dao.IFooDao;
import com.baeldung.persistence.dao.impl.BarAuditableDao;
import com.baeldung.persistence.dao.impl.BarDao;
import com.baeldung.persistence.dao.impl.BarJpaDao;
import com.baeldung.persistence.dao.impl.FooAuditableDao;
import com.baeldung.persistence.dao.impl.FooDao;
import com.baeldung.persistence.service.IBarAuditableService;
import com.baeldung.persistence.service.IBarService;
import com.baeldung.persistence.service.IFooAuditableService;
import com.baeldung.persistence.service.IFooService;
import com.baeldung.persistence.service.impl.BarAuditableService;
import com.baeldung.persistence.service.impl.BarJpaService;
import com.baeldung.persistence.service.impl.BarSpringDataJpaService;
import com.baeldung.persistence.service.impl.FooAuditableService;
import com.baeldung.persistence.service.impl.FooService;
import com.google.common.base.Preconditions;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = { "com.baeldung.persistence" }, transactionManagerRef = "jpaTransactionManager")
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@PropertySource({ "classpath:persistence-h2.properties" })
@ComponentScan({ "com.baeldung.persistence" })
public class PersistenceConfig {

    @Autowired
    private Environment env;

    public PersistenceConfig() {
        super();
    }

    @Bean("auditorProvider")
    public AuditorAware<String> auditorProvider() {
        return new AuditorAwareImpl();
    }
    
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        final LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(restDataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.baeldung.persistence.model" });
        sessionFactory.setHibernateProperties(hibernateProperties());

        return sessionFactory;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(restDataSource());
        emf.setPackagesToScan(new String[] { "com.baeldung.persistence.model" });

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(hibernateProperties());

        return emf;
    }

    @Bean
    public DataSource restDataSource() {
        final BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("jdbc.url")));
        dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("jdbc.user")));
        dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("jdbc.pass")));

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager hibernateTransactionManager() {
        final HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PlatformTransactionManager jpaTransactionManager() {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean
    public IBarService barJpaService() {
        return new BarJpaService();
    }

    @Bean
    public IBarService barSpringDataJpaService() {
        return new BarSpringDataJpaService();
    }

    @Bean
    public IFooService fooHibernateService() {
        return new FooService();
    }

    @Bean
    public IBarAuditableService barHibernateAuditableService() {
        return new BarAuditableService();
    }

    @Bean
    public IFooAuditableService fooHibernateAuditableService() {
        return new FooAuditableService();
    }

    @Bean
    public IBarDao barJpaDao() {
        return new BarJpaDao();
    }

    @Bean
    public IBarDao barHibernateDao() {
        return new BarDao();
    }

    @Bean
    public IBarAuditableDao barHibernateAuditableDao() {
        return new BarAuditableDao();
    }

    @Bean
    public IFooDao fooHibernateDao() {
        return new FooDao();
    }

    @Bean
    public IFooAuditableDao fooHibernateAuditableDao() {
        return new FooAuditableDao();
    }

    private final Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));

        hibernateProperties.setProperty("hibernate.show_sql", "true");
        // hibernateProperties.setProperty("hibernate.format_sql", "true");
        // hibernateProperties.setProperty("hibernate.globally_quoted_identifiers", "true");

        // Envers properties
        hibernateProperties.setProperty("org.hibernate.envers.audit_table_suffix", env.getProperty("envers.audit_table_suffix"));

        return hibernateProperties;
    }

}