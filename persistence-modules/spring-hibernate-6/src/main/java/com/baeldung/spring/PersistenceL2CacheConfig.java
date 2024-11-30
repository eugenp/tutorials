package com.baeldung.spring;

import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.cache.jcache.ConfigSettings;
import org.hibernate.cfg.AvailableSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.google.common.base.Preconditions;

import jakarta.persistence.Cache;
import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:persistence-h2-cache.properties" })
@ComponentScan({ "com.baeldung.hibernate.cache" })
@EnableJpaRepositories(basePackages = { "com.baeldung.hibernate.cache.dao" })
public class PersistenceL2CacheConfig {

    private final Environment env;

    @Autowired
    public PersistenceL2CacheConfig(final Environment env) {
        this.env = env;
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty(AvailableSettings.HBM2DDL_AUTO, env.getProperty(AvailableSettings.HBM2DDL_AUTO));
        hibernateProperties.setProperty(AvailableSettings.DIALECT, env.getProperty(AvailableSettings.DIALECT));
        hibernateProperties.setProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE, env.getProperty(AvailableSettings.USE_SECOND_LEVEL_CACHE));
        hibernateProperties.setProperty(AvailableSettings.USE_QUERY_CACHE, env.getProperty(AvailableSettings.USE_QUERY_CACHE));
        hibernateProperties.setProperty(AvailableSettings.SHOW_SQL, env.getProperty(AvailableSettings.SHOW_SQL));
        hibernateProperties.setProperty(AvailableSettings.CACHE_REGION_FACTORY, env.getProperty(AvailableSettings.CACHE_REGION_FACTORY));

        hibernateProperties.put(AvailableSettings.GENERATE_STATISTICS, env.getProperty(AvailableSettings.GENERATE_STATISTICS));

        hibernateProperties.setProperty(ConfigSettings.CONFIG_URI, env.getProperty(ConfigSettings.CONFIG_URI));
        hibernateProperties.setProperty(ConfigSettings.PROVIDER, env.getProperty(ConfigSettings.PROVIDER));
        hibernateProperties.setProperty(ConfigSettings.MISSING_CACHE_STRATEGY, env.getProperty(ConfigSettings.MISSING_CACHE_STRATEGY));


        return hibernateProperties;
    }

    @Bean
    public Cache cache(EntityManagerFactory emf) {
        return emf.getCache();
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("jdbc.url")));
        dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("jdbc.user")));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(getPackagesToScan());

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    protected String[] getPackagesToScan() {
        return new String[] { "com.baeldung.hibernate.cache.model" };
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }
}