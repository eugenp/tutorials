package com.baeldung.readonlytransactions.h2;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.util.Properties;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
public class Config {

    @Bean("h2DataSource")
    public DataSource dataSource() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:h2:mem:mydb;MODE=LEGACY");
        config.setUsername("sa");
        config.setPassword("");
        config.setDriverClassName("org.h2.Driver");
        return new HikariDataSource(config);
    }

    @Bean("h2EntityManagerFactory")
    public EntityManagerFactory entityManagerFactory(@Qualifier("h2DataSource") DataSource dataSource) {
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(false);

        LocalContainerEntityManagerFactoryBean managerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        managerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        managerFactoryBean.setPackagesToScan(Config.class.getPackage()
            .getName());
        managerFactoryBean.setDataSource(dataSource);

        Properties properties = new Properties();

        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");

        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("hibernate.format_sql", "true");

        managerFactoryBean.setJpaProperties(properties);
        managerFactoryBean.afterPropertiesSet();

        return managerFactoryBean.getObject();
    }
}
