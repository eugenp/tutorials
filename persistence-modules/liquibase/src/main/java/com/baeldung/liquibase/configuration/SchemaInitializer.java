package com.baeldung.liquibase.configuration;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SchemaInitializer implements BeanPostProcessor {

    @Value("${spring.liquibase.default-schema}")
    private String schemaName;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource) {
            DataSource dataSource = (DataSource) bean;
            try {
                Connection conn = dataSource.getConnection();
                Statement statement = conn.createStatement();
                statement.execute(String.format("CREATE SCHEMA IF NOT EXISTS %s", schemaName));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return bean;
    }
}