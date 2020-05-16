package com.baeldung.persistence.service.transactional;

import com.google.common.base.Preconditions;
import java.util.Properties;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
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
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.DefaultTransactionStatus;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
@EnableTransactionManagement
@PropertySource({ "classpath:persistence-h2.properties" })
@ComponentScan({ "com.baeldung.persistence","com.baeldung.jpa.dao" })
@EnableJpaRepositories(basePackages = "com.baeldung.jpa.dao")
public class PersistenceTransactionalTestConfig {

    public static class TransactionSynchronizationAdapterSpy extends TransactionSynchronizationAdapter {
        private int create, suspend;

        public int getSuspend() {
            return suspend;
        }

        public int getCreate() {
            return create;
        }

        public void create() {
           create++;
        }

        @Override
        public void suspend() {
            suspend++;
            super.suspend();
        }
    }


    public static class JpaTransactionManagerSpy extends JpaTransactionManager {
        @Override
        protected void prepareSynchronization(DefaultTransactionStatus status, TransactionDefinition definition) {
            super.prepareSynchronization(status, definition);
            if (status.isNewTransaction()) {
                if ( adapterSpyThreadLocal.get() == null ){
                    TransactionSynchronizationAdapterSpy spy = new TransactionSynchronizationAdapterSpy();
                    TransactionSynchronizationManager.registerSynchronization(spy);
                    adapterSpyThreadLocal.set(spy);
                }
                adapterSpyThreadLocal.get().create();
            }
        }
    }

    private static ThreadLocal<TransactionSynchronizationAdapterSpy> adapterSpyThreadLocal = new ThreadLocal<>();

    @Autowired
    private Environment env;

    public PersistenceTransactionalTestConfig() {
        super();
    }

    public static TransactionSynchronizationAdapterSpy getSpy(){
        if ( adapterSpyThreadLocal.get() == null )
            return new TransactionSynchronizationAdapterSpy();
        return adapterSpyThreadLocal.get();
    }

    public static void clearSpy(){
       adapterSpyThreadLocal.set(null);
    }

    // beans

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        final LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan(new String[] { "com.baeldung.persistence.model" });

        final JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());

        return em;
    }

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Preconditions.checkNotNull(env.getProperty("jdbc.driverClassName")));
        dataSource.setUrl(Preconditions.checkNotNull(env.getProperty("jdbc.url")));
        dataSource.setUsername(Preconditions.checkNotNull(env.getProperty("jdbc.user")));
        dataSource.setPassword(Preconditions.checkNotNull(env.getProperty("jdbc.pass")));

        return dataSource;
    }



    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManagerSpy transactionManager = new JpaTransactionManagerSpy();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    final Properties additionalProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));
        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", "false");
        return hibernateProperties;
    }


    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager){
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        template.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        return template;
    }


}