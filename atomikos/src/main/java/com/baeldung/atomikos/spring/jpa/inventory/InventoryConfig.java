package com.baeldung.atomikos.spring.jpa.inventory;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.atomikos.jdbc.AtomikosDataSourceBean;

@Configuration
@EnableJpaRepositories(basePackages = "com.baeldung.atomikos.spring.jpa.inventory", entityManagerFactoryRef = "inventoryEntityManager", transactionManagerRef = "transactionManager")
public class InventoryConfig {

    @Bean(initMethod = "init", destroyMethod = "close")
    public AtomikosDataSourceBean inventoryDataSource() {
        AtomikosDataSourceBean dataSource = new AtomikosDataSourceBean();
        dataSource.setLocalTransactionMode(true);
        dataSource.setUniqueResourceName("db1");
        dataSource.setXaDataSourceClassName("org.apache.derby.jdbc.EmbeddedXADataSource");
        Properties xaProperties = new Properties();
        xaProperties.put("databaseName", "db1");
        xaProperties.put("createDatabase", "create");
        dataSource.setXaProperties(xaProperties);
        dataSource.setPoolSize(10);
        return dataSource;
    }

    @Bean
    public EntityManagerFactory inventoryEntityManager() {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.baeldung.atomikos.spring.jpa.inventory");
        factory.setDataSource(inventoryDataSource());
        Properties jpaProperties = new Properties();
        //jpaProperties.put("hibernate.show_sql", "true");
        //jpaProperties.put("hibernate.format_sql", "true");
        jpaProperties.put("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
        jpaProperties.put("hibernate.current_session_context_class", "jta");
        jpaProperties.put("javax.persistence.transactionType", "jta");
        jpaProperties.put("hibernate.transaction.manager_lookup_class", "com.atomikos.icatch.jta.hibernate3.TransactionManagerLookup");
        jpaProperties.put("hibernate.hbm2ddl.auto", "create-drop");
        factory.setJpaProperties(jpaProperties);
        factory.afterPropertiesSet();
        return factory.getObject();
    }

}