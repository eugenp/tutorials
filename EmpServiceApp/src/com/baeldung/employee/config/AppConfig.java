package com.baeldung.employee.config;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.TransactionManager;

@Configuration
@EnableJpaRepositories(basePackages = "com.baeldung.employee.data", entityManagerFactoryRef = "emf")
@ComponentScan(basePackages = "com.baeldung.employee")
public class AppConfig {

    @Bean
    public DataSource h2DataSource() {

        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder.setType(EmbeddedDatabaseType.H2)
            .addScript("createEmployeeTable.sql")
            .build();
        return db;

    }

    @Bean
    public ModelMapper mapper() {

        return new ModelMapper();
    }

    /*    @Bean
    public ObjectMapper objectMapper() {
    
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, true);
    
        return mapper;
    }
    */

    @Bean(name = "emf")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();

        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setJpaVendorAdapter(vendorAdapter);
        factory.setPackagesToScan("com.baeldung.employee");
        factory.setDataSource(h2DataSource());
        // factory.setJpaProperties(jpaProperties());

        return factory;
    }

    @Bean
    public TransactionManager jpaTransactionManager(EntityManagerFactory emf) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

}
