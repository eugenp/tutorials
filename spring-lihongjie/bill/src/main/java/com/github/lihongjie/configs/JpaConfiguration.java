//package com.github.lihongjie.configs;
//
//import org.hibernate.ejb.HibernatePersistence;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.jdbc.datasource.DriverManagerDataSource;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//
//import javax.annotation.Resource;
//import javax.sql.DataSource;
//import java.util.Properties;
//
//@Configuration
//public class JpaConfiguration {
//
//    private static final String PROPERTY_NAME_DATABASE_DRIVER = "spring.datasource.driver-class-name";
//    private static final String PROPERTY_NAME_DATABASE_PASSWORD = "spring.datasource.password";
//    private static final String PROPERTY_NAME_DATABASE_URL = "spring.datasource.url";
//    private static final String PROPERTY_NAME_DATABASE_USERNAME = "spring.datasource.username";
//
//    private static final String PROPERTY_NAME_HIBERNATE_DIALECT = "hibernate.dialect";
//    private static final String PROPERTY_NAME_HIBERNATE_SHOW_SQL = "hibernate.show_sql";
//    private static final String PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN = "entitymanager.packages.to.scan";
//    private static final String PROPERTY_NAME_HBM2DDL = "hibernate.hbm2ddl.auto";
//
//    @Resource
//    private Environment env;
//
//    @Bean
//    public DataSource dataSource() {
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//        dataSource.setDriverClassName(env.getRequiredProperty(PROPERTY_NAME_DATABASE_DRIVER));
//        dataSource.setUrl(env.getRequiredProperty(PROPERTY_NAME_DATABASE_URL));
//        dataSource.setUsername(env.getRequiredProperty(PROPERTY_NAME_DATABASE_USERNAME));
//        dataSource.setPassword(env.getRequiredProperty(PROPERTY_NAME_DATABASE_PASSWORD));
//
//        return dataSource;
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
//        entityManagerFactoryBean.setDataSource(dataSource());
//        entityManagerFactoryBean.setPersistenceProviderClass(HibernatePersistence.class);
//        entityManagerFactoryBean.setPackagesToScan(env.getRequiredProperty(PROPERTY_NAME_ENTITYMANAGER_PACKAGES_TO_SCAN));
//
//        entityManagerFactoryBean.setJpaProperties(hibProperties());
//
//        return entityManagerFactoryBean;
//    }
//
//    @Bean
//    public JpaTransactionManager transactionManager() {
//        JpaTransactionManager transactionManager = new JpaTransactionManager();
//        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
//        return transactionManager;
//    }
//
//    private Properties hibProperties() {
//        Properties properties = new Properties();
//        properties.put(PROPERTY_NAME_HIBERNATE_DIALECT, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_DIALECT));
//        properties.put(PROPERTY_NAME_HIBERNATE_SHOW_SQL, env.getRequiredProperty(PROPERTY_NAME_HIBERNATE_SHOW_SQL));
//        properties.put(PROPERTY_NAME_HBM2DDL, env.getRequiredProperty(PROPERTY_NAME_HBM2DDL));
//        return properties;
//    }
//}
