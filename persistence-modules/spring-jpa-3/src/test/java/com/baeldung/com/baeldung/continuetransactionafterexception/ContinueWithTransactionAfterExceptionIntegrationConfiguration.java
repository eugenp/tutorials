package com.baeldung.com.baeldung.continuetransactionafterexception;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@PropertySource("continuetransactionafterexception/test.properties")
public class ContinueWithTransactionAfterExceptionIntegrationConfiguration {

    @Bean
    public DataSource dataSource() {
        EmbeddedDatabaseBuilder dbBuilder = new EmbeddedDatabaseBuilder();
        return dbBuilder.setType(EmbeddedDatabaseType.H2)
            .build();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Value("${hibernate.hbm2ddl.auto}") String hbm2ddlType, @Value("${hibernate.dialect}") String dialect, @Value("${hibernate.show_sql}") boolean showSql) {
        LocalContainerEntityManagerFactoryBean result = new LocalContainerEntityManagerFactoryBean();

        result.setDataSource(dataSource());
        result.setPackagesToScan("com.baeldung.continuetransactionafterexception.model");
        result.setJpaVendorAdapter(jpaVendorAdapter());

        Map<String, Object> jpaProperties = new HashMap<>();
        jpaProperties.put("hibernate.hbm2ddl.auto", hbm2ddlType);
        jpaProperties.put("hibernate.dialect", dialect);
        jpaProperties.put("hibernate.show_sql", showSql);
        result.setJpaPropertyMap(jpaProperties);

        return result;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setNestedTransactionAllowed(true);

        return transactionManager;
    }

    public JpaVendorAdapter jpaVendorAdapter() {
        return new HibernateJpaVendorAdapter();
    }
}
